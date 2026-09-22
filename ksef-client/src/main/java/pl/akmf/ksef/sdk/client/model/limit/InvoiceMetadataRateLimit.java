package pl.akmf.ksef.sdk.client.model.limit;

public class InvoiceMetadataRateLimit extends RateLimitBase {

    public InvoiceMetadataRateLimit() {

    }

    public InvoiceMetadataRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
