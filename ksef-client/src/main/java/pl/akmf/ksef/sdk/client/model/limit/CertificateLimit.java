package pl.akmf.ksef.sdk.client.model.limit;

public class CertificateLimit {
    private int maxCertificates;

    public CertificateLimit() {

    }

    public CertificateLimit(int maxCertificates) {
        this.maxCertificates = maxCertificates;
    }

    public int getMaxCertificates() {
        return maxCertificates;
    }

    public void setMaxCertificates(int maxCertificates) {
        this.maxCertificates = maxCertificates;
    }
}
