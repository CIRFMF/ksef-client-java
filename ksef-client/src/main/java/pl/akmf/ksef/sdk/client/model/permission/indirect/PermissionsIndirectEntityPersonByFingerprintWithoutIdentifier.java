package pl.akmf.ksef.sdk.client.model.permission.indirect;

import java.time.OffsetDateTime;

public class PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier {
    private String firstName;
    private String lastName;
    private OffsetDateTime birthDate;
    private PermissionsIndirectEntityIdentityDocument idDocument;

    public PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier() {
    }

    public PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier(String firstName, String lastName, OffsetDateTime birthDate, PermissionsIndirectEntityIdentityDocument idDocument) {
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

    public PermissionsIndirectEntityIdentityDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(PermissionsIndirectEntityIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}
