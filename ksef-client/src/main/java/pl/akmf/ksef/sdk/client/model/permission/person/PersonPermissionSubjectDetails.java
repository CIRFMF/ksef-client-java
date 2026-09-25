package pl.akmf.ksef.sdk.client.model.permission.person;

/**
 * PersonPermissionSubjectDetails.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermissionSubjectDetails {

    /**
     * Typ danych podmiotu.
     */
    private PersonPermissionSubjectDetailsType subjectDetailsType;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    private PersonPermissionPersonById personById;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    private PersonPermissionPersonByFingerprintWithId personByFpWithId;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    private PersonPermissionPersonByFingerprintNoId personByFpNoId;

    public PersonPermissionSubjectDetails() {
    }

    public PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType subjectDetailsType, PersonPermissionPersonById personById, PersonPermissionPersonByFingerprintWithId personByFpWithId, PersonPermissionPersonByFingerprintNoId personByFpNoId) {
        this.subjectDetailsType = subjectDetailsType;
        this.personById = personById;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
    }

    /**
     * Typ danych podmiotu.
     */
    public PersonPermissionSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych podmiotu.
     */
    public void setSubjectDetailsType(PersonPermissionSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    public PersonPermissionPersonById getPersonById() {
        return personById;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    public void setPersonById(PersonPermissionPersonById personById) {
        this.personById = personById;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    public PersonPermissionPersonByFingerprintWithId getPersonByFpWithId() {
        return personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    public void setPersonByFpWithId(PersonPermissionPersonByFingerprintWithId personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public PersonPermissionPersonByFingerprintNoId getPersonByFpNoId() {
        return personByFpNoId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public void setPersonByFpNoId(PersonPermissionPersonByFingerprintNoId personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }
}
