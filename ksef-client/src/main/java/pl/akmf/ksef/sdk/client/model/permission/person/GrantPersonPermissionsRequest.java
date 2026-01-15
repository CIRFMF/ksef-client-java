package pl.akmf.ksef.sdk.client.model.permission.person;

import java.util.List;

public class GrantPersonPermissionsRequest {
    private PersonPermissionsSubjectIdentifier subjectIdentifier;
    private List<PersonPermissionType> permissions;
    private String description;
    private PersonPermissionSubjectDetails subjectDetails;

    public GrantPersonPermissionsRequest() {
    }

    public GrantPersonPermissionsRequest(final PersonPermissionsSubjectIdentifier subjectIdentifier, final List<PersonPermissionType> permissions, final String description) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
    }

    public GrantPersonPermissionsRequest(PersonPermissionsSubjectIdentifier subjectIdentifier, List<PersonPermissionType> permissions, String description, PersonPermissionSubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    public PersonPermissionsSubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    public void setSubjectIdentifier(final PersonPermissionsSubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public List<PersonPermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(final List<PersonPermissionType> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public PersonPermissionSubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(PersonPermissionSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }
}

