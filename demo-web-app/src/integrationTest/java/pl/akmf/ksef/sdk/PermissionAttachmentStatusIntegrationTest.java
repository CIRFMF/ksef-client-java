package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.PermissionAttachmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataAttachmentRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataAttachmentRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;

class PermissionAttachmentStatusIntegrationTest extends BaseIntegrationTest {

    @Test
    void attachmentStatusTest() throws JAXBException, IOException, ApiException {
        // given
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();

        TestDataAttachmentRequest request = new TestDataAttachmentRequest();
        request.setNip(contextNip);

        ksefClient.addAttachmentPermissionTest(request);

        AuthTokensPair token = authWithCustomNip(contextNip, contextNip);

        //when
        PermissionAttachmentStatusResponse trueResponse = ksefClient.checkPermissionAttachmentInvoiceStatus(token.accessToken());

        //then
        Assertions.assertNotNull(trueResponse);
        Assertions.assertTrue(trueResponse.getIsAttachmentAllowed());

        TestDataAttachmentRemoveRequest removeRequest = new TestDataAttachmentRemoveRequest();
        removeRequest.setNip(contextNip);
        ksefClient.removeAttachmentPermissionTest(removeRequest);

        PermissionAttachmentStatusResponse falseResponse = ksefClient.checkPermissionAttachmentInvoiceStatus(token.accessToken());
        Assertions.assertNotNull(falseResponse);
        Assertions.assertFalse(falseResponse.getIsAttachmentAllowed());
    }
}
