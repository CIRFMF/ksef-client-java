package pl.akmf.ksef.sdk.client.model.permission.subunit;

public class PermissionsSubunitPersonByFingerprintWithIdentifier {
    private String firstName;
    private String lastName;
    private PermissionsSubunitPersonIdentifier identifier;

    public PermissionsSubunitPersonByFingerprintWithIdentifier() {
    }

    public PermissionsSubunitPersonByFingerprintWithIdentifier(String firstName, String lastName, PermissionsSubunitPersonIdentifier identifier) {
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

    public PermissionsSubunitPersonIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PermissionsSubunitPersonIdentifier identifier) {
        this.identifier = identifier;
    }
}





