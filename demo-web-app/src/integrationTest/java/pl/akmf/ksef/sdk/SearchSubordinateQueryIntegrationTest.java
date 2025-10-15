package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityPermissionsSubordinateEntityIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryResponse;
import pl.akmf.ksef.sdk.client.model.testdata.SubjectTypeTestData;
import pl.akmf.ksef.sdk.client.model.testdata.Subunit;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectRemoveRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

class SearchSubordinateQueryIntegrationTest extends BaseIntegrationTest {

    @Test
    void searchSubordinateRoles() throws JAXBException, IOException, ApiException {
        // given
        String subjectNip = IdentifierGeneratorUtils.generateRandomNIP();
        String subunitNip = IdentifierGeneratorUtils.generateRandomNIP();

        TestDataSubjectCreateRequest request = new TestDataSubjectCreateRequest();
        request.setCreatedDate(OffsetDateTime.now());
        request.setDescription("description");
        request.setSubjectNip(subjectNip);
        request.setSubunits(List.of(new Subunit(subunitNip, "test")));
        request.setSubjectType(SubjectTypeTestData.VAT_GROUP);

        ksefClient.createTestSubject(request);

        AuthTokensPair token = authWithCustomNip(subjectNip, subjectNip);

        //when
        SubordinateEntityRolesQueryRequest queryRequest = new SubordinateEntityRolesQueryRequest();
        queryRequest.setSubordinateEntityIdentifier(new EntityPermissionsSubordinateEntityIdentifier(EntityPermissionsSubordinateEntityIdentifier.IdentifierType.NIP, subunitNip));
        SubordinateEntityRolesQueryResponse response = ksefClient.searchSubordinateEntityInvoiceRoles(queryRequest, 0, 10, token.accessToken());

        //then
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getRoles().isEmpty());

        ksefClient.removeTestSubject(new TestDataSubjectRemoveRequest(subjectNip));
    }
}
