package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QuerySubordinateEntityRolesResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubordinateEntityRolesQueryResponse {

    /**
     * Lista odczytanych podmiotów podrzędnych i ich ról.
     */
    private List<SubordinateEntityRole> roles;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    SubordinateEntityRolesQueryResponse() {
    }

    public SubordinateEntityRolesQueryResponse(List<SubordinateEntityRole> roles, Boolean hasMore) {
        this.roles = roles;
        this.hasMore = hasMore;
    }

    /**
     * Lista odczytanych podmiotów podrzędnych i ich ról.
     */
    public List<SubordinateEntityRole> getRoles() {
        return roles;
    }

    /**
     * Lista odczytanych podmiotów podrzędnych i ich ról.
     */
    public void setRoles(List<SubordinateEntityRole> roles) {
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
