package pl.akmf.ksef.sdk.client.model.permission;

import java.io.Serializable;

public class OperationResponse implements Serializable {
    private String referenceNumber;

    public OperationResponse() {
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
