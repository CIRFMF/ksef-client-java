package pl.akmf.ksef.sdk.client.model.limit;

/**
 * SetRateLimitsRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SetRateLimitsRequest {

    /**
     * Limity dla ilości żądań do API.
     */
    private ApiRateLimitsChangeRequest rateLimits;

    public SetRateLimitsRequest() {
    }

    public SetRateLimitsRequest(ApiRateLimitsChangeRequest rateLimits) {
        this.rateLimits = rateLimits;
    }

    /**
     * Limity dla ilości żądań do API.
     */
    public ApiRateLimitsChangeRequest getRateLimits() {
        return rateLimits;
    }

    /**
     * Limity dla ilości żądań do API.
     */
    public void setRateLimits(ApiRateLimitsChangeRequest rateLimits) {
        this.rateLimits = rateLimits;
    }
}
