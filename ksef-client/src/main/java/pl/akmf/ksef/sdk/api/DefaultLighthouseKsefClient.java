package pl.akmf.ksef.sdk.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.akmf.ksef.sdk.client.interfaces.LighthouseKsefClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ApiResponse;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefMessagesResponse;
import pl.akmf.ksef.sdk.client.model.lighthouse.KsefStatusResponse;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static pl.akmf.ksef.sdk.api.HttpStatus.OK;
import static pl.akmf.ksef.sdk.api.HttpUtils.formatExceptionMessage;
import static pl.akmf.ksef.sdk.api.HttpUtils.isValidResponse;
import static pl.akmf.ksef.sdk.api.Url.LIGHTHOUSE_MESSAGES;
import static pl.akmf.ksef.sdk.api.Url.LIGHTHOUSE_STATUS;
import static pl.akmf.ksef.sdk.client.Headers.ACCEPT;
import static pl.akmf.ksef.sdk.client.Headers.APPLICATION_JSON;
import static pl.akmf.ksef.sdk.client.Headers.CONTENT_TYPE;

public class DefaultLighthouseKsefClient implements LighthouseKsefClient {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String baseURl;
    private final Duration timeout;
    private final Map<String, String> defaultHeaders;

    public DefaultLighthouseKsefClient(ObjectMapper objectMapper, HttpClient httpClient,
                                       String baseURl, Duration timeout, Map<String, String> defaultHeaders) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        this.baseURl = baseURl;
        this.timeout = timeout;
        this.defaultHeaders = defaultHeaders;
    }

    @Override
    public KsefStatusResponse getStatus() throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(LIGHTHOUSE_STATUS.getUrl(), headers);

        return getResponse(response, OK, LIGHTHOUSE_STATUS, KsefStatusResponse.class);
    }

    @Override
    public KsefMessagesResponse getMessages() throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(LIGHTHOUSE_MESSAGES.getUrl(), headers);

        return getResponse(response, OK, LIGHTHOUSE_MESSAGES, KsefMessagesResponse.class);
    }

    private HttpResponse<byte[]> get(String uri, Map<String, String> headers) {
        HttpRequest request = buildRequest(uri, headers);

        return sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
    }

    private HttpRequest buildRequest(String uri, Map<String, String> additionalHeaders) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + "/").resolve(uri))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);

        additionalHeaders.forEach(builder::header);
        builder.GET();

        return builder.build();
    }

    private HttpResponse<byte[]> sendHttpRequest(HttpRequest request, HttpResponse.BodyHandler<byte[]> bodyHandler) {
        try {
            return httpClient.send(request, bodyHandler);
        } catch (IOException | InterruptedException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    private <T> T getResponse(HttpResponse<byte[]> response,
                              HttpStatus expectedStatus,
                              Url operation,
                              Class<T> classType) throws ApiException {
        try {
            validResponse(response, expectedStatus, operation);
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(), classType))
                    .getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    private void validResponse(HttpResponse<byte[]> response,
                               HttpStatus expectedStatus,
                               Url operation) throws ApiException {
        try {
            if (!isValidResponse(response, expectedStatus)) {
                ExceptionResponse exception = null;

                String contentType = response.headers()
                        .firstValue(CONTENT_TYPE)
                        .orElse("")
                        .toLowerCase();

                if (contentType.contains(APPLICATION_JSON)) {
                    exception = response.body() == null ? null :
                            objectMapper.readValue(response.body(), ExceptionResponse.class);
                }
                String message = formatExceptionMessage(operation.getOperationId(), response.statusCode(), response.body());
                throw new ApiException(response.statusCode(), message, response.headers(), exception);
            }
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
