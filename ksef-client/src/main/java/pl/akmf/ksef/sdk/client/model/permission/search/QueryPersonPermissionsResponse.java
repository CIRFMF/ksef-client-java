package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QueryPersonPermissionsResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryPersonPermissionsResponse {

    /**
     * Lista odczytanych uprawnień.
     */
    private List<PersonPermission> permissions;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    QueryPersonPermissionsResponse() {
    }

    public QueryPersonPermissionsResponse(List<PersonPermission> permissions, Boolean hasMore) {
        this.permissions = permissions;
        this.hasMore = hasMore;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public List<PersonPermission> getPermissions() {
        return permissions;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public void setPermissions(List<PersonPermission> permissions) {
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
