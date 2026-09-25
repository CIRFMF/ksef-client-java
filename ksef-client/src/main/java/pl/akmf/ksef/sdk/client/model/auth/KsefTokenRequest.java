package pl.akmf.ksef.sdk.client.model.auth;

import java.util.List;

/**
 * KsefTokenRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code GenerateTokenRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class KsefTokenRequest {

    List<TokenPermissionType> permissions;

    String description;

    public KsefTokenRequest(List<TokenPermissionType> permissions, String description) {
        this.permissions = permissions;
        this.description = description;
    }

    public KsefTokenRequest() {
    }

    public List<TokenPermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<TokenPermissionType> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
