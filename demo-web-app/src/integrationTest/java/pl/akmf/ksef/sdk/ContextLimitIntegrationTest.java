package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.limit.BatchSessionLimit;
import pl.akmf.ksef.sdk.client.model.limit.ChangeContextLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.GetContextLimitResponse;
import pl.akmf.ksef.sdk.client.model.limit.OnlineSessionLimit;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;

class ContextLimitIntegrationTest extends BaseIntegrationTest {

    @Test
    void contextLimitE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        changeContextLimit(accessToken);

        GetContextLimitResponse limitAfterChanges = getExpectedResponseAfterChanges();

        getContextLimit(accessToken, limitAfterChanges);

        resetContextLimit(accessToken);

        GetContextLimitResponse baseLimits = getExpectedBaseResponse();

        getContextLimit(accessToken, baseLimits);
    }

    private void getContextLimit(String accessToken, GetContextLimitResponse expectedResponse) throws ApiException {
        GetContextLimitResponse response = ksefClient.getContextSessionLimit(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse.getBatchSession().getMaxInvoices(), response.getBatchSession().getMaxInvoices());
        Assertions.assertEquals(expectedResponse.getBatchSession().getMaxInvoiceWithAttachmentSizeInMB(),
                response.getBatchSession().getMaxInvoiceWithAttachmentSizeInMB());
        Assertions.assertEquals(expectedResponse.getBatchSession().getMaxInvoiceSizeInMB(),
                response.getBatchSession().getMaxInvoiceSizeInMB());
        Assertions.assertEquals(expectedResponse.getOnlineSession().getMaxInvoices(), response.getOnlineSession().getMaxInvoices());
        Assertions.assertEquals(expectedResponse.getOnlineSession().getMaxInvoiceSizeInMB(),
                response.getOnlineSession().getMaxInvoiceSizeInMB());
        Assertions.assertEquals(expectedResponse.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB(),
                response.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB());

    }

    private void changeContextLimit(String accessToken) throws ApiException {
        ChangeContextLimitRequest request = new ChangeContextLimitRequest();
        OnlineSessionLimit onlineSessionLimit = new OnlineSessionLimit();
        onlineSessionLimit.setMaxInvoiceSizeInMB(4);
        onlineSessionLimit.setMaxInvoiceWithAttachmentSizeInMB(5);
        onlineSessionLimit.setMaxInvoices(6);
        BatchSessionLimit batchSessionLimit = new BatchSessionLimit();
        batchSessionLimit.setMaxInvoiceSizeInMB(4);
        batchSessionLimit.setMaxInvoiceWithAttachmentSizeInMB(5);
        batchSessionLimit.setMaxInvoices(6);
        request.setOnlineSession(onlineSessionLimit);
        request.setBatchSession(batchSessionLimit);
        request.setOnlineSession(onlineSessionLimit);
        request.setBatchSession(batchSessionLimit);

        ksefClient.changeContextLimitTest(request, accessToken);
    }

    private void resetContextLimit(String accessToken) throws ApiException {
        ksefClient.resetContextLimitTest(accessToken);
    }

    private GetContextLimitResponse getExpectedBaseResponse() {
        GetContextLimitResponse response = new GetContextLimitResponse();
        OnlineSessionLimit onlineSessionLimit = new OnlineSessionLimit();
        onlineSessionLimit.setMaxInvoiceSizeInMB(1);
        onlineSessionLimit.setMaxInvoiceWithAttachmentSizeInMB(10);
        onlineSessionLimit.setMaxInvoices(100);
        BatchSessionLimit batchSessionLimit = new BatchSessionLimit();
        batchSessionLimit.setMaxInvoiceSizeInMB(1);
        batchSessionLimit.setMaxInvoiceWithAttachmentSizeInMB(10);
        batchSessionLimit.setMaxInvoices(100);
        response.setOnlineSession(onlineSessionLimit);
        response.setBatchSession(batchSessionLimit);

        return response;
    }

    private GetContextLimitResponse getExpectedResponseAfterChanges() {
        GetContextLimitResponse response = new GetContextLimitResponse();
        OnlineSessionLimit onlineSessionLimit = new OnlineSessionLimit();
        onlineSessionLimit.setMaxInvoiceSizeInMB(4);
        onlineSessionLimit.setMaxInvoiceWithAttachmentSizeInMB(5);
        onlineSessionLimit.setMaxInvoices(6);
        BatchSessionLimit batchSessionLimit = new BatchSessionLimit();
        batchSessionLimit.setMaxInvoiceSizeInMB(4);
        batchSessionLimit.setMaxInvoiceWithAttachmentSizeInMB(5);
        batchSessionLimit.setMaxInvoices(6);
        response.setOnlineSession(onlineSessionLimit);
        response.setBatchSession(batchSessionLimit);

        response.setOnlineSession(onlineSessionLimit);
        response.setBatchSession(batchSessionLimit);

        return response;
    }
}