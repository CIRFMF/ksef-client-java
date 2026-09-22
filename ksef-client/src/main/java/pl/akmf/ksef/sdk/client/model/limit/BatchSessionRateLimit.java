package pl.akmf.ksef.sdk.client.model.limit;

public class BatchSessionRateLimit extends RateLimitBase {

    public BatchSessionRateLimit() {

    }

    public BatchSessionRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
