package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.time.OffsetDateTime;

/**
 * CollectiveIdentifiersQueryResponseItem.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifiersQueryResponseItem {

    /**
     * Numer identyfikatora zbiorczego.
     */
    private String collectiveIdentifierNumber;

    /**
     * Data utworzenia identyfikatora zbiorczego.
     */
    private OffsetDateTime dateCreated;

    /**
     * Liczba faktura składająca się na identyfikator zbiorczy.
     */
    private Integer invoiceCount;

    /**
     * Określa czy identyfikator zbiorczy został wygenerowany w bieżącym kontekście.
     */
    private Boolean createdInCurrentContext;

    public CollectiveIdentifiersQueryResponseItem() {
    }

    public CollectiveIdentifiersQueryResponseItem(String collectiveIdentifierNumber, OffsetDateTime dateCreated, Integer invoiceCount, Boolean createdInCurrentContext) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
        this.dateCreated = dateCreated;
        this.invoiceCount = invoiceCount;
        this.createdInCurrentContext = createdInCurrentContext;
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

    /**
     * Liczba faktura składająca się na identyfikator zbiorczy.
     */
    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    /**
     * Liczba faktura składająca się na identyfikator zbiorczy.
     */
    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
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
}
