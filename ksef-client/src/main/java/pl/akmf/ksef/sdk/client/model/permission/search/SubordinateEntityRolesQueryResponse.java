package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

public class SubordinateEntityRolesQueryResponse {
    private List<SubordinateEntityRole> roles;
    private Boolean hasMore;

    SubordinateEntityRolesQueryResponse() {
    }

    public SubordinateEntityRolesQueryResponse(List<SubordinateEntityRole> roles, Boolean hasMore) {
        this.roles = roles;
        this.hasMore = hasMore;
    }

    public List<SubordinateEntityRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SubordinateEntityRole> roles) {
        this.roles = roles;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}

