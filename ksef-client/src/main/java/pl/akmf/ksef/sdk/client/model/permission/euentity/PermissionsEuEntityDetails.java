package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * EuEntityDetails.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsEuEntityDetails {

    /**
     * Pełna nazwa podmiotu unijnego.
     */
    private String fullName;

    /**
     * Adres podmiotu unijnego.
     */
    private String address;

    public PermissionsEuEntityDetails() {
    }

    public PermissionsEuEntityDetails(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
    }

    /**
     * Pełna nazwa podmiotu unijnego.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Pełna nazwa podmiotu unijnego.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Adres podmiotu unijnego.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Adres podmiotu unijnego.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
