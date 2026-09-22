package pl.akmf.ksef.sdk.client.model.limit;

public class SessionListRateLimit extends RateLimitBase {

    public SessionListRateLimit() {

    }

    public SessionListRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
