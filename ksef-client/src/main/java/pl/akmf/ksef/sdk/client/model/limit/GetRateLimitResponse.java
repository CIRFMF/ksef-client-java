package pl.akmf.ksef.sdk.client.model.limit;

public class GetRateLimitResponse {
    private OnlineSessionRateLimit onlineSession;
    private BatchSessionRateLimit batchSession;
    private InvoiceSendRateLimit invoiceSend;
    private InvoiceStatusRateLimit invoiceStatus;
    private SessionListRateLimit sessionList;
    private SessionInvoiceListRateLimit sessionInvoiceList;
    private SessionMiscRateLimits sessionMisc;
    private InvoiceMetadataRateLimit invoiceMetadata;
    private InvoiceExportRateLimit invoiceExport;
    private InvoiceExportStatusRateLimit invoiceStatusExport;
    private InvoiceDownloadRateLimit invoiceDownload;
    private OtherRateLimit other;

    public GetRateLimitResponse() {

    }

    public OnlineSessionRateLimit getOnlineSession() {
        return onlineSession;
    }

    public void setOnlineSession(OnlineSessionRateLimit onlineSession) {
        this.onlineSession = onlineSession;
    }

    public BatchSessionRateLimit getBatchSession() {
        return batchSession;
    }

    public void setBatchSession(BatchSessionRateLimit batchSession) {
        this.batchSession = batchSession;
    }

    public InvoiceSendRateLimit getInvoiceSend() {
        return invoiceSend;
    }

    public void setInvoiceSend(InvoiceSendRateLimit invoiceSend) {
        this.invoiceSend = invoiceSend;
    }

    public InvoiceStatusRateLimit getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatusRateLimit invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public SessionListRateLimit getSessionList() {
        return sessionList;
    }

    public void setSessionList(SessionListRateLimit sessionList) {
        this.sessionList = sessionList;
    }

    public SessionInvoiceListRateLimit getSessionInvoiceList() {
        return sessionInvoiceList;
    }

    public void setSessionInvoiceList(SessionInvoiceListRateLimit sessionInvoiceList) {
        this.sessionInvoiceList = sessionInvoiceList;
    }

    public SessionMiscRateLimits getSessionMisc() {
        return sessionMisc;
    }

    public void setSessionMisc(SessionMiscRateLimits sessionMisc) {
        this.sessionMisc = sessionMisc;
    }

    public InvoiceMetadataRateLimit getInvoiceMetadata() {
        return invoiceMetadata;
    }

    public void setInvoiceMetadata(InvoiceMetadataRateLimit invoiceMetadata) {
        this.invoiceMetadata = invoiceMetadata;
    }

    public InvoiceExportRateLimit getInvoiceExport() {
        return invoiceExport;
    }

    public void setInvoiceExport(InvoiceExportRateLimit invoiceExport) {
        this.invoiceExport = invoiceExport;
    }

    public InvoiceExportStatusRateLimit getInvoiceStatusExport() {
        return invoiceStatusExport;
    }

    public void setInvoiceStatusExport(InvoiceExportStatusRateLimit invoiceStatusExport) {
        this.invoiceStatusExport = invoiceStatusExport;
    }

    public InvoiceDownloadRateLimit getInvoiceDownload() {
        return invoiceDownload;
    }

    public void setInvoiceDownload(InvoiceDownloadRateLimit invoiceDownload) {
        this.invoiceDownload = invoiceDownload;
    }

    public OtherRateLimit getOther() {
        return other;
    }

    public void setOther(OtherRateLimit other) {
        this.other = other;
    }
}
