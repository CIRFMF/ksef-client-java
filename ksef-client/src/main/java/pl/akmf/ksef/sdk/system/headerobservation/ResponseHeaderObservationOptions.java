package pl.akmf.ksef.sdk.system.headerobservation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// Konfiguruje, które nagłówki odpowiedzi HTTP mają być zgłaszane przez ResponseHeaderCaptureHandler
// Domyślnie wyłączona.
public class ResponseHeaderObservationOptions {

    // Włącza obserwację nagłówków odpowiedzi. Domyślnie wyłączone.
    public boolean enabled = false;

    // Nazwy nagłówków odpowiedzi, o których zgłaszane będą zdarzenia. Porównanie bez uwzględniania
    // wielkości liter. Domyślnie zawiera nagłówki ostrzeżeń systemowych API KSeF; można dodać
    // kolejne bez zmian w kodzie klienta. np. w application.yaml
    public Set<String> headerNames = new HashSet<>(Collections.singleton("X-System-Warning"));

    public ResponseHeaderObservationOptions() {
    }

    public ResponseHeaderObservationOptions(boolean enabled, Set<String> headerNames) {
        this.enabled = enabled;
        this.headerNames = headerNames;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getHeaderNames() {
        return headerNames;
    }

    public void setHeaderNames(Set<String> headerNames) {
        this.headerNames = headerNames;
    }
}
