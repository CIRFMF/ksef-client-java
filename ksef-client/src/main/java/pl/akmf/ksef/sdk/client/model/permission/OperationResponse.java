package pl.akmf.ksef.sdk.client.model.permission;

import java.io.Serializable;

public class OperationResponse implements Serializable {
    @Deprecated
    private String operationReferenceNumber;
    private String referenceNumber;

    public OperationResponse() {
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
