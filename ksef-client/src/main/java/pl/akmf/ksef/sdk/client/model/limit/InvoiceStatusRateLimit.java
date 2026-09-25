package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla pobierania statusu faktury z sesji.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimitValues, ApiRateLimitValuesOverride}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceStatusRateLimit extends RateLimitBase {

    public InvoiceStatusRateLimit() {
    }

    public InvoiceStatusRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
