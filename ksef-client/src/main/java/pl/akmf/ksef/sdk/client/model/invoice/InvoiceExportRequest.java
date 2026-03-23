package pl.akmf.ksef.sdk.client.model.invoice;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;

public class InvoiceExportRequest {
    private EncryptionInfo encryption;
    private InvoiceExportFilters filters;
    // Określa, czy zwrócić tylko metadane faktur (plik _metadata.json bez faktur).
    private boolean onlyMetadata = false;

    public InvoiceExportRequest() {

    }

    public InvoiceExportRequest(EncryptionInfo encryption, InvoiceExportFilters filters) {
        this.encryption = encryption;
        this.filters = filters;
    }

    public InvoiceExportRequest(EncryptionInfo encryption, InvoiceExportFilters filters, boolean onlyMetadata) {
        this.encryption = encryption;
        this.filters = filters;
        this.onlyMetadata = onlyMetadata;
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

    public boolean isOnlyMetadata() {
        return onlyMetadata;
    }

    public void setOnlyMetadata(boolean onlyMetadata) {
        this.onlyMetadata = onlyMetadata;
    }
}