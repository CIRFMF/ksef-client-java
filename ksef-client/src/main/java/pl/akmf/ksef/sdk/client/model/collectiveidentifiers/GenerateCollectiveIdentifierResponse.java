package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

public class GenerateCollectiveIdentifierResponse {

    private String collectiveIdentifierNumber;

    public GenerateCollectiveIdentifierResponse() {
    }

    public GenerateCollectiveIdentifierResponse(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }
}
