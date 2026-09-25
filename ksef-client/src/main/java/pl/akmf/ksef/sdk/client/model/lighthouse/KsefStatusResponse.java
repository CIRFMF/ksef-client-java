package pl.akmf.ksef.sdk.client.model.lighthouse;

import java.util.List;

/**
 * Odpowiedź statusu systemu KSeF zwracana przez usługę statusową ("Latarnia" / Lighthouse) —
 * odrębną usługę monitorującą dostępność systemu KSeF
 * Pole {@code status} przyjmuje m.in. wartości
 * {@code AVAILABLE}, {@code MAINTENANCE}, {@code FAILURE}, {@code TOTAL_FAILURE}.
 */
public class KsefStatusResponse {

    /**
     * Status systemu KSeF.
     */
    private String status;

    /**
     * Wiadomości dotyczące statusu systemu KSeF.
     */
    private List<Message> messages;

    public KsefStatusResponse() {
    }

    public KsefStatusResponse(String status, List<Message> messages) {
        this.status = status;
        this.messages = messages;
    }

    /**
     * Status systemu KSeF.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Status systemu KSeF.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Wiadomości dotyczące statusu systemu KSeF.
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Wiadomości dotyczące statusu systemu KSeF.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
