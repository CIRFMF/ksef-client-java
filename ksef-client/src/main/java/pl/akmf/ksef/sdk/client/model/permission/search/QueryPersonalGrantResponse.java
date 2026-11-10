package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QueryPersonalGrantResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code QueryPersonalPermissionsResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryPersonalGrantResponse {

    /**
     * Lista odczytanych uprawnień.
     */
    private List<QueryPersonalGrantItem> permissions;

    /**
     * Lista uprawnień właścicielskich.
     */
    private List<QueryPersonalPermissionTypes> ownerPermissions;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    public QueryPersonalGrantResponse() {
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public List<QueryPersonalGrantItem> getPermissions() {
        return permissions;
    }

    /**
     * Lista odczytanych uprawnień.
     */
    public void setPermissions(List<QueryPersonalGrantItem> permissions) {
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

    /**
     * Lista uprawnień właścicielskich.
     */
    public List<QueryPersonalPermissionTypes> getOwnerPermissions() {
        return ownerPermissions;
    }

    /**
     * Lista uprawnień właścicielskich.
     */
    public void setOwnerPermissions(List<QueryPersonalPermissionTypes> ownerPermissions) {
        this.ownerPermissions = ownerPermissions;
    }
}
