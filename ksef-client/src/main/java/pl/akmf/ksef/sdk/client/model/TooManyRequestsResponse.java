package pl.akmf.ksef.sdk.client.model;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * TooManyRequestsResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TooManyRequestsResponse extends StatusInfo {

    /**
     * Pobiera opóźnienie ponawiania w sekundach z nagłówka Retry-After, jeśli zostało podane.
     */
    private Integer retryAfterSeconds;

    /**
     * Pobiera datę ponawiania z nagłówka Retry-After, jeśli zostało podane jako data.
     */
    private OffsetDateTime retryAfterDate;

    /**
     * Pobiera zalecane opóźnienie przed następną próbą.
     * Obliczone na podstawie nagłówka Retry-After lub domyślnie z wykładniczym wycofywaniem.
     */
    private Duration recommendedDelay;

    public TooManyRequestsResponse() {
    }

    public TooManyRequestsResponse(Integer code, String description, List<String> details) {
        super(code, description, details);
    }

    public TooManyRequestsResponse(Integer code, String description, List<String> details, Map<String, String> extensions) {
        super(code, description, details, extensions);
    }

    /**
     * Pobiera opóźnienie ponawiania w sekundach z nagłówka Retry-After, jeśli zostało podane.
     */
    public Integer getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    /**
     * Pobiera opóźnienie ponawiania w sekundach z nagłówka Retry-After, jeśli zostało podane.
     */
    public void setRetryAfterSeconds(Integer retryAfterSeconds) {
        this.retryAfterSeconds = retryAfterSeconds;
    }

    /**
     * Pobiera datę ponawiania z nagłówka Retry-After, jeśli zostało podane jako data.
     */
    public OffsetDateTime getRetryAfterDate() {
        return retryAfterDate;
    }

    /**
     * Pobiera datę ponawiania z nagłówka Retry-After, jeśli zostało podane jako data.
     */
    public void setRetryAfterDate(OffsetDateTime retryAfterDate) {
        this.retryAfterDate = retryAfterDate;
    }

    /**
     * Pobiera zalecane opóźnienie przed następną próbą.
     * Obliczone na podstawie nagłówka Retry-After lub domyślnie z wykładniczym wycofywaniem.
     */
    public Duration getRecommendedDelay() {
        return recommendedDelay;
    }

    /**
     * Pobiera zalecane opóźnienie przed następną próbą.
     * Obliczone na podstawie nagłówka Retry-After lub domyślnie z wykładniczym wycofywaniem.
     */
    public void setRecommendedDelay(Duration recommendedDelay) {
        this.recommendedDelay = recommendedDelay;
    }

    @Override
    public String toString() {
        return "retryAfterSeconds=" + retryAfterSeconds + ", retryAfterDate=" + retryAfterDate + ", recommendedDelay=" + recommendedDelay;
    }
}
