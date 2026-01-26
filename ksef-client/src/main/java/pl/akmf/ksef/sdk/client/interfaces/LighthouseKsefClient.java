package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefMessagesResponse;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefStatusResponse;

// Klient do odczytu statusu i komunikatów Latarni.
public interface LighthouseKsefClient {

    // Pobiera aktualny status systemu KSeF wraz z ewentualnymi komunikatami.
    KsefStatusResponse getStatus() throws ApiException;

    // Pobiera bieżące komunikaty Latarni.
    KsefMessagesResponse getMessages() throws ApiException;
}