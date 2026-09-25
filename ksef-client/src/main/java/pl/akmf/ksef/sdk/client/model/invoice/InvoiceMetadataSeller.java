package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * InvoiceMetadataSeller.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceMetadataSeller {

    /**
     * Nip sprzedawcy.
     */
    private String nip;

    /**
     * Nazwa sprzedawcy.
     */
    private String name;

    public InvoiceMetadataSeller() {
    }

    /**
     * Nip sprzedawcy.
     */
    public String getNip() {
        return nip;
    }

    /**
     * Nip sprzedawcy.
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * Nazwa sprzedawcy.
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa sprzedawcy.
     */
    public void setName(String name) {
        this.name = name;
    }
}
