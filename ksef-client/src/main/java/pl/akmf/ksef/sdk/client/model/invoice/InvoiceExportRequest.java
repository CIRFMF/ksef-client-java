package pl.akmf.ksef.sdk.client.model.invoice;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.CompressionType;

public class InvoiceExportRequest {
    private EncryptionInfo encryption;
    private InvoiceExportFilters filters;
    // Określa, czy zwrócić tylko metadane faktur (plik _metadata.json bez faktur).
    private boolean onlyMetadata = false;
    // Typ kompresji paczki eksportu faktur. Domyslnie API zachowuje kompatybilnosc i uzywa TAR GZ.
    private CompressionType compressionType = CompressionType.TarGz;

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

    public CompressionType getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(CompressionType compressionType) {
        this.compressionType = compressionType;
    }
}