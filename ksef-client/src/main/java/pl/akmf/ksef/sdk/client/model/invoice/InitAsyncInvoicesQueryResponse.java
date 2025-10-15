package pl.akmf.ksef.sdk.client.model.invoice;

public class InitAsyncInvoicesQueryResponse {
    @Deprecated
    private String operationReferenceNumber;
    public String referenceNumber;

    public InitAsyncInvoicesQueryResponse() {
    }

    @Deprecated
    public String getOperationReferenceNumber() {
        return operationReferenceNumber;
    }

    @Deprecated
    public void setOperationReferenceNumber(String operationReferenceNumber) {
        this.operationReferenceNumber = operationReferenceNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}

