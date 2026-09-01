package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.time.OffsetDateTime;

public class CollectiveIdentifiersQueryResponseItem {

    private String collectiveIdentifierNumber;
    private OffsetDateTime dateCreated;
    private Integer invoiceCount;
    private Boolean createdInCurrentContext;

    public CollectiveIdentifiersQueryResponseItem() {
    }

    public CollectiveIdentifiersQueryResponseItem(String collectiveIdentifierNumber, OffsetDateTime dateCreated, Integer invoiceCount, Boolean createdInCurrentContext) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
        this.dateCreated = dateCreated;
        this.invoiceCount = invoiceCount;
        this.createdInCurrentContext = createdInCurrentContext;
    }

    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public Boolean getCreatedInCurrentContext() {
        return createdInCurrentContext;
    }

    public void setCreatedInCurrentContext(Boolean createdInCurrentContext) {
        this.createdInCurrentContext = createdInCurrentContext;
    }
}
