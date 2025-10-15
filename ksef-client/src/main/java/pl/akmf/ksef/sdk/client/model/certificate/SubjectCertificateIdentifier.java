package pl.akmf.ksef.sdk.client.model.certificate;

public class SubjectCertificateIdentifier {
    private SubjectCertificateIdentifierType type;
    private String value;

    public SubjectCertificateIdentifier() {

    }

    public SubjectCertificateIdentifierType getType() {
        return type;
    }

    public void setType(SubjectCertificateIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
