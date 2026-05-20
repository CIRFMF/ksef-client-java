package pl.akmf.ksef.sdk.system.circuitbreaker;

import java.time.Duration;

// Wyjątek zgłaszany, gdy lokalny Circuit Breaker jest otwarty
// i żądanie HTTP zostało zablokowane (fail-fast).
public class KsefCircuitBreakerOpenException extends RuntimeException {

    private final Duration retryAfter;

    /**
     * Inicjalizuje nową instancję klasy
     *
     * @param message    - Szczegóły wyjątku.
     * @param retryAfter - Szacowany czas ponownej próby.
     */
    public KsefCircuitBreakerOpenException(String message, Duration retryAfter) {
        super(message);
        this.retryAfter = retryAfter;
    }

    public Duration getRetryAfter() {
        return retryAfter;
    }
}
