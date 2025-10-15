package pl.akmf.ksef.sdk.api;

import java.util.Arrays;
import java.util.List;

public enum HttpStatus {

    CONTINUE(100),
    OK(200),
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

    public static List<Integer> getErrorCodes() {
        return Arrays.asList(BAD_REQUEST.getCode(), UNAUTHORIZED.getCode(), FORBIDDEN.getCode(), INTERNAL_ERROR.getCode(), UNSUPPORTED_MEDIA_TYPE.getCode());
    }
}
