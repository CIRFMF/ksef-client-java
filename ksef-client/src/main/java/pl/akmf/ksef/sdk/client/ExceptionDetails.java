package pl.akmf.ksef.sdk.client;

import java.util.List;

/**
 * ExceptionDetails.
 * Reprezentuje pojedynczy szczegół wyjątku w odpowiedzi błędu API.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ExceptionDetails {
    /**
     * Numeryczny kod reprezentujący typ wyjątku.
     */
    private int exceptionCode;
    /**
     * Czytelny dla człowieka opis wyjątku.
     */
    private String exceptionDescription;
    /**
     * Opcjonalna lista dodatkowych komunikatów kontekstowych.
     */
    private List<String> details;

    public ExceptionDetails(int exceptionCode, String exceptionDescription, List<String> details) {
        this.exceptionCode = exceptionCode;
        this.exceptionDescription = exceptionDescription;
        this.details = details;
    }

    public ExceptionDetails() {

    }
    /**
     * Numeryczny kod reprezentujący typ wyjątku.
     */
    public int getExceptionCode() {
        return exceptionCode;
    }
    /**
     * Numeryczny kod reprezentujący typ wyjątku.
     */
    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
    /**
     * Czytelny dla człowieka opis wyjątku.
     */
    public String getExceptionDescription() {
        return exceptionDescription;
    }
    /**
     * Czytelny dla człowieka opis wyjątku.
     */
    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }
    /**
     * Opcjonalna lista dodatkowych komunikatów kontekstowych.
     */
    public List<String> getDetails() {
        return details;
    }
    /**
     * Opcjonalna lista dodatkowych komunikatów kontekstowych.
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "exceptionCode=" + exceptionCode +
                ", exceptionDescription='" + exceptionDescription + '\'' +
                ", details=" + details;
    }
}
