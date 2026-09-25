package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;

/**
 * Ogólny wyjątek klienta SDK reprezentujący błąd zwrócony przez API KSeF w przypadkach,
 * które nie kwalifikują się do żadnego bardziej szczegółowego typu wyjątku
 * (np. {@link ForbiddenApiException}, {@link UnauthorizedApiException}).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class KsefApiException extends ApiException {

    public KsefApiException(int code, String message) {
        super(code, message);
    }

    public KsefApiException(Throwable throwable) {
        super(throwable);
    }

    public KsefApiException(String message) {
        super(message);
    }

    public KsefApiException(int code, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(code, message, responseHeaders, exceptionResponse);
    }

    public KsefApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(code, url, method, message, responseHeaders, exceptionResponse);
    }

    @Override
    public String toString() {
        return "KsefApiException{" + super.toString() + "}";
    }
}
