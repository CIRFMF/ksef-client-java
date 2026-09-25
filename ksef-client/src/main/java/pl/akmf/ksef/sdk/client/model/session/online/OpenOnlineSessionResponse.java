package pl.akmf.ksef.sdk.client.model.session.online;

import java.time.OffsetDateTime;

/**
 * OpenOnlineSessionResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class OpenOnlineSessionResponse {

    /**
     * Numer referencyjny sesji.
     */
    private String referenceNumber;

    /**
     * Termin ważności sesji. Po jego upływie sesja zostanie automatycznie zamknięta.
     */
    private OffsetDateTime validUntil;

    public OpenOnlineSessionResponse() {
    }

    public OpenOnlineSessionResponse(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Numer referencyjny sesji.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny sesji.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Termin ważności sesji. Po jego upływie sesja zostanie automatycznie zamknięta.
     */
    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * Termin ważności sesji. Po jego upływie sesja zostanie automatycznie zamknięta.
     */
    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
