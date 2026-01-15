package pl.akmf.ksef.sdk.client.model.permission.person;

import java.time.OffsetDateTime;

public class PersonPermissionPersonByFingerprintNoId {
    private String firstName;
    private String lastName;
    private OffsetDateTime birthDate;
    private PersonPermissionIdentityDocument idDocument;

    public PersonPermissionPersonByFingerprintNoId() {
    }

    public PersonPermissionPersonByFingerprintNoId(String firstName, String lastName, OffsetDateTime birthDate, PersonPermissionIdentityDocument idDocument) {
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

    public PersonPermissionIdentityDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(PersonPermissionIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}
