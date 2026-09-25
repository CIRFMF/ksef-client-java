package pl.akmf.ksef.sdk.client.model.permission;

import pl.akmf.ksef.sdk.client.model.StatusInfo;

/**
 * PermissionStatusInfo.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PermissionsOperationStatusResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionStatusInfo {

    /**
     * Informacje o aktualnym statusie.
     */
    private StatusInfo status;

    public PermissionStatusInfo() {
    }

    public PermissionStatusInfo(StatusInfo status) {
        this.status = status;
    }

    /**
     * Informacje o aktualnym statusie.
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Informacje o aktualnym statusie.
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }
}
