package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class SubordinateEntityRole {
    private EntityRoleQueryParentEntityIdentifier subordinateEntityIdentifier;
    private SubordinateEntityRoleType role;
    private String description;
    private OffsetDateTime startDate;

    public SubordinateEntityRole() {
    }

    public EntityRoleQueryParentEntityIdentifier getSubordinateEntityIdentifier() {
        return subordinateEntityIdentifier;
    }

    public void setSubordinateEntityIdentifier(EntityRoleQueryParentEntityIdentifier subordinateEntityIdentifier) {
        this.subordinateEntityIdentifier = subordinateEntityIdentifier;
    }

    public SubordinateEntityRoleType getRole() {
        return role;
    }

    public void setRole(SubordinateEntityRoleType role) {
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

