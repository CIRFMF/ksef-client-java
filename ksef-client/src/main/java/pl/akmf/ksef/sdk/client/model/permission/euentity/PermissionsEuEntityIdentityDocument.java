package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * Dane dokumentu tożsamości osoby fizycznej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code IdDocument}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsEuEntityIdentityDocument {

    /**
     * Rodzaj dokumentu tożsamości.
     */
    private String type;

    /**
     * Seria i numer dokumentu tożsamości.
     */
    private String number;

    /**
     * Kraj wydania dokumentu tożsamości. Musi być zgodny z ISO 3166-1 alpha-2 (np. PL, DE, US) oraz zawierać dokładnie 2 wielkie litery.
     */
    private String country;

    public PermissionsEuEntityIdentityDocument() {
    }

    public PermissionsEuEntityIdentityDocument(String type, String number, String country) {
        this.type = type;
        this.number = number;
        this.country = country;
    }

    /**
     * Rodzaj dokumentu tożsamości.
     */
    public String getType() {
        return type;
    }

    /**
     * Rodzaj dokumentu tożsamości.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Seria i numer dokumentu tożsamości.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Seria i numer dokumentu tożsamości.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Kraj wydania dokumentu tożsamości. Musi być zgodny z ISO 3166-1 alpha-2 (np. PL, DE, US) oraz zawierać dokładnie 2 wielkie litery.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Kraj wydania dokumentu tożsamości. Musi być zgodny z ISO 3166-1 alpha-2 (np. PL, DE, US) oraz zawierać dokładnie 2 wielkie litery.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
