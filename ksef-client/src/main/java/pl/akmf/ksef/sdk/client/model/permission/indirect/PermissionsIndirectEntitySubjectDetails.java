package pl.akmf.ksef.sdk.client.model.permission.indirect;

public class PermissionsIndirectEntitySubjectDetails {
    private PermissionsIndirectEntitySubjectDetailsType subjectDetailsType;
    private PermissionsIndirectEntityPersonByIdentifier personById;
    private PermissionsIndirectEntityPersonByFingerprintWithIdentifier personByFpWithId;
    private PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier personByFpNoId;

    public PermissionsIndirectEntitySubjectDetails() {
    }

    public PermissionsIndirectEntitySubjectDetails(PermissionsIndirectEntitySubjectDetailsType subjectDetailsType, PermissionsIndirectEntityPersonByIdentifier personById, PermissionsIndirectEntityPersonByFingerprintWithIdentifier personByFpWithId, PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.subjectDetailsType = subjectDetailsType;
        this.personById = personById;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
    }

    public PermissionsIndirectEntitySubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(PermissionsIndirectEntitySubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public PermissionsIndirectEntityPersonByIdentifier getPersonById() {
        return personById;
    }

    public void setPersonById(PermissionsIndirectEntityPersonByIdentifier personById) {
        this.personById = personById;
    }

    public PermissionsIndirectEntityPersonByFingerprintWithIdentifier getPersonByFpWithId() {
        return personByFpWithId;
    }

    public void setPersonByFpWithId(PermissionsIndirectEntityPersonByFingerprintWithIdentifier personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    public PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier getPersonByFpNoId() {
        return personByFpNoId;
    }

    public void setPersonByFpNoId(PermissionsIndirectEntityPersonByFingerprintWithoutIdentifier personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }
}
