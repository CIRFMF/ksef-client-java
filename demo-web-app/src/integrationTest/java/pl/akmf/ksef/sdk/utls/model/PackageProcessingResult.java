package pl.akmf.ksef.sdk.utls.model;

import pl.akmf.ksef.sdk.client.model.invoice.InvoiceMetadata;

import java.util.List;
import java.util.Map;

public class PackageProcessingResult {
    private final List<InvoiceMetadata> invoiceMetadataList;
    private final Map<String, String> downloadedFiles;

    public PackageProcessingResult(List<InvoiceMetadata> invoiceMetadataList, Map<String, String> downloadedFiles) {
        this.invoiceMetadataList = invoiceMetadataList;
        this.downloadedFiles = downloadedFiles;
    }

    public List<InvoiceMetadata> getInvoiceMetadataList() {
        return invoiceMetadataList;
    }

    public Map<String, String> getDownloadedFiles() {
        return downloadedFiles;
    }
}
