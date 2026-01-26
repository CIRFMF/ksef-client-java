package pl.akmf.ksef.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.DefaultLighthouseKsefClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefMessagesResponse;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefStatusResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;

import java.util.List;

class LighthouseIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultLighthouseKsefClient lighthouseClient;

    @Test
    void shouldReturnStatus() throws ApiException {
        // when
        KsefStatusResponse response = lighthouseClient.getStatus();

        // then
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getStatus());
        // Dopuszczalne wartosci wg kontraktu: AVAILABLE, MAINTENANCE, FAILURE, TOTAL_FAILURE
        Assertions.assertTrue(
                List.of("AVAILABLE", "MAINTENANCE", "FAILURE", "TOTAL_FAILURE")
                        .contains(response.getStatus()));

        if (response.getMessages() != null) {
            response.getMessages().forEach(msg -> Assertions.assertAll(
                    () -> Assertions.assertTrue(msg.getId() != null && !msg.getId().isBlank(), "id"),
                    () -> Assertions.assertTrue(msg.getType() != null && !msg.getType().isBlank(), "type"),
                    () -> Assertions.assertTrue(msg.getTitle() != null && !msg.getTitle().isBlank(), "title"),
                    () -> Assertions.assertTrue(msg.getText() != null && !msg.getText().isBlank(), "text"),
                    () -> Assertions.assertNotNull(msg.getStart(), "start")
            ));
        }
    }

    @Test
    void shouldReturnMessages() throws ApiException {
        // when
        KsefMessagesResponse response = lighthouseClient.getMessages();

        // then
        Assertions.assertNotNull(response);

        response.forEach(msg -> Assertions.assertAll(
                () -> Assertions.assertTrue(msg.getId() != null && !msg.getId().isBlank(), "id"),
                () -> Assertions.assertTrue(msg.getType() != null && !msg.getType().isBlank(), "type"),
                () -> Assertions.assertTrue(msg.getTitle() != null && !msg.getTitle().isBlank(), "title"),
                () -> Assertions.assertTrue(msg.getText() != null && !msg.getText().isBlank(), "text"),
                () -> Assertions.assertNotNull(msg.getStart(), "start")
        ));
    }
}
