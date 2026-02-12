package pl.akmf.ksef.sdk.client.model;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class TooManyRequestsResponse extends StatusInfo {
    // Pobiera opóźnienie ponawiania w sekundach z nagłówka Retry-After, jeśli zostało podane.
    private Integer retryAfterSeconds;
    // Pobiera datę ponawiania z nagłówka Retry-After, jeśli zostało podane jako data.
    private OffsetDateTime retryAfterDate;
    // Pobiera zalecane opóźnienie przed następną próbą.
    // Obliczone na podstawie nagłówka Retry-After lub domyślnie z wykładniczym wycofywaniem.
    private Duration recommendedDelay;

    public TooManyRequestsResponse() {
    }

    public TooManyRequestsResponse(Integer code, String description, List<String> details) {
        super(code, description, details);
    }

    public TooManyRequestsResponse(Integer code, String description, List<String> details, Map<String, String> extensions) {
        super(code, description, details, extensions);
    }

    public Integer getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    public void setRetryAfterSeconds(Integer retryAfterSeconds) {
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public OffsetDateTime getRetryAfterDate() {
        return retryAfterDate;
    }

    public void setRetryAfterDate(OffsetDateTime retryAfterDate) {
        this.retryAfterDate = retryAfterDate;
    }

    public Duration getRecommendedDelay() {
        return recommendedDelay;
    }

    public void setRecommendedDelay(Duration recommendedDelay) {
        this.recommendedDelay = recommendedDelay;
    }

    @Override
    public String toString() {
        return "retryAfterSeconds=" + retryAfterSeconds +
                ", retryAfterDate=" + retryAfterDate +
                ", recommendedDelay=" + recommendedDelay;
    }
}
