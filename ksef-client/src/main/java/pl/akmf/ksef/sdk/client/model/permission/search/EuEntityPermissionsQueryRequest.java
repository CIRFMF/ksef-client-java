package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * EuEntityPermissionsQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EuEntityPermissionsQueryRequest {

    /**
     * Wartość identyfikatora (numeru identyfikacyjnego VAT) podmiotu unijnego (exact match).
     */
    private String vatUeIdentifier;

    /**
     * Odcisk palca certyfikatu kwalifikowanego uprawnionego (contains).
     */
    private String authorizedFingerprintIdentifier;

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    private List<EuEntityPermissionsQueryPermissionType> permissionTypes;

    public EuEntityPermissionsQueryRequest() {
    }

    public EuEntityPermissionsQueryRequest(String vatUeIdentifier, String authorizedFingerprintIdentifier, List<EuEntityPermissionsQueryPermissionType> permissionTypes) {
        this.vatUeIdentifier = vatUeIdentifier;
        this.authorizedFingerprintIdentifier = authorizedFingerprintIdentifier;
        this.permissionTypes = permissionTypes;
    }

    /**
     * Wartość identyfikatora (numeru identyfikacyjnego VAT) podmiotu unijnego (exact match).
     */
    public String getVatUeIdentifier() {
        return vatUeIdentifier;
    }

    /**
     * Wartość identyfikatora (numeru identyfikacyjnego VAT) podmiotu unijnego (exact match).
     */
    public void setVatUeIdentifier(String vatUeIdentifier) {
        this.vatUeIdentifier = vatUeIdentifier;
    }

    /**
     * Odcisk palca certyfikatu kwalifikowanego uprawnionego (contains).
     */
    public String getAuthorizedFingerprintIdentifier() {
        return authorizedFingerprintIdentifier;
    }

    /**
     * Odcisk palca certyfikatu kwalifikowanego uprawnionego (contains).
     */
    public void setAuthorizedFingerprintIdentifier(String authorizedFingerprintIdentifier) {
        this.authorizedFingerprintIdentifier = authorizedFingerprintIdentifier;
    }

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public List<EuEntityPermissionsQueryPermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public void setPermissionTypes(List<EuEntityPermissionsQueryPermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }
}
