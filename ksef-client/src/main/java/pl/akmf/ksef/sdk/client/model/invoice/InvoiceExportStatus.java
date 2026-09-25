package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.akmf.ksef.sdk.client.model.StatusInfo;
import java.time.OffsetDateTime;

/**
 * InvoiceExportStatus.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceExportStatusResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceExportStatus {

    /**
     * Status eksportu.
     */
    private StatusInfo status;

    /**
     * Data zakończenia przetwarzania żądania eksportu faktur.
     */
    private OffsetDateTime completedDate;

    @JsonProperty("package")
    private InvoiceExportPackage packageParts;

    /**
     * Data wygaśnięcia paczki faktur przygotowanej do pobrania. Po upływie tej daty paczka nie będzie już dostępna do pobrania.
     */
    private OffsetDateTime packageExpirationDate;

    public InvoiceExportStatus() {
    }

    /**
     * Status eksportu.
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Status eksportu.
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * Data zakończenia przetwarzania żądania eksportu faktur.
     */
    public OffsetDateTime getCompletedDate() {
        return completedDate;
    }

    /**
     * Data zakończenia przetwarzania żądania eksportu faktur.
     */
    public void setCompletedDate(OffsetDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public InvoiceExportPackage getPackageParts() {
        return packageParts;
    }

    public void setPackageParts(InvoiceExportPackage packageParts) {
        this.packageParts = packageParts;
    }

    /**
     * Data wygaśnięcia paczki faktur przygotowanej do pobrania. Po upływie tej daty paczka nie będzie już dostępna do pobrania.
     */
    public OffsetDateTime getPackageExpirationDate() {
        return packageExpirationDate;
    }

    /**
     * Data wygaśnięcia paczki faktur przygotowanej do pobrania. Po upływie tej daty paczka nie będzie już dostępna do pobrania.
     */
    public void setPackageExpirationDate(OffsetDateTime packageExpirationDate) {
        this.packageExpirationDate = packageExpirationDate;
    }
}
