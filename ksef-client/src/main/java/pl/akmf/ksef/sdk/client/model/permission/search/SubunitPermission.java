package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class SubunitPermission {
    private String id;
    private SubunitPermissionsAuthorizedIdentifier authorizedIdentifier;
    private SubunitPermissionsSubunitIdentifier subunitIdentifier;
    private SubunitPermissionsAuthorIdentifier authorIdentifier;
    private SubunitPermissionType permissionScope;
    private String description;
    private String subunitName;
    private OffsetDateTime startDate;

    public SubunitPermission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubunitPermissionsAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    public void setAuthorizedIdentifier(SubunitPermissionsAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    public SubunitPermissionsSubunitIdentifier getSubunitIdentifier() {
        return subunitIdentifier;
    }

    public void setSubunitIdentifier(SubunitPermissionsSubunitIdentifier subunitIdentifier) {
        this.subunitIdentifier = subunitIdentifier;
    }

    public SubunitPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    public void setAuthorIdentifier(SubunitPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    public SubunitPermissionType getPermissionScope() {
        return permissionScope;
    }

    public void setPermissionScope(SubunitPermissionType permissionScope) {
        this.permissionScope = permissionScope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubunitName() {
        return subunitName;
    }

    public void setSubunitName(String subunitName) {
        this.subunitName = subunitName;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}

