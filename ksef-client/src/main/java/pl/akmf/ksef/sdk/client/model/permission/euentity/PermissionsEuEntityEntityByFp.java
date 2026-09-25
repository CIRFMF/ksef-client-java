package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * Dane podmiotu. *Wymagane, gdy subjectDetailsType = EntityByFingerprint.*
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EntityByFingerprintDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsEuEntityEntityByFp {

    /**
     * Pełna nazwa podmiotu.
     */
    private String fullName;

    /**
     * Adres podmiotu.
     */
    private String address;

    public PermissionsEuEntityEntityByFp() {
    }

    public PermissionsEuEntityEntityByFp(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
    }

    /**
     * Pełna nazwa podmiotu.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Pełna nazwa podmiotu.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Adres podmiotu.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Adres podmiotu.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
