package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * InvoiceFormCode.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code FormCode}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceFormCode {

    /**
     * Kod systemowy
     */
    private String systemCode;

    /**
     * Wersja schematu
     */
    private String schemaVersion;

    /**
     * Wartość
     */
    private String value;

    public InvoiceFormCode() {
    }

    public InvoiceFormCode(String systemCode, String schemaVersion, String value) {
        this.systemCode = systemCode;
        this.schemaVersion = schemaVersion;
        this.value = value;
    }

    /**
     * Kod systemowy
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Kod systemowy
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * Wersja schematu
     */
    public String getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Wersja schematu
     */
    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    /**
     * Wartość
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość
     */
    public void setValue(String value) {
        this.value = value;
    }
}
