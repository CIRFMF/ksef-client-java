package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

public class QueryPersonalGrantRequest {
    private QueryPersonalGrantContextIdentifier contextIdentifier;
    private QueryPersonalGrantTargetIdentifier targetIdentifier;
    private List<QueryPersonalPermissionTypes> permissionTypes;
    private PermissionState permissionState;

    public QueryPersonalGrantRequest() {

    }

    public QueryPersonalGrantRequest(QueryPersonalGrantContextIdentifier contextIdentifier,
                                     QueryPersonalGrantTargetIdentifier targetIdentifier,
                                     List<QueryPersonalPermissionTypes> permissionTypes,
                                     PermissionState permissionState) {
        this.contextIdentifier = contextIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.permissionTypes = permissionTypes;
        this.permissionState = permissionState;
    }

    public QueryPersonalGrantContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(QueryPersonalGrantContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public QueryPersonalGrantTargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    public void setTargetIdentifier(QueryPersonalGrantTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public List<QueryPersonalPermissionTypes> getPermissionTypes() {
        return permissionTypes;
    }

    public void setPermissionTypes(List<QueryPersonalPermissionTypes> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }

    public PermissionState getPermissionState() {
        return permissionState;
    }

    public void setPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }
}
