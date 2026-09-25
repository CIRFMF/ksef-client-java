package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

/**
 * CollectiveIdentifiersByKsefNumberQueryResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifiersByKsefNumberQueryResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista identyfikatorów zbiorczych.
     */
    private List<CollectiveIdentifiersByKsefNumberQueryResponseItem> collectiveIdentifiers;

    public CollectiveIdentifiersByKsefNumberQueryResponse() {
    }

    public CollectiveIdentifiersByKsefNumberQueryResponse(String continuationToken, List<CollectiveIdentifiersByKsefNumberQueryResponseItem> collectiveIdentifiers) {
        this.continuationToken = continuationToken;
        this.collectiveIdentifiers = collectiveIdentifiers;
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public String getContinuationToken() {
        return continuationToken;
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    /**
     * Lista identyfikatorów zbiorczych.
     */
    public List<CollectiveIdentifiersByKsefNumberQueryResponseItem> getCollectiveIdentifiers() {
        return collectiveIdentifiers;
    }

    /**
     * Lista identyfikatorów zbiorczych.
     */
    public void setCollectiveIdentifiers(List<CollectiveIdentifiersByKsefNumberQueryResponseItem> collectiveIdentifiers) {
        this.collectiveIdentifiers = collectiveIdentifiers;
    }
}
