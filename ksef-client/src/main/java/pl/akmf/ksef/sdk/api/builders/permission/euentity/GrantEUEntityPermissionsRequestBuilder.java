package pl.akmf.ksef.sdk.api.builders.permission.euentity;

import pl.akmf.ksef.sdk.client.model.permission.euentity.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.PermissionsEuEntityDetails;
import pl.akmf.ksef.sdk.client.model.permission.euentity.PermissionsEuEntitySubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.euentity.SubjectIdentifier;

public class GrantEUEntityPermissionsRequestBuilder {
    private SubjectIdentifier subjectIdentifier;
    private ContextIdentifier contextIdentifier;
    private String description;
    private String euEntityName;
    private PermissionsEuEntitySubjectDetails subjectDetails;
    private PermissionsEuEntityDetails euEntityDetails;

    public GrantEUEntityPermissionsRequestBuilder withSubject(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
        return this;
    }

    public GrantEUEntityPermissionsRequestBuilder withContext(ContextIdentifier subjectIdentifier) {
        this.contextIdentifier = subjectIdentifier;
        return this;
    }

    public GrantEUEntityPermissionsRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GrantEUEntityPermissionsRequestBuilder withEuEntityName(String euEntityName) {
        this.euEntityName = euEntityName;
        return this;
    }

    public GrantEUEntityPermissionsRequestBuilder withSubjectDetails(PermissionsEuEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
        return this;
    }

    public GrantEUEntityPermissionsRequestBuilder withEuEntityDetails(PermissionsEuEntityDetails euEntityDetails) {
        this.euEntityDetails = euEntityDetails;
        return this;
    }

    public EuEntityPermissionsGrantRequest build() {
        EuEntityPermissionsGrantRequest request = new EuEntityPermissionsGrantRequest();
        request.setSubjectIdentifier(subjectIdentifier);
        request.setContextIdentifier(contextIdentifier);
        request.setDescription(description);
        request.setEuEntityName(euEntityName);
        request.setSubjectDetails(subjectDetails);
        request.setEuEntityDetails(euEntityDetails);
        return request;
    }
}
