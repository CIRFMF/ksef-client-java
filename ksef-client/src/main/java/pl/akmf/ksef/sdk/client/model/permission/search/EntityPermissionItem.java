package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class EntityPermissionItem {
    private Boolean canDelegate;
    private PersonPermissionsContextIdentifier contextIdentifier;
    private String description;
    private String id;
    private EntityPermissionItemScope permissionScope;
    private OffsetDateTime startDate;

    public EntityPermissionItem() {
    }

    public EntityPermissionItem(Boolean canDelegate, PersonPermissionsContextIdentifier contextIdentifier, String description, String id, EntityPermissionItemScope permissionScope, OffsetDateTime startDate) {
        this.canDelegate = canDelegate;
        this.contextIdentifier = contextIdentifier;
        this.description = description;
        this.id = id;
        this.permissionScope = permissionScope;
        this.startDate = startDate;
    }

    public Boolean getCanDelegate() {
        return canDelegate;
    }

    public void setCanDelegate(Boolean canDelegate) {
        this.canDelegate = canDelegate;
    }

    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityPermissionItemScope getPermissionScope() {
        return permissionScope;
    }

    public void setPermissionScope(EntityPermissionItemScope permissionScope) {
        this.permissionScope = permissionScope;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}
