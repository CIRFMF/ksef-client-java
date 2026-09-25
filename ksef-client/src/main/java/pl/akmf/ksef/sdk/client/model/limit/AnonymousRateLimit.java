package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla anonimowych operacji API (nieuwierzytelnionych).
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimitValues}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AnonymousRateLimit extends RateLimitBase {

    public AnonymousRateLimit() {
    }

    public AnonymousRateLimit(int perSecond, int perMinute, int perHour) {
        super(perSecond, perMinute, perHour);
    }
}
