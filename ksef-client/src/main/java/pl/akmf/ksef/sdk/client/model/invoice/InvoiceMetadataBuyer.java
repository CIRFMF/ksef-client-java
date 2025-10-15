package pl.akmf.ksef.sdk.client.model.invoice;


public class InvoiceMetadataBuyer {
    private InvoiceBuyerIdentifier identifier;
    private String name;

    public InvoiceMetadataBuyer() {
    }

    public InvoiceBuyerIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(InvoiceBuyerIdentifier identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

