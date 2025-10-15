package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantResponse;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataAuthorizedIdentifier;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataContextIdentifier;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermission;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermissionRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermissionRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

public class SearchPersonalGrantPermissionIntegrationTest extends BaseIntegrationTest {

    @Test
    void searchPersonalPermissionTest() throws JAXBException, IOException, ApiException {
        // given
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authNip = IdentifierGeneratorUtils.generateRandomNIP();

        TestDataPermissionRequest testDataPermissionRequest = new TestDataPermissionRequest();
        testDataPermissionRequest.setContextIdentifier(new TestDataContextIdentifier(TestDataContextIdentifier.ContextIdentifierType.NIP, contextNip));
        testDataPermissionRequest.setAuthorizedIdentifier(new TestDataAuthorizedIdentifier(TestDataAuthorizedIdentifier.TestDataAuthorizedIdentifierType.NIP, authNip));
        testDataPermissionRequest.setPermissions(List.of(new TestDataPermission("test", TestDataPermission.PermissionType.INVOICE_READ)));

        ksefClient.addTestPermission(testDataPermissionRequest);

        AuthTokensPair token = authWithCustomNip(contextNip, authNip);

        //when
        QueryPersonalGrantRequest request = new QueryPersonalGrantRequest();
        QueryPersonalGrantResponse response = ksefClient.searchPersonalGrantPermission(request, 0, 10, token.accessToken());

        //then
        Assertions.assertNotNull(response);
        Assertions.assertTrue(!response.getPermissions().isEmpty());

        ksefClient.removeTestPermission(new TestDataPermissionRemoveRequest(
                new TestDataContextIdentifier(TestDataContextIdentifier.ContextIdentifierType.NIP, contextNip),
                new TestDataAuthorizedIdentifier(TestDataAuthorizedIdentifier.TestDataAuthorizedIdentifierType.NIP, authNip))
        );
    }
}
