package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity globalne dla wszystkich operacji API (per adres IP).
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimitValues}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GlobalRateLimit extends RateLimitBase {

    public GlobalRateLimit() {
    }

    public GlobalRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
