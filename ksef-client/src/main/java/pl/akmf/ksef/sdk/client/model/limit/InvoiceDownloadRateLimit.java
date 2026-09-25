package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla pobierania faktur po numerze KSeF.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimitValues, ApiRateLimitValuesOverride}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceDownloadRateLimit extends RateLimitBase {

    public InvoiceDownloadRateLimit() {
    }

    public InvoiceDownloadRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
