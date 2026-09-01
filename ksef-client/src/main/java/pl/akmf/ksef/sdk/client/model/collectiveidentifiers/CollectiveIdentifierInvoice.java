package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

public class CollectiveIdentifierInvoice {

    private String ksefNumber;
    private CollectiveIdentifierInvoicePayment payment;
    private String description;

    public CollectiveIdentifierInvoice() {
    }

    public CollectiveIdentifierInvoice(String ksefNumber, CollectiveIdentifierInvoicePayment payment, String description) {
        this.ksefNumber = ksefNumber;
        this.payment = payment;
        this.description = description;
    }

    public String getKsefNumber() {
        return ksefNumber;
    }

    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    public CollectiveIdentifierInvoicePayment getPayment() {
        return payment;
    }

    public void setPayment(CollectiveIdentifierInvoicePayment payment) {
        this.payment = payment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
