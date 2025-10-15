package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

public class QueryPersonalGrantResponse {
    private List<QueryPersonalGrantItem> permissions;
    private Boolean hasMore;

    public QueryPersonalGrantResponse() {

    }

    public List<QueryPersonalGrantItem> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<QueryPersonalGrantItem> permissions) {
        this.permissions = permissions;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
