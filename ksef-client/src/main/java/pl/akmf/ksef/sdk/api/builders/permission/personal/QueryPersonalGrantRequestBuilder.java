package pl.akmf.ksef.sdk.api.builders.permission.personal;

import pl.akmf.ksef.sdk.client.model.permission.search.PermissionState;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantTargetIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalPermissionTypes;

import java.util.List;

public class QueryPersonalGrantRequestBuilder {
    private QueryPersonalGrantContextIdentifier contextIdentifier;
    private QueryPersonalGrantTargetIdentifier targetIdentifier;
    private List<QueryPersonalPermissionTypes> permissionTypes;
    private PermissionState permissionState;

    public QueryPersonalGrantRequestBuilder withContextIdentifier(QueryPersonalGrantContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
        return this;
    }

    public QueryPersonalGrantRequestBuilder withTargetIdentifier(QueryPersonalGrantTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
        return this;
    }

    public QueryPersonalGrantRequestBuilder withPermissionTypes(List<QueryPersonalPermissionTypes> permissionTypes) {
        this.permissionTypes = permissionTypes;
        return this;
    }

    public QueryPersonalGrantRequestBuilder withPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
        return this;
    }

    public QueryPersonalGrantRequest build() {
        return new QueryPersonalGrantRequest(contextIdentifier, targetIdentifier, permissionTypes, permissionState);
    }
}
