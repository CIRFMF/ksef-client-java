package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.time.OffsetDateTime;

/**
 * CollectiveIdentifiersByKsefNumberQueryResponseItem.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifiersByKsefNumberQueryResponseItem {

    /**
     * Numer identyfikatora zbiorczego.
     */
    private String collectiveIdentifierNumber;

    /**
     * Określa czy identyfikator zbiorczy został wygenerowany w bieżącym kontekście.
     */
    private Boolean createdInCurrentContext;

    /**
     * Data utworzenia identyfikatora zbiorczego.
     */
    private OffsetDateTime dateCreated;

    public CollectiveIdentifiersByKsefNumberQueryResponseItem() {
    }

    public CollectiveIdentifiersByKsefNumberQueryResponseItem(String collectiveIdentifierNumber, Boolean createdInCurrentContext, OffsetDateTime dateCreated) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
        this.createdInCurrentContext = createdInCurrentContext;
        this.dateCreated = dateCreated;
    }

    /**
     * Numer identyfikatora zbiorczego.
     */
    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    /**
     * Numer identyfikatora zbiorczego.
     */
    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    /**
     * Określa czy identyfikator zbiorczy został wygenerowany w bieżącym kontekście.
     */
    public Boolean getCreatedInCurrentContext() {
        return createdInCurrentContext;
    }

    /**
     * Określa czy identyfikator zbiorczy został wygenerowany w bieżącym kontekście.
     */
    public void setCreatedInCurrentContext(Boolean createdInCurrentContext) {
        this.createdInCurrentContext = createdInCurrentContext;
    }

    /**
     * Data utworzenia identyfikatora zbiorczego.
     */
    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Data utworzenia identyfikatora zbiorczego.
     */
    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
