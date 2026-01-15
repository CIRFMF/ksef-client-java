package pl.akmf.ksef.sdk.client.model.session;

import java.net.URI;
import java.time.OffsetDateTime;

public class UpoPageResponse {
    private String referenceNumber;
    private URI downloadUrl;
    private OffsetDateTime downloadUrlExpirationDate;

    public UpoPageResponse() {
    }

    public UpoPageResponse(String referenceNumber, URI downloadUrl) {
        this.referenceNumber = referenceNumber;
        this.downloadUrl = downloadUrl;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public URI getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(URI downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public OffsetDateTime getDownloadUrlExpirationDate() {
        return downloadUrlExpirationDate;
    }

    public void setDownloadUrlExpirationDate(OffsetDateTime downloadUrlExpirationDate) {
        this.downloadUrlExpirationDate = downloadUrlExpirationDate;
    }
}

