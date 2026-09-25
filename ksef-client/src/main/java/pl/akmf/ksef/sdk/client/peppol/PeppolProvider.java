package pl.akmf.ksef.sdk.client.peppol;

import java.time.OffsetDateTime;

/**
 * PeppolProvider.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PeppolProvider {

    /**
     * Identyfikator dostawcy usług Peppol.
     */
    private String id;

    /**
     * Nazwa dostawcy usług Peppol.
     */
    private String name;

    /**
     * Data rejestracji dostawcy usług Peppol w systemie.
     */
    private OffsetDateTime dateCreated;

    public PeppolProvider() {
    }

    /**
     * Identyfikator dostawcy usług Peppol.
     */
    public String getId() {
        return id;
    }

    /**
     * Identyfikator dostawcy usług Peppol.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Nazwa dostawcy usług Peppol.
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa dostawcy usług Peppol.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Data rejestracji dostawcy usług Peppol w systemie.
     */
    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Data rejestracji dostawcy usług Peppol w systemie.
     */
    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
