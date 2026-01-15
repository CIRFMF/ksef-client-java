package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityRolesResponse;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonRemoveRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.OffsetDateTime;

class SearchEntityInvoiceRoleIntegrationTest extends BaseIntegrationTest {

    @Test
    void searchEntityInvoiceRoles() throws JAXBException, IOException, ApiException {
        // given
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String pesel = IdentifierGeneratorUtils.getRandomPesel();

        TestDataPersonCreateRequest request = new TestDataPersonCreateRequest();
        request.setCreatedDate(OffsetDateTime.now());
        request.setDescription("description");
        request.setNip(contextNip);
        request.setPesel(pesel);
        request.setIsDeceased(false);
        request.setIsBailiff(Boolean.TRUE);

        ksefClient.createTestPerson(request);

        AuthTokensPair token = authWithCustomNip(contextNip, contextNip);

        //when
        QueryEntityRolesResponse response = ksefClient.searchEntityInvoiceRoles(0, 10, token.accessToken());

        //then
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getRoles().isEmpty());

        ksefClient.removeTestPerson(new TestDataPersonRemoveRequest(contextNip));
    }
}
