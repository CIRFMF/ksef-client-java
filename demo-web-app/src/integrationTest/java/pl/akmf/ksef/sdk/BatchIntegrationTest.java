package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class BatchIntegrationTest extends BaseIntegrationTest {
    private static final int NUMBER_OF_PARTS = 2;
    private static final int INVOICES_COUNT = 2;
    private static final String TMP = "tmp";
    private static final Path INVOICES_DIR = Paths.get(TMP + "/Invoices");

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    @Test
    void batchSessionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String sessionReferenceNumber = openBatchSessionAndSendInvoice(contextNip, accessToken);

        closeSession(sessionReferenceNumber, accessToken);

        String upoReferenceNumber = getBatchSessionStatus(sessionReferenceNumber, accessToken);

        List<SessionInvoiceStatusResponse> documents = getInvoice(sessionReferenceNumber, accessToken);

        getBatchInvoiceAndUpo(sessionReferenceNumber, documents.getFirst().getKsefNumber(), accessToken);

        getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);
    }

    @Test
    void batchSessionStreamE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String sessionReferenceNumber = openBatchSessionAndSendInvoiceStream(contextNip, accessToken);

        closeSession(sessionReferenceNumber, accessToken);

        String upoReferenceNumber = getBatchSessionStatus(sessionReferenceNumber, accessToken);

        List<SessionInvoiceStatusResponse> documents = getInvoice(sessionReferenceNumber, accessToken);

        getBatchInvoiceAndUpo(sessionReferenceNumber, documents.getFirst().getKsefNumber(), accessToken);

        getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);
    }

    private void getSessionUpo(String sessionReferenceNumber, String upoReferenceNumber, String accessToken) throws ApiException {

        byte[] sessionUpo = ksefClient.getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);

        Assertions.assertNotNull(sessionUpo);
    }

    private void getBatchInvoiceAndUpo(String sessionReferenceNumber, String ksefNumber, String accessToken) throws ApiException {
        byte[] upoResponse = ksefClient.getSessionInvoiceUpoByKsefNumber(sessionReferenceNumber, ksefNumber, accessToken);

        Assertions.assertNotNull(upoResponse);
    }

    private List<SessionInvoiceStatusResponse> getInvoice(String sessionReferenceNumber, String accessToken) throws ApiException {
        SessionInvoicesResponse response = ksefClient.getSessionInvoices(sessionReferenceNumber, null, 10,
                accessToken);

        Assertions.assertNotNull(response.getInvoices());
        Assertions.assertEquals(INVOICES_COUNT, response.getInvoices().size());
        return response.getInvoices();
    }

    private String getBatchSessionStatus(String referenceNumber, String accessToken) throws ApiException {
        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionStatusResponse response = ksefClient.getSessionStatus(referenceNumber, accessToken);
                    return response.getStatus().getCode() == 200;
                });

        SessionStatusResponse response = ksefClient.getSessionStatus(referenceNumber, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(INVOICES_COUNT, response.getInvoiceCount());
        Assertions.assertEquals(INVOICES_COUNT, response.getSuccessfulInvoiceCount());
        Assertions.assertEquals(0, response.getFailedInvoiceCount());

        return response.getUpo().getPages().getFirst().getReferenceNumber();
    }

    private void closeSession(String referenceNumber, String accessToken) throws ApiException {
        ksefClient.closeBatchSession(referenceNumber, accessToken);
    }

    private String openBatchSessionAndSendInvoice(String context, String accessToken) throws IOException, ApiException {
        //when
        String invoice = new String(Objects.requireNonNull(BaseIntegrationTest.class.getResourceAsStream("/xml/invoices/sample/invoice-template.xml"))
                .readAllBytes(), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        if (!Files.exists(INVOICES_DIR)) Files.createDirectories(INVOICES_DIR);

        List<Path> invoices = new ArrayList<>();
        for (int i = 0; i < INVOICES_COUNT; i++) {
            String invoiceTemplate = invoice
                    .replace("#nip#", context)
                    .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .replace("#invoice_number#", UUID.randomUUID().toString());

            Path invoiceFile = INVOICES_DIR.resolve("faktura_" + (i + 1) + ".xml");
            Files.writeString(invoiceFile, invoiceTemplate);
            invoices.add(invoiceFile);
        }

        // create ZIP in memory
        byte[] zipBytes;
        try (ByteArrayOutputStream zipStream = new ByteArrayOutputStream();
             ZipOutputStream archive = new ZipOutputStream(zipStream)) {

            for (Path file : invoices) {
                archive.putNextEntry(new ZipEntry(file.getFileName().toString()));
                byte[] fileContent = Files.readAllBytes(file);
                archive.write(fileContent);
                archive.closeEntry();
            }
            archive.finish();
            zipBytes = zipStream.toByteArray();
        }

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        // Split ZIP into ${numberOfParts} parts
        int partSize = (int) Math.ceil((double) zipBytes.length / NUMBER_OF_PARTS);

        List<byte[]> zipParts = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PARTS; i++) {
            int start = i * partSize;
            int size = Math.min(partSize, zipBytes.length - start);
            if (size <= 0) break;

            byte[] part = Arrays.copyOfRange(zipBytes, start, start + size);
            zipParts.add(part);
        }
        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = new ArrayList<>();
        for (int i = 0; i < zipParts.size(); i++) {
            byte[] encryptedZipPart = defaultCryptographyService.encryptBytesWithAES256(
                    zipParts.get(i),
                    encryptionData.cipherKey(),
                    encryptionData.cipherIv()
            );
            FileMetadata zipPartMetadata = defaultCryptographyService.getMetaData(encryptedZipPart);
            encryptedZipParts.add(new BatchPartSendingInfo(encryptedZipPart, zipPartMetadata, (i + 1)));
        }

        // Build request
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA());

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            BatchPartSendingInfo part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1, "faktura_part" + (i + 1) + ".zip.aes",
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        OpenBatchSessionRequest request = builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, accessToken);

        deleteDirectoryRecursively(Paths.get(TMP));

        Assertions.assertNotNull(response.getReferenceNumber());

        ksefClient.sendBatchParts(response, encryptedZipParts);

        return response.getReferenceNumber();
    }

    private String openBatchSessionAndSendInvoiceStream(String context, String accessToken) throws IOException, ApiException {
        //when
        String invoice = new String(Objects.requireNonNull(BaseIntegrationTest.class.getResourceAsStream("/xml/invoices/sample/invoice-template.xml"))
                .readAllBytes(), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        if (!Files.exists(INVOICES_DIR)) Files.createDirectories(INVOICES_DIR);

        List<Path> invoices = new ArrayList<>();
        for (int i = 0; i < INVOICES_COUNT; i++) {
            String invoiceTemplate = invoice
                    .replace("#nip#", context)
                    .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .replace("#invoice_number#", UUID.randomUUID().toString());

            Path invoiceFile = INVOICES_DIR.resolve("faktura_" + (i + 1) + ".xml");
            Files.writeString(invoiceFile, invoiceTemplate);
            invoices.add(invoiceFile);
        }

        // create ZIP in memory
        ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(zipBaos)) {
            zipOut.setLevel(Deflater.BEST_COMPRESSION);

            for (Path file : invoices) {
                ZipEntry entry = new ZipEntry(file.getFileName().toString());
                zipOut.putNextEntry(entry);

                Files.copy(file, zipOut);

                zipOut.closeEntry();
            }
        }
        byte[] zipBytes = zipBaos.toByteArray();

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        // Split ZIP into ${numberOfParts} parts
        int partSize = (int) Math.ceil((double) zipBytes.length / NUMBER_OF_PARTS);

        List<BatchPartStreamSendingInfo> encryptedStreamParts = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_PARTS; i++) {
            long start = i * partSize;
            long size = Math.min(partSize, zipBytes.length - start);
            if (size <= 0) break;

            byte[] partBytes = Arrays.copyOfRange(zipBytes, (int) start, (int) (start + size));

            ByteArrayInputStream partInputStream = new ByteArrayInputStream(partBytes);
            ByteArrayOutputStream encryptedBaos = new ByteArrayOutputStream();

            defaultCryptographyService.encryptStreamWithAES256(
                    partInputStream,
                    encryptedBaos,
                    encryptionData.cipherKey(),
                    encryptionData.cipherIv()
            );

            byte[] encryptedBytes = encryptedBaos.toByteArray();
            FileMetadata metadata = defaultCryptographyService.getMetaData(encryptedBytes);

            encryptedStreamParts.add(new BatchPartStreamSendingInfo(
                    new ByteArrayInputStream(encryptedBytes),
                    metadata,
                    i + 1
            ));
        }

        // Build request
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA());

        for (int i = 0; i < encryptedStreamParts.size(); i++) {
            BatchPartStreamSendingInfo part = encryptedStreamParts.get(i);
            builder = builder.addBatchFilePart(i + 1, "faktura_part" + (i + 1) + ".zip.aes",
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        OpenBatchSessionRequest request = builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, accessToken);

        deleteDirectoryRecursively(Paths.get(TMP));

        Assertions.assertNotNull(response.getReferenceNumber());

        ksefClient.sendBatchPartsWithStream(response, encryptedStreamParts);

        return response.getReferenceNumber();
    }

    private void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> walk = Files.walk(path)) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        }
    }
}
