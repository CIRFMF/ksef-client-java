package pl.akmf.ksef.sdk.client.model.exceptions;

import java.util.List;

/**
 * BadRequestApiError.
 * Reprezentuje pojedynczy błąd w odpowiedzi Problem Details dla HTTP 400 Bad Request.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code ApiError}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class BadRequestApiError {

    /**
     * Kod błędu.
     */
    private int code;

    /**
     * Ogólny opis błędu odpowiadający danemu kodowi.
     */
    private String description;

    /**
     * Lista szczegółowych komunikatów opisujących konkretny błąd. Może zawierać wiele wpisów dla jednego kodu błędu.
     */
    private List<String> details;

    public BadRequestApiError() {
    }

    public BadRequestApiError(int code, String description, List<String> details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }

    /**
     * Kod błędu.
     */
    public int getCode() {
        return code;
    }

    /**
     * Kod błędu.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Ogólny opis błędu odpowiadający danemu kodowi.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ogólny opis błędu odpowiadający danemu kodowi.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Lista szczegółowych komunikatów opisujących konkretny błąd. Może zawierać wiele wpisów dla jednego kodu błędu.
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Lista szczegółowych komunikatów opisujących konkretny błąd. Może zawierać wiele wpisów dla jednego kodu błędu.
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "{ code=" + code + ", description='" + description + ", details=" + details + '}';
    }
}
