package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * SubordinateEntityRole.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubordinateEntityRole {

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    private EntityRoleQueryParentEntityIdentifier subordinateEntityIdentifier;

    /**
     * Typ roli - powiązania z podmiotem nadrzędnym.
     */
    private SubordinateEntityRoleType role;

    /**
     * Opis powiązania.
     */
    private String description;

    /**
     * Data rozpoczęcia obowiązywania powiązania.
     */
    private OffsetDateTime startDate;

    public SubordinateEntityRole() {
    }

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    public EntityRoleQueryParentEntityIdentifier getSubordinateEntityIdentifier() {
        return subordinateEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    public void setSubordinateEntityIdentifier(EntityRoleQueryParentEntityIdentifier subordinateEntityIdentifier) {
        this.subordinateEntityIdentifier = subordinateEntityIdentifier;
    }

    /**
     * Typ roli - powiązania z podmiotem nadrzędnym.
     */
    public SubordinateEntityRoleType getRole() {
        return role;
    }

    /**
     * Typ roli - powiązania z podmiotem nadrzędnym.
     */
    public void setRole(SubordinateEntityRoleType role) {
        this.role = role;
    }

    /**
     * Opis powiązania.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis powiązania.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Data rozpoczęcia obowiązywania powiązania.
     */
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    /**
     * Data rozpoczęcia obowiązywania powiązania.
     */
    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }
}
