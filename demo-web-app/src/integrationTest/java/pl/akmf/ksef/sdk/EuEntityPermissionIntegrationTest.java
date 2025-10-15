package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.euentity.EuEntityPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.euentity.GrantEUEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.euentity.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermissionsQueryPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEuEntityPermissionsResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class EuEntityPermissionIntegrationTest extends BaseIntegrationTest {

    @Test
    void euEntityPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String nip = IdentifierGeneratorUtils.getRandomNip();
        String nipVatUe = IdentifierGeneratorUtils.getRandomNipVatEU(nip, "CZ");
        String accessToken = authWithCustomNip(nip, nip).accessToken();
        String euEntity = IdentifierGeneratorUtils.getRandomNipVatEU("CZ");

        // Nadaj uprawnienia jednostce EU
        String grantReferenceNumber = grantEuEntityPermission(euEntity, nipVatUe, accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(10, SECONDS)
                .until(() -> isOperationFinish(grantReferenceNumber, accessToken));

        List<EuEntityPermission> permissions = checkPermission(euEntity, 4, accessToken);

        EuEntityPermission invoiceWritePermission = permissions.stream()
                .filter(e -> e.getPermissionScope() == EuEntityPermissionsQueryPermissionType.INVOICEWRITE)
                .findFirst()
                .orElseThrow();
        String revokeReferenceNumber = revokePermission(invoiceWritePermission.getId(), accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(revokeReferenceNumber, accessToken));

        checkPermission(euEntity, 3, accessToken);
    }

    private List<EuEntityPermission> checkPermission(String subjectContext, int expectedNumberOfPermissions, String accessToken) throws ApiException {
        EuEntityPermissionsQueryRequest request = new EuEntityPermissionsQueryRequestBuilder()
                .withAuthorizedFingerprintIdentifier(subjectContext)
                .build();

        QueryEuEntityPermissionsResponse response = ksefClient.searchGrantedEuEntityPermissions(request, 0, 10, accessToken);

        Assertions.assertEquals(expectedNumberOfPermissions, response.getPermissions().size());

        return response.getPermissions();
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private String grantEuEntityPermission(String euEntity, String nipVatUe, String accessToken) throws ApiException {
        EuEntityPermissionsGrantRequest request = new GrantEUEntityPermissionsRequestBuilder()
                .withSubject(new SubjectIdentifier(SubjectIdentifier.IdentifierType.FINGERPRINT, euEntity))
                .withEuEntityName("Sample Subject Name")
                .withContext(new ContextIdentifier(ContextIdentifier.IdentifierType.NIP_VAT_UE, nipVatUe))
                .withDescription("E2E EU Entity Permission Test")
                .build();

        OperationResponse response = ksefClient.grantsPermissionEUEntity(request, accessToken);

        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        if (operations != null && operations.getStatus().getCode() >= 400) {
            throw new RuntimeException("Could not finish operation: " + operations.getStatus().getDescription());
        }
        return operations != null && operations.getStatus().getCode() == 200;
    }
}
