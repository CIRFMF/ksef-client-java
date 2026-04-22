package pl.akmf.ksef.sdk.client.model.exceptions;

import java.util.List;

// Reprezentuje pojedynczy błąd w odpowiedzi Problem Details dla HTTP 400 Bad Request.
public class BadRequestApiError {

    // Kod błędu.
    private int code;

    // Ogólny opis błędu odpowiadający danemu kodowi.
    private String description;

    // Lista szczegółowych komunikatów opisujących konkretny błąd.
    // Może zawierać wiele wpisów dla jednego kodu błędu.
    private List<String> details;

    public BadRequestApiError() {
    }

    public BadRequestApiError(int code, String description, List<String> details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "{ code=" + code +
                ", description='" + description +
                ", details=" + details +
                '}';
    }
}
