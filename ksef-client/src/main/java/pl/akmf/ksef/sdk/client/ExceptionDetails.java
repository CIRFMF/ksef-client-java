package pl.akmf.ksef.sdk.client;

import java.util.List;

public class ExceptionDetails {
    private int exceptionCode;
    private String exceptionDescription;
    private List<String> details;

    public ExceptionDetails(int exceptionCode, String exceptionDescription, List<String> details) {
        this.exceptionCode = exceptionCode;
        this.exceptionDescription = exceptionDescription;
        this.details = details;
    }

    public ExceptionDetails() {

    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
