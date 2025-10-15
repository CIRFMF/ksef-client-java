package pl.akmf.ksef.sdk.client.model.invoice;

public class InvoiceBuyerIdentifier {
    private IdentifierType type;
    private String value;

    public InvoiceBuyerIdentifier() {
    }

    public IdentifierType getType() {
        return type;
    }

    public void setType(IdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

