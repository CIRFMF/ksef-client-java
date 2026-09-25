package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * CertificateLimit.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateLimit {

    /**
     * Pozostała wartość limitu.
     */
    private Integer remaining;

    /**
     * Maksymalna liczba zasobów dozwolona w ramach limitu.
     */
    private Integer limit;

    public CertificateLimit() {
    }

    public CertificateLimit(Integer remaining, Integer limit) {
        this.remaining = remaining;
        this.limit = limit;
    }

    /**
     * Pozostała wartość limitu.
     */
    public Integer getRemaining() {
        return remaining;
    }

    /**
     * Pozostała wartość limitu.
     */
    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    /**
     * Maksymalna liczba zasobów dozwolona w ramach limitu.
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Maksymalna liczba zasobów dozwolona w ramach limitu.
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
