package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.invoices.InvoiceQueryFiltersBuilder;
import pl.akmf.ksef.sdk.api.builders.invoices.InvoicesAsyncQueryFiltersBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.ExceptionDetails;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.invoice.InitAsyncInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportStatus;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackageMetadata;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackagePart;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.client.model.util.SortOrder;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.FilesUtil;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThrows;

class QueryInvoiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    @Test
    void queryInvoiceE2ETest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);

        String invoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template_v3.xml", accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken));

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> waitForStoringInvoice(sessionReferenceNumber, invoiceReferenceNumber, accessToken));

        throwWhileSendingInvoiceMetadataRequestWithWrongPageSize(accessToken);

        getInvoiceMetadata(accessToken);

        InvoiceExportStatus invoiceExportStatus = fetchAsyncInvoiceExportStatus(accessToken, encryptionData);

        downloadAndProcessPackageAsync(invoiceExportStatus, encryptionData);
    }

    private void getInvoiceMetadata(String accessToken) throws ApiException {
        InvoiceQueryFilters request = new InvoiceQueryFiltersBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusDays(10),
                                OffsetDateTime.now().plusDays(2)))
                .build();

        QueryInvoiceMetadataResponse response = ksefClient.queryInvoiceMetadata(0, 10, SortOrder.ASC, request, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getInvoices().size());
    }

    private void throwWhileSendingInvoiceMetadataRequestWithWrongPageSize(String accessToken) {
        InvoiceQueryFilters request = new InvoiceQueryFiltersBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusDays(10), OffsetDateTime.now().plusDays(10)))
                .build();


        ApiException apiException = assertThrows(ApiException.class, () ->
                ksefClient.queryInvoiceMetadata(0, 5, SortOrder.ASC, request, accessToken)
        );
        Assertions.assertEquals(400, apiException.getCode());
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21405, details.getExceptionCode());
        Assertions.assertEquals("Błąd walidacji danych wejściowych.", details.getExceptionDescription());
        Assertions.assertEquals("'pageSize' must be between 10 and 250. You entered 5.", details.getDetails().getFirst());
    }

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                   statusResponse.getSuccessfulInvoiceCount() != null &&
                   statusResponse.getSuccessfulInvoiceCount() > 0;
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private boolean waitForStoringInvoice(String sessionReferenceNumber, String invoiceReferenceNumber, String accessToken) {
        try {
            SessionInvoiceStatusResponse statusResponse = ksefClient.getSessionInvoiceStatus(sessionReferenceNumber, invoiceReferenceNumber, accessToken);
            return Objects.nonNull(statusResponse.getPermanentStorageDate());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode,
                                     SchemaVersion schemaVersion,
                                     SessionValue value,
                                     String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendInvoiceOnlineSession(String nip, String sessionReferenceNumber, EncryptionData encryptionData,
                                            String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#",
                        LocalDate.of(2025, 9, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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

    private InvoiceExportStatus fetchAsyncInvoiceExportStatus(String accessToken, EncryptionData encryptionData) throws ApiException {
        InvoiceExportFilters filters = new InvoicesAsyncQueryFiltersBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusDays(10), OffsetDateTime.now().plusDays(10)))
                .build();

        InvoiceExportRequest request = new InvoiceExportRequest(
                new EncryptionInfo(encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()), filters);

        InitAsyncInvoicesQueryResponse response = ksefClient.initAsyncQueryInvoice(request, accessToken);

        await().atMost(45, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isInvoiceFetched(response.getReferenceNumber(), accessToken));

        return ksefClient.checkStatusAsyncQueryInvoice(response.getReferenceNumber(), accessToken);
    }

    private Boolean isInvoiceFetched(String referenceNumber, String accessToken) throws ApiException {
        InvoiceExportStatus response = ksefClient.checkStatusAsyncQueryInvoice(referenceNumber, accessToken);

        Assertions.assertNotNull(response);
        return response.getStatus().getCode().equals(200);
    }


    private void downloadAndProcessPackageAsync(InvoiceExportStatus invoiceExportStatus, EncryptionData encryptionData) throws IOException {
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

        List<String> invoices = downloadedFiles.keySet()
                .stream()
                .filter(fileName -> fileName.endsWith(".xml"))
                .toList();

        Assertions.assertEquals(1, invoices.size());
        Assertions.assertEquals(1, invoicePackageMetadata.getInvoices().size());
        Assertions.assertTrue(invoices.getFirst().contains(invoicePackageMetadata.getInvoices().getFirst().getKsefNumber()));
    }
}
