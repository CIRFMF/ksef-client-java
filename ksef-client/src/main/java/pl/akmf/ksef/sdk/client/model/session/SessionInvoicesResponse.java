package pl.akmf.ksef.sdk.client.model.session;

import java.util.List;

public class SessionInvoicesResponse {
    private String continuationToken;
    private List<SessionInvoiceStatusResponse> invoices;

    public SessionInvoicesResponse() {
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public List<SessionInvoiceStatusResponse> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<SessionInvoiceStatusResponse> invoices) {
        this.invoices = invoices;
    }
}

