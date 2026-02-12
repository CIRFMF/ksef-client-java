package pl.akmf.ksef.sdk.client;

import java.util.List;

// Reprezentuje pojedynczy szczegół wyjątku w odpowiedzi błędu API.
public class ExceptionDetails {
    // Numeryczny kod reprezentujący typ wyjątku.
    private int exceptionCode;
    // Czytelny dla człowieka opis wyjątku.
    private String exceptionDescription;
    // Opcjonalna lista dodatkowych komunikatów kontekstowych.
    private List<String> details;

    public ExceptionDetails(int exceptionCode, String exceptionDescription, List<String> details) {
        this.exceptionCode = exceptionCode;
        this.exceptionDescription = exceptionDescription;
        this.details = details;
    }

    public ExceptionDetails() {

    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    public List<String> getDetails() {
        return details;
    }

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
