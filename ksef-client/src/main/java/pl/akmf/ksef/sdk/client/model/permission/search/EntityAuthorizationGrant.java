package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class EntityAuthorizationGrant {
    private String id;
    private EntityAuthorizationsAuthorIdentifier authorIdentifier;
    private EntityAuthorizationsAuthorizedEntityIdentifier authorizedEntityIdentifier;
    private EntityAuthorizationsAuthorizingEntityIdentifier authorizingEntityIdentifier;
    private InvoicePermissionType authorizationScope;
    private String description;
    private OffsetDateTime startDate;

    public EntityAuthorizationGrant() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityAuthorizationsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    public void setAuthorIdentifier(EntityAuthorizationsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    public EntityAuthorizationsAuthorizedEntityIdentifier getAuthorizedEntityIdentifier() {
        return authorizedEntityIdentifier;
    }

    public void setAuthorizedEntityIdentifier(EntityAuthorizationsAuthorizedEntityIdentifier authorizedEntityIdentifier) {
        this.authorizedEntityIdentifier = authorizedEntityIdentifier;
    }

    public EntityAuthorizationsAuthorizingEntityIdentifier getAuthorizingEntityIdentifier() {
        return authorizingEntityIdentifier;
    }

    public void setAuthorizingEntityIdentifier(EntityAuthorizationsAuthorizingEntityIdentifier authorizingEntityIdentifier) {
        this.authorizingEntityIdentifier = authorizingEntityIdentifier;
    }

    public InvoicePermissionType getAuthorizationScope() {
        return authorizationScope;
    }

    public void setAuthorizationScope(InvoicePermissionType authorizationScope) {
        this.authorizationScope = authorizationScope;
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
