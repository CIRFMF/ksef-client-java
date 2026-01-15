package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.PersonPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermission;
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

class PersonalPermissionAuthorizedPeselInNipContext extends BaseIntegrationTest {

    @Test
    void personPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedPesel = IdentifierGeneratorUtils.getRandomPesel();

        // Owner auth (kontekst NIP)
        String ownerToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        // GRANT (publiczne API): nadajemy InvoiceRead + InvoiceWrite dla osoby identyfikowanej PESEL
        String operationNumber = grantPermission(authorizedPesel, ownerToken);

        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(operationNumber, ownerToken));

        // Osoba (PESEL) – generujemy cert testowy i uwierzytelniamy się w KONTEKŚCIE NIP właściciela
        String authorizedAccessToken = authWithCustomPesel(ownerNip, authorizedPesel).accessToken();
        Assertions.assertNotNull(authorizedAccessToken);

        // Zapytanie: personal/grants — obowiązujące (Active) uprawnienia w bieżącym kontekście NIP
        List<PersonPermission> grantedPermission = searchPermission(authorizedPesel, ownerToken);

        Assertions.assertEquals(2, grantedPermission.size());
        Assertions.assertTrue(grantedPermission.stream()
                .map(PersonPermission::getPermissionScope)
                .toList()
                .containsAll(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD)));

        //usunięcie uprawnień oraz podmiotu
        grantedPermission.forEach(permission -> {
            String revokeOperationNumber = revokePermission(permission.getId(), ownerToken);

            await().atMost(15, SECONDS)
                    .pollInterval(1, SECONDS)
                    .until(() -> isOperationFinish(revokeOperationNumber, ownerToken));
        });
    }

    private List<PersonPermission> searchPermission(String personValue, String accessToken) throws ApiException {
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.PESEL, personValue))
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(request, 0, 10, accessToken);

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

    private String grantPermission(String personValue, String accessToken) throws ApiException {
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.PESEL, personValue))
                .withPermissions(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD))
                .withDescription("e2e test grant")
                .build();

        OperationResponse response = ksefClient.grantsPermissionPerson(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }
}
