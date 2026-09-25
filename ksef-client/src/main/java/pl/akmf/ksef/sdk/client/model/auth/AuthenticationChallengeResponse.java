package pl.akmf.ksef.sdk.client.model.auth;

import java.time.Instant;

/**
 * AuthenticationChallengeResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthenticationChallengeResponse {

    private String challenge;

    /**
     * Adres IP klienta.
     */
    private String clientIp;

    /**
     * Czas wygenerowania challenge-a.
     */
    private Instant timestamp;

    /**
     * Czas wygenerowania challenge-a w milisekundach od 1 stycznia 1970 roku (Unix timestamp).
     */
    private long timestampMs;

    public AuthenticationChallengeResponse() {
    }

    public AuthenticationChallengeResponse(String challenge, Instant timestamp) {
        this.challenge = challenge;
        this.timestamp = timestamp;
    }

    public AuthenticationChallengeResponse(String challenge, Instant timestamp, long timestampMs) {
        this.challenge = challenge;
        this.timestamp = timestamp;
        this.timestampMs = timestampMs;
    }

    public AuthenticationChallengeResponse(String challenge, String clientIp, Instant timestamp, long timestampMs) {
        this.challenge = challenge;
        this.clientIp = clientIp;
        this.timestamp = timestamp;
        this.timestampMs = timestampMs;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    /**
     * Czas wygenerowania challenge-a.
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Czas wygenerowania challenge-a.
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Czas wygenerowania challenge-a w milisekundach od 1 stycznia 1970 roku (Unix timestamp).
     */
    public long getTimestampMs() {
        return timestampMs;
    }

    /**
     * Czas wygenerowania challenge-a w milisekundach od 1 stycznia 1970 roku (Unix timestamp).
     */
    public void setTimestampMs(long timestampMs) {
        this.timestampMs = timestampMs;
    }

    /**
     * Adres IP klienta.
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * Adres IP klienta.
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
