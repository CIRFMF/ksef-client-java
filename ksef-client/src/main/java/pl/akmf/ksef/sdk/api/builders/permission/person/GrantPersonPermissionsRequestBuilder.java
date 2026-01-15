package pl.akmf.ksef.sdk.api.builders.permission.person;

import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;

import java.util.List;

public class GrantPersonPermissionsRequestBuilder {
    private PersonPermissionsSubjectIdentifier subjectIdentifier;
    private List<PersonPermissionType> permissions;
    private String description;
    private PersonPermissionSubjectDetails subjectDetails;

    public GrantPersonPermissionsRequestBuilder withSubjectIdentifier(PersonPermissionsSubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
        return this;
    }

    public GrantPersonPermissionsRequestBuilder withPermissions(List<PersonPermissionType> permissions) {
        this.permissions = permissions;
        return this;
    }

    public GrantPersonPermissionsRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GrantPersonPermissionsRequestBuilder withSubjectDetails(PersonPermissionSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
        return this;
    }

    public GrantPersonPermissionsRequest build() {
        return new GrantPersonPermissionsRequest(subjectIdentifier, permissions, description, subjectDetails);
    }
}