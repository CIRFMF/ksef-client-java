package pl.akmf.ksef.sdk.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.akmf.ksef.sdk.client.model.ApiClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ApiResponse;
import pl.akmf.ksef.sdk.client.model.Pair;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityAuthorizationPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityAuthorizationPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityRolesResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEuEntityPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QuerySubordinateEntityRolesResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QuerySubunitPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsQueryRequest;

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

public class SearchPermissionApi {
    private final HttpClient memberVarHttpClient;
    private final ObjectMapper memberVarObjectMapper;
    private final String memberVarBaseUri;
    private final Consumer<HttpRequest.Builder> memberVarInterceptor;
    private final Duration memberVarReadTimeout;
    private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;

    public SearchPermissionApi() {
        this(new ApiClient());
    }

    public SearchPermissionApi(ApiClient apiClient) {
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
     * Pobranie listy uprawnień o charakterze uprawnień nadanych podmiotom
     *
     * @param pageOffset                                 (optional)
     * @param pageSize                                   (optional)
     * @param entityAuthorizationPermissionsQueryRequest (optional)
     * @return ApiResponse&lt;QueryEntityAuthorizationPermissionsResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<QueryEntityAuthorizationPermissionsResponse> apiV2PermissionsQueryAuthorizationsGrantsPost(Integer pageOffset, Integer pageSize, EntityAuthorizationPermissionsQueryRequest entityAuthorizationPermissionsQueryRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2PermissionsQueryAuthorizationsGrantsPostRequestBuilder(pageOffset, pageSize, entityAuthorizationPermissionsQueryRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2PermissionsQueryAuthorizationsGrantsPost", localVarResponse);
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

    private HttpRequest.Builder apiV2PermissionsQueryAuthorizationsGrantsPostRequestBuilder(Integer pageOffset, Integer pageSize, EntityAuthorizationPermissionsQueryRequest entityAuthorizationPermissionsQueryRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/permissions/query/authorizations/grants";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));

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
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(entityAuthorizationPermissionsQueryRequest);
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
     * Pobranie listy uprawnień do obsługi faktur nadanych podmiotom
     *
     * @param pageOffset (optional)
     * @param pageSize   (optional)
     * @return ApiResponse&lt;QueryEntityRolesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<QueryEntityRolesResponse> apiV2PermissionsQueryEntitiesRolesGet(Integer pageOffset, Integer pageSize) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2PermissionsQueryEntitiesRolesGetRequestBuilder(pageOffset, pageSize);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2PermissionsQueryEntitiesRolesGet", localVarResponse);
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

    private HttpRequest.Builder apiV2PermissionsQueryEntitiesRolesGetRequestBuilder(Integer pageOffset, Integer pageSize) {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/permissions/query/entities/roles";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));

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
     * Pobranie listy uprawnień nadanych podmiotom unijnym
     *
     * @param pageOffset                      (optional)
     * @param pageSize                        (optional)
     * @param euEntityPermissionsQueryRequest (optional)
     * @return ApiResponse&lt;QueryEuEntityPermissionsResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<QueryEuEntityPermissionsResponse> apiV2PermissionsQueryEuEntitiesGrantsPost(Integer pageOffset, Integer pageSize, EuEntityPermissionsQueryRequest euEntityPermissionsQueryRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2PermissionsQueryEuEntitiesGrantsPostRequestBuilder(pageOffset, pageSize, euEntityPermissionsQueryRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2PermissionsQueryEuEntitiesGrantsPost", localVarResponse);
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

    private HttpRequest.Builder apiV2PermissionsQueryEuEntitiesGrantsPostRequestBuilder(Integer pageOffset, Integer pageSize, EuEntityPermissionsQueryRequest euEntityPermissionsQueryRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/permissions/query/eu-entities/grants";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));

        if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
            StringJoiner queryJoiner = new StringJoiner("&");
            localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
            if (localVarQueryStringJoiner.length() != 0) {
                queryJoiner.add(localVarQueryStringJoiner.toString());
            }
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
        } else {
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
        }

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(euEntityPermissionsQueryRequest);
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
     * Pobranie listy uprawnień do pracy w KSeF nadanych osobom fizycznym
     *
     * @param pageOffset                    (optional)
     * @param pageSize                      (optional)
     * @param personPermissionsQueryRequest (optional)
     * @return ApiResponse&lt;QueryPersonPermissionsResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<QueryPersonPermissionsResponse> apiV2PermissionsQueryPersonsGrantsPost(Integer pageOffset, Integer pageSize, PersonPermissionsQueryRequest personPermissionsQueryRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2PermissionsQueryPersonsGrantsPostRequestBuilder(pageOffset, pageSize, personPermissionsQueryRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2PermissionsQueryPersonsGrantsPost", localVarResponse);
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

    private HttpRequest.Builder apiV2PermissionsQueryPersonsGrantsPostRequestBuilder(Integer pageOffset, Integer pageSize, PersonPermissionsQueryRequest personPermissionsQueryRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/permissions/query/persons/grants";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));

        if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
            StringJoiner queryJoiner = new StringJoiner("&");
            localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
            if (localVarQueryStringJoiner.length() != 0) {
                queryJoiner.add(localVarQueryStringJoiner.toString());
            }
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
        } else {
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
        }

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(personPermissionsQueryRequest);
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
     * Pobranie listy uprawnień do obsługi faktur nadanych podmiotom
     *
     * @param pageOffset                         (optional)
     * @param pageSize                           (optional)
     * @param subordinateEntityRolesQueryRequest (optional)
     * @return ApiResponse&lt;QuerySubordinateEntityRolesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<QuerySubordinateEntityRolesResponse> apiV2PermissionsQuerySubordinateEntitiesRolesPost(Integer pageOffset, Integer pageSize, SubordinateEntityRolesQueryRequest subordinateEntityRolesQueryRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2PermissionsQuerySubordinateEntitiesRolesPostRequestBuilder(pageOffset, pageSize, subordinateEntityRolesQueryRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2PermissionsQuerySubordinateEntitiesRolesPost", localVarResponse);
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

    private HttpRequest.Builder apiV2PermissionsQuerySubordinateEntitiesRolesPostRequestBuilder(Integer pageOffset, Integer pageSize, SubordinateEntityRolesQueryRequest subordinateEntityRolesQueryRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/permissions/query/subordinate-entities/roles";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));

        if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
            StringJoiner queryJoiner = new StringJoiner("&");
            localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
            if (localVarQueryStringJoiner.length() != 0) {
                queryJoiner.add(localVarQueryStringJoiner.toString());
            }
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
        } else {
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
        }

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(subordinateEntityRolesQueryRequest);
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
     * Pobranie listy uprawnień administratora podmiotu podrzędnego
     *
     * @param pageOffset                     (optional)
     * @param pageSize                       (optional)
     * @param subunitPermissionsQueryRequest (optional)
     * @return ApiResponse&lt;QuerySubunitPermissionsResponse&gt;
     * @throws ApiException if fails to make API call
     */
    public ApiResponse<QuerySubunitPermissionsResponse> apiV2PermissionsQuerySubunitsGrantsPost(Integer pageOffset, Integer pageSize, SubunitPermissionsQueryRequest subunitPermissionsQueryRequest) throws ApiException {
        HttpRequest.Builder localVarRequestBuilder = apiV2PermissionsQuerySubunitsGrantsPostRequestBuilder(pageOffset, pageSize, subunitPermissionsQueryRequest);
        try {
            HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
                    localVarRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());
            if (memberVarResponseInterceptor != null) {
                memberVarResponseInterceptor.accept(localVarResponse);
            }
            if (localVarResponse.statusCode() / 100 != 2) {
                throw getApiException("apiV2PermissionsQuerySubunitsGrantsPost", localVarResponse);
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

    private HttpRequest.Builder apiV2PermissionsQuerySubunitsGrantsPostRequestBuilder(Integer pageOffset, Integer pageSize, SubunitPermissionsQueryRequest subunitPermissionsQueryRequest) throws ApiException {

        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        String localVarPath = "/api/v2/permissions/query/subunits/grants";

        List<Pair> localVarQueryParams = new ArrayList<>();
        StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageOffset", pageOffset));
        localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));

        if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
            StringJoiner queryJoiner = new StringJoiner("&");
            localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
            if (localVarQueryStringJoiner.length() != 0) {
                queryJoiner.add(localVarQueryStringJoiner.toString());
            }
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
        } else {
            localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
        }

        localVarRequestBuilder.header("Content-Type", "application/json");
        localVarRequestBuilder.header("Accept", "application/json");

        try {
            byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(subunitPermissionsQueryRequest);
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
