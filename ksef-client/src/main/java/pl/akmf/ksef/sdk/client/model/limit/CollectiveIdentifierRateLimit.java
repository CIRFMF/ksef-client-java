package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla identyfikatorów zbiorczych
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimitValues, ApiRateLimitValuesOverride}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierRateLimit extends RateLimitBase {

    public CollectiveIdentifierRateLimit() {
    }

    public CollectiveIdentifierRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
