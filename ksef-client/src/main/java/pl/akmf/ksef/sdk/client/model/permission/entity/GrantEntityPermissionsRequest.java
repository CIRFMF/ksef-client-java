package pl.akmf.ksef.sdk.client.model.permission.entity;

import java.util.List;

/**
 * GrantEntityPermissionsRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EntityPermissionsGrantRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GrantEntityPermissionsRequest {

    /**
     * Identyfikator podmiotu.
     */
    private SubjectIdentifier subjectIdentifier;

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    private List<EntityPermission> permissions;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private PermissionsEntitySubjectDetails subjectDetails;

    public GrantEntityPermissionsRequest() {
    }

    public GrantEntityPermissionsRequest(SubjectIdentifier subjectIdentifier, List<EntityPermission> permissions, String description, PermissionsEntitySubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    /**
     * Identyfikator podmiotu.
     */
    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator podmiotu.
     */
    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public List<EntityPermission> getPermissions() {
        return permissions;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public void setPermissions(List<EntityPermission> permissions) {
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
    public PermissionsEntitySubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(PermissionsEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     *
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EntityDetails}.
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public static class PermissionsEntitySubjectDetails {

        /**
         * Pełna nazwa podmiotu.
         */
        private String fullName;

        public PermissionsEntitySubjectDetails() {
        }

        public PermissionsEntitySubjectDetails(String fullName) {
            this.fullName = fullName;
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
    }
}
