package pl.akmf.ksef.sdk.client.model.limit;

public class InvoiceDownloadRateLimit extends RateLimitBase {

    public InvoiceDownloadRateLimit() {

    }

    public InvoiceDownloadRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
