package pl.akmf.ksef.sdk.client.model.session;


import java.time.OffsetDateTime;
import java.util.List;

public class SessionsQueryRequest {
    private SessionType sessionType;
    private String referenceNumber;
    private OffsetDateTime dateCreatedFrom;
    private OffsetDateTime dateCreatedTo;
    private OffsetDateTime dateClosedFrom;
    private OffsetDateTime dateClosedTo;
    private OffsetDateTime dateModifiedFrom;
    private OffsetDateTime dateModifiedTo;
    private List<CommonSessionStatus> statuses;

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
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

    public OffsetDateTime getDateClosedFrom() {
        return dateClosedFrom;
    }

    public void setDateClosedFrom(OffsetDateTime dateClosedFrom) {
        this.dateClosedFrom = dateClosedFrom;
    }

    public OffsetDateTime getDateClosedTo() {
        return dateClosedTo;
    }

    public void setDateClosedTo(OffsetDateTime dateClosedTo) {
        this.dateClosedTo = dateClosedTo;
    }

    public OffsetDateTime getDateModifiedFrom() {
        return dateModifiedFrom;
    }

    public void setDateModifiedFrom(OffsetDateTime dateModifiedFrom) {
        this.dateModifiedFrom = dateModifiedFrom;
    }

    public OffsetDateTime getDateModifiedTo() {
        return dateModifiedTo;
    }

    public void setDateModifiedTo(OffsetDateTime dateModifiedTo) {
        this.dateModifiedTo = dateModifiedTo;
    }

    public List<CommonSessionStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CommonSessionStatus> statuses) {
        this.statuses = statuses;
    }
}

