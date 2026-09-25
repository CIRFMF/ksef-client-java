package pl.akmf.ksef.sdk.client.model.session.batch;

/**
 * Wewnętrzna para klucz-wartość klienta SDK reprezentująca pojedynczy nagłówek HTTP
 * przy wysyłce części paczki wsadowej.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class HeaderEntryType {

    private String key;

    private String value;

    public HeaderEntryType() {
    }

    public HeaderEntryType(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
