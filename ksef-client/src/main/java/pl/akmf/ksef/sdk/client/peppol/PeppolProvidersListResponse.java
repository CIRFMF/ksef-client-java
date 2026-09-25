package pl.akmf.ksef.sdk.client.peppol;

import java.util.List;

/**
 * PeppolProvidersListResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code QueryPeppolProvidersResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PeppolProvidersListResponse {

    /**
     * Lista dostawców usług Peppol.
     */
    private List<PeppolProvider> peppolProviders;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    public PeppolProvidersListResponse() {
    }

    /**
     * Lista dostawców usług Peppol.
     */
    public List<PeppolProvider> getPeppolProviders() {
        return peppolProviders;
    }

    /**
     * Lista dostawców usług Peppol.
     */
    public void setPeppolProviders(List<PeppolProvider> peppolProviders) {
        this.peppolProviders = peppolProviders;
    }

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
