package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.invoices.InvoiceQueryFiltersBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.entity.GrantEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ForbiddenApiException;
import pl.akmf.ksef.sdk.client.model.ForbiddenProblemDetails;
import pl.akmf.ksef.sdk.client.model.UnauthorizedApiException;
import pl.akmf.ksef.sdk.client.model.UnauthorizedProblemDetails;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.exceptions.BadRequestApiError;
import pl.akmf.ksef.sdk.client.model.exceptions.BadRequestApiException;
import pl.akmf.ksef.sdk.client.model.exceptions.BadRequestProblemDetails;
import pl.akmf.ksef.sdk.client.model.exceptions.TooManyRequestsApiException;
import pl.akmf.ksef.sdk.client.model.exceptions.TooManyRequestsProblemDetails;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.limit.EffectiveApiRateLimits;
import pl.akmf.ksef.sdk.client.model.limit.OnlineSessionRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.SetRateLimitsRequest;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
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
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.akmf.ksef.sdk.api.Url.GRANT_INVOICE_SUBJECT_PERMISSION;

class ExceptionsApiIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    @Test
    void forbiddenTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String subjectNip = IdentifierGeneratorUtils.generateRandomNIP();
        String thirdNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String grantReferenceNumber = grantPermission(subjectNip, accessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(grantReferenceNumber, accessToken));

        String accessTokenSubject = authWithCustomNip(contextNip, subjectNip).accessToken();

        ApiException apiException = assertThrows(ApiException.class, () ->
                grantPermission(thirdNip, accessTokenSubject));
        Assertions.assertEquals(403, apiException.getCode());
        Assertions.assertEquals("POST", apiException.getMethod());
        Assertions.assertTrue(apiException.getUrl().contains(GRANT_INVOICE_SUBJECT_PERMISSION.getUrl()));
        ForbiddenApiException forbiddenApiException = (ForbiddenApiException) apiException;
        Assertions.assertNotNull(forbiddenApiException);
        ForbiddenProblemDetails forbiddenProblemDetails = forbiddenApiException.getForbiddenProblemDetails();
        Assertions.assertNotNull(forbiddenProblemDetails);
        Assertions.assertEquals("Forbidden", forbiddenProblemDetails.getTitle());
        Assertions.assertEquals(403, forbiddenProblemDetails.getStatus());
        Assertions.assertEquals("missing-permissions", forbiddenProblemDetails.getReasonCode());
        Assertions.assertEquals("Brak wymaganych uprawnień do wykonania operacji w bieżącym kontekście.", forbiddenProblemDetails.getDetail());
        Assertions.assertNotNull(forbiddenProblemDetails.getInstance());
        Assertions.assertNotNull(forbiddenProblemDetails.getTraceId());
        Assertions.assertNotNull(forbiddenProblemDetails.getTimestamp());
        Map<String, Object> security = forbiddenProblemDetails.getSecurity();
        Assertions.assertNotNull(security);
        Assertions.assertTrue(security.get("requiredAnyOfPermissions").toString().contains("CredentialsManage"));
        Assertions.assertTrue(security.get("presentPermissions").toString().contains("InvoiceRead"));
        Assertions.assertTrue(security.get("presentPermissions").toString().contains("InvoiceWrite"));
    }

    @Test
    void unauthorizedTest() {
        String subjectNip = IdentifierGeneratorUtils.generateRandomNIP();

        ApiException apiException = assertThrows(ApiException.class, () ->
                grantPermission(subjectNip, "invalidToken"));
        Assertions.assertEquals(401, apiException.getCode());
        Assertions.assertEquals("POST", apiException.getMethod());
        Assertions.assertTrue(apiException.getUrl().contains(GRANT_INVOICE_SUBJECT_PERMISSION.getUrl()));
        UnauthorizedApiException unauthorizedApiException = (UnauthorizedApiException) apiException;
        Assertions.assertNotNull(unauthorizedApiException);
        UnauthorizedProblemDetails unauthorizedProblemDetails = unauthorizedApiException.getUnauthorizedProblemDetails();
        Assertions.assertNotNull(unauthorizedProblemDetails);
        Assertions.assertEquals("Unauthorized", unauthorizedProblemDetails.getTitle());
        Assertions.assertEquals(401, unauthorizedProblemDetails.getStatus());
        Assertions.assertEquals("Wymagane jest uwierzytelnienie.", unauthorizedProblemDetails.getDetail());
        Assertions.assertNotNull(unauthorizedProblemDetails.getInstance());
        Assertions.assertNotNull(unauthorizedProblemDetails.getTraceId());
        Assertions.assertNotNull(unauthorizedProblemDetails.getTimestamp());
    }

    @Test
    void badRequestTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        EffectiveApiRateLimits invalidLimits = new EffectiveApiRateLimits(
                new OnlineSessionRateLimit(1, 1, 11111111),
                null, null, null, null, null, null, null, null, null, null, null
        );

        SetRateLimitsRequest badRequest = new SetRateLimitsRequest(invalidLimits);

        ApiException apiException = Assertions.assertThrows(ApiException.class, () ->
                ksefClient.setRateLimits(badRequest, accessToken));

        BadRequestApiException badRequestApiException = (BadRequestApiException) apiException;
        Assertions.assertNotNull(badRequestApiException);
        BadRequestProblemDetails badRequestProblemDetails = badRequestApiException.getBadRequestProblemDetails();
        Assertions.assertNotNull(badRequestProblemDetails);
        Assertions.assertEquals("Bad Request", badRequestProblemDetails.getTitle());
        Assertions.assertEquals(400, badRequestProblemDetails.getStatus());
        Assertions.assertEquals("Żądanie jest nieprawidłowe.", badRequestProblemDetails.getDetail());
        Assertions.assertNotNull(badRequestProblemDetails.getInstance());
        Assertions.assertNotNull(badRequestProblemDetails.getTraceId());
        Assertions.assertNotNull(badRequestProblemDetails.getTimestamp());
        List<BadRequestApiError> errors = badRequestProblemDetails.getErrors();
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Błąd walidacji danych wejściowych.", errors.get(0).getDescription());
        Assertions.assertEquals(21405, errors.get(0).getCode());
        List<String> details = errors.get(0).getDetails();
        Assertions.assertEquals(11, details.size());
        Assertions.assertTrue(details.stream()
                .anyMatch(s -> s.contains("'rateLimits.onlineSession.perHour' must be between 1 and 1200. You entered 11111111.")));
    }

    @Test
    void tooManyRequestTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);

        String invoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template_v3.xml", accessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken));

        await().pollDelay(Duration.ZERO)
                .atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> waitForStoringInvoice(sessionReferenceNumber, invoiceReferenceNumber, accessToken));

        for (int i = 0; i < 160; i++) {
            getInvoiceMetadata(accessToken);
        }
        ApiException apiException = Assertions.assertThrows(ApiException.class, () ->
                getInvoiceMetadata(accessToken));

        TooManyRequestsApiException tooManyRequestsApiException = (TooManyRequestsApiException) apiException;
        Assertions.assertNotNull(tooManyRequestsApiException);
        TooManyRequestsProblemDetails tooManyRequestsProblemDetails = tooManyRequestsApiException.getTooManyRequestsProblemDetails();
        Assertions.assertNotNull(tooManyRequestsProblemDetails);
        Assertions.assertEquals("Too Many Requests", tooManyRequestsProblemDetails.getTitle());
        Assertions.assertEquals(429, tooManyRequestsProblemDetails.getStatus());
        Assertions.assertTrue(tooManyRequestsProblemDetails.getDetail().contains("Przekroczono limit 160 żądań na minutę. Spróbuj ponownie po"));
        Assertions.assertNotNull(tooManyRequestsProblemDetails.getInstance());
        Assertions.assertNotNull(tooManyRequestsProblemDetails.getTraceId());
        Assertions.assertNotNull(tooManyRequestsProblemDetails.getTimestamp());
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private String grantPermission(String targetNip, String accessToken) throws ApiException {
        GrantEntityPermissionsRequest request = new GrantEntityPermissionsRequestBuilder()
                .withPermissions(List.of(
                        new EntityPermission(EntityPermissionType.INVOICE_READ, true),
                        new EntityPermission(EntityPermissionType.INVOICE_WRITE, false)))
                .withDescription("description")
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, targetNip))
                .withSubjectDetails(
                        new GrantEntityPermissionsRequest.PermissionsEntitySubjectDetails("Testowo")
                )
                .build();

        OperationResponse response = ksefClient.grantsPermissionEntity(request, accessToken);
        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
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

    private QueryInvoiceMetadataResponse getInvoiceMetadata(String accessToken) throws ApiException {
        InvoiceQueryFilters request = new InvoiceQueryFiltersBuilder()
                .withSubjectType(InvoiceQuerySubjectType.SUBJECT1)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING, OffsetDateTime.now().minusDays(10),
                                OffsetDateTime.now().plusDays(10)))
                .build();

        QueryInvoiceMetadataResponse response = ksefClient.queryInvoiceMetadata(0, 10, SortOrder.ASC, request, accessToken);

        Assertions.assertNotNull(response);

        return response;
    }

}