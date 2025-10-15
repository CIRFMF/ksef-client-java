package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

public class EntityAuthorizationPermissionsQueryRequest {
    private EntityAuthorizationsAuthorizingEntityIdentifier authorizingIdentifier;
    private EntityAuthorizationsAuthorizedEntityIdentifier authorizedIdentifier;
    private QueryType queryType;
    private List<InvoicePermissionType> permissionTypes;

    public EntityAuthorizationPermissionsQueryRequest() {
    }

    public EntityAuthorizationPermissionsQueryRequest(EntityAuthorizationsAuthorizingEntityIdentifier authorizingIdentifier,
                                                      EntityAuthorizationsAuthorizedEntityIdentifier authorizedIdentifier,
                                                      QueryType queryType,
                                                      List<InvoicePermissionType> permissionTypes) {
        this.authorizingIdentifier = authorizingIdentifier;
        this.authorizedIdentifier = authorizedIdentifier;
        this.queryType = queryType;
        this.permissionTypes = permissionTypes;
    }

    public EntityAuthorizationsAuthorizingEntityIdentifier getAuthorizingIdentifier() {
        return authorizingIdentifier;
    }

    public void setAuthorizingIdentifier(EntityAuthorizationsAuthorizingEntityIdentifier authorizingIdentifier) {
        this.authorizingIdentifier = authorizingIdentifier;
    }

    public EntityAuthorizationsAuthorizedEntityIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    public void setAuthorizedIdentifier(EntityAuthorizationsAuthorizedEntityIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<InvoicePermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    public void setPermissionTypes(List<InvoicePermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }
}

