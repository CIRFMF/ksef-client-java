package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QuerySubunitPermissionsResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QuerySubunitPermissionsResponse {

    /**
     * Lista odczytanych uprawnień.
     */
    private List<SubunitPermission> permissions;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    QuerySubunitPermissionsResponse() {
    }

    public QuerySubunitPermissionsResponse(List<SubunitPermission> permissions, Boolean hasMore) {
        this.permissions = permissions;
        this.hasMore = hasMore;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public List<SubunitPermission> getPermissions() {
        return permissions;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public void setPermissions(List<SubunitPermission> permissions) {
        this.permissions = permissions;
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
