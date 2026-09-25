package pl.akmf.ksef.sdk.client.model.permission.search;

/**
 * EntityPermissionsQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityPermissionsQueryRequest {

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    private PersonPermissionsContextIdentifier contextIdentifier;

    public EntityPermissionsQueryRequest() {
    }

    public EntityPermissionsQueryRequest(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }
}
