package pl.akmf.ksef.sdk.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.akmf.ksef.sdk.client.model.ApiClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ApiResponse;
import pl.akmf.ksef.sdk.client.model.Pair;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateLimitsResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateMetadataListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateMetadataListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateRevokeRequest;
import pl.akmf.ksef.sdk.client.model.certificate.SendCertificateEnrollmentRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class CertificateApi {
    private final HttpClient memberVarHttpClient;
    private final ObjectMapper memberVarObjectMapper;
    private final String memberVarBaseUri;
    private final Consumer<HttpRequest.Builder> memberVarInterceptor;
    private final Duration memberVarReadTimeout;
    private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;

    public CertificateApi(ApiClient apiClient) {
        memberVarHttpClient = apiClient.getHttpClient();
        memberVarObjectMapper = apiClient.getObjectMapper();
        memberVarBaseUri = apiClient.getBaseUri();
        memberVarInterceptor = apiClient.getRequestInterceptor();
        memberVarReadTimeout = apiClient.getReadTimeout();
        memberVarResponseInterceptor = apiClient.getResponseInterceptor();
    }

    protected ApiException getApiException(String operationId, HttpResponse<InputStream> response) throws IOException {
        String body = response.body() == null ? null : new String(response.body().readAllBytes());
        String message = formatExceptionMessage(operationId, response.statusCode(), body);
        return new ApiException(response.statusCode(), message, response.headers(), body);
    }

    private String formatExceptionMessage(String operationId, int statusCode, String body) {
        if (body == null || body.isEmpty()) {
            body = "[no body]";
        }
        return operationId + " call failed with: " + statusCode + " - " + body;
    }

    /**
     * Unieważnienie certyfikatu
     * Unieważnia certyfikat o podanym numerze seryjnym.
     *
     * @param certificateSerialNumber  Numer seryjny certyfikatu (required)
     * @param certificateRevokeRequest (optional)
     * @throws ApiException if fails to make API call
     */
    public void apiV2CertificatesCertificateSerialNumberRevokePostWithHttpInfo(String certificateSerialNumber,
                                                                               CertificateRevokeRequest certificateRevokeRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesCertificateSerialNumberRevokePostRequestBuilder(certificateSerialNumber, certificateRevokeRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            try {
                if (localVarResponse.statusCode() / 100 != 2) {
                    throw getApiException("apiV2CertificatesCertificateSerialNumberRevokePost", localVarResponse);
                }
                localVarResponse.statusCode();
                localVarResponse.headers();
            } finally {
                // Drain the InputStream
                localVarResponse.body().close();
            }
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesCertificateSerialNumberRevokePostRequestBuilder(String certificateSerialNumber,
                                                                                                 CertificateRevokeRequest certificateRevokeRequest) throws ApiException {
        // verify the required parameter 'certificateSerialNumber' is set
        if (certificateSerialNumber == null) {
            throw new ApiException(400, "Missing the required parameter 'certificateSerialNumber' when calling apiV2CertificatesCertificateSerialNumberRevokePost");
        }

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/{certificateSerialNumber}/revoke"
                .replace("{certificateSerialNumber}", ApiClient.urlEncode(certificateSerialNumber));

        localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(certificateRevokeRequest);
            localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
        } catch (IOException e) {
            throw new ApiException(e);
        }
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

    /**
     * Pobranie danych do wniosku certyfikacyjnego
     * Zwraca dane wymagane do przygotowania wniosku certyfikacyjnego.
     *
     * @return ApiResponse&lt;CertificateEnrollmentDataResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<CertificateEnrollmentsInfoResponse> apiV2CertificatesEnrollmentsDataGetWithHttpInfo() throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesEnrollmentsDataGetRequestBuilder();
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2CertificatesEnrollmentsDataGet", localVarResponse);
            }
            return new ApiResponse<>(
                    localVarResponse.statusCode(),
                    localVarResponse.headers().map(),
                    localVarResponse.body() == null ? null : memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<>() {
                    }) // closes the InputStream
            );
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesEnrollmentsDataGetRequestBuilder() {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/enrollments/data";

        localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

        localVarRequestBuilder.header("Accept", "application/json");

        localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

    /**
     * Wysyłka wniosku certyfikacyjnego
     * Przyjmuje wniosek certyfikacyjny i rozpoczyna jego przetwarzanie.
     *
     * @param enrollCertificateRequest (optional)
     * @return ApiResponse&lt;EnrollCertificateResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<CertificateEnrollmentResponse> apiV2CertificatesEnrollmentsPostWithHttpInfo(SendCertificateEnrollmentRequest enrollCertificateRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesEnrollmentsPostRequestBuilder(enrollCertificateRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2CertificatesEnrollmentsPost", localVarResponse);
            }
            return new ApiResponse<>(
                    localVarResponse.statusCode(),
                    localVarResponse.headers().map(),
                    localVarResponse.body() == null ? null : memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<>() {
                    }) // closes the InputStream
            );
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesEnrollmentsPostRequestBuilder(SendCertificateEnrollmentRequest enrollCertificateRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/enrollments";

        localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(enrollCertificateRequest);
            localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
        } catch (IOException e) {
            throw new ApiException(e);
        }
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

    /**
     * Pobranie statusu przetwarzania wniosku certyfikacyjnego
     * Zwraca informacje o statusie wniosku certyfikacyjnego.
     *
     * @param referenceNumber Numer referencyjny wniosku certyfikacyjnego (required)
     * @return ApiResponse&lt;CertificateEnrollmentStatusResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<CertificateEnrollmentStatusResponse> apiV2CertificatesEnrollmentsReferenceNumberGetWithHttpInfo(String referenceNumber) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesEnrollmentsReferenceNumberGetRequestBuilder(referenceNumber);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2CertificatesEnrollmentsReferenceNumberGet", localVarResponse);
            }
            return new ApiResponse<>(
                    localVarResponse.statusCode(),
                    localVarResponse.headers().map(),
                    localVarResponse.body() == null ? null : memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<>() {
                    }) // closes the InputStream
            );
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesEnrollmentsReferenceNumberGetRequestBuilder(String referenceNumber) throws ApiException {
        // verify the required parameter 'referenceNumber' is set
        if (referenceNumber == null) {
            throw new ApiException(400, "Missing the required parameter 'referenceNumber' when calling apiV2CertificatesEnrollmentsReferenceNumberGet");
        }

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/enrollments/{referenceNumber}"
                .replace("{referenceNumber}", ApiClient.urlEncode(referenceNumber));

        localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

        localVarRequestBuilder.header("Accept", "application/json");

        localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

    /**
     * Pobranie danych o limitach certyfikatów
     * Zwraca informacje o limitach certyfikatów oraz informacje czy użytkownik może zawnioskować o certyfikat.
     *
     * @return ApiResponse&lt;CertificateLimitsResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<CertificateLimitsResponse> apiV2CertificatesLimitsGetWithHttpInfo() throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesLimitsGetRequestBuilder();
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2CertificatesLimitsGet", localVarResponse);
            }
            return new ApiResponse<>(
                    localVarResponse.statusCode(),
                    localVarResponse.headers().map(),
                    localVarResponse.body() == null ? null : memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<>() {
                    }) // closes the InputStream
            );
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesLimitsGetRequestBuilder() {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/limits";

        localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

        localVarRequestBuilder.header("Accept", "application/json");

        localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

    /**
     * Pobranie listy metadanych certyfikatów
     * Zwraca listę certyfikatów spełniających podane kryteria wyszukiwania. W przypadku braku podania kryteriów wyszukiwania zwrócona zostanie nieprzefiltrowana lista.
     *
     * @param pageSize                       Rozmiar strony wyników (optional, default to 10)
     * @param pageOffset                     Numner strony wyników (optional, default to 0)
     * @param certificateMetadataListRequest Kryteria filtrowania (optional)
     * @return ApiResponse&lt;QueryCertificatesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<CertificateMetadataListResponse> apiV2CertificatesQueryPostWithHttpInfo(Integer pageSize, Integer pageOffset, CertificateMetadataListRequest certificateMetadataListRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesQueryPostRequestBuilder(pageSize, pageOffset, certificateMetadataListRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2CertificatesQueryPost", localVarResponse);
            }
            return new ApiResponse<>(
                    localVarResponse.statusCode(),
                    localVarResponse.headers().map(),
                    localVarResponse.body() == null ? null : memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<>() {
                    }) // closes the InputStream
            );
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesQueryPostRequestBuilder(Integer pageSize, Integer pageOffset, CertificateMetadataListRequest certificateMetadataListRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/query";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));

        if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
            StringJoiner queryJoiner = new StringJoiner("&");
            localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
            if (localVarQueryStringJoiner.length() != 0) {
                queryJoiner.add(localVarQueryStringJoiner.toString());
            }
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner));
        } else {
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
        }

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(certificateMetadataListRequest);
            localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
        } catch (IOException e) {
            throw new ApiException(e);
        }
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

    /**
     * Pobranie certyfikatu lub listy certyfikatów
     * Zwraca certyfikaty o podanych numerach seryjnych w formacie DER zakodowanym w Base64.
     *
     * @param certificateListRequest (optional)
     * @return ApiResponse&lt;RetrieveCertificatesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<CertificateListResponse> apiV2CertificatesRetrievePostWithHttpInfo(CertificateListRequest certificateListRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2CertificatesRetrievePostRequestBuilder(certificateListRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2CertificatesRetrievePost", localVarResponse);
            }
            return new ApiResponse<>(
                    localVarResponse.statusCode(),
                    localVarResponse.headers().map(),
                    localVarResponse.body() == null ? null : memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<>() {
                    }) // closes the InputStream
            );
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    private HttpRequest.Builder apiV2CertificatesRetrievePostRequestBuilder(CertificateListRequest certificateListRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/certificates/retrieve";

        localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(certificateListRequest);
            localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
        } catch (IOException e) {
            throw new ApiException(e);
        }
        if (memberVarReadTimeout != null) {
            localVarRequestBuilder.timeout(memberVarReadTimeout);
        }
        if (memberVarInterceptor != null) {
            memberVarInterceptor.accept(localVarRequestBuilder);
        }
        return localVarRequestBuilder;
    }

}
