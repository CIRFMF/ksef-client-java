package pl.akmf.ksef.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.peppol.PeppolProvidersListResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;

public class PeppolIIntegrationTest extends BaseIntegrationTest {

    @Test
    void peppolProviderIntegrationTest() throws ApiException {

        PeppolProvidersListResponse response = ksefClient.getPeppolProvidersList(0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(!response.getPeppolProviders().isEmpty());
    }
}