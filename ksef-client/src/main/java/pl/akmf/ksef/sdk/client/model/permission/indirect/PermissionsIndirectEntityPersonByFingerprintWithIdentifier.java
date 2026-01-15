package pl.akmf.ksef.sdk.client.model.permission.indirect;

public class PermissionsIndirectEntityPersonByFingerprintWithIdentifier {
    private String firstName;
    private String lastName;
    private PermissionsIndirectEntityPersonIdentifier identifier;

    public PermissionsIndirectEntityPersonByFingerprintWithIdentifier() {
    }

    public PermissionsIndirectEntityPersonByFingerprintWithIdentifier(String firstName, String lastName, PermissionsIndirectEntityPersonIdentifier identifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifier = identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PermissionsIndirectEntityPersonIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PermissionsIndirectEntityPersonIdentifier identifier) {
        this.identifier = identifier;
    }
}
