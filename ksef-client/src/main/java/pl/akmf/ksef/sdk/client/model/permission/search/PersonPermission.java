package pl.akmf.ksef.sdk.client.model.permission.search;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;

import java.time.OffsetDateTime;

public class PersonPermission {
    private String id;
    private PersonPermissionsAuthorizedIdentifier authorizedIdentifier;
    private PersonPermissionsContextIdentifier contextIdentifier;
    private PersonPermissionsTargetIdentifier targetIdentifier;
    private PersonPermissionsAuthorIdentifier authorIdentifier;
    private PersonPermissionType permissionScope;
    private String description;
    private PermissionState permissionState;
    private OffsetDateTime startDate;
    private boolean canDelegate;

    public PersonPermission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonPermissionsAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    public void setAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public PersonPermissionsTargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    public void setTargetIdentifier(PersonPermissionsTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public PersonPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    public void setAuthorIdentifier(PersonPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    public PersonPermissionType getPermissionScope() {
        return permissionScope;
    }

    public void setPermissionScope(PersonPermissionType permissionScope) {
        this.permissionScope = permissionScope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionState getPermissionState() {
        return permissionState;
    }

    public void setPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isCanDelegate() {
        return canDelegate;
    }

    public void setCanDelegate(boolean canDelegate) {
        this.canDelegate = canDelegate;
    }
}

