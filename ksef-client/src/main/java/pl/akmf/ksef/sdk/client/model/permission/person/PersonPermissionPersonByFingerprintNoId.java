package pl.akmf.ksef.sdk.client.model.permission.person;

import java.time.OffsetDateTime;

/**
 * PersonPermissionPersonByFingerprintNoId.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonByFingerprintWithoutIdentifierDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermissionPersonByFingerprintNoId {

    /**
     * Imię osoby fizycznej.
     */
    private String firstName;

    /**
     * Nazwisko osoby fizycznej.
     */
    private String lastName;

    /**
     * Data urodzenia osoby fizycznej.
     */
    private OffsetDateTime birthDate;

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    private PersonPermissionIdentityDocument idDocument;

    public PersonPermissionPersonByFingerprintNoId() {
    }

    public PersonPermissionPersonByFingerprintNoId(String firstName, String lastName, OffsetDateTime birthDate, PersonPermissionIdentityDocument idDocument) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.idDocument = idDocument;
    }

    /**
     * Imię osoby fizycznej.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Imię osoby fizycznej.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Nazwisko osoby fizycznej.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Nazwisko osoby fizycznej.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Data urodzenia osoby fizycznej.
     */
    public OffsetDateTime getBirthDate() {
        return birthDate;
    }

    /**
     * Data urodzenia osoby fizycznej.
     */
    public void setBirthDate(OffsetDateTime birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    public PersonPermissionIdentityDocument getIdDocument() {
        return idDocument;
    }

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    public void setIdDocument(PersonPermissionIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}
