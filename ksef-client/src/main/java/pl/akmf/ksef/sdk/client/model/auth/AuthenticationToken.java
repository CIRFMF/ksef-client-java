package pl.akmf.ksef.sdk.client.model.auth;

import java.time.OffsetDateTime;
import java.util.List;

public class AuthenticationToken {
    private String referenceNumber;
    private AuthorTokenIdentifier authorIdentifier;
    private ContextIdentifier contextIdentifier;
    private String description;
    private List<TokenPermissionType> requestedPermissions;
    private OffsetDateTime dateCreated;
    private OffsetDateTime lastUseDate;
    private AuthenticationTokenStatus status;
    private List<String> statusDetails;

    public AuthenticationToken() {
    }


    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public AuthorTokenIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    public void setAuthorIdentifier(AuthorTokenIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    public ContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(ContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TokenPermissionType> getRequestedPermissions() {
        return requestedPermissions;
    }

    public void setRequestedPermissions(List<TokenPermissionType> requestedPermissions) {
        this.requestedPermissions = requestedPermissions;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUseDate() {
        return lastUseDate;
    }

    public void setLastUseDate(OffsetDateTime lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    public AuthenticationTokenStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenticationTokenStatus status) {
        this.status = status;
    }

    public List<String> getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(List<String> statusDetails) {
        this.statusDetails = statusDetails;
    }
}
