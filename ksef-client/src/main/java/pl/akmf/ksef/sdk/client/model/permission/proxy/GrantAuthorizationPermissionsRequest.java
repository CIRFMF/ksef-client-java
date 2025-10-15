package pl.akmf.ksef.sdk.client.model.permission.proxy;

import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;

public class GrantAuthorizationPermissionsRequest {
    private SubjectIdentifier  subjectIdentifier;
    private InvoicePermissionType permission;
    private String description;

    public GrantAuthorizationPermissionsRequest() {

    }

    public GrantAuthorizationPermissionsRequest(SubjectIdentifier subjectIdentifier, InvoicePermissionType permission, String description) {
        this.subjectIdentifier = subjectIdentifier;
        this.permission = permission;
        this.description = description;
    }

    public SubjectIdentifier  getSubjectIdentifier() {
        return subjectIdentifier;
    }

    public void setSubjectIdentifier(SubjectIdentifier  subjectIdentifier) {
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
}
