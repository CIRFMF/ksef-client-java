package pl.akmf.ksef.sdk.client.model.permission.person;

public class PersonPermissionSubjectDetails {
    private PersonPermissionSubjectDetailsType subjectDetailsType;
    private PersonPermissionPersonById personById;
    private PersonPermissionPersonByFingerprintWithId personByFpWithId;
    private PersonPermissionPersonByFingerprintNoId personByFpNoId;

    public PersonPermissionSubjectDetails() {
    }

    public PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType subjectDetailsType, PersonPermissionPersonById personById, PersonPermissionPersonByFingerprintWithId personByFpWithId, PersonPermissionPersonByFingerprintNoId personByFpNoId) {
        this.subjectDetailsType = subjectDetailsType;
        this.personById = personById;
        this.personByFpWithId = personByFpWithId;
        this.personByFpNoId = personByFpNoId;
    }

    public PersonPermissionSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(PersonPermissionSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public PersonPermissionPersonById getPersonById() {
        return personById;
    }

    public void setPersonById(PersonPermissionPersonById personById) {
        this.personById = personById;
    }

    public PersonPermissionPersonByFingerprintWithId getPersonByFpWithId() {
        return personByFpWithId;
    }

    public void setPersonByFpWithId(PersonPermissionPersonByFingerprintWithId personByFpWithId) {
        this.personByFpWithId = personByFpWithId;
    }

    public PersonPermissionPersonByFingerprintNoId getPersonByFpNoId() {
        return personByFpNoId;
    }

    public void setPersonByFpNoId(PersonPermissionPersonByFingerprintNoId personByFpNoId) {
        this.personByFpNoId = personByFpNoId;
    }
}
