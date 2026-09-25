package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * ThirdSubject.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceMetadataThirdSubject}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ThirdSubject {

    /**
     * Identyfikator podmiotu trzeciego.
     */
    private ThirdSubjectIdentifier identifier;

    /**
     * Nazwa podmiotu trzeciego.
     */
    private String name;

    /**
     * Rola podmiotu trzeciego.
     */
    private int role;

    public ThirdSubject(final ThirdSubjectIdentifier identifier, final String name, final int role) {
        this.identifier = identifier;
        this.name = name;
        this.role = role;
    }

    public ThirdSubject() {
    }

    /**
     * Identyfikator podmiotu trzeciego.
     */
    public ThirdSubjectIdentifier getIdentifier() {
        return identifier;
    }

    /**
     * Identyfikator podmiotu trzeciego.
     */
    public void setIdentifier(final ThirdSubjectIdentifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Nazwa podmiotu trzeciego.
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa podmiotu trzeciego.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Rola podmiotu trzeciego.
     */
    public int getRole() {
        return role;
    }

    /**
     * Rola podmiotu trzeciego.
     */
    public void setRole(final int role) {
        this.role = role;
    }
}
