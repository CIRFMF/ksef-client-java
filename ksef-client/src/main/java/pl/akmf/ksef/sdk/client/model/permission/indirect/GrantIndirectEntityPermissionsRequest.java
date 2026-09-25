package pl.akmf.ksef.sdk.client.model.permission.indirect;

import java.util.List;

/**
 * GrantIndirectEntityPermissionsRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code IndirectPermissionsGrantRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GrantIndirectEntityPermissionsRequest {

    /**
     * Identyfikator osoby fizycznej.
     */
    private SubjectIdentifier subjectIdentifier;

    /**
     * Identyfikator kontekstu klienta. Nie przekazanie identyfikatora oznacza, że uprawnienie nadane w sposób pośredni jest typu generalnego.
     */
    private TargetIdentifier targetIdentifier;

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    private List<IndirectPermissionType> permissions;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private PermissionsIndirectEntitySubjectDetails subjectDetails;

    public GrantIndirectEntityPermissionsRequest() {
    }

    public GrantIndirectEntityPermissionsRequest(SubjectIdentifier subjectIdentifier, TargetIdentifier targetIdentifier, List<IndirectPermissionType> permissions, String description) {
        this.subjectIdentifier = subjectIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.permissions = permissions;
        this.description = description;
    }

    public GrantIndirectEntityPermissionsRequest(SubjectIdentifier subjectIdentifier, TargetIdentifier targetIdentifier, List<IndirectPermissionType> permissions, String description, PermissionsIndirectEntitySubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.permissions = permissions;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public List<IndirectPermissionType> getPermissions() {
        return permissions;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public void setPermissions(List<IndirectPermissionType> permissions) {
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
     * Identyfikator kontekstu klienta. Nie przekazanie identyfikatora oznacza, że uprawnienie nadane w sposób pośredni jest typu generalnego.
     */
    public TargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    /**
     * Identyfikator kontekstu klienta. Nie przekazanie identyfikatora oznacza, że uprawnienie nadane w sposób pośredni jest typu generalnego.
     */
    public void setTargetIdentifier(TargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public PermissionsIndirectEntitySubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(PermissionsIndirectEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }
}
