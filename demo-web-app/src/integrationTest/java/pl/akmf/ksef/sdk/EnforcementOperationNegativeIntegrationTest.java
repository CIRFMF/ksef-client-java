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
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsAuthorizedIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType.ENFORCEMENTOPERATIONS;

public class EnforcementOperationNegativeIntegrationTest extends BaseIntegrationTest {

    @Test
    void grantEnforcementOperationAsCourtBailiffE2E() throws JAXBException, IOException, ApiException {
        //zwykły kontekst NIP (brak roli EnforcementAuthority lub CourtBailiff)
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedNip = IdentifierGeneratorUtils.generateRandomNIP();

        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //próba nadania uprawnienia EnforcementOperations
        String operationNumber = grantPermission(authorizedNip, accessToken);

        // Odczytywanie statusu operacji aż będzie różny od 200 (niepowodzenie)
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isGrantProcessPermissionFinish(operationNumber, accessToken));

        // Potwierdzenie, że uprawnienie nie zostało nadane (nie występuje w wyszukiwaniu)
        QueryPersonPermissionsResponse queryRevokeResponse = searchGrantedPermission(authorizedNip, accessToken);
        Assertions.assertTrue(queryRevokeResponse.getPermissions().isEmpty());
    }

    private Boolean isGrantProcessPermissionFinish(String grantReferenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
        return status.getStatus().getCode() == 430;
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
}
