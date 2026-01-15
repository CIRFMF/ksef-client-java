package pl.akmf.ksef.sdk.client.model.permission.proxy;

import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;

public class GrantAuthorizationPermissionsRequest {
    private SubjectIdentifier subjectIdentifier;
    private InvoicePermissionType permission;
    private String description;
    private PermissionsAuthorizationSubjectDetails subjectDetails;

    public GrantAuthorizationPermissionsRequest() {
    }

    public GrantAuthorizationPermissionsRequest(SubjectIdentifier subjectIdentifier, InvoicePermissionType permission, String description) {
        this.subjectIdentifier = subjectIdentifier;
        this.permission = permission;
        this.description = description;
    }

    public GrantAuthorizationPermissionsRequest(SubjectIdentifier subjectIdentifier, InvoicePermissionType permission, String description, PermissionsAuthorizationSubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permission = permission;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public InvoicePermissionType getPermission() {
        return permission;
    }

    public void setPermission(InvoicePermissionType permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionsAuthorizationSubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(PermissionsAuthorizationSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    public class PermissionsAuthorizationSubjectDetails {
        private String fullName;

        public PermissionsAuthorizationSubjectDetails() {
        }

        public PermissionsAuthorizationSubjectDetails(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
