package pl.akmf.ksef.sdk.client.model.limit;

public class AnonymousRateLimit extends RateLimitBase {

    public AnonymousRateLimit() {
    }

    public AnonymousRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }

}
