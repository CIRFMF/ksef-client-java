package pl.akmf.ksef.sdk.client.model.limit;

// Aktualnie obowiązujące limity ilości żądań przesyłanych do API.
public class EffectiveApiRateLimits {

    // Limity otwierania/zamykania sesji interaktywnych.
    private OnlineSessionRateLimit onlineSession;
    // Limity otwierania/zamykania sesji wsadowych.
    private BatchSessionRateLimit batchSession;
    // Limity wysyłki faktur.
    private InvoiceSendRateLimit invoiceSend;
    // Limity pobierania statusu faktury z sesji.
    private InvoiceStatusRateLimit invoiceStatus;
    // Limity pobierania listy sesji.
    private SessionListRateLimit sessionList;
    // Limity pobierania listy faktur w sesji.
    private SessionInvoiceListRateLimit sessionInvoiceList;
    // Limity pozostałych operacji w ramach sesji.
    private SessionMiscRateLimits sessionMisc;
    // Limity pobierania metadanych faktur.
    private InvoiceMetadataRateLimit invoiceMetadata;
    // Limity eksportu paczki faktur.
    private InvoiceExportRateLimit invoiceExport;
    // Limity eksportu paczki faktur.
    private InvoiceExportStatusRateLimit invoiceStatusExport;
    // Limity pobierania faktur po numerze KSeF.
    private InvoiceDownloadRateLimit invoiceDownload;
    // Limity pozostałych operacji API.
    private OtherRateLimit other;

    public EffectiveApiRateLimits() {
    }

    public EffectiveApiRateLimits(OnlineSessionRateLimit onlineSession, BatchSessionRateLimit batchSession,
                                  InvoiceSendRateLimit invoiceSend, InvoiceStatusRateLimit invoiceStatus,
                                  SessionListRateLimit sessionList, SessionInvoiceListRateLimit sessionInvoiceList,
                                  SessionMiscRateLimits sessionMisc, InvoiceMetadataRateLimit invoiceMetadata,
                                  InvoiceExportRateLimit invoiceExport, InvoiceExportStatusRateLimit invoiceStatusExport,
                                  InvoiceDownloadRateLimit invoiceDownload, OtherRateLimit other) {
        this.onlineSession = onlineSession;
        this.batchSession = batchSession;
        this.invoiceSend = invoiceSend;
        this.invoiceStatus = invoiceStatus;
        this.sessionList = sessionList;
        this.sessionInvoiceList = sessionInvoiceList;
        this.sessionMisc = sessionMisc;
        this.invoiceMetadata = invoiceMetadata;
        this.invoiceExport = invoiceExport;
        this.invoiceStatusExport = invoiceStatusExport;
        this.invoiceDownload = invoiceDownload;
        this.other = other;
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