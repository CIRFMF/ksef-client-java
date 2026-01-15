package pl.akmf.ksef.sdk.client.model.permission.subunit;

public class SubunitPermissionsGrantRequest {
    private SubjectIdentifier subjectIdentifier;
    private ContextIdentifier contextIdentifier;
    private String description;
    private String subunitName;
    private SubunitSubjectDetails subjectDetails;

    public SubunitPermissionsGrantRequest() {
    }

    public SubunitPermissionsGrantRequest(SubjectIdentifier subjectIdentifier, ContextIdentifier contextIdentifier, String description, String subunitName, SubunitSubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.contextIdentifier = contextIdentifier;
        this.description = description;
        this.subunitName = subunitName;
        this.subjectDetails = subjectDetails;
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

    public String getSubunitName() {
        return subunitName;
    }

    public void setSubunitName(String subunitName) {
        this.subunitName = subunitName;
    }

    public SubunitSubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(SubunitSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }
}

