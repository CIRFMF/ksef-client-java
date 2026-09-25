package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * EntityAuthorizationPermissionsQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityAuthorizationPermissionsQueryRequest {

    /**
     * Identyfikator podmiotu uprawniającego.
     */
    private EntityAuthorizationsAuthorizingEntityIdentifier authorizingIdentifier;

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    private EntityAuthorizationsAuthorizedEntityIdentifier authorizedIdentifier;

    /**
     * Typ zapytania.
     */
    private QueryType queryType;

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    private List<InvoicePermissionType> permissionTypes;

    public EntityAuthorizationPermissionsQueryRequest() {
    }

    public EntityAuthorizationPermissionsQueryRequest(EntityAuthorizationsAuthorizingEntityIdentifier authorizingIdentifier, EntityAuthorizationsAuthorizedEntityIdentifier authorizedIdentifier, QueryType queryType, List<InvoicePermissionType> permissionTypes) {
        this.authorizingIdentifier = authorizingIdentifier;
        this.authorizedIdentifier = authorizedIdentifier;
        this.queryType = queryType;
        this.permissionTypes = permissionTypes;
    }

    /**
     * Identyfikator podmiotu uprawniającego.
     */
    public EntityAuthorizationsAuthorizingEntityIdentifier getAuthorizingIdentifier() {
        return authorizingIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawniającego.
     */
    public void setAuthorizingIdentifier(EntityAuthorizationsAuthorizingEntityIdentifier authorizingIdentifier) {
        this.authorizingIdentifier = authorizingIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    public EntityAuthorizationsAuthorizedEntityIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    public void setAuthorizedIdentifier(EntityAuthorizationsAuthorizedEntityIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    /**
     * Typ zapytania.
     */
    public QueryType getQueryType() {
        return queryType;
    }

    /**
     * Typ zapytania.
     */
    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public List<InvoicePermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public void setPermissionTypes(List<InvoicePermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }
}
