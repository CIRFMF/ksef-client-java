package pl.akmf.ksef.sdk.client.model.limit;

public class OnlineSessionRateLimit extends RateLimitBase {

    public OnlineSessionRateLimit() {

    }

    public OnlineSessionRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
