package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

/**
 * CollectiveIdentifierInvoicesQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierInvoicesQueryRequest {

    /**
     * Numery identyfikatorów zbiorczych. Maksymalna liczba to 10.
     */
    private List<String> collectiveIdentifierNumbers;

    public CollectiveIdentifierInvoicesQueryRequest() {
    }

    public CollectiveIdentifierInvoicesQueryRequest(List<String> collectiveIdentifierNumbers) {
        this.collectiveIdentifierNumbers = collectiveIdentifierNumbers;
    }

    /**
     * Numery identyfikatorów zbiorczych. Maksymalna liczba to 10.
     */
    public List<String> getCollectiveIdentifierNumbers() {
        return collectiveIdentifierNumbers;
    }

    /**
     * Numery identyfikatorów zbiorczych. Maksymalna liczba to 10.
     */
    public void setCollectiveIdentifierNumbers(List<String> collectiveIdentifierNumbers) {
        this.collectiveIdentifierNumbers = collectiveIdentifierNumbers;
    }
}
