package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * SendCertificateEnrollmentRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EnrollCertificateRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SendCertificateEnrollmentRequest {

    /**
     * Nazwa własna certyfikatu.
     */
    private String certificateName;

    /**
     * Wniosek certyfikacyjny PKCS#10 (CSR) w formacie DER, zakodowany w formacie Base64.
     */
    private byte[] csr;

    /**
     * Data rozpoczęcia ważności certyfikatu. Jeśli nie zostanie podana, certyfikat będzie ważny od momentu jego wystawienia.
     */
    private String validFrom;

    /**
     * Typ certyfikatu.
     */
    private CertificateType certificateType;

    public SendCertificateEnrollmentRequest() {
    }

    public SendCertificateEnrollmentRequest(String certificateName, byte[] csr, String validFrom, CertificateType certificateType) {
        this.certificateName = certificateName;
        this.csr = csr;
        this.validFrom = validFrom;
        this.certificateType = certificateType;
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
     * Wniosek certyfikacyjny PKCS#10 (CSR) w formacie DER, zakodowany w formacie Base64.
     */
    public byte[] getCsr() {
        return csr;
    }

    /**
     * Wniosek certyfikacyjny PKCS#10 (CSR) w formacie DER, zakodowany w formacie Base64.
     */
    public void setCsr(byte[] csr) {
        this.csr = csr;
    }

    /**
     * Data rozpoczęcia ważności certyfikatu. Jeśli nie zostanie podana, certyfikat będzie ważny od momentu jego wystawienia.
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Data rozpoczęcia ważności certyfikatu. Jeśli nie zostanie podana, certyfikat będzie ważny od momentu jego wystawienia.
     */
    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
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
