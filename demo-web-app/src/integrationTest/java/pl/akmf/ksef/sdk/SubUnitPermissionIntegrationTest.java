package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsGrantRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QuerySubunitPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRole;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.subunit.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;

class SubUnitPermissionIntegrationTest extends BaseIntegrationTest {

    @Test
    void subUnitPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String unitNip = IdentifierGeneratorUtils.generateRandomNIP();
        String internalNip = unitNip + "-11111";
        String subUnitNip = IdentifierGeneratorUtils.generateRandomNIP();
        String subUnitAdmin = IdentifierGeneratorUtils.generateRandomNIP();

        //Inicjalizuje uwierzytelnienie jednostki głównej.
        String unitAccessToken = authWithCustomNip(unitNip, unitNip).accessToken();

        //Nadanie uprawnienia SubunitManage, CredentialsManage do zarządzania jednostką podrzędną
        String grantReferenceNumber = grantPermissionToAdministrateSubUnit(subUnitNip, unitAccessToken);

        await()
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(grantReferenceNumber, unitAccessToken));

        //Uwierzytelnia w kontekście jednostki głównej jako jednostka podrzędna przy użyciu certyfikatu osobistego.
        String subunitAccessToken = authWithCustomNip(unitNip, subUnitNip).accessToken();

        //Nadanie uprawnień administratora podmiotu podrzędnego jako jednostka podrzędna
        String operationGrantNumber = grantPermissionSubunit(subUnitAdmin, internalNip, subunitAccessToken);

        await()
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(operationGrantNumber, subunitAccessToken));

        //Wyszukaj uprawnienia nadane administratorowi jednostki podrzędnej
        List<SubunitPermission> subUnitPermission = searchGrantedRole(subunitAccessToken);
        Assertions.assertTrue(subUnitPermission.size() > 0);

        //Pobierz listę podmiotów podrzędnych jeżeli podmiot bieżącego kontekstu ma rolę podmiotu nadrzędnego
        List<SubordinateEntityRole> subordinateEntities = searchSubordinateEntity(unitAccessToken, subUnitNip);
        Assertions.assertNotNull(subordinateEntities);

        //Cofnij uprawnienia nadane administratorowi jednostki podrzędnej
        String revokeOperationReferenceNumber = revokePermission(subUnitPermission.getFirst().getId(), subunitAccessToken);

        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(revokeOperationReferenceNumber, subunitAccessToken));
    }

    private List<SubordinateEntityRole> searchSubordinateEntity(String unitAccessToken, String subunitNip) throws ApiException {
        SubordinateEntityRolesQueryRequest queryRequest = new SubordinateEntityRolesQueryRequest();
        SubordinateEntityRolesQueryResponse response = ksefClient.searchSubordinateEntityInvoiceRoles(queryRequest, 0, 10, unitAccessToken);

        return response.getRoles();
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) {
        try {
            PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
            return status != null && status.getStatus().getCode() == 200;
        } catch (ApiException e) {
            return false;
        }
    }

    private List<SubunitPermission> searchGrantedRole(String accessToken) throws ApiException {
        SubunitPermissionsQueryRequest request = new SubunitPermissionsQueryRequestBuilder()
                .build();

        QuerySubunitPermissionsResponse response = ksefClient.searchSubunitAdminPermissions(request, 0, 10, accessToken);

        return response.getPermissions();
    }

    private String grantPermissionToAdministrateSubUnit(String subjectNip, String accessToken) throws ApiException {
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, subjectNip))
                .withPermissions(List.of(PersonPermissionType.CREDENTIALSMANAGE, PersonPermissionType.SUBUNITMANAGE))
                .withDescription("e2e subunit test")
                .build();

        OperationResponse response = ksefClient.grantsPermissionPerson(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private String grantPermissionSubunit(String subjectNip, String contextNip, String accessToken) throws ApiException {
        SubunitPermissionsGrantRequest request = new SubunitPermissionsGrantRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, subjectNip))
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.INTERNALID, contextNip))
                .withDescription("e2e subunit test")
                .withSubunitName("test_e2e")
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

