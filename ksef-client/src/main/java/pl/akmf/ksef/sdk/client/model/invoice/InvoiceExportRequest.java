package pl.akmf.ksef.sdk.client.model.invoice;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;

public class InvoiceExportRequest {
    private EncryptionInfo encryption;
    private InvoiceExportFilters filters;

    public InvoiceExportRequest() {

    }

    public InvoiceExportRequest(EncryptionInfo encryption, InvoiceExportFilters filters) {
        this.encryption = encryption;
        this.filters = filters;
    }

    public EncryptionInfo getEncryption() {
        return encryption;
    }

    public void setEncryption(EncryptionInfo encryption) {
        this.encryption = encryption;
    }

    public InvoiceExportFilters getFilters() {
        return filters;
    }

    public void setFilters(InvoiceExportFilters filters) {
        this.filters = filters;
    }
}