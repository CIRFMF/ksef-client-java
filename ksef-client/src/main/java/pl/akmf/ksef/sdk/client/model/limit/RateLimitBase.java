package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla pobierania listy faktur w sesji.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimitValues}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public abstract class RateLimitBase {

    /**
     * Limit na sekundę.
     */
    private int perSecond;

    /**
     * Limit na minutę.
     */
    private int perMinute;

    /**
     * Limit na godzinę.
     */
    private int perHour;

    public RateLimitBase() {
    }

    public RateLimitBase(int perSecond, int perMinute, int perHour) {
        this.perSecond = perSecond;
        this.perMinute = perMinute;
        this.perHour = perHour;
    }

    /**
     * Limit na sekundę.
     */
    public int getPerSecond() {
        return perSecond;
    }

    /**
     * Limit na sekundę.
     */
    public void setPerSecond(int perSecond) {
        this.perSecond = perSecond;
    }

    /**
     * Limit na minutę.
     */
    public int getPerMinute() {
        return perMinute;
    }

    /**
     * Limit na minutę.
     */
    public void setPerMinute(int perMinute) {
        this.perMinute = perMinute;
    }

    /**
     * Limit na godzinę.
     */
    public int getPerHour() {
        return perHour;
    }

    /**
     * Limit na godzinę.
     */
    public void setPerHour(int perHour) {
        this.perHour = perHour;
    }
}
