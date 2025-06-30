package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoice;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.xml.ContextIdentifierTypeEnum;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class BatchIntegrationTest extends BaseIntegrationTest {
    private static final int NUMBER_OF_PARTS = 2;
    private static final int INVOICES_COUNT = 4;
    private static final String TMP = "tmp";
    private static final Path INVOICES_DIR = Paths.get(TMP + "/Invoices");

    @Test
    void batchSessionE2EIntegrationTest() throws JAXBException, IOException, InterruptedException, ApiException {
        String contextNip = TestUtils.generateRandomNIP();
        authWithCustomNip(contextNip, ContextIdentifierTypeEnum.NIP, contextNip);

        var sessionReferenceNumber = openBatchSessionAndSendInvoice(contextNip);

        closeSession(sessionReferenceNumber);

        Thread.sleep(8000);

        var upoReferenceNumber = getBatchSessionStatus(sessionReferenceNumber);

        var documents = getInvoice(sessionReferenceNumber);

        getBatchInvoiceAndUpo(sessionReferenceNumber, documents.getFirst().getKsefNumber());

        getSessionUpo(sessionReferenceNumber, upoReferenceNumber);
    }

    private void getSessionUpo(String sessionReferenceNumber, String upoReferenceNumber) throws ApiException {
        var sessionUpo = defaultKsefClient.getSessionUpo(sessionReferenceNumber, upoReferenceNumber);

        Assertions.assertNotNull(sessionUpo);
    }

    private void getBatchInvoiceAndUpo(String sessionReferenceNumber, String ksefNumber) throws ApiException {
        var upoResponse = defaultKsefClient.getSessionInvoiceUpoByKsefNumber(sessionReferenceNumber, ksefNumber);

        Assertions.assertNotNull(upoResponse);
    }

    private List<SessionInvoice> getInvoice(String sessionReferenceNumber) throws ApiException {
        var response = defaultKsefClient.getSessionInvoices(sessionReferenceNumber, 10, 0);

        Assertions.assertNotNull(response.getInvoices());
        Assertions.assertEquals(INVOICES_COUNT, response.getTotalCount());
        return response.getInvoices();
    }

    private String getBatchSessionStatus(String referenceNumber) throws ApiException {
        var response = defaultKsefClient.getSessionStatus(referenceNumber);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus().getCode());
        Assertions.assertEquals(INVOICES_COUNT, response.getInvoiceCount());
        Assertions.assertEquals(INVOICES_COUNT, response.getSuccessfulInvoiceCount());
        Assertions.assertEquals(0, response.getFailedInvoiceCount());

        return response.getUpo().getPages().getFirst().getReferenceNumber();
    }

    private void closeSession(String referenceNumber) throws ApiException {
        defaultKsefClient.closeBatchSession(referenceNumber);
    }

    private String openBatchSessionAndSendInvoice(String context) throws IOException, ApiException, InterruptedException {
        //when
        String invoice = new String(BaseIntegrationTest.class.getResourceAsStream("/xml/invoices/sample/invoice-template.xml")
                .readAllBytes(), StandardCharsets.UTF_8);

        DefaultCryptographyService cryptographyService = new DefaultCryptographyService(defaultKsefClient);
        EncryptionData encryptionData = cryptographyService.getEncryptionData();

        if (!Files.exists(INVOICES_DIR)) Files.createDirectories(INVOICES_DIR);

        List<Path> invoices = new ArrayList<>();
        for (int i = 0; i < INVOICES_COUNT; i++) {
            String invoiceTemplate = invoice
                    .replace("#nip#", context)
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
        var zipMetadata = cryptographyService.getMetaData(zipBytes);

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
            byte[] encryptedZipPart = cryptographyService.encryptBytesWithAES256(
                    zipParts.get(i),
                    encryptionData.cipherKey(),
                    encryptionData.cipherIv()
            );
            FileMetadata zipPartMetadata = cryptographyService.getMetaData(encryptedZipPart);
            encryptedZipParts.add(new BatchPartSendingInfo(encryptedZipPart, zipPartMetadata, (i + 1)));
        }

        // Build request
        var builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode("FA (2)", "1-0E", "FA")
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA(), false);

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            var part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1, "faktura_part" + (i + 1) + ".zip.aes",
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        OpenBatchSessionRequest request = builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();

        var response = defaultKsefClient.batchOpen(request);

        deleteDirectoryRecursively(Paths.get(TMP));

        Assertions.assertNotNull(response.getReferenceNumber());

        defaultKsefClient.sendBatchParts(response, encryptedZipParts);

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
