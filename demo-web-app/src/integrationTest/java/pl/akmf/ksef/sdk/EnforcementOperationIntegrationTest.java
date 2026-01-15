package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.person.PersonPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsAuthorizedIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.testdata.SubjectTypeTestData;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectRemoveRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType.ENFORCEMENTOPERATIONS;

class EnforcementOperationIntegrationTest extends BaseIntegrationTest {

    @Test
    void grantEnforcementOperationAsEnforcementAuthorityE2E() throws JAXBException, IOException, ApiException {
        //Utworzenie kontekstu EnforcementAuthority oraz uwierzytelnienie
        String enforcementAuthorityNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedNip = IdentifierGeneratorUtils.generateRandomNIP();

        createEnforcementSubject(enforcementAuthorityNip);

        String accessToken = authWithCustomNip(enforcementAuthorityNip, enforcementAuthorityNip).accessToken();

        //Nadawanie uprawnienia EnforcementOperations dla osoby uwierzytelniającej się nipem `authorizedNip`
        String operationNumber = grantPermission(authorizedNip, accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isPermissionStatusReady(operationNumber, accessToken));

        // Wyszukanie i weryfikacja, że uprawnienie jest widoczne i zawiera oczekiwany zakres
        QueryPersonPermissionsResponse queryGrantedResponse = searchGrantedPermission(authorizedNip, accessToken);
        Assertions.assertEquals(1, queryGrantedResponse.getPermissions().size());
        PersonPermission grantedPermission = queryGrantedResponse.getPermissions().getFirst();

        Assertions.assertEquals(ENFORCEMENTOPERATIONS, grantedPermission.getPermissionScope());

        // Wycofanie wszystkich znalezionych uprawnień
        String revokeOperationNumber = revokePermission(grantedPermission.getId(), accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isPermissionStatusReady(revokeOperationNumber, accessToken));

        // Ponowne wyszukiwanie - upewnienie się, że uprawnienie zniknęło
        QueryPersonPermissionsResponse queryRevokeResponse = searchGrantedPermission(authorizedNip, accessToken);
        Assertions.assertTrue(queryRevokeResponse.getPermissions().isEmpty());

        //usunięcie stworzonego podmiotu
        removeSubject(enforcementAuthorityNip);
    }

    @Test
    void grantEnforcementOperationAsCourtBailiffE2E() throws JAXBException, IOException, ApiException {
        //Utworzenie kontekstu EnforcementAuthority oraz uwierzytelnienie
        String courtBailiffNip = IdentifierGeneratorUtils.generateRandomNIP();
        String courtBailiffPesel = IdentifierGeneratorUtils.getRandomPesel();
        String authorizedNip = IdentifierGeneratorUtils.generateRandomNIP();

        createCourtBailiff(courtBailiffNip, courtBailiffPesel);

        String accessToken = authWithCustomNip(courtBailiffNip, courtBailiffNip).accessToken();

        //Nadawanie uprawnienia EnforcementOperations dla osoby uwierzytelniającej się nipem `authorizedNip`
        String operationNumber = grantPermission(authorizedNip, accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isPermissionStatusReady(operationNumber, accessToken));

        // Wyszukanie i weryfikacja, że uprawnienie jest widoczne i zawiera oczekiwany zakres
        QueryPersonPermissionsResponse queryGrantedResponse = searchGrantedPermission(authorizedNip, accessToken);
        Assertions.assertEquals(1, queryGrantedResponse.getPermissions().size());
        PersonPermission grantedPermission = queryGrantedResponse.getPermissions().getFirst();

        Assertions.assertEquals(ENFORCEMENTOPERATIONS, grantedPermission.getPermissionScope());

        // Wycofanie wszystkich znalezionych uprawnień
        String revokeOperationNumber = revokePermission(grantedPermission.getId(), accessToken);

        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isPermissionStatusReady(revokeOperationNumber, accessToken));

        // Ponowne wyszukiwanie - upewnienie się, że uprawnienie zniknęło
        QueryPersonPermissionsResponse queryRevokeResponse = searchGrantedPermission(authorizedNip, accessToken);
        Assertions.assertTrue(queryRevokeResponse.getPermissions().isEmpty());

        //usunięcie stworzonego podmiotu
        removePerson(courtBailiffNip);
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
        return status != null && status.getStatus().getCode() == 200;
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private String grantPermission(String subjectNip, String accessToken) throws ApiException {
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequest();
        request.setPermissions(List.of(ENFORCEMENTOPERATIONS));
        request.setSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, subjectNip));
        request.setDescription("e2e enforement test");

        OperationResponse response = ksefClient.grantsPermissionPerson(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private QueryPersonPermissionsResponse searchGrantedPermission(String authorizedNip, String accessToken) throws ApiException {
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.NIP, authorizedNip))
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        return ksefClient.searchGrantedPersonPermissions(request, 0, 10, accessToken);
    }

    private void createEnforcementSubject(String nip) throws ApiException {
        TestDataSubjectCreateRequest request = new TestDataSubjectCreateRequest();
        request.setSubjectNip(nip);
        request.setSubjectType(SubjectTypeTestData.ENFORCEMENT_AUTHORITY);
        request.setDescription("e2e court bailiff test");

        ksefClient.createTestSubject(request);

    }

    private void removeSubject(String nip) throws ApiException {
        TestDataSubjectRemoveRequest request = new TestDataSubjectRemoveRequest();
        request.setSubjectNip(nip);

        ksefClient.removeTestSubject(request);
    }

    private void createCourtBailiff(String nip, String pesel) throws ApiException {
        TestDataPersonCreateRequest request = new TestDataPersonCreateRequest();
        request.setNip(nip);
        request.setPesel(pesel);
        request.setDescription("Court Bailif");
        request.setIsBailiff(true);
        request.setIsDeceased(false);
        ksefClient.createTestPerson(request);
    }

    private void removePerson(String nip) throws ApiException {
        TestDataPersonRemoveRequest request = new TestDataPersonRemoveRequest(nip);

        ksefClient.removeTestPerson(request);
    }
}

