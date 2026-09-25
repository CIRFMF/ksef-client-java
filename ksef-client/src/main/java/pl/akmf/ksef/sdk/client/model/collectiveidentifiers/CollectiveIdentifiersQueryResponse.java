package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

/**
 * CollectiveIdentifiersQueryResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifiersQueryResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista identyfikatorów zbiorczych.
     */
    private List<CollectiveIdentifiersQueryResponseItem> collectiveIdentifiers;

    public CollectiveIdentifiersQueryResponse() {
    }

    public CollectiveIdentifiersQueryResponse(String continuationToken, List<CollectiveIdentifiersQueryResponseItem> collectiveIdentifiers) {
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
    public List<CollectiveIdentifiersQueryResponseItem> getCollectiveIdentifiers() {
        return collectiveIdentifiers;
    }

    /**
     * Lista identyfikatorów zbiorczych.
     */
    public void setCollectiveIdentifiers(List<CollectiveIdentifiersQueryResponseItem> collectiveIdentifiers) {
        this.collectiveIdentifiers = collectiveIdentifiers;
    }
}
