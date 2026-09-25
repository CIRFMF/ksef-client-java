package pl.akmf.ksef.sdk.client.model.lighthouse;

import java.time.OffsetDateTime;

/**
 * Pojedynczy komunikat usługi statusowej KSeF ("Latarnia" / Lighthouse) — odrębnej
 * usługi monitorującej dostępność systemu KSeF
 */
public class Message {

    /**
     * Identyfikator komunikatu.
     */
    private String id;

    /**
     * Identyfikator zdarzenia (grupy komunikatow), pozwalajacy powiazac komunikaty (np. start i koniec tej samej awarii).
     */
    private int eventId;

    /**
     * Kategoria komunikatu.
     */
    private String category;

    /**
     * Typ komunikatu.
     */
    private String type;

    /**
     * Tytul komunikatu.
     */
    private String title;

    /**
     * Tresc komunikatu.
     */
    private String text;

    /**
     * Poczatek okresu obowiazywania komunikatu.
     */
    private OffsetDateTime start;

    /**
     * Koniec okresu obowiazywania komunikatu.
     */
    private OffsetDateTime end;

    /**
     * Wersja komunikatu.
     */
    private int version;

    /**
     * Data i godzina udostepnienia komunikatu w serwisach Latarni.
     */
    private OffsetDateTime published;

    public Message() {
    }

    public Message(String id, int eventId, String category, String type, String title, String text, OffsetDateTime start, OffsetDateTime end, int version, OffsetDateTime published) {
        this.id = id;
        this.eventId = eventId;
        this.category = category;
        this.type = type;
        this.title = title;
        this.text = text;
        this.start = start;
        this.end = end;
        this.version = version;
        this.published = published;
    }

    /**
     * Identyfikator komunikatu.
     */
    public String getId() {
        return id;
    }

    /**
     * Identyfikator komunikatu.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Identyfikator zdarzenia (grupy komunikatow), pozwalajacy powiazac komunikaty (np. start i koniec tej samej awarii).
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Identyfikator zdarzenia (grupy komunikatow), pozwalajacy powiazac komunikaty (np. start i koniec tej samej awarii).
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Kategoria komunikatu.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Kategoria komunikatu.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Typ komunikatu.
     */
    public String getType() {
        return type;
    }

    /**
     * Typ komunikatu.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Tytul komunikatu.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Tytul komunikatu.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Tresc komunikatu.
     */
    public String getText() {
        return text;
    }

    /**
     * Tresc komunikatu.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Poczatek okresu obowiazywania komunikatu.
     */
    public OffsetDateTime getStart() {
        return start;
    }

    /**
     * Poczatek okresu obowiazywania komunikatu.
     */
    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    /**
     * Koniec okresu obowiazywania komunikatu.
     */
    public OffsetDateTime getEnd() {
        return end;
    }

    /**
     * Koniec okresu obowiazywania komunikatu.
     */
    public void setEnd(OffsetDateTime end) {
        this.end = end;
    }

    /**
     * Wersja komunikatu.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Wersja komunikatu.
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Data i godzina udostepnienia komunikatu w serwisach Latarni.
     */
    public OffsetDateTime getPublished() {
        return published;
    }

    /**
     * Data i godzina udostepnienia komunikatu w serwisach Latarni.
     */
    public void setPublished(OffsetDateTime published) {
        this.published = published;
    }
}
