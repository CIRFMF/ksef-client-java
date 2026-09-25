package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * DownloadInvoiceBuyer
 */
public class DownloadInvoiceBuyer {

    private IdentifierType identifierType;

    private String identifier;

    private String name;

    public DownloadInvoiceBuyer() {
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
