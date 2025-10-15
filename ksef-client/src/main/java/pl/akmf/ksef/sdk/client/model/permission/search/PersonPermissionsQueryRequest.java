package pl.akmf.ksef.sdk.client.model.permission.search;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;

import java.util.List;

public class PersonPermissionsQueryRequest {
    private PersonPermissionsAuthorIdentifier authorIdentifier;
    private PersonPermissionsAuthorizedIdentifier authorizedIdentifier;
    private PersonPermissionsTargetIdentifier targetIdentifier;
    private PersonPermissionsContextIdentifier contextIdentifier;
    private List<PersonPermissionType> permissionTypes;
    private PermissionState permissionState;
    private PersonPermissionQueryType queryType;

    public PersonPermissionsQueryRequest() {
    }

    public PersonPermissionsQueryRequest(PersonPermissionsAuthorIdentifier authorIdentifier,
                                         PersonPermissionsAuthorizedIdentifier authorizedIdentifier,
                                         PersonPermissionsTargetIdentifier targetIdentifier,
                                         PersonPermissionsContextIdentifier contextIdentifier,
                                         List<PersonPermissionType> permissionTypes,
                                         PermissionState permissionState,
                                         PersonPermissionQueryType queryType) {
        this.authorIdentifier = authorIdentifier;
        this.authorizedIdentifier = authorizedIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.contextIdentifier = contextIdentifier;
        this.permissionTypes = permissionTypes;
        this.permissionState = permissionState;
        this.queryType = queryType;
    }

    public PersonPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    public void setAuthorIdentifier(PersonPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    public PersonPermissionsAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    public void setAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    public PersonPermissionsTargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    public void setTargetIdentifier(PersonPermissionsTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public List<PersonPermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    public void setPermissionTypes(List<PersonPermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }

    public PermissionState getPermissionState() {
        return permissionState;
    }

    public void setPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }

    public PersonPermissionQueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(PersonPermissionQueryType queryType) {
        this.queryType = queryType;
    }
}

