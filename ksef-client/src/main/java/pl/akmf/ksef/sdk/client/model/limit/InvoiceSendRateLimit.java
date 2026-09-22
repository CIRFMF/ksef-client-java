package pl.akmf.ksef.sdk.client.model.limit;

public class InvoiceSendRateLimit extends RateLimitBase {

    public InvoiceSendRateLimit() {

    }

    public InvoiceSendRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
