package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.time.OffsetDateTime;

public class CollectiveIdentifiersByKsefNumberQueryResponseItem {

    private String collectiveIdentifierNumber;
    private Boolean createdInCurrentContext;
    private OffsetDateTime dateCreated;

    public CollectiveIdentifiersByKsefNumberQueryResponseItem() {
    }

    public CollectiveIdentifiersByKsefNumberQueryResponseItem(String collectiveIdentifierNumber, Boolean createdInCurrentContext, OffsetDateTime dateCreated) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
        this.createdInCurrentContext = createdInCurrentContext;
        this.dateCreated = dateCreated;
    }

    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    public Boolean getCreatedInCurrentContext() {
        return createdInCurrentContext;
    }

    public void setCreatedInCurrentContext(Boolean createdInCurrentContext) {
        this.createdInCurrentContext = createdInCurrentContext;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
