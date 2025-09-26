package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.invoices.InvoiceMetadataQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.invoices.InvoicesAsyncQueryFiltersBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.interfaces.CryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportStatus;
import pl.akmf.ksef.sdk.client.model.invoice.InitAsyncInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceMetadataQueryRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportRequest;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThrows;

public class QueryInvoiceIntegrationTest extends BaseIntegrationTest {

    private EncryptionData encryptionData;

    @Test
    void queryInvoiceE2ETest() throws JAXBException, IOException, ApiException, CertificateException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        CryptographyService cryptographyService = new DefaultCryptographyService(createKSeFClient());
        encryptionData = cryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, "1-0E", "FA", accessToken);

        String invoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, sessionReferenceNumber, encryptionData, cryptographyService, "/xml/invoices/sample/invoice-template_v3.xml", accessToken);

        isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken);

        isInvoiceProcessed(sessionReferenceNumber, invoiceReferenceNumber, accessToken);

        throwWhileSendingInvoiceMetadataRequestWithWrongPageSize(accessToken);

        waitForStoringInvoice();

        getInvoiceMetadata(accessToken);

        fetchAsyncInvoice(accessToken);
    }

    private void waitForStoringInvoice() {
        await().timeout(45, SECONDS)
                .pollDelay(44,SECONDS)
                .untilAsserted(() -> Assertions.assertTrue(true));
    }

    private void getInvoiceMetadata(String accessToken) throws ApiException {
        InvoiceMetadataQueryRequest request = new InvoiceMetadataQueryRequestBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusYears(1),
                                OffsetDateTime.now()))
                .build();

        QueryInvoiceMetadataResponse response = createKSeFClient().queryInvoiceMetadata(0, 10, request, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getInvoices().size());
    }

    private void throwWhileSendingInvoiceMetadataRequestWithWrongPageSize(String accessToken) {
        InvoiceMetadataQueryRequest request = new InvoiceMetadataQueryRequestBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusDays(10), OffsetDateTime.now().plusDays(10)))
                .build();


        ApiException apiException = assertThrows(ApiException.class, () ->
                createKSeFClient().queryInvoiceMetadata(0, 5, request, accessToken)
        );
        Assertions.assertEquals(400, apiException.getCode());
        Assertions.assertTrue(apiException.getResponseBody().contains("must be between 10 and 250. You entered 5"));
    }

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = createKSeFClient().getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                    statusResponse.getSuccessfulInvoiceCount() != null &&
                    statusResponse.getSuccessfulInvoiceCount() > 0;
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private boolean isInvoiceProcessed(String sessionReferenceNumber, String invoiceReferenceNumber, String accessToken) {
        try {
            SessionInvoiceStatusResponse statusResponse = createKSeFClient().getSessionInvoiceStatus(sessionReferenceNumber, invoiceReferenceNumber, accessToken);
            return statusResponse != null && statusResponse.getStatus().getCode() == 200;
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode, String schemaVersion, String value, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = createKSeFClient().openOnlineSession(request, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendInvoiceOnlineSession(String nip, String sessionReferenceNumber, EncryptionData encryptionData,
                                            CryptographyService cryptographyService, String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(Objects.requireNonNull(BaseIntegrationTest.class.getResourceAsStream(path))
                .readAllBytes(), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#",
                        LocalDate.of(2025, 9, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());

        byte[] invoice = invoiceTemplate.getBytes(StandardCharsets.UTF_8);

        byte[] encryptedInvoice = cryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        FileMetadata invoiceMetadata = cryptographyService.getMetaData(invoice);
        FileMetadata encryptedInvoiceMetadata = cryptographyService.getMetaData(encryptedInvoice);

        SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .build();

        SendInvoiceResponse sendInvoiceResponse = createKSeFClient().onlineSessionSendInvoice(sessionReferenceNumber, sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        return sendInvoiceResponse.getReferenceNumber();
    }

    private void fetchAsyncInvoice(String accessToken) throws ApiException {
        InvoiceExportFilters filters = new InvoicesAsyncQueryFiltersBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusDays(10), OffsetDateTime.now().plusDays(10)))
                .build();

        InvoiceExportRequest request = new InvoiceExportRequest(
                new EncryptionInfo(encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()),filters);

        InitAsyncInvoicesQueryResponse response = createKSeFClient().initAsyncQueryInvoice(request, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isInvoiceFetched(response.getOperationReferenceNumber(), accessToken));
    }

    private Boolean isInvoiceFetched(String operationReferenceNumber, String accessToken) throws ApiException {
        InvoiceExportStatus response = createKSeFClient().checkStatusAsyncQueryInvoice(operationReferenceNumber, accessToken);

        Assertions.assertNotNull(response);
        return response.getStatus().getCode().equals(200);
    }
}
