package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

public class EuEntityPermission {
    private String id;
    private EuEntityPermissionsAuthorIdentifier authorIdentifier;
    private String vatUeIdentifier;
    private String euEntityName;
    private String authorizedFingerprintIdentifier;
    private EuEntityPermissionsQueryPermissionType permissionScope;
    private EuEntityPermissionSubjectPersonDetails subjectPersonDetails;
    private EuEntityPermissionSubjectEntityDetails subjectEntityDetails;
    private EuEntityPermissionEuEntityDetails euEntityDetails;
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

    public EuEntityPermissionSubjectPersonDetails getSubjectPersonDetails() {
        return subjectPersonDetails;
    }

    public void setSubjectPersonDetails(EuEntityPermissionSubjectPersonDetails subjectPersonDetails) {
        this.subjectPersonDetails = subjectPersonDetails;
    }

    public EuEntityPermissionSubjectEntityDetails getSubjectEntityDetails() {
        return subjectEntityDetails;
    }

    public void setSubjectEntityDetails(EuEntityPermissionSubjectEntityDetails subjectEntityDetails) {
        this.subjectEntityDetails = subjectEntityDetails;
    }

    public EuEntityPermissionEuEntityDetails getEuEntityDetails() {
        return euEntityDetails;
    }

    public void setEuEntityDetails(EuEntityPermissionEuEntityDetails euEntityDetails) {
        this.euEntityDetails = euEntityDetails;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}

