package pl.akmf.ksef.sdk.client.model.permission.indirect;

import java.time.LocalDate;

public class PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private PermissionsIndirectEntityIdentityDocument idDocument;

    public PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier() {
    }

    public PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier(String firstName, String lastName, LocalDate birthDate, PermissionsIndirectEntityIdentityDocument idDocument) {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PermissionsIndirectEntityIdentityDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(PermissionsIndirectEntityIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}
