package pl.akmf.ksef.sdk.client.model.limit;

public class CollectiveIdentifierSessionLimits {

    private int maxInvoices;

    public CollectiveIdentifierSessionLimits() {
    }

    public CollectiveIdentifierSessionLimits(int maxInvoices) {
        this.maxInvoices = maxInvoices;
    }

    public int getMaxInvoices() {
        return maxInvoices;
    }

    public void setMaxInvoices(int maxInvoices) {
        this.maxInvoices = maxInvoices;
    }
}
