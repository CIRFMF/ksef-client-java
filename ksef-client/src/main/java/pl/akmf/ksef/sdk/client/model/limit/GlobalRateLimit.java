package pl.akmf.ksef.sdk.client.model.limit;

public class GlobalRateLimit extends RateLimitBase {

    public GlobalRateLimit() {
    }

    public GlobalRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
