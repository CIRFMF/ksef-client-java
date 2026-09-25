package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * AuthorizedSubject.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceMetadataAuthorizedSubject}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthorizedSubject {

    /**
     * Nip podmiotu upoważnionego
     */
    private String nip;

    /**
     * Nazwa podmiotu upoważnionego.
     */
    private String name;

    /**
     * Rola podmiotu upoważnionego.
     */
    private int role;

    public AuthorizedSubject() {
    }

    /**
     * Nip podmiotu upoważnionego
     */
    public String getNip() {
        return nip;
    }

    /**
     * Nip podmiotu upoważnionego
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * Nazwa podmiotu upoważnionego.
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa podmiotu upoważnionego.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Rola podmiotu upoważnionego.
     */
    public int getRole() {
        return role;
    }

    /**
     * Rola podmiotu upoważnionego.
     */
    public void setRole(int role) {
        this.role = role;
    }
}
