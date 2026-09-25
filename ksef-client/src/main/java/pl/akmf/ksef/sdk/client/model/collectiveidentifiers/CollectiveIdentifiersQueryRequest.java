package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.time.OffsetDateTime;

/**
 * CollectiveIdentifiersQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifiersQueryRequest {

    /**
     * Numer identyfikatora zbiorczego.
     */
    private String collectiveIdentifierNumber;

    /**
     * Data utworzenia identyfikatora zbiorczego (od), maksymalny przedział czasu to 100 dni.
     */
    private OffsetDateTime dateCreatedFrom;

    /**
     * Data utworzenia identyfikatora zbiorczego (do).
     */
    private OffsetDateTime dateCreatedTo;

    /**
     * Liczba faktur w identyfikatorze zbiorczym (od).
     */
    private Integer invoiceCountFrom;

    /**
     * Liczba faktur w identyfikatorze zbiorczym (do).
     */
    private Integer invoiceCountTo;

    /**
     * Określa czy identyfikator zbiorczy został wygenerowany w bieżącym kontekście.
     */
    private Boolean createdInCurrentContext;

    public CollectiveIdentifiersQueryRequest() {
    }

    public CollectiveIdentifiersQueryRequest(String collectiveIdentifierNumber, OffsetDateTime dateCreatedFrom, OffsetDateTime dateCreatedTo, Integer invoiceCountFrom, Integer invoiceCountTo, Boolean createdInCurrentContext) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
        this.dateCreatedFrom = dateCreatedFrom;
        this.dateCreatedTo = dateCreatedTo;
        this.invoiceCountFrom = invoiceCountFrom;
        this.invoiceCountTo = invoiceCountTo;
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
     * Data utworzenia identyfikatora zbiorczego (od), maksymalny przedział czasu to 100 dni.
     */
    public OffsetDateTime getDateCreatedFrom() {
        return dateCreatedFrom;
    }

    /**
     * Data utworzenia identyfikatora zbiorczego (od), maksymalny przedział czasu to 100 dni.
     */
    public void setDateCreatedFrom(OffsetDateTime dateCreatedFrom) {
        this.dateCreatedFrom = dateCreatedFrom;
    }

    /**
     * Data utworzenia identyfikatora zbiorczego (do).
     */
    public OffsetDateTime getDateCreatedTo() {
        return dateCreatedTo;
    }

    /**
     * Data utworzenia identyfikatora zbiorczego (do).
     */
    public void setDateCreatedTo(OffsetDateTime dateCreatedTo) {
        this.dateCreatedTo = dateCreatedTo;
    }

    /**
     * Liczba faktur w identyfikatorze zbiorczym (od).
     */
    public Integer getInvoiceCountFrom() {
        return invoiceCountFrom;
    }

    /**
     * Liczba faktur w identyfikatorze zbiorczym (od).
     */
    public void setInvoiceCountFrom(Integer invoiceCountFrom) {
        this.invoiceCountFrom = invoiceCountFrom;
    }

    /**
     * Liczba faktur w identyfikatorze zbiorczym (do).
     */
    public Integer getInvoiceCountTo() {
        return invoiceCountTo;
    }

    /**
     * Liczba faktur w identyfikatorze zbiorczym (do).
     */
    public void setInvoiceCountTo(Integer invoiceCountTo) {
        this.invoiceCountTo = invoiceCountTo;
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
