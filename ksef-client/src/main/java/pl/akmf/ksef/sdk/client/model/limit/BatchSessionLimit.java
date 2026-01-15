package pl.akmf.ksef.sdk.client.model.limit;

public class BatchSessionLimit {
    private int maxInvoiceSizeInMB;
    private int maxInvoiceWithAttachmentSizeInMB;
    private int maxInvoices;

    public BatchSessionLimit() {

    }

    public int getMaxInvoiceSizeInMB() {
        return maxInvoiceSizeInMB;
    }

    public void setMaxInvoiceSizeInMB(int maxInvoiceSizeInMB) {
        this.maxInvoiceSizeInMB = maxInvoiceSizeInMB;
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
