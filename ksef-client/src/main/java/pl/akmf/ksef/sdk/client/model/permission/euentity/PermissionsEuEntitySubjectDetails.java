package pl.akmf.ksef.sdk.client.model.permission.euentity;

public class PermissionsEuEntitySubjectDetails {
    private PermissionsEuEntitySubjectDetailsType subjectDetailsType;
    private PermissionsEuEntityPersonByFpNoId personByFpNoId;
    private PermissionsEuEntityEntityByFp entityByFp;

    public PermissionsEuEntitySubjectDetails() {
    }

    public PermissionsEuEntitySubjectDetails(PermissionsEuEntitySubjectDetailsType subjectDetailsType, PermissionsEuEntityPersonByFpNoId personByFpNoId, PermissionsEuEntityEntityByFp entityByFp) {
        this.subjectDetailsType = subjectDetailsType;
        this.personByFpNoId = personByFpNoId;
        this.entityByFp = entityByFp;
    }

    public PermissionsEuEntitySubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(PermissionsEuEntitySubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public PermissionsEuEntityPersonByFpNoId getPersonByFpNoId() {
        return personByFpNoId;
    }

    public void setPersonByFpNoId(PermissionsEuEntityPersonByFpNoId personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }

    public PermissionsEuEntityEntityByFp getEntityByFp() {
        return entityByFp;
    }

    public void setEntityByFp(PermissionsEuEntityEntityByFp entityByFp) {
        this.entityByFp = entityByFp;
    }
}
