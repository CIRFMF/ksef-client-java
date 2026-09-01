package pl.akmf.ksef.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;

import java.util.List;
import java.util.Map;

class ResponseHeaderObservationIntegrationTests extends BaseIntegrationTest {

    private static final String testSystemWarningHeader = "X-Test-System-Warning";
    private static final String systemWarningHeader = "X-System-Warning";
    private static final String forcedWarningMessage = "Test-forced-system-warning";

    // Test weryfikujący, że nagłówek X-System-Warning
    // (wymuszony na środowisku TEST przez X-Test-System-Warning) da się odczytać przez subskrypcję
    @Test
    void testSubscriptionResponseHeaders() throws ApiException {

        ksefClient.getResponseHeaderCaptureHandler().subscribe(systemWarningHeader);
        // wymuszamy X-System-Warning przez X-Test-System-Warning na tym jednym żądaniu,
        ksefClient.addDefaultHeader(testSystemWarningHeader, forcedWarningMessage);
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        Assertions.assertNotNull(challenge);
        Assertions.assertNotNull(challenge.getChallenge());

        Map<String, Map<String, List<String>>> capturedHeaders = ksefClient.getResponseHeaderCaptureHandler().getCaptured();
        ksefClient.getResponseHeaderCaptureHandler().clear(); // po każdym requeście należy wyczyścić subskrybowane nagłówki
        Assertions.assertTrue(capturedHeaders.entrySet().stream()
                .anyMatch(entry -> {
                    var values = entry.getValue().get(systemWarningHeader.toLowerCase());
                    return values != null &&
                            values.stream()
                                    .anyMatch(v -> v.contains(forcedWarningMessage));
                })
        );

        // unsubscribe i sprawdzamy czy mamy w handlerze nagłówek
        ksefClient.getResponseHeaderCaptureHandler().unsubscribe(systemWarningHeader);
        challenge = ksefClient.getAuthChallenge();

        Assertions.assertNotNull(challenge);
        Assertions.assertNotNull(challenge.getChallenge());

        capturedHeaders = ksefClient.getResponseHeaderCaptureHandler().getCaptured();
        Assertions.assertTrue(capturedHeaders.isEmpty());

        // usuwamy z domyslnych nagłówków X-Test-System-Warning
        ksefClient.removeDefaultHeader(testSystemWarningHeader);
    }
}
