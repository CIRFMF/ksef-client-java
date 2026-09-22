package pl.akmf.ksef.sdk.client.model.limit;

public class SessionInvoiceListRateLimit extends RateLimitBase {

    public SessionInvoiceListRateLimit() {

    }

    public SessionInvoiceListRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
