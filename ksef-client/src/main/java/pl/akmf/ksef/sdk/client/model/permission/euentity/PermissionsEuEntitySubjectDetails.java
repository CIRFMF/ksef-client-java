package pl.akmf.ksef.sdk.client.model.permission.euentity;

public class PermissionsEuEntitySubjectDetails {
    private PermissionsEuEntitySubjectDetailsType subjectDetailsType;
    private PermissionsEuEntityPersonByFpWithId permissionsEuEntityPersonByFpWithId;
    private PermissionsEuEntityPersonByFpNoId personByFpNoId;
    private PermissionsEuEntityEntityByFp entityByFp;

    public PermissionsEuEntitySubjectDetails() {
    }

    public PermissionsEuEntitySubjectDetails(PermissionsEuEntitySubjectDetailsType subjectDetailsType,
                                             PermissionsEuEntityPersonByFpWithId permissionsEuEntityPersonByFpWithId,
                                             PermissionsEuEntityPersonByFpNoId personByFpNoId,
                                             PermissionsEuEntityEntityByFp entityByFp) {
        this.subjectDetailsType = subjectDetailsType;
        this.permissionsEuEntityPersonByFpWithId = permissionsEuEntityPersonByFpWithId;
        this.personByFpNoId = personByFpNoId;
        this.entityByFp = entityByFp;
    }

    @Deprecated
    public PermissionsEuEntitySubjectDetails(PermissionsEuEntitySubjectDetailsType subjectDetailsType,
                                             PermissionsEuEntityPersonByFpNoId personByFpNoId,
                                             PermissionsEuEntityEntityByFp entityByFp) {
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

    public PermissionsEuEntityPersonByFpWithId getPermissionsEuEntityPersonByFpWithId() {
        return permissionsEuEntityPersonByFpWithId;
    }

    public void setPermissionsEuEntityPersonByFpWithId(PermissionsEuEntityPersonByFpWithId permissionsEuEntityPersonByFpWithId) {
        this.permissionsEuEntityPersonByFpWithId = permissionsEuEntityPersonByFpWithId;
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
