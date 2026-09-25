package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QueryEntityAuthorizationPermissionsResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryEntityAuthorizationPermissionsResponse {

    /**
     * Lista odczytanych uprawnień.
     */
    private List<EntityAuthorizationGrant> authorizationGrants;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    QueryEntityAuthorizationPermissionsResponse() {
    }

    public QueryEntityAuthorizationPermissionsResponse(List<EntityAuthorizationGrant> authorizationGrants, Boolean hasMore) {
        this.authorizationGrants = authorizationGrants;
        this.hasMore = hasMore;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public List<EntityAuthorizationGrant> getAuthorizationGrants() {
        return authorizationGrants;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public void setAuthorizationGrants(List<EntityAuthorizationGrant> authorizationGrants) {
        this.authorizationGrants = authorizationGrants;
    }

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
