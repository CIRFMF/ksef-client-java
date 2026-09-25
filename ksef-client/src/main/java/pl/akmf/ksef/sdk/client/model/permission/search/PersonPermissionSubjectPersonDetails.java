package pl.akmf.ksef.sdk.client.model.permission.search;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionIdentityDocument;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;

/**
 * Dane osoby uprawnionej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PermissionsSubjectPersonDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermissionSubjectPersonDetails {

    /**
     * Typ danych uprawnionej osoby fizycznej.
     */
    private PersonPermissionSubjectDetailsType subjectDetailsType;

    /**
     * Imię osoby fizycznej.
     */
    private String firstName;

    /**
     * Nazwisko osoby fizycznej.
     */
    private String lastName;

    /**
     * Identyfikator osoby fizycznej.
     */
    private PersonPermissionPersonIdentifier personIdentifier;

    /**
     * Data urodzenia osoby fizycznej.
     */
    private String birthDate;

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    private PersonPermissionIdentityDocument idDocument;

    public PersonPermissionSubjectPersonDetails() {
    }

    public PersonPermissionSubjectPersonDetails(PersonPermissionSubjectDetailsType subjectDetailsType, String firstName, String lastName, PersonPermissionPersonIdentifier personIdentifier, String birthDate, PersonPermissionIdentityDocument idDocument) {
        this.subjectDetailsType = subjectDetailsType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personIdentifier = personIdentifier;
        this.birthDate = birthDate;
        this.idDocument = idDocument;
    }

    /**
     * Typ danych uprawnionej osoby fizycznej.
     */
    public PersonPermissionSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych uprawnionej osoby fizycznej.
     */
    public void setSubjectDetailsType(PersonPermissionSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
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
     * Identyfikator osoby fizycznej.
     */
    public PersonPermissionPersonIdentifier getPersonIdentifier() {
        return personIdentifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public void setPersonIdentifier(PersonPermissionPersonIdentifier personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    /**
     * Data urodzenia osoby fizycznej.
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Data urodzenia osoby fizycznej.
     */
    public void setBirthDate(String birthDate) {
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
