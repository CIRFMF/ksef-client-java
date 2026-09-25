package pl.akmf.ksef.sdk.client.model.permission.euentity;

import java.util.List;

/**
 * EuEntityPermissionsGrantRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GrantEUEntityRepresentativePermissionsRequest {

    /**
     * Identyfikator podmiotu uprawnianego.
     */
    private SubjectIdentifier subjectIdentifier;

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    private List<EuEntityPermissionType> permissions;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private PermissionsEuEntitySubjectDetails subjectDetails;

    public GrantEUEntityRepresentativePermissionsRequest() {
    }

    public GrantEUEntityRepresentativePermissionsRequest(SubjectIdentifier subjectIdentifier, List<EuEntityPermissionType> permissions, String description, PermissionsEuEntitySubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    /**
     * Identyfikator podmiotu uprawnianego.
     */
    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnianego.
     */
    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public List<EuEntityPermissionType> getPermissions() {
        return permissions;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public void setPermissions(List<EuEntityPermissionType> permissions) {
        this.permissions = permissions;
    }

    /**
     * Opis uprawnienia
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis uprawnienia
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public PermissionsEuEntitySubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(PermissionsEuEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }
}
