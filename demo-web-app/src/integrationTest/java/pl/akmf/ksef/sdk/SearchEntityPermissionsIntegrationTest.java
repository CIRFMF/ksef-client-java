package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsGrantRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.subunit.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.PermissionsSubunitPersonByIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.PermissionsSubunitSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitSubjectDetails;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

class SearchEntityPermissionsIntegrationTest extends BaseIntegrationTest {

    @Test
    void searchEntityPermissionsTest() throws JAXBException, IOException, ApiException {
        String nip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair token = authWithCustomNip(nip, nip);

        PersonPermissionsContextIdentifier contextIdentifier = new PersonPermissionsContextIdentifier();
        contextIdentifier.setValue(nip);
        contextIdentifier.setType(PersonPermissionsContextIdentifier.IdentifierType.NIP);
        EntityPermissionsQueryRequest request = new EntityPermissionsQueryRequest(contextIdentifier);

        QueryEntityPermissionsResponse queryEntitiesGrantsAsyncResponse = ksefClient.searchEntityInvoiceContext(request, 0, 10, token.accessToken());

        Assertions.assertNotNull(queryEntitiesGrantsAsyncResponse);
        Assertions.assertNotNull(queryEntitiesGrantsAsyncResponse.getPermissions());
        Assertions.assertTrue(queryEntitiesGrantsAsyncResponse.getPermissions().size() >= 0);
    }

    @Test
    void searchEntityPermissionsWithInternalId() throws JAXBException, IOException, ApiException {
        String nip = IdentifierGeneratorUtils.generateRandomNIP();
        String internalId = IdentifierGeneratorUtils.generateInternalIdentifier(nip);

        AuthTokensPair token = authWithCustomNip(nip, nip);

        SubunitPermissionsGrantRequest grantSubunitPermissionsReques = new SubunitPermissionsGrantRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, nip))
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.INTERNALID, internalId))
                .withDescription("E2E - grant subunit admin by subunit context")
                .withSubunitName("E2E VATGroup Subunit")
                .withSubjectDetails(
                        new SubunitSubjectDetails(PermissionsSubunitSubjectDetailsType.PersonByIdentifier,
                                new PermissionsSubunitPersonByIdentifier("Jan", "Kowalski"),
                                null,
                                null
                        )
                )
                .build();

        String operationGrantNumber = ksefClient.grantsPermissionSubUnit(grantSubunitPermissionsReques, token.accessToken()).getReferenceNumber();

        await().pollDelay(Duration.ZERO)
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(operationGrantNumber, token.accessToken()));
        authAsInternalId(internalId, nip);

        PersonPermissionsContextIdentifier contextIdentifier = new PersonPermissionsContextIdentifier();
        contextIdentifier.setValue(internalId);
        contextIdentifier.setType(PersonPermissionsContextIdentifier.IdentifierType.INTERNAL_ID);
        EntityPermissionsQueryRequest request = new EntityPermissionsQueryRequest(contextIdentifier);

        QueryEntityPermissionsResponse queryEntitiesGrantsAsyncResponse = ksefClient.searchEntityInvoiceContext(request, 0, 10, token.accessToken());

        Assertions.assertNotNull(queryEntitiesGrantsAsyncResponse);
        Assertions.assertNotNull(queryEntitiesGrantsAsyncResponse.getPermissions());
        Assertions.assertTrue(queryEntitiesGrantsAsyncResponse.getPermissions().size() >= 0);
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
        return status != null && status.getStatus().getCode() == 200;
    }
}
