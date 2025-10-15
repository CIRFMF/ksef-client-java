package pl.akmf.ksef.sdk.client.model.limit;

public class BatchSessionLimit {
    @Deprecated
    private int maxInvoiceSizeInMib;
    private int maxInvoiceSizeInMB;
    @Deprecated
    private int maxInvoiceWithAttachmentSizeInMib;
    private int maxInvoiceWithAttachmentSizeInMB;
    private int maxInvoices;

    public BatchSessionLimit() {

    }

    @Deprecated
    public BatchSessionLimit(int maxInvoiceSizeInMib, int maxInvoiceWithAttachmentSizeInMib, int maxInvoices) {
        this.maxInvoiceSizeInMib = maxInvoiceSizeInMib;
        this.maxInvoiceWithAttachmentSizeInMib = maxInvoiceWithAttachmentSizeInMib;
        this.maxInvoices = maxInvoices;
    }

    @Deprecated
    public int getMaxInvoiceSizeInMib() {
        return maxInvoiceSizeInMib;
    }

    @Deprecated
    public void setMaxInvoiceSizeInMib(int maxInvoiceSizeInMib) {
        this.maxInvoiceSizeInMib = maxInvoiceSizeInMib;
    }


    public int getMaxInvoiceSizeInMB() {
        return maxInvoiceSizeInMB;
    }

    public void setMaxInvoiceSizeInMB(int maxInvoiceSizeInMB) {
        this.maxInvoiceSizeInMB = maxInvoiceSizeInMB;
    }

    @Deprecated
    public int getMaxInvoiceWithAttachmentSizeInMib() {
        return maxInvoiceWithAttachmentSizeInMib;
    }

    @Deprecated
    public void setMaxInvoiceWithAttachmentSizeInMib(int maxInvoiceWithAttachmentSizeInMib) {
        this.maxInvoiceWithAttachmentSizeInMib = maxInvoiceWithAttachmentSizeInMib;
    }

    public int getMaxInvoiceWithAttachmentSizeInMB() {
        return maxInvoiceWithAttachmentSizeInMB;
    }

    public void setMaxInvoiceWithAttachmentSizeInMB(int maxInvoiceWithAttachmentSizeInMB) {
        this.maxInvoiceWithAttachmentSizeInMB = maxInvoiceWithAttachmentSizeInMB;
    }

    public int getMaxInvoices() {
        return maxInvoices;
    }

    public void setMaxInvoices(int maxInvoices) {
        this.maxInvoices = maxInvoices;
    }
}
