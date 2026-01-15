package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.invoice.InitAsyncInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportPackage;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportStatus;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceMetadata;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackageMetadata;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackagePart;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionResponse;
import pl.akmf.ksef.sdk.client.model.util.ZipInputStreamWithSize;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.FilesUtil;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;
import pl.akmf.ksef.sdk.utls.model.ExportTask;
import pl.akmf.ksef.sdk.utls.model.PackageProcessingResult;
import pl.akmf.ksef.sdk.utls.model.TimeWindows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class IncrementalInvoiceRetrieveIntegrationTest extends BaseIntegrationTest {
    private static final int DEFAULT_NUMBER_OF_PARTS = 2;
    private static final int DEFAULT_INVOICES_COUNT = 15;
    private static final String PATH_SAMPLE_INVOICE_TEMPLATE_XML = "/xml/invoices/sample/invoice-template_v3.xml";

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    // Wzorcowy mechanizm przyrostowego pobierania faktur z KSeF z obsługą punktu kontynuacji (HWM shift) oraz deduplikacji.
    // Kroki:
    // 1. Przygotowanie przykładowej paczki faktur poprzez sesję wsadową (batch session)
    // 2. Okienkowy eksport faktur (windowing) z obsługą punktu kontynuacji (HWM), limitów i retry po HTTP 429
    //    - RestrictToPermanentStorageHwmDate = true zapewnia tryb snapshot i stabilne pole PermanentStorageHwmDate
    // 3. Pobranie i odszyfrowanie pakietów eksportu przy użyciu CryptographyService
    // 4. De-duplikacja faktur na podstawie pliku _metadata.json znajdującego się w paczkach (HWM znacznie minimalizuje duplikaty dzięki trybowi snapshot, ale nie eliminuje ich całkowicie!)
    @Test
    void invoiceIncrementalRetrievalWithHwmShift() throws JAXBException, IOException, ApiException {
        //1: Generowanie faktur w celu uzyskania danych do eksportu
        OffsetDateTime batchCreationStart = OffsetDateTime.now();
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        List<String> sessionInvoices = openBatchSessionAndSendInvoicesPartsStream(contextNip, accessToken, DEFAULT_INVOICES_COUNT, DEFAULT_NUMBER_OF_PARTS);

        OffsetDateTime batchCreationCompleted = OffsetDateTime.now();

        // Kolekcje do deduplikacji oraz weryfikacji rezultatów
        Map<String, InvoiceMetadata> uniqueInvoices = new HashMap<>();
        AtomicInteger totalMetadataEntries = new AtomicInteger();
        AtomicInteger hmwShiftCount = new AtomicInteger();

        //Słownik do śledzenia punku kontynuacji dla każdego subjectType
        Map<InvoiceQuerySubjectType, OffsetDateTime> continuationPoints = new HashMap<>();

        //2 Budowanie listy okien czasowych
        List<TimeWindows> timeWindows = buildIncrementalWindows(batchCreationStart, batchCreationCompleted);

        //tworzenie planu exportu - krótki(okno czasowe, typ podmiotu)
        List<InvoiceQuerySubjectType> subjectTypes = Arrays.stream(InvoiceQuerySubjectType.values())
                .filter(x -> x != InvoiceQuerySubjectType.SUBJECTAUTHORIZED)
                .toList();

        List<ExportTask> exportTasks = timeWindows.stream()
                .flatMap(window -> subjectTypes.stream()
                        .map(subjectType -> new ExportTask(window.getFrom(), window.getTo(), subjectType)))
                .sorted(Comparator.comparing(ExportTask::getFrom)
                        .thenComparing(ExportTask::getSubjectType))
                .toList();

        await().atMost(125, SECONDS)
                .pollInterval(120, SECONDS)
                .until(() -> true);

        exportTasks.forEach(task -> {
            EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();
            OffsetDateTime effectiveFrom = getEffectiveStartDate(continuationPoints, task.getSubjectType(), task.getFrom());
            String operationReferenceNumber = initiateInvoiceExportAsync(effectiveFrom, task.getTo(), task.getSubjectType(), accessToken, encryptionData.encryptionInfo());

            InvoiceExportStatus status = waitForExportCompletion(operationReferenceNumber, accessToken);

            if (status.getPackageParts().getInvoiceCount() == 0) {
                return;
            }
            // Dodawanie unikalnych faktur - deduplikacja           `
            PackageProcessingResult packageProcessingResult = downloadAndProcessPackageAsync(status, encryptionData);
            totalMetadataEntries.addAndGet(packageProcessingResult.getInvoiceMetadataList().size());

            packageProcessingResult.getInvoiceMetadataList()
                    .stream()
                    .distinct()
                    .forEach(summary -> uniqueInvoices.put(summary.getKsefNumber(), summary));

            // Obsługa flagi isTruncated i HWM - aktualizacja punktu kontynuacji, jeśli paczka została obcięta
            // lub ma stabilny PermanentStorageHwmDate. Zakres okna pozostaje bez zmian, aktualizowany jest tylko
            // punkt kontynuacji (LastPermanentStorageDate lub PermanentStorageHwmDate), aby następne okno zaczynało się
            // od miejsca, gdzie poprzednie zostało przerwane, zapewniając ciągłość pobierania bez pominiętych faktur.
            OffsetDateTime previousContinuationPoint = continuationPoints.get(task.getSubjectType());
            updateContinuationPointIfNeeded(continuationPoints, task.getSubjectType(), status.getPackageParts());
            OffsetDateTime newContinuationPoint = continuationPoints.get(task.getSubjectType());

            // Zliczanie przesunięć HWM
            if (newContinuationPoint != previousContinuationPoint) {
                hmwShiftCount.getAndIncrement();
            }
        });

        // Weryfikacja, że wszystkie utworzone faktury zostały znalezione w eksporcie
        Assertions.assertTrue(sameElements(sessionInvoices, uniqueInvoices.keySet()));

        // Weryfikacja, że metadane zawierają dokładnie tyle wpisów, co unikalne faktury
        Assertions.assertEquals(totalMetadataEntries.get(), sessionInvoices.size());

        // Weryfikacja, że mechanizm HWM shift działał (punkt kontynuacji był aktualizowany)
        Assertions.assertTrue(hmwShiftCount.get() > 0, "Oczekiwano co najmniej jednego przesunięcia punktu kontynuacji przez HWM");
    }

    // Przyrostowe pobierania faktur z KSeF prezentujące obsługę deduplikacji.
    // Kroki:
    // 1. Przygotowanie przykładowej paczki faktur poprzez sesję wsadową (batch session)
    // 2. Okienkowy eksport faktur (windowing) z obsługą limitów i retry po HTTP 429
    //    - RestrictToPermanentStorageHwmDate = true zapewnia tryb snapshot i stabilne pole PermanentStorageHwmDate
    // 3. Pobranie i odszyfrowanie pakietów eksportu przy użyciu CryptographyService
    // 4. De-duplikacja faktur na podstawie pliku _metadata.json znajdującego się w paczkach (znacznie ograniczona dzięki trybowi snapshot HWM)
    @Test
    void invoiceIncrementalRetrievalWithDeduplication() throws JAXBException, IOException, ApiException {
        //1: Generowanie faktur w celu uzyskania danych do eksportu
        OffsetDateTime batchCreationStart = OffsetDateTime.now();
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        List<String> sessionInvoices = openBatchSessionAndSendInvoicesPartsStream(contextNip, accessToken, DEFAULT_INVOICES_COUNT, DEFAULT_NUMBER_OF_PARTS);

        OffsetDateTime batchCreationCompleted = OffsetDateTime.now();

        // Kolekcje do deduplikacji oraz weryfikacji rezultatów
        Map<String, InvoiceMetadata> uniqueInvoices = new HashMap<>();
        AtomicBoolean hasDuplicates = new AtomicBoolean(false);
        AtomicInteger totalMetadataEntries = new AtomicInteger();

        //2 Budowanie listy okien czasowych
        List<TimeWindows> timeWindows = buildIncrementalWindows(batchCreationStart, batchCreationCompleted);

        //tworzenie planu exportu - krótki(okno czasowe, typ podmiotu)
        List<InvoiceQuerySubjectType> subjectTypes = Arrays.stream(InvoiceQuerySubjectType.values())
                .filter(x -> x != InvoiceQuerySubjectType.SUBJECTAUTHORIZED)
                .toList();

        List<ExportTask> exportTasks = timeWindows.stream()
                .flatMap(window -> subjectTypes.stream()
                        .map(subjectType -> new ExportTask(window.getFrom(), window.getTo(), subjectType)))
                .sorted(Comparator.comparing(ExportTask::getFrom)
                        .thenComparing(ExportTask::getSubjectType))
                .toList();

        await().atMost(125, SECONDS)
                .pollInterval(120, SECONDS)
                .until(() -> true);

        exportTasks.forEach(task -> {
            //UWAGA:
            // W tym teście CELOWO używamy oryginalnych granic okien, ignorując HWM/continuation points,
            // aby wymusić nakładające się zapytania i duplikaty, dla scenariuszy produkcyjnych zalecane wzorowanie się na teście invoiceIncrementalRetrievalWithHwmShift
            EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();
            String operationReferenceNumber = initiateInvoiceExportAsync(task.getFrom(), task.getTo(), task.getSubjectType(), accessToken, encryptionData.encryptionInfo());

            InvoiceExportStatus status = waitForExportCompletion(operationReferenceNumber, accessToken);

            if (status.getPackageParts().getInvoiceCount() == 0) {
                return;
            }
            PackageProcessingResult packageProcessingResult = downloadAndProcessPackageAsync(status, encryptionData);
            totalMetadataEntries.addAndGet(packageProcessingResult.getInvoiceMetadataList().size());

            // Dodawanie unikalnych faktur i wykrywanie duplikatów
            hasDuplicates.set(packageProcessingResult.getInvoiceMetadataList()
                    .stream()
                    .anyMatch(summary -> uniqueInvoices.containsKey(summary.getKsefNumber())));

            packageProcessingResult.getInvoiceMetadataList()
                    .stream()
                    .distinct()
                    .forEach(summary -> uniqueInvoices.put(summary.getKsefNumber(), summary));
        });

        // Weryfikacja, że wszystkie utworzone faktury zostały znalezione w eksporcie
        Assertions.assertTrue(sameElements(sessionInvoices, uniqueInvoices.keySet()));

        // Weryfikacja, że metadane zawierają więcej wpisów niż unikalne faktury (przez duplikaty)
        Assertions.assertTrue(totalMetadataEntries.get() > uniqueInvoices.size(),
                "Metadane powinny zawierać więcej niż " + uniqueInvoices.size() + " wpisów przez duplikaty, znaleziono: " + totalMetadataEntries.get());

        // Weryfikacja, że deduplikacja faktycznie była potrzebna (przez nakładające się okna czasowe)
        Assertions.assertTrue(hasDuplicates.get());
    }

    private InvoiceExportStatus waitForExportCompletion(String operationReferenceNumber, String accessToken) {
        AtomicReference<InvoiceExportStatus> status = new AtomicReference<>();
        await().atMost(20, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    status.set(ksefClient.checkStatusAsyncQueryInvoice(operationReferenceNumber, accessToken));
                    if (status.get().getStatus().getCode() > 400) {
                        Assertions.fail("Unexpected status code " + status.get().getStatus().getCode() + " "
                                        + status.get().getStatus().getDescription());
                    }
                    return status.get().getStatus().getCode().equals(200);
                });

        return status.get();
    }

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken, int expectedInvoice) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                   statusResponse.getSuccessfulInvoiceCount() != null &&
                   statusResponse.getSuccessfulInvoiceCount() == expectedInvoice;
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private String initiateInvoiceExportAsync(OffsetDateTime windowFrom,
                                              OffsetDateTime windowTo, InvoiceQuerySubjectType subjectType,
                                              String accessToken, EncryptionInfo encryptionInfo) {
        InvoiceExportFilters filters = new InvoiceExportFilters();
        filters.setSubjectType(subjectType);
        filters.setDateRange(new InvoiceQueryDateRange(
                InvoiceQueryDateType.PERMANENTSTORAGE,
                windowFrom,
                windowTo,
                true));

        InvoiceExportRequest request = new InvoiceExportRequest();
        request.setFilters(filters);
        request.setEncryption(encryptionInfo);

        try {
            InitAsyncInvoicesQueryResponse response = ksefClient.initAsyncQueryInvoice(request, accessToken);
            return response.getReferenceNumber();
        } catch (ApiException exception) {
            throw new SystemKSeFSDKException(exception.getMessage(), exception);
        }
    }

    private List<String> openBatchSessionAndSendInvoicesPartsStream(String context, String accessToken,
                                                                    int invoicesCount,
                                                                    int invoicesPartCount) throws IOException, ApiException {
        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(invoicesCount, context, invoice);

        ZipInputStreamWithSize zipInputStreamWithSize = FilesUtil.createZipInputStream(invoicesInMemory);
        InputStream zipInputStream = zipInputStreamWithSize.getInputStream();
        int zipLength = zipInputStreamWithSize.getZipLength();

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipInputStream);
        zipInputStream.reset();

        List<BatchPartStreamSendingInfo> encryptedStreamParts = FilesUtil.splitAndEncryptZipStream(zipInputStream, invoicesPartCount, zipLength, encryptionData.cipherKey(),
                encryptionData.cipherIv(), defaultCryptographyService);

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequestForStream(zipMetadata, encryptedStreamParts, encryptionData);

        OpenBatchSessionResponse openBatchSessionResponse = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openBatchSessionResponse.getReferenceNumber());

        ksefClient.sendBatchPartsWithStream(openBatchSessionResponse, encryptedStreamParts);

        ksefClient.closeBatchSession(openBatchSessionResponse.getReferenceNumber(), accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(openBatchSessionResponse.getReferenceNumber(), accessToken, DEFAULT_INVOICES_COUNT));

        //check if all invoices have been stored permanently
        AtomicReference<List<String>> ksefNumberList = new AtomicReference<>();
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> {
                    List<String> ksefNumbers =
                            ksefClient.getSessionInvoices(openBatchSessionResponse.getReferenceNumber(), null, 100, accessToken)
                                    .getInvoices()
                                    .stream()
                                    .filter(e -> Objects.nonNull(e.getPermanentStorageDate()))
                                    .map(SessionInvoiceStatusResponse::getKsefNumber)
                                    .toList();
                    if (ksefNumbers.size() == DEFAULT_INVOICES_COUNT) {
                        ksefNumberList.set(ksefNumbers);
                        return true;
                    }

                    return false;
                });

        return ksefNumberList.get();
    }

    private OpenBatchSessionRequest buildOpenBatchSessionRequestForStream(FileMetadata zipMetadata,
                                                                          List<BatchPartStreamSendingInfo> encryptedZipParts,
                                                                          EncryptionData encryptionData) {
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA());

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            BatchPartStreamSendingInfo part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1,
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        return builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();
    }

    private PackageProcessingResult downloadAndProcessPackageAsync(InvoiceExportStatus invoiceExportStatus,
                                                                   EncryptionData encryptionData) {
        try {
            List<InvoicePackagePart> parts = invoiceExportStatus.getPackageParts().getParts();
            byte[] mergedZip = FilesUtil.mergeZipParts(
                    encryptionData,
                    parts,
                    part -> ksefClient.downloadPackagePart(part),
                    (encryptedPackagePart, key, iv) -> defaultCryptographyService.decryptBytesWithAes256(encryptedPackagePart, key, iv)
            );
            Map<String, String> downloadedFiles = FilesUtil.unzip(mergedZip);

            String metadataJson = downloadedFiles.keySet()
                    .stream()
                    .filter(fileName -> fileName.endsWith(".json"))
                    .findFirst()
                    .map(downloadedFiles::get)
                    .orElse(null);
            InvoicePackageMetadata invoicePackageMetadata = objectMapper.readValue(metadataJson, InvoicePackageMetadata.class);

            return new PackageProcessingResult(invoicePackageMetadata.getInvoices(), downloadedFiles);
        } catch (IOException exception) {
            throw new SystemKSeFSDKException(exception.getMessage(), exception);
        }
    }

    private List<TimeWindows> buildIncrementalWindows(OffsetDateTime batchCreationStart, OffsetDateTime batchCreationCompleted) {
        List<TimeWindows> timeWindows = new ArrayList<>();

        // Celowe przygotowanie okien, które mają duże pokrycie w celu zasymulowania deduplikacji.
        OffsetDateTime firstWindowStart = batchCreationStart.minusMinutes(10);
        OffsetDateTime firstWindowsStop = batchCreationCompleted.plusMinutes(5);
        timeWindows.add(new TimeWindows(firstWindowStart, firstWindowsStop));

        OffsetDateTime secondWindowsStop = batchCreationCompleted.plusMinutes(10);
        timeWindows.add(new TimeWindows(batchCreationStart, secondWindowsStop));

        return timeWindows;
    }

    /**
     * Zwraca efektywną datę rozpoczęcia eksportu, uwzględniając punkt kontynuacji dla obciętych paczek.
     * Jeśli poprzednia paczka dla danego SubjectType została obcięta (IsTruncated=true),
     * wykorzystywane jest LastPermanentStorageDate lub PermanentStorageHwmDate z trybu snapshot (RestrictToPermanentStorageHwmDate=true)
     * w celu zapewnienia ciągłości pobierania bez pominięcia faktur.
     */
    private static OffsetDateTime getEffectiveStartDate(Map<InvoiceQuerySubjectType, OffsetDateTime> continuationPoints,
                                                        InvoiceQuerySubjectType subjectType,
                                                        OffsetDateTime windowFrom) {
        OffsetDateTime continuationPoint = continuationPoints.get(subjectType);
        if (continuationPoint != null) {
            return continuationPoint;
        }
        return windowFrom;
    }

    /**
     * Aktualizuje punkt kontynuacji dla danego SubjectType, jeśli paczka została obcięta (IsTruncated=true).
     * Punkt kontynuacji to LastPermanentStorageDate z obciętej paczki lub PermanentStorageHwmDate (stabilny HWM z RestrictToPermanentStorageHwmDate=true)
     * i służy jako punkt startowy dla następnego eksportu w celu zapewnienia, że żadne faktury nie zostaną pominięte.
     * Dzięki trybowi snapshot (RestrictToPermanentStorageHwmDate=true) pole PermanentStorageHwmDate jest zawsze dostępne i stabilne.
     */
    private void updateContinuationPointIfNeeded(Map<InvoiceQuerySubjectType, OffsetDateTime> continuationPoints,
                                                 InvoiceQuerySubjectType subjectType,
                                                 InvoiceExportPackage invoiceExportPackage) {
        if (Boolean.TRUE.equals(invoiceExportPackage.getIsTruncated()) && Objects.nonNull(invoiceExportPackage.getLastPermanentStorageDate())) {
            // Priorytet 1: Paczka obcięta - LastPermanentStorageDate (przerwanie przetwarzania)
            continuationPoints.put(subjectType, invoiceExportPackage.getLastPermanentStorageDate());
        } else if (Objects.nonNull(invoiceExportPackage.getPermanentStorageHwmDate())) {
            // Priorytet 2: Stabilny HWM jako granica kolejnego okna
            continuationPoints.put(subjectType, invoiceExportPackage.getPermanentStorageHwmDate());
        } else {
            // Zakres w pełni przetworzony - usunięcie punktu kontynuacji
            continuationPoints.remove(subjectType);
        }
    }

    private boolean sameElements(List<String> a, Set<String> b) {
        if (a == null || b == null) return false;
        if (a.size() != b.size()) return false;

        List<String> sa = new ArrayList<>(a);
        List<String> sb = new ArrayList<>(b);

        Collections.sort(sa);
        Collections.sort(sb);

        return sa.equals(sb);
    }

}
