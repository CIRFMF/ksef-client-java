package pl.akmf.ksef.sdk.client.model;

public class ExceptionResponse {
    private ExceptionObject exception;

    public ExceptionResponse() {

    }

    public ExceptionResponse(ExceptionObject exception) {
        this.exception = exception;
    }

    public ExceptionObject getException() {
        return exception;
    }

    public void setException(ExceptionObject exception) {
        this.exception = exception;
    }
}
