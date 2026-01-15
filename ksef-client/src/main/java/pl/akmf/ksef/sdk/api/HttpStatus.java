package pl.akmf.ksef.sdk.api;

public enum HttpStatus {

    CONTINUE(100),
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_ERROR(500);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
