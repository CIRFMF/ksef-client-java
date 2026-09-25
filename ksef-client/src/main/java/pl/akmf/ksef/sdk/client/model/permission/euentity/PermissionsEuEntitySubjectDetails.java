package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * PermissionsEuEntitySubjectDetails.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EuEntityPermissionSubjectDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsEuEntitySubjectDetails {

    /**
     * Typ danych podmiotu.
     */
    private PermissionsEuEntitySubjectDetailsType subjectDetailsType;

    private PermissionsEuEntityPersonByFpWithId personByFpWithId;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    private PermissionsEuEntityPersonByFpNoId personByFpNoId;

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = EntityByFingerprint.*
     */
    private PermissionsEuEntityEntityByFp entityByFp;

    public PermissionsEuEntitySubjectDetails() {
    }

    public PermissionsEuEntitySubjectDetails(PermissionsEuEntitySubjectDetailsType subjectDetailsType, PermissionsEuEntityPersonByFpWithId personByFpWithId, PermissionsEuEntityPersonByFpNoId personByFpNoId, PermissionsEuEntityEntityByFp entityByFp) {
        this.subjectDetailsType = subjectDetailsType;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
        this.entityByFp = entityByFp;
    }

    @Deprecated
    public PermissionsEuEntitySubjectDetails(PermissionsEuEntitySubjectDetailsType subjectDetailsType, PermissionsEuEntityPersonByFpNoId personByFpNoId, PermissionsEuEntityEntityByFp entityByFp) {
        this.subjectDetailsType = subjectDetailsType;
        this.personByFpNoId = personByFpNoId;
        this.entityByFp = entityByFp;
    }

    /**
     * Typ danych podmiotu.
     */
    public PermissionsEuEntitySubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych podmiotu.
     */
    public void setSubjectDetailsType(PermissionsEuEntitySubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public PermissionsEuEntityPersonByFpWithId getPersonByFpWithId() {
        return personByFpWithId;
    }

    public void setPersonByFpWithId(PermissionsEuEntityPersonByFpWithId personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public PermissionsEuEntityPersonByFpNoId getPersonByFpNoId() {
        return personByFpNoId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = PersonByFingerprintWithoutIdentifier.*
     */
    public void setPersonByFpNoId(PermissionsEuEntityPersonByFpNoId personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = EntityByFingerprint.*
     */
    public PermissionsEuEntityEntityByFp getEntityByFp() {
        return entityByFp;
    }

    /**
     * Dane podmiotu. *Wymagane, gdy subjectDetailsType = EntityByFingerprint.*
     */
    public void setEntityByFp(PermissionsEuEntityEntityByFp entityByFp) {
        this.entityByFp = entityByFp;
    }
}
