package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.entity.GrantEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.PersonPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class EntityPermissionIntegrationTest extends BaseIntegrationTest {

    private static final String DESCRIPTION = "E2E test grant";

    @Test
    void entityPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String subjectNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String grantReferenceNumber = grantPermission(subjectNip, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(grantReferenceNumber, accessToken));

        List<String> permission = searchPermission(2, accessToken);

        permission.forEach(e -> {
            String revokeReferenceNumber = revokePermission(e, accessToken);

            await().atMost(30, SECONDS)
                    .pollInterval(2, SECONDS)
                    .until(() -> isOperationFinish(revokeReferenceNumber, accessToken));
        });

        searchPermission(0, accessToken);
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private List<String> searchPermission(int expectedRolesAmount, String accessToken) throws ApiException {
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(request, 0, 10, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedRolesAmount, response.getPermissions().size());

        return response.getPermissions()
                .stream()
                .map(PersonPermission::getId)
                .toList();
    }

    private String grantPermission(String targetNip, String accessToken) throws ApiException {
        GrantEntityPermissionsRequest request = new GrantEntityPermissionsRequestBuilder()
                .withPermissions(List.of(
                        new EntityPermission(EntityPermissionType.INVOICE_READ, true),
                        new EntityPermission(EntityPermissionType.INVOICE_WRITE, false)))
                .withDescription(DESCRIPTION)
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, targetNip))
                .build();

        OperationResponse response = ksefClient.grantsPermissionEntity(request, accessToken);
        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }
}
