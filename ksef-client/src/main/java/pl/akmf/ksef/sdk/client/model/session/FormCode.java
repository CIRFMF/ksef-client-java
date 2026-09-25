package pl.akmf.ksef.sdk.client.model.session;

/**
 * FormCode.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class FormCode {

    /**
     * Kod systemowy
     */
    private SystemCode systemCode;

    /**
     * Wersja schematu
     */
    private SchemaVersion schemaVersion;

    /**
     * Wartość
     */
    private SessionValue value;

    public FormCode() {
    }

    public FormCode(SystemCode systemCode, SchemaVersion schemaVersion, SessionValue value) {
        this.systemCode = systemCode;
        this.schemaVersion = schemaVersion;
        this.value = value;
    }

    /**
     * Kod systemowy
     */
    public SystemCode getSystemCode() {
        return systemCode;
    }

    /**
     * Kod systemowy
     */
    public void setSystemCode(SystemCode systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * Wersja schematu
     */
    public SchemaVersion getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Wersja schematu
     */
    public void setSchemaVersion(SchemaVersion schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    /**
     * Wartość
     */
    public SessionValue getValue() {
        return value;
    }

    /**
     * Wartość
     */
    public void setValue(SessionValue value) {
        this.value = value;
    }
}
