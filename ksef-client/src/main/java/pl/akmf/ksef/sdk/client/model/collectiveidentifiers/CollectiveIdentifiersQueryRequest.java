package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.time.OffsetDateTime;

public class CollectiveIdentifiersQueryRequest {

    private String collectiveIdentifierNumber;
    private OffsetDateTime dateCreatedFrom;
    private OffsetDateTime dateCreatedTo;
    private Integer invoiceCountFrom;
    private Integer invoiceCountTo;
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

    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    public OffsetDateTime getDateCreatedFrom() {
        return dateCreatedFrom;
    }

    public void setDateCreatedFrom(OffsetDateTime dateCreatedFrom) {
        this.dateCreatedFrom = dateCreatedFrom;
    }

    public OffsetDateTime getDateCreatedTo() {
        return dateCreatedTo;
    }

    public void setDateCreatedTo(OffsetDateTime dateCreatedTo) {
        this.dateCreatedTo = dateCreatedTo;
    }

    public Integer getInvoiceCountFrom() {
        return invoiceCountFrom;
    }

    public void setInvoiceCountFrom(Integer invoiceCountFrom) {
        this.invoiceCountFrom = invoiceCountFrom;
    }

    public Integer getInvoiceCountTo() {
        return invoiceCountTo;
    }

    public void setInvoiceCountTo(Integer invoiceCountTo) {
        this.invoiceCountTo = invoiceCountTo;
    }

    public Boolean getCreatedInCurrentContext() {
        return createdInCurrentContext;
    }

    public void setCreatedInCurrentContext(Boolean createdInCurrentContext) {
        this.createdInCurrentContext = createdInCurrentContext;
    }
}
