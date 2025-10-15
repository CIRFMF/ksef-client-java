package pl.akmf.ksef.sdk.client.model.invoice;

public class ThirdSubjectIdentifier {
    private ThirdSubjectIdentifierType type;
    private String value;

    public ThirdSubjectIdentifier() {

    }

    public ThirdSubjectIdentifier(ThirdSubjectIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ThirdSubjectIdentifierType getType() {
        return type;
    }

    public void setType(ThirdSubjectIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
