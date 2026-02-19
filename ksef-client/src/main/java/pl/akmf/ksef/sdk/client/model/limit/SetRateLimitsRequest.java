package pl.akmf.ksef.sdk.client.model.limit;

public class SetRateLimitsRequest {

    private EffectiveApiRateLimits rateLimits;

    public SetRateLimitsRequest() {
    }

    public SetRateLimitsRequest(EffectiveApiRateLimits rateLimits) {
        this.rateLimits = rateLimits;
    }

    public EffectiveApiRateLimits getRateLimits() {
        return rateLimits;
    }

    public void setRateLimits(EffectiveApiRateLimits rateLimits) {
        this.rateLimits = rateLimits;
    }
}