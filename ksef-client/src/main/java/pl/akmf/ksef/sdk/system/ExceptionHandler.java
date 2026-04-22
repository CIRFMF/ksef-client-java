package pl.akmf.ksef.sdk.system;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.akmf.ksef.sdk.api.HttpStatus;
import pl.akmf.ksef.sdk.api.Url;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.ForbiddenApiException;
import pl.akmf.ksef.sdk.client.model.ForbiddenProblemDetails;
import pl.akmf.ksef.sdk.client.model.KsefApiException;
import pl.akmf.ksef.sdk.client.model.TooManyRequestsResponse;
import pl.akmf.ksef.sdk.client.model.UnauthorizedApiException;
import pl.akmf.ksef.sdk.client.model.UnauthorizedProblemDetails;
import pl.akmf.ksef.sdk.client.model.exceptions.BadRequestApiException;
import pl.akmf.ksef.sdk.client.model.exceptions.BadRequestProblemDetails;
import pl.akmf.ksef.sdk.client.model.exceptions.GoneApiException;
import pl.akmf.ksef.sdk.client.model.exceptions.GoneProblemDetails;
import pl.akmf.ksef.sdk.client.model.exceptions.TooManyRequestsApiException;
import pl.akmf.ksef.sdk.client.model.exceptions.TooManyRequestsProblemDetails;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static pl.akmf.ksef.sdk.api.HttpUtils.formatExceptionMessage;
import static pl.akmf.ksef.sdk.client.Headers.APPLICATION_JSON;
import static pl.akmf.ksef.sdk.client.Headers.APPLICATION_PROBLEM_JSON;
import static pl.akmf.ksef.sdk.client.Headers.CONTENT_TYPE;
import static pl.akmf.ksef.sdk.client.Headers.RETRY_AFTER;

public class ExceptionHandler {

    private final ObjectMapper objectMapper;

    public ExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handleException(HttpResponse<byte[]> response, Url operation) throws IOException, ApiException {
        ExceptionResponse exception = null;
        String uri = response.uri().toString();
        String method = Optional.of(response)
                .map(HttpResponse::request)
                .map(HttpRequest::method)
                .orElse("");

        String contentType = response.headers()
                .firstValue(CONTENT_TYPE)
                .orElse("")
                .toLowerCase();

        String message = formatExceptionMessage(operation.getOperationId(), response.statusCode(), response.body());

        if (contentType.contains(APPLICATION_JSON)
                || contentType.contains(APPLICATION_PROBLEM_JSON)) {
            try {
                if (HttpStatus.UNAUTHORIZED.getCode() == response.statusCode()) {
                    tryParseException(() -> {
                        UnauthorizedProblemDetails unauthorizedProblemDetails = objectMapper.readValue(response.body(), UnauthorizedProblemDetails.class);
                        throw new UnauthorizedApiException(response.statusCode(), uri, method, message, response.headers(), unauthorizedProblemDetails);
                    });
                } else if (HttpStatus.FORBIDDEN.getCode() == response.statusCode()) {
                    tryParseException(() -> {
                        ForbiddenProblemDetails forbiddenProblemDetails = objectMapper.readValue(response.body(), ForbiddenProblemDetails.class);
                        throw new ForbiddenApiException(response.statusCode(), uri, method, message, response.headers(), forbiddenProblemDetails);
                    });
                } else if (HttpStatus.GONE.getCode() == response.statusCode()) {
                    tryParseException(() -> {
                        GoneProblemDetails goneProblemDetails = objectMapper.readValue(response.body(), GoneProblemDetails.class);
                        throw new GoneApiException(response.statusCode(), uri, method, message, response.headers(), goneProblemDetails);
                    });
                } else if (HttpStatus.TOO_MANY_REQUESTS.getCode() == response.statusCode()) {
                    tryParseException(() -> {
                        TooManyRequestsProblemDetails tooManyRequestsProblemDetails = objectMapper.readValue(response.body(), TooManyRequestsProblemDetails.class);
                        throw new TooManyRequestsApiException(response.statusCode(), uri, method, message, response.headers(), tooManyRequestsProblemDetails);
                    });
                } else {
                    tryParseException(() -> {
                        BadRequestProblemDetails badRequestProblemDetails = objectMapper.readValue(response.body(), BadRequestProblemDetails.class);
                        throw new BadRequestApiException(response.statusCode(), uri, method, message, response.headers(), badRequestProblemDetails);
                    });
                }

                // Fallback do formatu legacy ApiErrorResponse
                exception = objectMapper.readValue(response.body(), ExceptionResponse.class);
                if (HttpStatus.TOO_MANY_REQUESTS.getCode() == response.statusCode()) {
                    setErrorDetailsFor429Status(response.headers(), exception.getStatus());
                }
            } catch (DatabindException e) {
                throw new KsefApiException(response.statusCode(), uri, method, message, response.headers(), exception);
            }
        }
        throw new KsefApiException(response.statusCode(), uri, method, message, response.headers(), exception);
    }

    private static <T> void tryParseException(ErrorSupplier<T> supplier) throws ApiException {
        try {
            supplier.get();
        } catch (IOException ignore) {
        }
    }

    @FunctionalInterface
    interface ErrorSupplier<T> {
        T get() throws IOException, ApiException;
    }

    private void setErrorDetailsFor429Status(HttpHeaders headers, TooManyRequestsResponse status) {
        String retryAfterString = headers.firstValue(RETRY_AFTER)
                .orElse(null);

        if (retryAfterString != null && !retryAfterString.isBlank()) {
            Integer seconds = tryParseInt(retryAfterString);
            // Jeśli dostępne są sekundy z Retry-After, wykorzystanie ich wartości
            if (seconds != null) {
                status.setRetryAfterSeconds(seconds);
                status.setRecommendedDelay(Duration.ofSeconds(seconds));
                return;
            }
            OffsetDateTime retryAfterDate = tryParseOffsetDateTime(retryAfterString);
            // Jeśli dostępna jest data z Retry-After, obliczenie delty
            if (retryAfterDate != null) {
                status.setRetryAfterDate(retryAfterDate);
                Duration delta = Duration.between(OffsetDateTime.now(ZoneOffset.UTC), retryAfterDate);
                status.setRecommendedDelay(delta.isNegative() || delta.isZero()
                        ? Duration.ofSeconds(1)
                        : delta);
            }
        }
    }

    private static Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static OffsetDateTime tryParseOffsetDateTime(String value) {
        try {
            return OffsetDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
