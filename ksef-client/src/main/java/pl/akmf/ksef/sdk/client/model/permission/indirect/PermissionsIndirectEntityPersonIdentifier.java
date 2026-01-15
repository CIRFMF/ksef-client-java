package pl.akmf.ksef.sdk.client.model.permission.indirect;

public class PermissionsIndirectEntityPersonIdentifier {
    private PermissionsIndirectEntityIdentifierType type;
    private String value;

    public PermissionsIndirectEntityPersonIdentifier() {
    }

    public PermissionsIndirectEntityPersonIdentifier(PermissionsIndirectEntityIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public PermissionsIndirectEntityIdentifierType getType() {
        return type;
    }

    public void setType(PermissionsIndirectEntityIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
