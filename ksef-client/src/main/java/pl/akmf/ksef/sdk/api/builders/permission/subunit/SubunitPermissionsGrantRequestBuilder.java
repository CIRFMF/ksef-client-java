package pl.akmf.ksef.sdk.api.builders.permission.subunit;

import pl.akmf.ksef.sdk.client.model.permission.subunit.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;

public class SubunitPermissionsGrantRequestBuilder {
    private SubjectIdentifier subjectIdentifier;
    private ContextIdentifier contextIdentifier;
    private String description;
    private String subunitName;

    public SubunitPermissionsGrantRequestBuilder withSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
        return this;
    }

    public SubunitPermissionsGrantRequestBuilder withContextIdentifier(ContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
        return this;
    }

    public SubunitPermissionsGrantRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SubunitPermissionsGrantRequestBuilder withSubunitName(String subunitName) {
        this.subunitName = subunitName;
        return this;
    }

    public SubunitPermissionsGrantRequest build() {
        SubunitPermissionsGrantRequest request = new SubunitPermissionsGrantRequest();
        request.setSubjectIdentifier(subjectIdentifier);
        request.setContextIdentifier(contextIdentifier);
        request.setDescription(description);
        request.setSubunitName(subunitName);
        return request;
    }
}
