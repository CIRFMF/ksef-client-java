package pl.akmf.ksef.sdk.client.model.lighthouse;

import java.util.List;

// Odpowiedź statusu systemu KSeF zwracana przez Latarnię.
public class KsefStatusResponse {

    // Status systemu KSeF.
    // Możliwe wartości: AVAILABLE, MAINTENANCE, FAILURE, TOTAL_FAILURE
    private String status;

    // Wiadomości dotyczące statusu systemu KSeF.
    private List<Message> messages;

    public KsefStatusResponse() {
    }

    public KsefStatusResponse(String status, List<Message> messages) {
        this.status = status;
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
