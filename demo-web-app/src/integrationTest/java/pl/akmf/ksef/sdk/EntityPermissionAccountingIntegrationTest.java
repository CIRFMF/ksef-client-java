package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.entity.GrantEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class EntityPermissionAccountingIntegrationTest extends BaseIntegrationTest {

    private static final String DESCRIPTION = "E2E test grant";

    @Test
    void shouldReturnPermissionsSearchedBySubjectInEntityContext() throws JAXBException, IOException, ApiException {
        String jdgNip = IdentifierGeneratorUtils.generateRandomNIP(); //jdg
        String otherJdgNip = IdentifierGeneratorUtils.generateRandomNIP();  //inna jdg
        String brNip = IdentifierGeneratorUtils.generateRandomNIP();   // biuro rachunkowe
        String kdpNip = IdentifierGeneratorUtils.generateRandomNIP();  // kancelaria doradztwa podatkowego

        //uwierzytelnie jdg w własnym zakresie
        String accessTokenJdg = authWithCustomNip(jdgNip, jdgNip).accessToken();

        //nadanie uprawnień biuru rachunkowego
        String brGrantInJdg = grantPermission(brNip, accessTokenJdg);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(brGrantInJdg, accessTokenJdg));

        // nadanie uprawnień kancelarii doradztwa podatkowego
        String kdpGrantInJdg = grantPermission(kdpNip, accessTokenJdg);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(kdpGrantInJdg, accessTokenJdg));

        // uwierzytelnienie otherJdg we własnym kontekście
        String accessTokenOtherJdg = authWithCustomNip(otherJdgNip, otherJdgNip).accessToken();

        // nadanie uprawnień biuru rachunkowemu
        String brGRantInOtherJdg = grantPermission(brNip, accessTokenOtherJdg);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(brGRantInOtherJdg, accessTokenOtherJdg));

        // nadanie uprawnień kancelarii doradztwa podatkowego
        String kdpGrantInOtherJdg = grantPermission(kdpNip, accessTokenOtherJdg);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(kdpGrantInOtherJdg, accessTokenOtherJdg));

        // w tym momencie:
        // biuro rachunkowe ma uprawnienia w kontekście jdg i otherJdg (razem 4 uprawnienia)
        // kancelaria doradztwa podatkowego ma uprawnienia w kontekście jdg i otherJdg (razem 4 uprawnienia)

        // uwierzytelnienie: biuro rachunkowe w kontekście jdg
        String accessTokenBr = authWithCustomNip(jdgNip, brNip).accessToken();

        // uprawnienia biura rachunkowego w kontekście jdg

        QueryPersonalGrantRequest request = new QueryPersonalGrantRequest();
        QueryPersonalGrantResponse response = ksefClient.searchPersonalGrantPermission(request, 0, 10, accessTokenBr);

        Assertions.assertFalse(response.getPermissions().isEmpty());
        Assertions.assertEquals(2, response.getPermissions().size());
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private String grantPermission(String targetNip, String accessToken) throws ApiException {
        GrantEntityPermissionsRequest request = new GrantEntityPermissionsRequestBuilder()
                .withPermissions(List.of(
                        new EntityPermission(EntityPermissionType.INVOICE_READ, true),
                        new EntityPermission(EntityPermissionType.INVOICE_WRITE, false)))
                .withDescription(DESCRIPTION)
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, targetNip))
                .build();

        OperationResponse response = ksefClient.grantsPermissionEntity(request, accessToken);
        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }
}

