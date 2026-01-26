package pl.akmf.ksef.sdk.client.model.lighthouse;

import java.time.OffsetDateTime;

// Komunikat z Latarni (Lighthouse)
public class Message {

    // Identyfikator komunikatu.
    private String id;
    // Identyfikator zdarzenia (grupy komunikatow), pozwalajacy powiazac komunikaty (np. start i koniec tej samej awarii).
    private int eventId;
    // Kategoria komunikatu.
    private String cat;
    // Typ komunikatu.
    private String type;
    // Tytul komunikatu.
    private String title;
    // Tresc komunikatu.
    private String text;
    // Poczatek okresu obowiazywania komunikatu.
    private OffsetDateTime start;
    // Koniec okresu obowiazywania komunikatu.
    private OffsetDateTime end;
    // Wersja komunikatu.
    private int version;
    // Data i godzina udostepnienia komunikatu w serwisach Latarni.
    private OffsetDateTime published;

    public Message() {
    }

    public Message(String id, int eventId, String cat, String type, String title, String text, OffsetDateTime start,
                   OffsetDateTime end, int version, OffsetDateTime published) {
        this.id = id;
        this.eventId = eventId;
        this.cat = cat;
        this.type = type;
        this.title = title;
        this.text = text;
        this.start = start;
        this.end = end;
        this.version = version;
        this.published = published;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public void setEnd(OffsetDateTime end) {
        this.end = end;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public OffsetDateTime getPublished() {
        return published;
    }

    public void setPublished(OffsetDateTime published) {
        this.published = published;
    }
}
