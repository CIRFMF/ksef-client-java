package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

public class QueryEntityPermissionsResponse {
    private List<EntityPermissionItem> permissions;
    private Boolean hasMore;

    public QueryEntityPermissionsResponse() {
    }

    public QueryEntityPermissionsResponse(List<EntityPermissionItem> permissions, Boolean hasMore) {
        this.permissions = permissions;
        this.hasMore = hasMore;
    }

    public List<EntityPermissionItem> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<EntityPermissionItem> permissions) {
        this.permissions = permissions;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
