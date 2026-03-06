package pl.akmf.ksef.sdk.client.model.permission.search;

public class EntityPermissionsQueryRequest {
    private PersonPermissionsContextIdentifier contextIdentifier;

    public EntityPermissionsQueryRequest() {
    }

    public EntityPermissionsQueryRequest(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }
}
