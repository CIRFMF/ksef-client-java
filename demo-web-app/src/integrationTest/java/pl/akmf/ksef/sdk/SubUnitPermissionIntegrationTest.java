package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsGrantRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.search.QuerySubunitPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsSubunitIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class SubUnitPermissionIntegrationTest extends BaseIntegrationTest {

    @Test
    void subUnitPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String unitValue = IdentifierGeneratorUtils.generateRandomNIP();
        String subUnitNip = contextNip + "-11111";

        String grantReferenceNumber = grantPermissionSubunit(unitValue, subUnitNip, accessToken);

        await()
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(grantReferenceNumber, accessToken));

        List<String> permission = searchGrantedRole(subUnitNip, 1, accessToken);

        permission.forEach(e -> {
            String revokeReferenceNumber = revokePermission(e, accessToken);

            await().atMost(30, SECONDS)
                    .pollInterval(5, SECONDS)
                    .until(() -> isPermissionStatusReady(revokeReferenceNumber, accessToken));
        });

        searchGrantedRole(subUnitNip, 0, accessToken);
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) {
        try {
            PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
            return status != null && status.getStatus().getCode() == 200;
        } catch (ApiException e) {
            return false;
        }
    }

    private List<String> searchGrantedRole(String subUnitNip, int expectedSize, String accessToken) throws ApiException {
        SubunitPermissionsQueryRequest request = new SubunitPermissionsQueryRequestBuilder()
                .withSubunitIdentifier(new SubunitPermissionsSubunitIdentifier(SubunitPermissionsSubunitIdentifier.IdentifierType.INTERNALID, subUnitNip))
                .build();

        QuerySubunitPermissionsResponse response = ksefClient.searchSubunitAdminPermissions(request, 0, 10, accessToken);
        Assertions.assertEquals(expectedSize, response.getPermissions().size());

        return response.getPermissions()
                .stream()
                .map(SubunitPermission::getId)
                .toList();
    }

    private String grantPermissionSubunit(String subjectNip, String contextNip, String accessToken) throws ApiException {
        SubunitPermissionsGrantRequest request = new SubunitPermissionsGrantRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, subjectNip))
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.INTERNALID, contextNip))
                .withDescription("e2e subunit test")
                .withSubunitName("test")
                .build();

        OperationResponse response = ksefClient.grantsPermissionSubUnit(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }
}

