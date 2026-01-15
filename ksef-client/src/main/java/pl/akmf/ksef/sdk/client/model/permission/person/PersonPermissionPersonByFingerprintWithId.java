package pl.akmf.ksef.sdk.client.model.permission.person;

public class PersonPermissionPersonByFingerprintWithId {
    private String firstName;
    private String lastName;
    private PersonPermissionsSubjectIdentifier identifier;

    public PersonPermissionPersonByFingerprintWithId() {
    }

    public PersonPermissionPersonByFingerprintWithId(String firstName, String lastName, PersonPermissionsSubjectIdentifier identifier) {
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

    public PersonPermissionsSubjectIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PersonPermissionsSubjectIdentifier identifier) {
        this.identifier = identifier;
    }
}
