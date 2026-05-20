package pl.akmf.ksef.sdk.system.circuitbreaker;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.time.Instant;

import static pl.akmf.ksef.sdk.api.HttpStatus.INTERNAL_ERROR;
import static pl.akmf.ksef.sdk.api.HttpStatus.REQUEST_TIMEOUT;
import static pl.akmf.ksef.sdk.api.HttpStatus.TOO_MANY_REQUESTS;

public class KsefCircuitBreakerHandler {

    private final KsefCircuitBreakerOptions options;
    private final Object gate = new Object();
    private final Duration breakDuration;

    private final HttpClient httpClient;

    private CircuitState state = CircuitState.CLOSED;
    private Instant openUntilUtc = Instant.MIN;
    private int consecutiveFailures;
    private boolean halfOpenProbeInProgress;

    public KsefCircuitBreakerHandler(HttpClient httpClient, KsefCircuitBreakerOptions options) {
        this.httpClient = httpClient;
        this.options = options;

        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }

        if (options.getFailureThreshold() <= 0) {
            throw new IllegalArgumentException("failureThreshold must be > 0");
        }

        if (options.getBreakDurationSeconds() <= 0) {
            throw new IllegalArgumentException("breakDurationSeconds must be > 0");
        }

        this.breakDuration = Duration.ofSeconds(options.getBreakDurationSeconds());
    }

    public HttpResponse<byte[]> send(HttpRequest request, HttpResponse.BodyHandler<byte[]> bodyHandler) throws IOException, InterruptedException {
        if (!options.isEnabled()) {
            return httpClient.send(request, bodyHandler);
        }

        if (!tryAcquirePermission()) {
            throw new KsefCircuitBreakerOpenException("Circuit Breaker is OPEN. Retry after ~ " +
                    Math.max(1, getRetryAfterSeconds()), Duration.ofSeconds(getRetryAfterSeconds())
            );
        }

        try {
            HttpResponse<byte[]> response = httpClient.send(request, bodyHandler);

            if (isTransientFailure(response.statusCode())) {
                onFailure();
            } else {
                onSuccess();
            }

            return response;

        } catch (IOException | InterruptedException ex) {
            if (shouldCountAsFailure(ex)) {
                onFailure();
                throw ex;
            } else {
                releaseHalfOpenProbeWithoutStateChange();
                throw ex;
            }
        }
    }

    private boolean tryAcquirePermission() {
        Instant now = Instant.now();

        synchronized (gate) {
            if (state == CircuitState.OPEN) {
                if (now.isBefore(openUntilUtc)) {
                    return false;
                }

                state = CircuitState.HALF_OPEN;
                halfOpenProbeInProgress = false;
            }

            if (state == CircuitState.HALF_OPEN) {
                if (halfOpenProbeInProgress) {
                    return false;
                }

                halfOpenProbeInProgress = true;
            }

            return true;
        }
    }

    private void onSuccess() {
        synchronized (gate) {
            consecutiveFailures = 0;

            if (state == CircuitState.HALF_OPEN) {
                state = CircuitState.CLOSED;
                halfOpenProbeInProgress = false;
                openUntilUtc = Instant.MIN;
            }
        }
    }

    private void onFailure() {
        Instant now = Instant.now();

        synchronized (gate) {
            if (state == CircuitState.HALF_OPEN) {
                openCircuit(now);
                return;
            }

            if (state == CircuitState.CLOSED) {
                consecutiveFailures++;

                if (consecutiveFailures >= options.getFailureThreshold()) {
                    openCircuit(now);
                }
            }
        }
    }

    private void openCircuit(Instant now) {
        state = CircuitState.OPEN;
        openUntilUtc = now.plus(breakDuration);
        consecutiveFailures = 0;
        halfOpenProbeInProgress = false;
    }

    private void releaseHalfOpenProbeWithoutStateChange() {
        synchronized (gate) {
            if (state == CircuitState.HALF_OPEN) {
                halfOpenProbeInProgress = false;
            }
        }
    }

    private boolean isTransientFailure(int statusCode) {
        return statusCode >= INTERNAL_ERROR.getCode()
                || statusCode == TOO_MANY_REQUESTS.getCode()
                || statusCode == REQUEST_TIMEOUT.getCode();
    }

    private boolean shouldCountAsFailure(Exception ex) {
        if (ex instanceof IOException || ex instanceof HttpTimeoutException) {
            return true;
        }

        if (ex instanceof InterruptedException) {
            return false; // cancellation equivalent
        }

        return true;
    }

    private long getRetryAfterSeconds() {
        long seconds = Duration.between(Instant.now(), openUntilUtc).getSeconds();
        return Math.max(seconds, 1);
    }

    enum CircuitState {
        CLOSED,
        OPEN,
        HALF_OPEN
    }
}