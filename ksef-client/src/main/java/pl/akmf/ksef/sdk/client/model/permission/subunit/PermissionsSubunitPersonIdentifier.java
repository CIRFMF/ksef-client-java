package pl.akmf.ksef.sdk.client.model.permission.subunit;

public class PermissionsSubunitPersonIdentifier {
    private PermissionsSubunitIdentifierType type;
    private String value;

    public PermissionsSubunitPersonIdentifier() {
    }

    public PermissionsSubunitPersonIdentifier(PermissionsSubunitIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public PermissionsSubunitIdentifierType getType() {
        return type;
    }

    public void setType(PermissionsSubunitIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
