package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * Informacje o limitach wniosków oraz certyfikatów dla uwierzytelnionego podmiotu.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateLimitsResponse {

    /**
     * Flaga informująca czy uwierzytelniony podmiot może złożyć nowy wniosek o certyfikat.
     */
    private Boolean canRequest;

    /**
     * Informacje o limitach związanych z liczbą możliwych do złożenia wniosków certyfikacyjnych.
     */
    private CertificateLimit enrollment;

    /**
     * Informacje o limitach dotyczących liczby aktywnych certyfikatów wydanych dla danego podmiotu.
     */
    private CertificateLimit certificate;

    public CertificateLimitsResponse() {
    }

    /**
     * Flaga informująca czy uwierzytelniony podmiot może złożyć nowy wniosek o certyfikat.
     */
    public Boolean getCanRequest() {
        return canRequest;
    }

    /**
     * Flaga informująca czy uwierzytelniony podmiot może złożyć nowy wniosek o certyfikat.
     */
    public void setCanRequest(Boolean canRequest) {
        this.canRequest = canRequest;
    }

    /**
     * Informacje o limitach związanych z liczbą możliwych do złożenia wniosków certyfikacyjnych.
     */
    public CertificateLimit getEnrollment() {
        return enrollment;
    }

    /**
     * Informacje o limitach związanych z liczbą możliwych do złożenia wniosków certyfikacyjnych.
     */
    public void setEnrollment(CertificateLimit enrollment) {
        this.enrollment = enrollment;
    }

    /**
     * Informacje o limitach dotyczących liczby aktywnych certyfikatów wydanych dla danego podmiotu.
     */
    public CertificateLimit getCertificate() {
        return certificate;
    }

    /**
     * Informacje o limitach dotyczących liczby aktywnych certyfikatów wydanych dla danego podmiotu.
     */
    public void setCertificate(CertificateLimit certificate) {
        this.certificate = certificate;
    }
}
