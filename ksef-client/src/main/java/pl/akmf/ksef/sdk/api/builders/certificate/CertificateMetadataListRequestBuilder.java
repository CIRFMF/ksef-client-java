package pl.akmf.ksef.sdk.api.builders.certificate;

import pl.akmf.ksef.sdk.client.model.certificate.CertificateListItemStatus;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateType;
import pl.akmf.ksef.sdk.client.model.certificate.QueryCertificatesRequest;

import java.time.OffsetDateTime;

public class CertificateMetadataListRequestBuilder {
    private String certificateSerialNumber;
    private String name;
    private CertificateType type;
    private CertificateListItemStatus status;
    private OffsetDateTime expiresAfter;

    public CertificateMetadataListRequestBuilder withCertificateSerialNumber(final String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
        return this;
    }

    public CertificateMetadataListRequestBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public CertificateMetadataListRequestBuilder withType(final CertificateType type) {
        this.type = type;
        return this;
    }

    public CertificateMetadataListRequestBuilder withStatus(final CertificateListItemStatus status) {
        this.status = status;
        return this;
    }

    public CertificateMetadataListRequestBuilder withExpiresAfter(final OffsetDateTime expiresAfter) {
        this.expiresAfter = expiresAfter;
        return this;
    }

    public QueryCertificatesRequest build() {
        QueryCertificatesRequest request = new QueryCertificatesRequest();
        request.setCertificateSerialNumber(certificateSerialNumber);
        request.setName(name);
        request.setType(type);
        request.setStatus(status);
        request.setExpiresAfter(expiresAfter);

        return request;
    }

}
