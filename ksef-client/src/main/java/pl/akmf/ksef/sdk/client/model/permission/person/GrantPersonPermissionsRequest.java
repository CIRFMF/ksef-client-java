package pl.akmf.ksef.sdk.client.model.permission.person;

import java.util.List;

/**
 * PersonPermissionsGrantRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GrantPersonPermissionsRequest {

    /**
     * Identyfikator osoby fizycznej.
     */
    private PersonPermissionsSubjectIdentifier subjectIdentifier;

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    private List<PersonPermissionType> permissions;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private PersonPermissionSubjectDetails subjectDetails;

    public GrantPersonPermissionsRequest() {
    }

    public GrantPersonPermissionsRequest(final PersonPermissionsSubjectIdentifier subjectIdentifier, final List<PersonPermissionType> permissions, final String description) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
    }

    public GrantPersonPermissionsRequest(PersonPermissionsSubjectIdentifier subjectIdentifier, List<PersonPermissionType> permissions, String description, PersonPermissionSubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public PersonPermissionsSubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public void setSubjectIdentifier(final PersonPermissionsSubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public List<PersonPermissionType> getPermissions() {
        return permissions;
    }

    /**
     * Lista nadawanych uprawnień. Każda wartość może wystąpić tylko raz.
     */
    public void setPermissions(final List<PersonPermissionType> permissions) {
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
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public PersonPermissionSubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(PersonPermissionSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }
}
