package pl.akmf.ksef.sdk.client.model.limit;

/**
 * EffectiveApiRateLimitValues, ApiRateLimitValuesOverride.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceExportStatusRateLimit extends RateLimitBase {

    public InvoiceExportStatusRateLimit() {
    }

    public InvoiceExportStatusRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
