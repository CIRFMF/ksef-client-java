package pl.akmf.ksef.sdk.client.model.permission.indirect;

/**
 * PermissionsIndirectEntitySubjectDetails.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonPermissionSubjectDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsIndirectEntitySubjectDetails {

    /**
     * Typ danych podmiotu.
     */
    private PermissionsIndirectEntitySubjectDetailsType subjectDetailsType;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    private PermissionsIndirectEntityPersonByIdentifier personById;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    private PermissionsIndirectEntityPersonByFingerprintWithIdentifier personByFpWithId;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    private PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier personByFpNoId;

    public PermissionsIndirectEntitySubjectDetails() {
    }

    public PermissionsIndirectEntitySubjectDetails(PermissionsIndirectEntitySubjectDetailsType subjectDetailsType, PermissionsIndirectEntityPersonByIdentifier personById, PermissionsIndirectEntityPersonByFingerprintWithIdentifier personByFpWithId, PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.subjectDetailsType = subjectDetailsType;
        this.personById = personById;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
    }

    /**
     * Typ danych podmiotu.
     */
    public PermissionsIndirectEntitySubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych podmiotu.
     */
    public void setSubjectDetailsType(PermissionsIndirectEntitySubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    public PermissionsIndirectEntityPersonByIdentifier getPersonById() {
        return personById;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByIdentifier.*
     */
    public void setPersonById(PermissionsIndirectEntityPersonByIdentifier personById) {
        this.personById = personById;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    public PermissionsIndirectEntityPersonByFingerprintWithIdentifier getPersonByFpWithId() {
        return personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithIdentifier.*
     */
    public void setPersonByFpWithId(PermissionsIndirectEntityPersonByFingerprintWithIdentifier personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier getPersonByFpNoId() {
        return personByFpNoId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public void setPersonByFpNoId(PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }
}
