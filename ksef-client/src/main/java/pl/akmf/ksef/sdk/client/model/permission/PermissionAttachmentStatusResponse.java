package pl.akmf.ksef.sdk.client.model.permission;

import java.time.OffsetDateTime;

/**
 * PermissionAttachmentStatusResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code CheckAttachmentPermissionStatusResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionAttachmentStatusResponse {

    /**
     * Informacja czy Podmiot ma obecnie możliwość dodawania Załączników do Faktur
     */
    private Boolean isAttachmentAllowed;

    /**
     * Data i czas zakończenia możliwość dodawania przez Podmiot Załączników do Faktur. Brak podanej daty oznacza bezterminową możliwość dodawania Załączników do Faktur
     */
    private OffsetDateTime revokedDate;

    public PermissionAttachmentStatusResponse() {
    }

    /**
     * Informacja czy Podmiot ma obecnie możliwość dodawania Załączników do Faktur
     */
    public Boolean getIsAttachmentAllowed() {
        return isAttachmentAllowed;
    }

    /**
     * Informacja czy Podmiot ma obecnie możliwość dodawania Załączników do Faktur
     */
    public void setIsAttachmentAllowed(Boolean isAttachmentAllowed) {
        this.isAttachmentAllowed = isAttachmentAllowed;
    }

    /**
     * Data i czas zakończenia możliwość dodawania przez Podmiot Załączników do Faktur. Brak podanej daty oznacza bezterminową możliwość dodawania Załączników do Faktur
     */
    public OffsetDateTime getRevokedDate() {
        return revokedDate;
    }

    /**
     * Data i czas zakończenia możliwość dodawania przez Podmiot Załączników do Faktur. Brak podanej daty oznacza bezterminową możliwość dodawania Załączników do Faktur
     */
    public void setRevokedDate(OffsetDateTime revokedDate) {
        this.revokedDate = revokedDate;
    }
}
