package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.StatusInfo;
import pl.akmf.ksef.sdk.client.model.invoice.DownloadInvoiceRequest;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.UpoPageResponse;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.KSeFNumberValidator;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class OnlineSessionIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    private EncryptionData encryptionData;

    @Test
    void onlineSessionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        encryptionData = defaultCryptographyService.getEncryptionData();

        // Step 1: Open session and return referenceNumber
        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);

        // Step 2: Send invoice
        String invoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, sessionReferenceNumber, encryptionData,
                "/xml/invoices/sample/invoice-template.xml", accessToken);

        // Wait for invoice to be processed && check session status
        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken));

        // Step 3: Close session
        closeOnlineSession(sessionReferenceNumber, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isUpoGenerated(sessionReferenceNumber, accessToken));

        // Step 4: Get documents
        SessionInvoiceStatusResponse sessionInvoice = getOnlineSessionDocuments(sessionReferenceNumber, accessToken);
        String ksefNumber = sessionInvoice.getKsefNumber();

        // Step 5: Get status after close
        String upoReferenceNumber = getOnlineSessionUpoAfterCloseSession(sessionReferenceNumber, accessToken);

        // Step 6: Get UPO
        getOnlineSessionInvoiceUpo(sessionReferenceNumber, ksefNumber, accessToken);
        getOnlineSessionInvoiceUpoByInvoiceReferenceNumber(sessionReferenceNumber, invoiceReferenceNumber, accessToken);

        // Step 7: Get session UPO
        getOnlineSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);

        // Step 8: Get invoice
        getInvoice(sessionInvoice.getInvoiceNumber(), accessToken);
    }

    @Test
    void shouldReturn445SessionStatusWhileSendingWrongInvoiceAndCloseSession() throws JAXBException, IOException, ApiException {
        String wrongNip = "123";
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        encryptionData = defaultCryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);

        sendInvoiceOnlineSession(wrongNip, sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template.xml", accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
                    return statusResponse != null && statusResponse.getFailedInvoiceCount() > 0;
                });

        closeOnlineSession(sessionReferenceNumber, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
                    StatusInfo sessionStatus = statusResponse.getStatus();
                    return statusResponse.getFailedInvoiceCount() > 0 && sessionStatus.getCode() == 445;
                });
    }

    @Test
    void onlineSessionV3E2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        encryptionData = defaultCryptographyService.getEncryptionData();

        // Step 1: Open session and return referenceNumber
        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);

        // Step 2: Send invoice
        String invoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, sessionReferenceNumber, encryptionData,
                "/xml/invoices/sample/invoice-template_v3.xml", accessToken);

        // Wait for invoice to be processed
        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken));

        // Step 3: Close session
        closeOnlineSession(sessionReferenceNumber, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isUpoGenerated(sessionReferenceNumber, accessToken));

        // Step 4: Get documents
        SessionInvoiceStatusResponse sessionInvoice = getOnlineSessionDocuments(sessionReferenceNumber, accessToken);
        String ksefNumber = sessionInvoice.getKsefNumber();

        validKseFNumber(ksefNumber);
        // Step 5: Get status after close
        String upoReferenceNumber = getOnlineSessionUpoAfterCloseSession(sessionReferenceNumber, accessToken);

        // Step 6: Get UPO
        getOnlineSessionInvoiceUpo(sessionReferenceNumber, ksefNumber, accessToken);
        getOnlineSessionInvoiceUpoByInvoiceReferenceNumber(sessionReferenceNumber, invoiceReferenceNumber, accessToken);

        // Step 7 Get session UPO
        getOnlineSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);

        // Step 8: Get invoice
        getInvoice(sessionInvoice.getInvoiceNumber(), accessToken);
    }

    private static void validKseFNumber(String ksefNumber) {
        KSeFNumberValidator.ValidationResult result = KSeFNumberValidator.isValid(ksefNumber);

        Assertions.assertTrue(result.isValid());
    }

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                   statusResponse.getSuccessfulInvoiceCount() != null &&
                   statusResponse.getSuccessfulInvoiceCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isUpoGenerated(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null && statusResponse.getStatus().getCode() == 200;
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private String getOnlineSessionUpoAfterCloseSession(String sessionReferenceNumber, String accessToken) throws ApiException {
        SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
        Assertions.assertNotNull(statusResponse);
        Assertions.assertNotNull(statusResponse.getSuccessfulInvoiceCount());
        Assertions.assertEquals(1, (int) statusResponse.getSuccessfulInvoiceCount());
        Assertions.assertNull(statusResponse.getFailedInvoiceCount());
        Assertions.assertNotNull(statusResponse.getUpo());
        Assertions.assertEquals(200, (int) statusResponse.getStatus().getCode());
        UpoPageResponse upoPageResponse = statusResponse.getUpo().getPages().getFirst();
        Assertions.assertNotNull(upoPageResponse);
        Assertions.assertNotNull(upoPageResponse.getReferenceNumber());

        return upoPageResponse.getReferenceNumber();
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode,
                                     SchemaVersion schemaVersion, SessionValue value, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendInvoiceOnlineSession(String nip, String sessionReferenceNumber, EncryptionData encryptionData,
                                            String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(Objects.requireNonNull(BaseIntegrationTest.class.getResourceAsStream(path))
                .readAllBytes(), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());

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

        SendInvoiceResponse sendInvoiceResponse = ksefClient.onlineSessionSendInvoice(sessionReferenceNumber, sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        return sendInvoiceResponse.getReferenceNumber();
    }

    private void closeOnlineSession(String sessionReferenceNumber, String accessToken) throws ApiException {
        ksefClient.closeOnlineSession(sessionReferenceNumber, accessToken);
    }

    private SessionInvoiceStatusResponse getOnlineSessionDocuments(String sessionReferenceNumber, String accessToken) throws ApiException {
        SessionInvoicesResponse sessionInvoices = ksefClient.getSessionInvoices(sessionReferenceNumber, null, 10, accessToken);
        Assertions.assertEquals(1, sessionInvoices.getInvoices().size());
        SessionInvoiceStatusResponse invoice = sessionInvoices.getInvoices().getFirst();
        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getOrdinalNumber());
        Assertions.assertNotNull(invoice.getInvoiceNumber());
        Assertions.assertNotNull(invoice.getKsefNumber());
        Assertions.assertNotNull(invoice.getReferenceNumber());
        Assertions.assertNotNull(invoice.getInvoiceHash());
        Assertions.assertNotNull(invoice.getInvoicingDate());
        Assertions.assertNotNull(invoice.getStatus());
        Assertions.assertEquals(200, invoice.getStatus().getCode());

        return invoice;
    }

    private void getOnlineSessionInvoiceUpo(String sessionReferenceNumber, String ksefNumber, String accessToken) throws ApiException {
        byte[] upoResponse = ksefClient.getSessionInvoiceUpoByKsefNumber(sessionReferenceNumber, ksefNumber, accessToken);

        Assertions.assertNotNull(upoResponse);
    }

    private void getOnlineSessionInvoiceUpoByInvoiceReferenceNumber(String sessionReferenceNumber, String invoiceReferenceNumber, String accessToken) throws ApiException {
        byte[] upoResponse = ksefClient.getSessionInvoiceUpoByReferenceNumber(sessionReferenceNumber, invoiceReferenceNumber, accessToken);

        Assertions.assertNotNull(upoResponse);
    }

    private void getOnlineSessionUpo(String sessionReferenceNumber, String upoReferenceNumber, String accessToken) throws ApiException {
        byte[] sessionUpo = ksefClient.getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);

        Assertions.assertNotNull(sessionUpo);
    }

    private void getInvoice(String ksefNumber, String accessToken) throws ApiException {
        byte[] invoice = ksefClient.getInvoice(ksefNumber, accessToken);
        Assertions.assertNotNull(invoice);

        DownloadInvoiceRequest request = new DownloadInvoiceRequest();
        request.setKsefNumber(ksefNumber);
        invoice = ksefClient.getInvoice(request, accessToken);
        Assertions.assertNotNull(invoice);
    }
}