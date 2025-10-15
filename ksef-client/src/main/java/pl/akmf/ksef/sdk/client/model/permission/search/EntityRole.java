package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;


public class EntityRole {
    private EntityRoleQueryParentEntityIdentifier parentEntityIdentifier;
    private EntityRoleType role;
    private String description;
    private OffsetDateTime startDate;

    public EntityRole() {
    }

    public EntityRoleQueryParentEntityIdentifier getParentEntityIdentifier() {
        return parentEntityIdentifier;
    }

    public void setParentEntityIdentifier(EntityRoleQueryParentEntityIdentifier parentEntityIdentifier) {
        this.parentEntityIdentifier = parentEntityIdentifier;
    }

    public EntityRoleType getRole() {
        return role;
    }

    public void setRole(EntityRoleType role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}

