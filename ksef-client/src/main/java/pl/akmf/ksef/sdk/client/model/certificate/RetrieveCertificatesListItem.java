package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * RetrieveCertificatesListItem.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class RetrieveCertificatesListItem {

    /**
     * Certyfikat w formacie DER, zakodowany w formacie Base64.
     */
    private byte[] certificate;

    /**
     * Nazwa własna certyfikatu.
     */
    private String certificateName;

    /**
     * Numer seryjny certyfikatu.
     */
    private String certificateSerialNumber;

    /**
     * Typ certyfikatu.
     */
    private CertificateType certificateType;

    public RetrieveCertificatesListItem() {
    }

    public RetrieveCertificatesListItem(byte[] certificate, String certificateName, String certificateSerialNumber, CertificateType certificateType) {
        this.certificate = certificate;
        this.certificateName = certificateName;
        this.certificateSerialNumber = certificateSerialNumber;
        this.certificateType = certificateType;
    }

    /**
     * Certyfikat w formacie DER, zakodowany w formacie Base64.
     */
    public byte[] getCertificate() {
        return certificate;
    }

    /**
     * Certyfikat w formacie DER, zakodowany w formacie Base64.
     */
    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    /**
     * Nazwa własna certyfikatu.
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * Nazwa własna certyfikatu.
     */
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    /**
     * Numer seryjny certyfikatu.
     */
    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    /**
     * Numer seryjny certyfikatu.
     */
    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    /**
     * Typ certyfikatu.
     */
    public CertificateType getCertificateType() {
        return certificateType;
    }

    /**
     * Typ certyfikatu.
     */
    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }
}
