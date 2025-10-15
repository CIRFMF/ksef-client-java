package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class EuEntityPermission {
    private String id;
    private EuEntityPermissionsAuthorIdentifier authorIdentifier;
    private String vatUeIdentifier;
    private String euEntityName;
    private String authorizedFingerprintIdentifier;
    private EuEntityPermissionsQueryPermissionType permissionScope;
    private String description;
    private OffsetDateTime startDate;

    public EuEntityPermission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EuEntityPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    public void setAuthorIdentifier(EuEntityPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    public String getVatUeIdentifier() {
        return vatUeIdentifier;
    }

    public void setVatUeIdentifier(String vatUeIdentifier) {
        this.vatUeIdentifier = vatUeIdentifier;
    }

    public String getEuEntityName() {
        return euEntityName;
    }

    public void setEuEntityName(String euEntityName) {
        this.euEntityName = euEntityName;
    }

    public String getAuthorizedFingerprintIdentifier() {
        return authorizedFingerprintIdentifier;
    }

    public void setAuthorizedFingerprintIdentifier(String authorizedFingerprintIdentifier) {
        this.authorizedFingerprintIdentifier = authorizedFingerprintIdentifier;
    }

    public EuEntityPermissionsQueryPermissionType getPermissionScope() {
        return permissionScope;
    }

    public void setPermissionScope(EuEntityPermissionsQueryPermissionType permissionScope) {
        this.permissionScope = permissionScope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}

