package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

public class CollectiveIdentifierInvoicesQueryRequest {

    // Numery identyfikatorów zbiorczych. Maksymalna liczba to 10.
    private List<String> collectiveIdentifierNumbers;

    public CollectiveIdentifierInvoicesQueryRequest() {
    }

    public CollectiveIdentifierInvoicesQueryRequest(List<String> collectiveIdentifierNumbers) {
        this.collectiveIdentifierNumbers = collectiveIdentifierNumbers;
    }

    public List<String> getCollectiveIdentifierNumbers() {
        return collectiveIdentifierNumbers;
    }

    public void setCollectiveIdentifierNumbers(List<String> collectiveIdentifierNumbers) {
        this.collectiveIdentifierNumbers = collectiveIdentifierNumbers;
    }
}
