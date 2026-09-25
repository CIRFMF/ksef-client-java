package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QueryEntityRolesResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryEntityRolesResponse {

    /**
     * Lista odczytanych ról podmiotu.
     */
    private List<EntityRole> roles;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    QueryEntityRolesResponse() {
    }

    public QueryEntityRolesResponse(List<EntityRole> roles, Boolean hasMore) {
        this.roles = roles;
        this.hasMore = hasMore;
    }

    /**
     * Lista odczytanych ról podmiotu.
     */
    public List<EntityRole> getRoles() {
        return roles;
    }

    /**
     * Lista odczytanych ról podmiotu.
     */
    public void setRoles(List<EntityRole> roles) {
        this.roles = roles;
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
