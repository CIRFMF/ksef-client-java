package pl.akmf.ksef.sdk.client.model.limit;

public class InvoiceExportStatusRateLimit extends RateLimitBase {

    public InvoiceExportStatusRateLimit() {

    }

    public InvoiceExportStatusRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
