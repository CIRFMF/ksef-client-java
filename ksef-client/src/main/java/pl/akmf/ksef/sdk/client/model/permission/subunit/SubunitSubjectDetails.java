package pl.akmf.ksef.sdk.client.model.permission.subunit;

public class SubunitSubjectDetails {
    private PermissionsSubunitSubjectDetailsType subjectDetailsType;
    private PermissionsSubunitPersonByIdentifier personById;
    private PermissionsSubunitPersonByFingerprintWithIdentifier personByFpWithId;
    private PermissionsSubunitPersonByFingerprintWithoutIdentifier personByFpNoId;

    public SubunitSubjectDetails() {
    }

    public SubunitSubjectDetails(PermissionsSubunitSubjectDetailsType subjectDetailsType, PermissionsSubunitPersonByIdentifier personById, PermissionsSubunitPersonByFingerprintWithIdentifier personByFpWithId, PermissionsSubunitPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.subjectDetailsType = subjectDetailsType;
        this.personById = personById;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
    }

    public PermissionsSubunitSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(PermissionsSubunitSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public PermissionsSubunitPersonByIdentifier getPersonById() {
        return personById;
    }

    public void setPersonById(PermissionsSubunitPersonByIdentifier personById) {
        this.personById = personById;
    }

    public PermissionsSubunitPersonByFingerprintWithIdentifier getPersonByFpWithId() {
        return personByFpWithId;
    }

    public void setPersonByFpWithId(PermissionsSubunitPersonByFingerprintWithIdentifier personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    public PermissionsSubunitPersonByFingerprintWithoutIdentifier getPersonByFpNoId() {
        return personByFpNoId;
    }

    public void setPersonByFpNoId(PermissionsSubunitPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }
}
