package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class QueryPersonalGrantItem {
    private String id;
    private QueryPersonalGrantContextIdentifier contextIdentifier;
    private QueryPersonalGrantAuthorizedIdentifier authorizedIdentifier;
    private QueryPersonalGrantTargetIdentifier targetIdentifier;
    private QueryPersonalPermissionTypes permissionScope;
    private String description;
    private PermissionState permissionState;
    private OffsetDateTime startDate;
    private Boolean canDelegate;

    public QueryPersonalGrantItem() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryPersonalGrantContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(QueryPersonalGrantContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public QueryPersonalGrantAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    public void setAuthorizedIdentifier(QueryPersonalGrantAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    public QueryPersonalGrantTargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    public void setTargetIdentifier(QueryPersonalGrantTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public QueryPersonalPermissionTypes getPermissionScope() {
        return permissionScope;
    }

    public void setPermissionScope(QueryPersonalPermissionTypes permissionScope) {
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

    public Boolean getCanDelegate() {
        return canDelegate;
    }

    public void setCanDelegate(Boolean canDelegate) {
        this.canDelegate = canDelegate;
    }
}
