package pl.akmf.ksef.sdk.client.model.certificate;

public class RetrieveCertificatesListItem {
    private byte[] certificate;
    private String certificateName;
    private String certificateSerialNumber;
    private CertificateType certificateType;

    public RetrieveCertificatesListItem() {
    }

    public RetrieveCertificatesListItem(byte[] certificate, String certificateName, String certificateSerialNumber, CertificateType certificateType) {
        this.certificate = certificate;
        this.certificateName = certificateName;
        this.certificateSerialNumber = certificateSerialNumber;
        this.certificateType = certificateType;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }
}

