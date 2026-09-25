package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;

/**
 * Ogólny, wewnętrzny kontener odpowiedzi HTTP klienta SDK: przechowuje kod statusu,
 * nagłówki odpowiedzi oraz zdeserializowane dane odpowiedzi typu {@code T}.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ApiResponse<T> {

    private final int statusCode;

    private final HttpHeaders headers;

    private final T data;

    /**
     * @param statusCode The status code of HTTP response
     * @param headers    The headers of HTTP response
     */
    public ApiResponse(int statusCode, HttpHeaders headers) {
        this(statusCode, headers, null);
    }

    /**
     * @param statusCode The status code of HTTP response
     * @param headers    The headers of HTTP response
     * @param data       The object deserialized from response bod
     */
    public ApiResponse(int statusCode, HttpHeaders headers, T data) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public T getData() {
        return data;
    }
}
