package pl.akmf.ksef.sdk.client.model.permission.euentity;

import java.time.OffsetDateTime;

public class PermissionsEuEntityPersonByFpNoId {
    private String firstName;
    private String lastName;
    private OffsetDateTime birthDate;
    private PermissionsEuEntityIdentityDocument idDocument;

    public PermissionsEuEntityPersonByFpNoId() {
    }

    public PermissionsEuEntityPersonByFpNoId(String firstName, String lastName, OffsetDateTime birthDate, PermissionsEuEntityIdentityDocument idDocument) {
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

    public PermissionsEuEntityIdentityDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(PermissionsEuEntityIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}
