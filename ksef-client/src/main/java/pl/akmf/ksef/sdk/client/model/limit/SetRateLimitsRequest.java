package pl.akmf.ksef.sdk.client.model.limit;

public class SetRateLimitsRequest {

    private ApiRateLimitsChangeRequest rateLimits;

    public SetRateLimitsRequest() {
    }

    public SetRateLimitsRequest(ApiRateLimitsChangeRequest rateLimits) {
        this.rateLimits = rateLimits;
    }

    public ApiRateLimitsChangeRequest getRateLimits() {
        return rateLimits;
    }

    public void setRateLimits(ApiRateLimitsChangeRequest rateLimits) {
        this.rateLimits = rateLimits;
    }
}