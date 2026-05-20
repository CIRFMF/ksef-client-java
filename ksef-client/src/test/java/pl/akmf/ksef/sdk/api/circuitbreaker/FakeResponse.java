package pl.akmf.ksef.sdk.api.circuitbreaker;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class FakeResponse implements HttpResponse<byte[]> {

    private final int statusCode;

    public FakeResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public byte[] body() {
        return "ok".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return Optional.empty();
    }

    @Override
    public HttpRequest request() {
        return null;
    }

    @Override
    public Optional<HttpResponse<byte[]>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public HttpHeaders headers() {
        return HttpHeaders.of(Map.of(), (a, b) -> true);
    }

    @Override
    public URI uri() {
        return URI.create("http://test");
    }

    @Override
    public HttpClient.Version version() {
        return HttpClient.Version.HTTP_2;
    }
}
