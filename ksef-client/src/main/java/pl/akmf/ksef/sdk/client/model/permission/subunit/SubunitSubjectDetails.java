package pl.akmf.ksef.sdk.client.model.permission.subunit;

/**
 * SubunitSubjectDetails.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonPermissionSubjectDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubunitSubjectDetails {

    /**
     * Typ danych podmiotu.
     */
    private PermissionsSubunitSubjectDetailsType subjectDetailsType;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    private PermissionsSubunitPersonByIdentifier personById;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    private PermissionsSubunitPersonByFingerprintWithIdentifier personByFpWithId;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    private PermissionsSubunitPersonByFingerprintWithoutIdentifier personByFpNoId;

    public SubunitSubjectDetails() {
    }

    public SubunitSubjectDetails(PermissionsSubunitSubjectDetailsType subjectDetailsType, PermissionsSubunitPersonByIdentifier personById, PermissionsSubunitPersonByFingerprintWithIdentifier personByFpWithId, PermissionsSubunitPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.subjectDetailsType = subjectDetailsType;
        this.personById = personById;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
    }

    /**
     * Typ danych podmiotu.
     */
    public PermissionsSubunitSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych podmiotu.
     */
    public void setSubjectDetailsType(PermissionsSubunitSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    public PermissionsSubunitPersonByIdentifier getPersonById() {
        return personById;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    public void setPersonById(PermissionsSubunitPersonByIdentifier personById) {
        this.personById = personById;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    public PermissionsSubunitPersonByFingerprintWithIdentifier getPersonByFpWithId() {
        return personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    public void setPersonByFpWithId(PermissionsSubunitPersonByFingerprintWithIdentifier personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public PermissionsSubunitPersonByFingerprintWithoutIdentifier getPersonByFpNoId() {
        return personByFpNoId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public void setPersonByFpNoId(PermissionsSubunitPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }
}
