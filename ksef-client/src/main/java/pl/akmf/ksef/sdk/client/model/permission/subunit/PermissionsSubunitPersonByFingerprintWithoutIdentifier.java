package pl.akmf.ksef.sdk.client.model.permission.subunit;

import java.time.OffsetDateTime;

public class PermissionsSubunitPersonByFingerprintWithoutIdentifier {
    private String firstName;
    private String lastName;
    private OffsetDateTime birthDate;
    private PermissionsSubunitIdentityDocument idDocument;

    public PermissionsSubunitPersonByFingerprintWithoutIdentifier() {
    }

    public PermissionsSubunitPersonByFingerprintWithoutIdentifier(String firstName, String lastName, OffsetDateTime birthDate, PermissionsSubunitIdentityDocument idDocument) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.idDocument = idDocument;
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

    public OffsetDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(OffsetDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public PermissionsSubunitIdentityDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(PermissionsSubunitIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}
