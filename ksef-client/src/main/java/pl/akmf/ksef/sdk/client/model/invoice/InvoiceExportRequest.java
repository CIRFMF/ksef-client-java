package pl.akmf.ksef.sdk.client.model.invoice;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.CompressionType;

/**
 * InvoiceExportRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceExportRequest {

    /**
     * Informacje wymagane do zaszyfrowania wyniku zapytania.
     */
    private EncryptionInfo encryption;

    /**
     * Zestaw filtrów do wyszukiwania faktur.
     */
    private InvoiceExportFilters filters;

    /**
     * Określa, czy zwrócić tylko metadane faktur (plik _metadata.json bez faktur).
     */
    private boolean onlyMetadata = false;

    /**
     * Typ kompresji paczki eksportu faktur. Domyslnie API zachowuje kompatybilnosc i uzywa TAR GZ.
     */
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

    /**
     * Informacje wymagane do zaszyfrowania wyniku zapytania.
     */
    public EncryptionInfo getEncryption() {
        return encryption;
    }

    /**
     * Informacje wymagane do zaszyfrowania wyniku zapytania.
     */
    public void setEncryption(EncryptionInfo encryption) {
        this.encryption = encryption;
    }

    /**
     * Zestaw filtrów do wyszukiwania faktur.
     */
    public InvoiceExportFilters getFilters() {
        return filters;
    }

    /**
     * Zestaw filtrów do wyszukiwania faktur.
     */
    public void setFilters(InvoiceExportFilters filters) {
        this.filters = filters;
    }

    public boolean isOnlyMetadata() {
        return onlyMetadata;
    }

    /**
     * Określa, czy zwrócić tylko metadane faktur (plik _metadata.json bez faktur).
     */
    public void setOnlyMetadata(boolean onlyMetadata) {
        this.onlyMetadata = onlyMetadata;
    }

    /**
     * Typ kompresji paczki eksportu faktur. Domyslnie API zachowuje kompatybilnosc i uzywa TAR GZ.
     */
    public CompressionType getCompressionType() {
        return compressionType;
    }

    /**
     * Typ kompresji paczki eksportu faktur. Domyslnie API zachowuje kompatybilnosc i uzywa TAR GZ.
     */
    public void setCompressionType(CompressionType compressionType) {
        this.compressionType = compressionType;
    }
}
