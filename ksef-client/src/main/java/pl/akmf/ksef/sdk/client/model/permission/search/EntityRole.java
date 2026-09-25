package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * EntityRole.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityRole {

    /**
     * Identyfikator podmiotu nadrzędnego.
     */
    private EntityRoleQueryParentEntityIdentifier parentEntityIdentifier;

    /**
     * Typ roli - powiązania z podmiotem nadrzędnym.
     */
    private EntityRoleType role;

    /**
     * Opis roli.
     */
    private String description;

    /**
     * Data rozpoczęcia obowiązywania roli.
     */
    private OffsetDateTime startDate;

    public EntityRole() {
    }

    /**
     * Identyfikator podmiotu nadrzędnego.
     */
    public EntityRoleQueryParentEntityIdentifier getParentEntityIdentifier() {
        return parentEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu nadrzędnego.
     */
    public void setParentEntityIdentifier(EntityRoleQueryParentEntityIdentifier parentEntityIdentifier) {
        this.parentEntityIdentifier = parentEntityIdentifier;
    }

    /**
     * Typ roli - powiązania z podmiotem nadrzędnym.
     */
    public EntityRoleType getRole() {
        return role;
    }

    /**
     * Typ roli - powiązania z podmiotem nadrzędnym.
     */
    public void setRole(EntityRoleType role) {
        this.role = role;
    }

    /**
     * Opis roli.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis roli.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Data rozpoczęcia obowiązywania roli.
     */
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    /**
     * Data rozpoczęcia obowiązywania roli.
     */
    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}
