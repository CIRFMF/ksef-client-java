package pl.akmf.ksef.sdk.api.builders.permission.entity;

import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier;

import java.util.List;

public class GrantEntityPermissionsRequestBuilder {
    private SubjectIdentifier subjectIdentifier;
    private List<EntityPermission> permissions;
    private String description;
    private GrantEntityPermissionsRequest.PermissionsEntitySubjectDetails subjectDetails;

    public GrantEntityPermissionsRequestBuilder withSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
        return this;
    }

    public GrantEntityPermissionsRequestBuilder withPermissions(List<EntityPermission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public GrantEntityPermissionsRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GrantEntityPermissionsRequestBuilder withSubjectDetails(GrantEntityPermissionsRequest.PermissionsEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
        return this;
    }

    public GrantEntityPermissionsRequest build() {
        GrantEntityPermissionsRequest request = new GrantEntityPermissionsRequest();
        request.setSubjectIdentifier(subjectIdentifier);
        request.setPermissions(permissions);
        request.setDescription(description);
        request.setSubjectDetails(subjectDetails);
        return request;
    }
}
