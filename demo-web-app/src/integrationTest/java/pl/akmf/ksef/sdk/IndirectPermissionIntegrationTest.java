package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.indirect.GrantIndirectEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.PersonPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.indirect.GrantIndirectEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.indirect.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.indirect.TargetIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static pl.akmf.ksef.sdk.client.model.permission.indirect.IndirectPermissionType.INVOICE_WRITE;

class IndirectPermissionIntegrationTest extends BaseIntegrationTest {

    @Test
    void indirectPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String contextAccessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        String subjectNip = IdentifierGeneratorUtils.getRandomNip("9");
        String targetNip = IdentifierGeneratorUtils.getRandomNip("9");

        String grantIndirectReferenceNumber = grantIndirectPermission(targetNip, subjectNip, contextAccessToken);

        await().atMost(30, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantIndirectReferenceNumber, contextAccessToken));

        String permissionId = checkGrantedPermission(contextAccessToken);

        String revokeReferenceNumberOperation = revokePermission(permissionId, contextAccessToken);

        await().atMost(30, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(revokeReferenceNumberOperation, contextAccessToken));

        checkRevokePermission(contextAccessToken);
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private String checkGrantedPermission(String accessToken) throws ApiException {
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(request, 0, 10, accessToken);
        Assertions.assertEquals(1, response.getPermissions().size());

        return response.getPermissions().getFirst().getId();
    }

    private void checkRevokePermission(String accessToken) throws ApiException {
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(request, 0, 10, accessToken);
        Assertions.assertTrue(response.getPermissions().isEmpty());
    }

    private String grantIndirectPermission(String targetNip, String subjectNip, String accessToken) throws ApiException {
        GrantIndirectEntityPermissionsRequest request = new GrantIndirectEntityPermissionsRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, subjectNip))
                .withTargetIdentifier(new TargetIdentifier(TargetIdentifier.IdentifierType.NIP, targetNip))
                .withPermissions(List.of(INVOICE_WRITE))
                .withDescription("E2E indirect grantE2E indirect grant")
                .build();

        OperationResponse response = ksefClient.grantsPermissionIndirectEntity(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }
}
