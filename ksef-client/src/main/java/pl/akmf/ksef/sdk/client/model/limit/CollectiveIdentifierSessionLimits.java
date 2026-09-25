package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla identyfikatorów zbiorczych.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code CollectiveIdentifierEffectiveContextLimits, CollectiveIdentifierContextLimitsOverride}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierSessionLimits {

    /**
     * Maksymalna ilość faktur które można przesłać w pojedynczym identyfikatorze zbiorczym.
     */
    private int maxInvoices;

    public CollectiveIdentifierSessionLimits() {
    }

    public CollectiveIdentifierSessionLimits(int maxInvoices) {
        this.maxInvoices = maxInvoices;
    }

    /**
     * Maksymalna ilość faktur które można przesłać w pojedynczym identyfikatorze zbiorczym.
     */
    public int getMaxInvoices() {
        return maxInvoices;
    }

    /**
     * Maksymalna ilość faktur które można przesłać w pojedynczym identyfikatorze zbiorczym.
     */
    public void setMaxInvoices(int maxInvoices) {
        this.maxInvoices = maxInvoices;
    }
}
