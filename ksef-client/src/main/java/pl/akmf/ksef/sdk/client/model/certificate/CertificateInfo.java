package pl.akmf.ksef.sdk.client.model.certificate;

import java.time.OffsetDateTime;

public class CertificateInfo {
    private String certificateSerialNumber;
    private String name;
    private CertificateType type;
    private String commonName;
    private CertificateListItemStatus status;
    private SubjectCertificateIdentifier subjectIdentifier;
    private OffsetDateTime validFrom;
    private OffsetDateTime validTo;
    private OffsetDateTime lastUseDate;
    private OffsetDateTime requestDate;

    public CertificateInfo() {

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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public CertificateListItemStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateListItemStatus status) {
        this.status = status;
    }

    public SubjectCertificateIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    public void setSubjectIdentifier(SubjectCertificateIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public OffsetDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(OffsetDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public OffsetDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(OffsetDateTime validTo) {
        this.validTo = validTo;
    }

    public OffsetDateTime getLastUseDate() {
        return lastUseDate;
    }

    public void setLastUseDate(OffsetDateTime lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    public CertificateType getType() {
        return type;
    }

    public void setType(CertificateType type) {
        this.type = type;
    }

    public OffsetDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(OffsetDateTime requestDate) {
        this.requestDate = requestDate;
    }
}