package pl.akmf.ksef.sdk.client.model.permission.euentity;

public class EuEntityPermissionsGrantRequest {
    private SubjectIdentifier subjectIdentifier;
    private ContextIdentifier contextIdentifier;
    private String description;
    private String euEntityName;
    private PermissionsEuEntitySubjectDetails subjectDetails;
    private PermissionsEuEntityDetails euEntityDetails;

    public EuEntityPermissionsGrantRequest() {
    }

    public EuEntityPermissionsGrantRequest(SubjectIdentifier subjectIdentifier, ContextIdentifier contextIdentifier, String description, String euEntityName, PermissionsEuEntitySubjectDetails subjectDetails, PermissionsEuEntityDetails euEntityDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.contextIdentifier = contextIdentifier;
        this.description = description;
        this.euEntityName = euEntityName;
        this.subjectDetails = subjectDetails;
        this.euEntityDetails = euEntityDetails;
    }

    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public ContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(ContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEuEntityName() {
        return euEntityName;
    }

    public void setEuEntityName(String euEntityName) {
        this.euEntityName = euEntityName;
    }

    public PermissionsEuEntitySubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(PermissionsEuEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    public PermissionsEuEntityDetails getEuEntityDetails() {
        return euEntityDetails;
    }

    public void setEuEntityDetails(PermissionsEuEntityDetails euEntityDetails) {
        this.euEntityDetails = euEntityDetails;
    }
}

