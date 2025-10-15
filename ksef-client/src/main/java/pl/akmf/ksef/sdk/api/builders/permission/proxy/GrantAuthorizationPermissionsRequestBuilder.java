package pl.akmf.ksef.sdk.api.builders.permission.proxy;

import pl.akmf.ksef.sdk.client.model.permission.proxy.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.proxy.GrantAuthorizationPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;

public class GrantAuthorizationPermissionsRequestBuilder {
    private SubjectIdentifier subjectIdentifier;
    private InvoicePermissionType permission;
    private String description;

    public GrantAuthorizationPermissionsRequestBuilder withSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
        return this;
    }

    public GrantAuthorizationPermissionsRequestBuilder withPermission(InvoicePermissionType permission) {
        this.permission = permission;
        return this;
    }

    public GrantAuthorizationPermissionsRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GrantAuthorizationPermissionsRequest build(){
        return new GrantAuthorizationPermissionsRequest(subjectIdentifier, permission, description);
    }
}
