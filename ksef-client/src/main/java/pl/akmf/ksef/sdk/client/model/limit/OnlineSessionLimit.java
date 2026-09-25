package pl.akmf.ksef.sdk.client.model.limit;

/**
 * Limity dla sesji interaktywnych.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code OnlineSessionEffectiveContextLimits, OnlineSessionContextLimitsOverride}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class OnlineSessionLimit {

    /**
     * Maksymalny rozmiar faktury w MB.
     */
    private int maxInvoiceSizeInMB;

    /**
     * Maksymalny rozmiar faktury z załącznikiem w MB.
     */
    private int maxInvoiceWithAttachmentSizeInMB;

    /**
     * Maksymalna ilość faktur które można przesłać w pojedynczej sesji.
     */
    private int maxInvoices;

    public OnlineSessionLimit() {
    }

    /**
     * Maksymalny rozmiar faktury w MB.
     */
    public int getMaxInvoiceSizeInMB() {
        return maxInvoiceSizeInMB;
    }

    /**
     * Maksymalny rozmiar faktury w MB.
     */
    public void setMaxInvoiceSizeInMB(int maxInvoiceSizeInMB) {
        this.maxInvoiceSizeInMB = maxInvoiceSizeInMB;
    }

    /**
     * Maksymalny rozmiar faktury z załącznikiem w MB.
     */
    public int getMaxInvoiceWithAttachmentSizeInMB() {
        return maxInvoiceWithAttachmentSizeInMB;
    }

    /**
     * Maksymalny rozmiar faktury z załącznikiem w MB.
     */
    public void setMaxInvoiceWithAttachmentSizeInMB(int maxInvoiceWithAttachmentSizeInMB) {
        this.maxInvoiceWithAttachmentSizeInMB = maxInvoiceWithAttachmentSizeInMB;
    }

    /**
     * Maksymalna ilość faktur które można przesłać w pojedynczej sesji.
     */
    public int getMaxInvoices() {
        return maxInvoices;
    }

    /**
     * Maksymalna ilość faktur które można przesłać w pojedynczej sesji.
     */
    public void setMaxInvoices(int maxInvoices) {
        this.maxInvoices = maxInvoices;
    }
}
