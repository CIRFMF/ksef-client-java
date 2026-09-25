package pl.akmf.ksef.sdk.client.model.permission.proxy;

import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;

/**
 * GrantAuthorizationPermissionsRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EntityAuthorizationPermissionsGrantRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GrantAuthorizationPermissionsRequest {

    /**
     * Identyfikator podmiotu uprawnianego.
     */
    private SubjectIdentifier subjectIdentifier;

    /**
     * Rodzaj uprawnienia.
     */
    private InvoicePermissionType permission;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private PermissionsAuthorizationSubjectDetails subjectDetails;

    public GrantAuthorizationPermissionsRequest() {
    }

    public GrantAuthorizationPermissionsRequest(SubjectIdentifier subjectIdentifier, InvoicePermissionType permission, String description) {
        this.subjectIdentifier = subjectIdentifier;
        this.permission = permission;
        this.description = description;
    }

    public GrantAuthorizationPermissionsRequest(SubjectIdentifier subjectIdentifier, InvoicePermissionType permission, String description, PermissionsAuthorizationSubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permission = permission;
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
     * Rodzaj uprawnienia.
     */
    public InvoicePermissionType getPermission() {
        return permission;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setPermission(InvoicePermissionType permission) {
        this.permission = permission;
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
    public PermissionsAuthorizationSubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(PermissionsAuthorizationSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     *
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EntityDetails}.
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public static class PermissionsAuthorizationSubjectDetails {

        /**
         * Pełna nazwa podmiotu.
         */
        private String fullName;

        public PermissionsAuthorizationSubjectDetails() {
        }

        public PermissionsAuthorizationSubjectDetails(String fullName) {
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
