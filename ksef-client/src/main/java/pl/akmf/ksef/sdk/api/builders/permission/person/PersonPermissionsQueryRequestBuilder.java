package pl.akmf.ksef.sdk.api.builders.permission.person;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.search.PermissionState;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsAuthorIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsAuthorizedIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsTargetIdentifier;

import java.util.List;

public class PersonPermissionsQueryRequestBuilder {
    private PersonPermissionsAuthorIdentifier authorIdentifier;
    private PersonPermissionsAuthorizedIdentifier authorizedIdentifier;
    private PersonPermissionsTargetIdentifier targetIdentifier;
    private PersonPermissionsContextIdentifier contextIdentifier;
    private List<PersonPermissionType> permissionTypes;
    private PermissionState permissionState;
    private PersonPermissionQueryType queryType;

    public PersonPermissionsQueryRequestBuilder withAuthorIdentifier(PersonPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
        return this;
    }

    public PersonPermissionsQueryRequestBuilder withAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
        return this;
    }

    public PersonPermissionsQueryRequestBuilder withTargetIdentifier(PersonPermissionsTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
        return this;
    }

    public PersonPermissionsQueryRequestBuilder withContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
        return this;
    }

    public PersonPermissionsQueryRequestBuilder withPermissionTypes(List<PersonPermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
        return this;
    }

    public PersonPermissionsQueryRequestBuilder withPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
        return this;
    }

    public PersonPermissionsQueryRequestBuilder withQueryType(PersonPermissionQueryType queryType) {
        this.queryType = queryType;
        return this;
    }

    public PersonPermissionsQueryRequest build() {
        return new PersonPermissionsQueryRequest(authorIdentifier, authorizedIdentifier, targetIdentifier, contextIdentifier, permissionTypes, permissionState, queryType);
    }
}