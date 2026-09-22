package pl.akmf.ksef.sdk.client.model.limit;

public class CollectiveIdentifierRateLimit extends RateLimitBase {

    public CollectiveIdentifierRateLimit() {
    }

    public CollectiveIdentifierRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
