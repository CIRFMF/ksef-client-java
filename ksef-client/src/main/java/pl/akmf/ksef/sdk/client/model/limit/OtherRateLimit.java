package pl.akmf.ksef.sdk.client.model.limit;

public class OtherRateLimit extends RateLimitBase {

    public OtherRateLimit() {

    }

    public OtherRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }

}
