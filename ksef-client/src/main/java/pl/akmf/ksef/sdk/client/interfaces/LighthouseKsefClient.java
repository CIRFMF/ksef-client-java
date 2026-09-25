package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefMessagesResponse;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefStatusResponse;

/**
 * Klient do odczytu statusu i komunikatów usługi statusowej KSeF ("Latarnia" / Lighthouse).
 */
public interface LighthouseKsefClient {

    /**
     * Pobiera aktualny status systemu KSeF (np. dostępność, przerwa techniczna, awaria).
     *
     * @return Aktualny status systemu KSeF.
     */
    KsefStatusResponse getStatus() throws ApiException;

    /**
     * Pobiera bieżące komunikaty usługi statusowej KSeF ("Latarnia").
     *
     * @return Lista bieżących komunikatów.
     */
    KsefMessagesResponse getMessages() throws ApiException;
}
