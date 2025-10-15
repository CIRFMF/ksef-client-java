package pl.akmf.ksef.sdk.client.model.certificate;

import java.util.List;

public class CertificateListResponse {
    private List<RetrieveCertificatesListItem> certificates;

    public CertificateListResponse() {
    }

    public CertificateListResponse(List<RetrieveCertificatesListItem> certificates) {
        this.certificates = certificates;
    }

    public List<RetrieveCertificatesListItem> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<RetrieveCertificatesListItem> certificates) {
        this.certificates = certificates;
    }
}

