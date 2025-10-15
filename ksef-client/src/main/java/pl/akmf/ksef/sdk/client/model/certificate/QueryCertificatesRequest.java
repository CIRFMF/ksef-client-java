package pl.akmf.ksef.sdk.client.model.certificate;

import java.time.OffsetDateTime;

public class QueryCertificatesRequest {
    private String certificateSerialNumber;
    private String name;
    private CertificateType type;
    private CertificateListItemStatus status;
    private OffsetDateTime expiresAfter;

    public QueryCertificatesRequest() {
    }

    public QueryCertificatesRequest(String certificateSerialNumber,
                                    String name,
                                    CertificateType type,
                                    CertificateListItemStatus status,
                                    OffsetDateTime expiresAfter) {
        this.certificateSerialNumber = certificateSerialNumber;
        this.name = name;
        this.type = type;
        this.status = status;
        this.expiresAfter = expiresAfter;
    }

    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CertificateType getType() {
        return type;
    }

    public void setType(CertificateType type) {
        this.type = type;
    }

    public CertificateListItemStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateListItemStatus status) {
        this.status = status;
    }

    public OffsetDateTime getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(OffsetDateTime expiresAfter) {
        this.expiresAfter = expiresAfter;
    }
}

