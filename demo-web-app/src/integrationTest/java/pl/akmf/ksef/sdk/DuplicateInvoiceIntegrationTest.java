package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.client.model.util.ZipInputStreamWithSize;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.FilesUtil;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class DuplicateInvoiceIntegrationTest extends BaseIntegrationTest {

    private static final int BATCH_TOTAL_INVOICES = 1;
    private static final int PART_QUANTITY = 1;

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    static Stream<Arguments> inputTestParameters() {
        return Stream.of(
                Arguments.of(SystemCode.FA_2, "invoice-template.xml"),
                Arguments.of(SystemCode.FA_3, "invoice-template_v3.xml")
        );
    }

    // Scenariusz duplikatu faktury (bez polegania na wyjątku HTTP):
    // 1. Wysyła fakturę w sesji wsadowej (batch) – przetworzona jako poprawna.
    // 2. Otwiera sesję interaktywną (online).
    // 3. Wysyła tę samą fakturę (ten sam numer / ta sama treść logiczna).
    // 4. Zamyka sesję online.
    // 5. Pobiera listę nieudanych faktur: GET /sessions/{ref}/invoices/failed.
    // 6. Weryfikuje, że status zawiera informację o duplikacie (kod 440).
    @ParameterizedTest
    @MethodSource("inputTestParameters")
    void DuplicateInvoiceEndToEndFailedListContainsDuplicate(SystemCode systemCode, String invoiceTemplatePath) throws JAXBException, IOException, ApiException {
        String sellerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(sellerNip, sellerNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();
        String sharedInvoiceNumber = UUID.randomUUID().toString();

        // 1. Batch: wysłanie i przetworzenie faktury
        String batchSessionRef = openBatchSessionAndSendInvoicesPartsStream(sellerNip, systemCode, invoiceTemplatePath, sharedInvoiceNumber, encryptionData, accessToken);
        checkSessionStatus(batchSessionRef, accessToken, 200, 1, 0);

        // 2-4. Online: przetworzenie duplikatu
        String onlineSessionRef = openOnlineSessionAndSendInvoice(encryptionData, sellerNip, sharedInvoiceNumber, systemCode, invoiceTemplatePath, accessToken);
        checkSessionStatus(onlineSessionRef, accessToken, 100, null, 1);

        // 5. Pobranie nieudanych faktur /invoices/failed (duplikat)
        SessionInvoicesResponse failedInvoices = getFailedInvoices(onlineSessionRef, accessToken);

        SessionInvoiceStatusResponse failed = failedInvoices.getInvoices().getFirst();
        Assertions.assertNotNull(failed.getStatus());
        Assertions.assertNotNull(failed.getStatus().getCode());
        int duplicateInvoice = 440;
        Assertions.assertEquals(duplicateInvoice, failed.getStatus().getCode());
    }

    // Odwrócony scenariusz duplikatu:
    // 1. Wysyła fakturę w sesji online – przetworzona jako poprawna.
    // 2. Wysyła identyczną fakturę w sesji wsadowej.
    // 3. Pobiera listę nieudanych faktur wsadu i oczekuje kodu duplikatu (440).
    @ParameterizedTest
    @MethodSource("inputTestParameters")
    void DuplicateInvoiceOnlineFirstBatchFailedListContainsDuplicate(SystemCode systemCode, String invoiceTemplatePath) throws JAXBException, IOException, ApiException {
        String sellerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(sellerNip, sellerNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();
        String sharedInvoiceNumber = UUID.randomUUID().toString();

        // 1. Online: pierwsze wysłanie (powinno przejść poprawnie)
        String onlineSessionRef = openOnlineSessionAndSendInvoice(encryptionData, sellerNip, sharedInvoiceNumber, systemCode, invoiceTemplatePath, accessToken);
        // Sesja online może zakończyć się kodem 200
        checkSessionStatus(onlineSessionRef, accessToken, 100, 1, null);

        // 2. Batch: wysłanie duplikatu
        String batchSessionRef = openBatchSessionAndSendInvoicesPartsStream(sellerNip, systemCode, invoiceTemplatePath, sharedInvoiceNumber, encryptionData, accessToken);
        checkSessionStatus(batchSessionRef, accessToken, 445, 0, 1);

        // 3. Pobranie nieudanych faktur sesji wsadowej (polling aż dostępne)
        SessionInvoicesResponse failedBatchInvoices = getFailedInvoices(batchSessionRef, accessToken);

        SessionInvoiceStatusResponse failed = failedBatchInvoices.getInvoices().getFirst();
        Assertions.assertNotNull(failed.getStatus());
        Assertions.assertNotNull(failed.getStatus().getCode());
        int duplicateInvoice = 440;
        Assertions.assertEquals(duplicateInvoice, failed.getStatus().getCode());
    }

    private String openBatchSessionAndSendInvoicesPartsStream(String contextNip, SystemCode systemCode, String invoiceTemplatePath, String invoiceNumber,
                                                              EncryptionData encryptionData, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(readBytesFromPath("/xml/invoices/sample/" + invoiceTemplatePath), StandardCharsets.UTF_8);

        Map<String, byte[]> invoicesInMemory = new HashMap<>();
        for (int i = 0; i < BATCH_TOTAL_INVOICES; i++) {
            String invoice = invoiceTemplate
                    .replace("#nip#", contextNip)
                    .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .replace("#invoice_number#", invoiceNumber);

            invoicesInMemory.put("faktura_" + (i + 1) + ".xml", invoice.getBytes(StandardCharsets.UTF_8));
        }
        ZipInputStreamWithSize zipInputStreamWithSize = FilesUtil.createZipInputStream(invoicesInMemory);
        InputStream zipInputStream = zipInputStreamWithSize.getInputStream();
        int zipLength = zipInputStreamWithSize.getZipLength();

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipInputStream);
        zipInputStream.reset();

        List<BatchPartStreamSendingInfo> encryptedStreamParts = FilesUtil.splitAndEncryptZipStream(zipInputStream, PART_QUANTITY, zipLength, encryptionData.cipherKey(),
                encryptionData.cipherIv(), defaultCryptographyService);

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequestForStream(zipMetadata, encryptedStreamParts, systemCode, encryptionData);

        OpenBatchSessionResponse openBatchSessionResponse = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openBatchSessionResponse.getReferenceNumber());

        ksefClient.sendBatchPartsWithStream(openBatchSessionResponse, encryptedStreamParts);

        ksefClient.closeBatchSession(openBatchSessionResponse.getReferenceNumber(), accessToken);

        return openBatchSessionResponse.getReferenceNumber();
    }

    private OpenBatchSessionRequest buildOpenBatchSessionRequestForStream(FileMetadata zipMetadata,
                                                                          List<BatchPartStreamSendingInfo> encryptedZipParts,
                                                                          SystemCode systemCode,
                                                                          EncryptionData encryptionData) {
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(systemCode, SchemaVersion.VERSION_1_0E, SessionValue.FA)
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

    private void checkSessionStatus(String sessionReferenceNumber, String accessToken, int expectedStatus, Integer expectedSuccessfulInvoice, Integer expectedFailedInvoice) {
        await().atMost(40, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
                    return statusResponse.getStatus() != null
                           && statusResponse.getStatus().getCode() == expectedStatus
                           && statusResponse.getSuccessfulInvoiceCount() == expectedSuccessfulInvoice
                           && statusResponse.getFailedInvoiceCount() == expectedFailedInvoice;
                });
    }

    private String openOnlineSessionAndSendInvoice(EncryptionData encryptionData, String nip, String invoiceNumber, SystemCode systemCode, String invoiceTemplatePath, String accessToken) throws ApiException, IOException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, SchemaVersion.VERSION_1_0E, SessionValue.FA))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());

        String invoiceTemplate = new String(readBytesFromPath("/xml/invoices/sample/" + invoiceTemplatePath), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", invoiceNumber);

        byte[] invoice = invoiceTemplate.getBytes(StandardCharsets.UTF_8);

        byte[] encryptedInvoice = defaultCryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        FileMetadata invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        FileMetadata encryptedInvoiceMetadata = defaultCryptographyService.getMetaData(encryptedInvoice);

        SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .build();

        SendInvoiceResponse sendInvoiceResponse = ksefClient.onlineSessionSendInvoice(openOnlineSessionResponse.getReferenceNumber(), sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        return openOnlineSessionResponse.getReferenceNumber();
    }

    private SessionInvoicesResponse getFailedInvoices(String sessionRef, String accessToken) {
        AtomicReference<SessionInvoicesResponse> failedInvoicesResponse = new AtomicReference<>();
        await().atMost(120, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionInvoicesResponse failedBatchInvoices = ksefClient.getSessionFailedInvoices(sessionRef, null, 10, accessToken);
                    failedInvoicesResponse.set(failedBatchInvoices);
                    return failedBatchInvoices.getInvoices() != null
                           && failedBatchInvoices.getInvoices().size() > 0;
                });

        return failedInvoicesResponse.get();
    }
}