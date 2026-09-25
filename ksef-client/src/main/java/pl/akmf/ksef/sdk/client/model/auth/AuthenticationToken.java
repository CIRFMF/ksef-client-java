package pl.akmf.ksef.sdk.client.model.auth;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * AuthenticationToken.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code TokenStatusResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthenticationToken {

    /**
     * Numer referencyjny tokena KSeF.
     */
    private String referenceNumber;

    /**
     * Identyfikator osoby która wygenerowała token.
     */
    private AuthorTokenIdentifier authorIdentifier;

    /**
     * Identyfikator kontekstu, w którym został wygenerowany token i do którego daje dostęp.
     */
    private ContextIdentifier contextIdentifier;

    /**
     * Opis tokena.
     */
    private String description;

    /**
     * Uprawnienia przypisane tokenowi.
     */
    private List<TokenPermissionType> requestedPermissions;

    /**
     * Data i czas utworzenia tokena.
     */
    private OffsetDateTime dateCreated;

    /**
     * Data ostatniego użycia tokena.
     */
    private OffsetDateTime lastUseDate;

    /**
     * Status tokena.
     */
    private AuthenticationTokenStatus status;

    /**
     * Dodatkowe informacje na temat statusu, zwracane w przypadku błędów.
     */
    private List<String> statusDetails;

    public AuthenticationToken() {
    }

    /**
     * Numer referencyjny tokena KSeF.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny tokena KSeF.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Identyfikator osoby która wygenerowała token.
     */
    public AuthorTokenIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    /**
     * Identyfikator osoby która wygenerowała token.
     */
    public void setAuthorIdentifier(AuthorTokenIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    /**
     * Identyfikator kontekstu, w którym został wygenerowany token i do którego daje dostęp.
     */
    public ContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu, w którym został wygenerowany token i do którego daje dostęp.
     */
    public void setContextIdentifier(ContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    /**
     * Opis tokena.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis tokena.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Uprawnienia przypisane tokenowi.
     */
    public List<TokenPermissionType> getRequestedPermissions() {
        return requestedPermissions;
    }

    /**
     * Uprawnienia przypisane tokenowi.
     */
    public void setRequestedPermissions(List<TokenPermissionType> requestedPermissions) {
        this.requestedPermissions = requestedPermissions;
    }

    /**
     * Data i czas utworzenia tokena.
     */
    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Data i czas utworzenia tokena.
     */
    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Data ostatniego użycia tokena.
     */
    public OffsetDateTime getLastUseDate() {
        return lastUseDate;
    }

    /**
     * Data ostatniego użycia tokena.
     */
    public void setLastUseDate(OffsetDateTime lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    /**
     * Status tokena.
     */
    public AuthenticationTokenStatus getStatus() {
        return status;
    }

    /**
     * Status tokena.
     */
    public void setStatus(AuthenticationTokenStatus status) {
        this.status = status;
    }

    /**
     * Dodatkowe informacje na temat statusu, zwracane w przypadku błędów.
     */
    public List<String> getStatusDetails() {
        return statusDetails;
    }

    /**
     * Dodatkowe informacje na temat statusu, zwracane w przypadku błędów.
     */
    public void setStatusDetails(List<String> statusDetails) {
        this.statusDetails = statusDetails;
    }
}
