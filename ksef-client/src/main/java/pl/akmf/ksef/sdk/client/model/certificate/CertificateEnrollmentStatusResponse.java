package pl.akmf.ksef.sdk.client.model.certificate;

import pl.akmf.ksef.sdk.client.model.StatusInfo;
import java.time.OffsetDateTime;

/**
 * CertificateEnrollmentStatusResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateEnrollmentStatusResponse {

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    private OffsetDateTime requestDate;

    /**
     * Informacje o aktualnym statusie.
     */
    private StatusInfo status;

    /**
     * Numer seryjny wygenerowanego certyfikatu (w formacie szesnastkowym). Zwracany w przypadku prawidłowego przeprocesowania wniosku certyfikacyjnego.
     */
    private String certificateSerialNumber;

    public CertificateEnrollmentStatusResponse() {
    }

    public CertificateEnrollmentStatusResponse(OffsetDateTime requestDate, StatusInfo status, String certificateSerialNumber) {
        this.requestDate = requestDate;
        this.status = status;
        this.certificateSerialNumber = certificateSerialNumber;
    }

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    public OffsetDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    public void setRequestDate(OffsetDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Informacje o aktualnym statusie.
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Informacje o aktualnym statusie.
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * Numer seryjny wygenerowanego certyfikatu (w formacie szesnastkowym). Zwracany w przypadku prawidłowego przeprocesowania wniosku certyfikacyjnego.
     */
    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    /**
     * Numer seryjny wygenerowanego certyfikatu (w formacie szesnastkowym). Zwracany w przypadku prawidłowego przeprocesowania wniosku certyfikacyjnego.
     */
    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }
}
