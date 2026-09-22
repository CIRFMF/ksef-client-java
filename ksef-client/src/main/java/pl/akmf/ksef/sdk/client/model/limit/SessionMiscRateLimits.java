package pl.akmf.ksef.sdk.client.model.limit;

public class SessionMiscRateLimits extends RateLimitBase {

    public SessionMiscRateLimits() {

    }

    public SessionMiscRateLimits(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
