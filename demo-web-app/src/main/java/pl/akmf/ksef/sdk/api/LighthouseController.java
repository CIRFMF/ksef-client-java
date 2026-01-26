package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefMessagesResponse;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefStatusResponse;

// Kontroler demonstracyjny prezentujący działanie klienta Latarni (status systemu KSeF i komunikaty).
@RestController
@RequiredArgsConstructor
public class LighthouseController {

    private final DefaultLighthouseKsefClient lighthouseClient;

    // Zwraca bieżący status systemu KSeF wg Latarni.
    @GetMapping("/lighthouse/status")
    public KsefStatusResponse getLighthouseStatus() throws ApiException {
        return lighthouseClient.getStatus();
    }

    // Zwraca bieżące komunikaty Latarni.
    @GetMapping("/lighthouse/messages")
    public KsefMessagesResponse getLighthouseMessages() throws ApiException {
        return lighthouseClient.getMessages();
    }
}
