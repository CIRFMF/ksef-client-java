package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.entity.GrantEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ForbiddenApiException;
import pl.akmf.ksef.sdk.client.model.ForbiddenProblemDetails;
import pl.akmf.ksef.sdk.client.model.UnauthorizedApiException;
import pl.akmf.ksef.sdk.client.model.UnauthorizedProblemDetails;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.akmf.ksef.sdk.api.Url.GRANT_INVOICE_SUBJECT_PERMISSION;

class ExceptionsApiIntegrationTest extends BaseIntegrationTest {

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
}