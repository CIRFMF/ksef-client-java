package pl.akmf.ksef.sdk.client.model.limit;

public class InvoiceStatusRateLimit extends RateLimitBase {

    public InvoiceStatusRateLimit() {

    }

    public InvoiceStatusRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
