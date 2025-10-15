package pl.akmf.ksef.sdk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import pl.akmf.ksef.sdk.client.interfaces.KSeFClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ApiResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthKsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.AuthOperationStatusResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationToken;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenRefreshResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthorTokenIdentifier;
import pl.akmf.ksef.sdk.client.model.auth.GenerateTokenResponse;
import pl.akmf.ksef.sdk.client.model.auth.KsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.QueryTokensResponse;
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateLimitsResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateMetadataListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateRevokeRequest;
import pl.akmf.ksef.sdk.client.model.certificate.QueryCertificatesRequest;
import pl.akmf.ksef.sdk.client.model.certificate.SendCertificateEnrollmentRequest;
import pl.akmf.ksef.sdk.client.model.certificate.publickey.PublicKeyCertificate;
import pl.akmf.ksef.sdk.client.model.invoice.DownloadInvoiceRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InitAsyncInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportStatus;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.limit.ChangeContextLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.ChangeSubjectCertificateLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.GetContextLimitResponse;
import pl.akmf.ksef.sdk.client.model.limit.GetSubjectLimitResponse;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionAttachmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.GrantEUEntityRepresentativePermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.indirect.GrantIndirectEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.proxy.GrantAuthorizationPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityAuthorizationPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityAuthorizationPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityRolesResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEuEntityPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QuerySubunitPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationListResponse;
import pl.akmf.ksef.sdk.client.model.session.CommonSessionStatus;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryResponse;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.batch.PackagePartSignatureInitResponseType;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataAttachmentRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataAttachmentRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermissionRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermissionRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectRemoveRequest;
import pl.akmf.ksef.sdk.client.peppol.PeppolProvidersListResponse;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static pl.akmf.ksef.sdk.api.HttpUtils.buildUrlWithParams;
import static pl.akmf.ksef.sdk.api.HttpUtils.validateResponseStatus;
import static pl.akmf.ksef.sdk.api.Url.AUTH_CHALLENGE;
import static pl.akmf.ksef.sdk.api.Url.AUTH_KSEF_TOKEN;
import static pl.akmf.ksef.sdk.api.Url.AUTH_TOKEN_REEDEM;
import static pl.akmf.ksef.sdk.api.Url.AUTH_TOKEN_SIGNATURE;
import static pl.akmf.ksef.sdk.api.Url.AUTH_TOKEN_STATUS;
import static pl.akmf.ksef.sdk.api.Url.BATCH_SESSION_CLOSE;
import static pl.akmf.ksef.sdk.api.Url.BATCH_SESSION_OPEN;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_ENROLLMENT;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_ENROLLMENT_DATA;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_LIMIT;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_METADATA;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_RETRIEVE;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_REVOKE;
import static pl.akmf.ksef.sdk.api.Url.CERTIFICATE_STATUS;
import static pl.akmf.ksef.sdk.api.Url.GRANT_AUTHORIZED_SUBJECT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_EU_ADMINISTRATOR_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_EU_REPRESENTATIVE;
import static pl.akmf.ksef.sdk.api.Url.GRANT_INDIRECT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_INVOICE_SUBJECT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_PERSON_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_SUBUNIT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_DOWNLOAD;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_DOWNLOAD_BY_KSEF;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_EXPORT_INIT;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_EXPORT_STATUS;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_QUERY_METADATA;
import static pl.akmf.ksef.sdk.api.Url.JWT_TOKEN_REFRESH;
import static pl.akmf.ksef.sdk.api.Url.JWT_TOKEN_REVOKE;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_CHANGE_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_RESET_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_SUBJECT_CERTIFICATE;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_SUBJECT_CERTIFICATE_RESET_TEST;
import static pl.akmf.ksef.sdk.api.Url.PEPPOL_QUERY;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_ATTACHMENT_STATUS;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_REVOKE_AUTHORIZATION;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_REVOKE_COMMON;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_AUTHORIZATIONS_GRANT;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_ENTITY_ROLES;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_EU_ENTITY_GRANT;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_PERSONAL_GRANTS;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_PERSON_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_SUBORDINATE_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_SUBUNIT_GRANT;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_STATUS;
import static pl.akmf.ksef.sdk.api.Url.SECURITY_PUBLIC_KEY_CERTIFICATE;
import static pl.akmf.ksef.sdk.api.Url.SESSION_ACTIVE_SESSIONS;
import static pl.akmf.ksef.sdk.api.Url.SESSION_CLOSE;
import static pl.akmf.ksef.sdk.api.Url.SESSION_INVOICE;
import static pl.akmf.ksef.sdk.api.Url.SESSION_INVOICE_FAILED;
import static pl.akmf.ksef.sdk.api.Url.SESSION_INVOICE_GET_BY_REFERENCE_NUMBER;
import static pl.akmf.ksef.sdk.api.Url.SESSION_INVOICE_SEND;
import static pl.akmf.ksef.sdk.api.Url.SESSION_INVOICE_UPO_BY_INVOICE_REFERENCE;
import static pl.akmf.ksef.sdk.api.Url.SESSION_INVOICE_UPO_BY_KSEF;
import static pl.akmf.ksef.sdk.api.Url.SESSION_LIST;
import static pl.akmf.ksef.sdk.api.Url.SESSION_OPEN;
import static pl.akmf.ksef.sdk.api.Url.SESSION_REVOKE_CURRENT_SESSION;
import static pl.akmf.ksef.sdk.api.Url.SESSION_REVOKE_SESSION;
import static pl.akmf.ksef.sdk.api.Url.SESSION_STATUS;
import static pl.akmf.ksef.sdk.api.Url.SESSION_UPO;
import static pl.akmf.ksef.sdk.api.Url.TEST_ATTACHMENT;
import static pl.akmf.ksef.sdk.api.Url.TEST_ATTACHMENT_REVOKE;
import static pl.akmf.ksef.sdk.api.Url.TEST_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.TEST_PERMISSION_REVOKE;
import static pl.akmf.ksef.sdk.api.Url.TEST_PERSON_CREATE;
import static pl.akmf.ksef.sdk.api.Url.TEST_PERSON_DELETE;
import static pl.akmf.ksef.sdk.api.Url.TEST_SUBJECT_CREATE;
import static pl.akmf.ksef.sdk.api.Url.TEST_SUBJECT_DELETE;
import static pl.akmf.ksef.sdk.api.Url.TOKEN_GENERATE;
import static pl.akmf.ksef.sdk.api.Url.TOKEN_LIST;
import static pl.akmf.ksef.sdk.api.Url.TOKEN_REVOKE;
import static pl.akmf.ksef.sdk.api.Url.TOKEN_STATUS;
import static pl.akmf.ksef.sdk.client.Headers.ACCEPT;
import static pl.akmf.ksef.sdk.client.Headers.APPLICATION_JSON;
import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;
import static pl.akmf.ksef.sdk.client.Headers.BEARER;
import static pl.akmf.ksef.sdk.client.Headers.BLOCK_BLOB;
import static pl.akmf.ksef.sdk.client.Headers.CONTENT_TYPE;
import static pl.akmf.ksef.sdk.client.Headers.CONTINUATION_TOKEN;
import static pl.akmf.ksef.sdk.client.Headers.OCTET_STREAM;
import static pl.akmf.ksef.sdk.client.Headers.X_KSEF_FEATURE;
import static pl.akmf.ksef.sdk.client.Headers.X_MS_BLOB_TYPE;
import static pl.akmf.ksef.sdk.client.Parameter.AUTHOR_IDENTIFIER;
import static pl.akmf.ksef.sdk.client.Parameter.AUTHOR_IDENTIFIER_TYPE;
import static pl.akmf.ksef.sdk.client.Parameter.DATE_CLOSED_FROM;
import static pl.akmf.ksef.sdk.client.Parameter.DATE_CLOSED_TO;
import static pl.akmf.ksef.sdk.client.Parameter.DATE_CREATED_FROM;
import static pl.akmf.ksef.sdk.client.Parameter.DATE_CREATED_TO;
import static pl.akmf.ksef.sdk.client.Parameter.DATE_MODIFIED_FROM;
import static pl.akmf.ksef.sdk.client.Parameter.DATE_MODIFIED_TO;
import static pl.akmf.ksef.sdk.client.Parameter.DESCRIPTION;
import static pl.akmf.ksef.sdk.client.Parameter.PAGE_OFFSET;
import static pl.akmf.ksef.sdk.client.Parameter.PAGE_SIZE;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_CERTIFICATE_SERIAL_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_INVOICE_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_KSEF_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_KSEF_REFERENCE_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_PERMISSION_ID;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_REFERENCE_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.PATH_UPO_REFERENCE_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.REFERENCE_NUMBER;
import static pl.akmf.ksef.sdk.client.Parameter.SESSION_TYPE;
import static pl.akmf.ksef.sdk.client.Parameter.STATUS;
import static pl.akmf.ksef.sdk.client.Parameter.STATUSES;
import static pl.akmf.ksef.sdk.client.Parameter.VERIFY_CERTIFICATE_CHAIN;

public class DefaultKsefClient implements KSeFClient {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private final ObjectMapper objectMapper;
    private final HttpClient apiClient;
    private final String baseURl;
    private final Duration timeout;
    private final Map<String, String> defaultHeaders;

    public DefaultKsefClient(HttpClient apiClient,
                             KsefApiProperties ksefApiProperties,
                             ObjectMapper objectMapper) {
        this.apiClient = apiClient;
        this.defaultHeaders = ksefApiProperties.getDefaultHeaders();
        this.timeout = ksefApiProperties.getRequestTimeout();
        this.baseURl = ksefApiProperties.getBaseUri();
        this.objectMapper = objectMapper;
    }

    /**
     * Nadanie podmiotom uprawnień o charakterze upoważnień
     *
     * @param entityAuthorizationPermissionsGrantRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionsProxyEntity(GrantAuthorizationPermissionsRequest entityAuthorizationPermissionsGrantRequest,
                                                          String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_AUTHORIZED_SUBJECT_PERMISSION.getUrl(), entityAuthorizationPermissionsGrantRequest, headers);

        validateResponseStatus(GRANT_AUTHORIZED_SUBJECT_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Nadanie uprawnień w sposób pośredni
     *
     * @param grantIndirectEntityPermissionsRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionIndirectEntity(GrantIndirectEntityPermissionsRequest grantIndirectEntityPermissionsRequest,
                                                            String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_INDIRECT_PERMISSION.getUrl(), grantIndirectEntityPermissionsRequest, headers);

        validateResponseStatus(GRANT_INDIRECT_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Otwarcie sesji wsadowej
     * Inicjalizacja wysyłki wsadowej paczki faktur.
     *
     * @param openBatchSessionRequest (optional)
     * @return ApiResponse&lt;OpenBatchSessionResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OpenBatchSessionResponse openBatchSession(OpenBatchSessionRequest openBatchSessionRequest,
                                                     String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(BATCH_SESSION_OPEN.getUrl(), openBatchSessionRequest, headers);

        validateResponseStatus(BATCH_SESSION_OPEN.getOperationId(), response);
        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OpenBatchSessionResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Zamknięcie sesji wsadowej
     * Informuje system o tym, że wszystkie pliki zostały przekazane i można rozpocząć ich przetwarzanie.
     *
     * @param referenceNumber Numer referencyjny sesji (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public void closeBatchSession(String referenceNumber, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling closeBatchSession");
        }

        String uri = buildUrlWithParams(BATCH_SESSION_CLOSE.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = post(uri, null, headers);

        validateResponseStatus(BATCH_SESSION_CLOSE.getOperationId(), response);
    }

    /**
     * Wysyłanie faktur w częściach
     * Inicjalizacja wysyłki wsadowej paczki faktur.
     *
     * @param openBatchSessionResponse
     * @param parts
     */
    @Override
    public void sendBatchParts(OpenBatchSessionResponse openBatchSessionResponse, List<BatchPartSendingInfo> parts) throws ApiException {
        if (CollectionUtils.isEmpty(parts)) {
            throw new IllegalArgumentException("No files to send.");
        }

        List<PackagePartSignatureInitResponseType> responsePartUploadRequests = openBatchSessionResponse.getPartUploadRequests();

        if (CollectionUtils.isEmpty(responsePartUploadRequests)) {
            throw new IllegalStateException("No information about parts to send.");
        }
        List<String> errors = new ArrayList<>();

        for (PackagePartSignatureInitResponseType responsePart : responsePartUploadRequests) {
            parts.stream()
                    .filter(p -> responsePart.getOrdinalNumber() == p.getOrdinalNumber())
                    .forEach(singlePart -> singleBatchPartSendingProcess(singlePart, responsePart, errors));
        }

        if (!errors.isEmpty()) {
            throw new ApiException("Errors when sending parts:\n" + String.join("\n", errors));
        }
    }

    /**
     * Wysyłanie faktur w częściach
     * Inicjalizacja wysyłki wsadowej paczki faktur.
     *
     * @param openBatchSessionResponse
     * @param parts
     */
    @Override
    public void sendBatchPartsWithStream(OpenBatchSessionResponse openBatchSessionResponse, List<BatchPartStreamSendingInfo> parts) throws ApiException {
        if (CollectionUtils.isEmpty(parts)) {
            throw new IllegalArgumentException("No files to send.");
        }

        List<PackagePartSignatureInitResponseType> responsePartUploadRequests = openBatchSessionResponse.getPartUploadRequests();
        if (CollectionUtils.isEmpty(responsePartUploadRequests)) {
            throw new IllegalStateException("No information about parts to send.");
        }

        List<String> errors = new ArrayList<>();

        for (PackagePartSignatureInitResponseType responsePart : responsePartUploadRequests) {
            parts.stream()
                    .filter(p -> responsePart.getOrdinalNumber() == p.getOrdinalNumber())
                    .forEach(singlePart -> singleBatchPartSendingProcessByStream(singlePart, responsePart, errors));
        }

        if (!errors.isEmpty()) {
            throw new ApiException("Errors when sending parts:\n" + String.join("\n", errors));
        }
    }

    /**
     * Otwarcie sesji interaktywnej
     * Inicjalizacja wysyłki interaktywnej faktur.
     *
     * @param openOnlineSessionRequest (optional)
     * @return ApiResponse&lt;OpenOnlineSessionResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OpenOnlineSessionResponse openOnlineSession(OpenOnlineSessionRequest openOnlineSessionRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(SESSION_OPEN.getUrl(), openOnlineSessionRequest, headers);

        validateResponseStatus(SESSION_OPEN.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OpenOnlineSessionResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Zamknięcie sesji interaktywnej
     * Zamyka sesję interaktywną i rozpoczyna generowanie zbiorczego UPO.
     *
     * @param referenceNumber Numer referencyjny sesji (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public void closeOnlineSession(String referenceNumber, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling apiV2SessionsOnlineReferenceNumberClosePost");
        }

        String uri = buildUrlWithParams(SESSION_CLOSE.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, null, headers);

        validateResponseStatus(SESSION_CLOSE.getOperationId(), response);
    }

    /**
     * Wysłanie faktury
     * Przyjmuje zaszyfrowaną fakturę oraz jej metadane i rozpoczyna jej przetwarzanie.
     *
     * @param referenceNumber                 Numer referencyjny sesji (required)
     * @param sendInvoiceOnlineSessionRequest (optional)
     * @return ApiResponse&lt;SendDocumentResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SendInvoiceResponse onlineSessionSendInvoice(String referenceNumber, SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling apiV2SessionsOnlineReferenceNumberClosePost");
        }

        String uri = buildUrlWithParams(SESSION_INVOICE_SEND.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, sendInvoiceOnlineSessionRequest, headers);

        validateResponseStatus(SESSION_INVOICE_SEND.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SendInvoiceResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie danych o limitach certyfikatów
     * Zwraca informacje o limitach certyfikatów oraz informacje czy użytkownik może zawnioskować o certyfikat.
     *
     * @return ApiResponse&lt;CertificateLimitsResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public CertificateLimitsResponse getCertificateLimits(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(CERTIFICATE_LIMIT.getUrl(), headers);

        validateResponseStatus(CERTIFICATE_LIMIT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            CertificateLimitsResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie danych do wniosku certyfikacyjnego
     * Zwraca dane wymagane do przygotowania wniosku certyfikacyjnego.
     *
     * @return ApiResponse&lt;CertificateEnrollmentDataResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public CertificateEnrollmentsInfoResponse getCertificateEnrollmentInfo(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(CERTIFICATE_ENROLLMENT_DATA.getUrl(), headers);

        validateResponseStatus(CERTIFICATE_ENROLLMENT_DATA.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            CertificateEnrollmentsInfoResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Wysyłka wniosku certyfikacyjnego
     * Przyjmuje wniosek certyfikacyjny i rozpoczyna jego przetwarzanie.
     *
     * @param enrollCertificateRequest (optional)
     * @return ApiResponse&lt;EnrollCertificateResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public CertificateEnrollmentResponse sendCertificateEnrollment(SendCertificateEnrollmentRequest enrollCertificateRequest,
                                                                   String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(CERTIFICATE_ENROLLMENT.getUrl(), enrollCertificateRequest, headers);

        validateResponseStatus(CERTIFICATE_ENROLLMENT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            CertificateEnrollmentResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie statusu przetwarzania wniosku certyfikacyjnego
     * Zwraca informacje o statusie wniosku certyfikacyjnego.
     *
     * @param referenceNumber Numer referencyjny wniosku certyfikacyjnego (required)
     * @return ApiResponse&lt;CertificateEnrollmentStatusResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public CertificateEnrollmentStatusResponse getCertificateEnrollmentStatus(String referenceNumber, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(CERTIFICATE_STATUS.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(CERTIFICATE_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            CertificateEnrollmentStatusResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie certyfikatu lub listy certyfikatów
     * Zwraca certyfikaty o podanych numerach seryjnych w formacie DER zakodowanym w Base64.
     *
     * @param certificateListRequest (optional)
     * @return ApiResponse&lt;RetrieveCertificatesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public CertificateListResponse getCertificateList(CertificateListRequest certificateListRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(CERTIFICATE_RETRIEVE.getUrl(), certificateListRequest, headers);

        validateResponseStatus(CERTIFICATE_RETRIEVE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            CertificateListResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Unieważnienie certyfikatu
     * Unieważnia certyfikat o podanym numerze seryjnym.
     *
     * @param certificateSerialNumber  Numer seryjny certyfikatu (required)
     * @param certificateRevokeRequest (optional)
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeCertificate(CertificateRevokeRequest certificateRevokeRequest, String certificateSerialNumber, String accessToken) throws ApiException {
        if (certificateSerialNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'certificateSerialNumber' when calling revokeCertificate");
        }

        String uri = buildUrlWithParams(CERTIFICATE_REVOKE.getUrl(), new HashMap<>())
                .replace(PATH_CERTIFICATE_SERIAL_NUMBER, certificateSerialNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, certificateRevokeRequest, headers);

        validateResponseStatus(CERTIFICATE_REVOKE.getOperationId(), response);
    }

    /**
     * Pobranie listy metadanych certyfikatów
     * Zwraca listę certyfikatów spełniających podane kryteria wyszukiwania. W przypadku braku podania kryteriów wyszukiwania zwrócona zostanie nieprzefiltrowana lista.
     *
     * @param pageSize                 Rozmiar strony wyników (optional, default to 10)
     * @param pageOffset               Numner strony wyników (optional, default to 0)
     * @param queryCertificatesRequest Kryteria filtrowania (optional)
     * @return ApiResponse&lt;QueryCertificatesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public CertificateMetadataListResponse getCertificateMetadataList(QueryCertificatesRequest queryCertificatesRequest,
                                                                      int pageSize, int pageOffset, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(CERTIFICATE_METADATA.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, queryCertificatesRequest, headers);

        validateResponseStatus(CERTIFICATE_METADATA.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            CertificateMetadataListResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Inicjalizacja mechanizmu uwierzytelnienia i autoryzacji
     *
     * @return ApiResponse&lt;AuthenticationChallengeResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public AuthenticationChallengeResponse getAuthChallenge() throws ApiException {
        Map<String, String> headers = new HashMap<>();

        HttpResponse<byte[]> response = post(AUTH_CHALLENGE.getUrl(), null, headers);

        validateResponseStatus(AUTH_CHALLENGE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            AuthenticationChallengeResponse.class)
            ).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Rozpoczęcie procesu uwierzytelniania
     * Rozpoczyna proces uwierzytelniania na podstawie podpisanego XMLa.
     *
     * @param signedXml (required)
     * @return ApiResponse&lt;AuthenticationInitResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SignatureResponse submitAuthTokenRequest(String signedXml, boolean verifyCertificateChain) throws ApiException {
        if (signedXml == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'body' when calling apiV2AuthTokenSignaturePost");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put(VERIFY_CERTIFICATE_CHAIN, String.valueOf(verifyCertificateChain));

        String uri = buildUrlWithParams(AUTH_TOKEN_SIGNATURE.getUrl(), params);
        Map<String, String> headers = new HashMap<>();
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, signedXml, headers);

        validateResponseStatus(AUTH_TOKEN_SIGNATURE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SignatureResponse.class))
                    .getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Rozpoczęcie procesu uwierzytelniania tokenem
     * Rozpoczyna proces uwierzytelniania na podstawie tokenu
     *
     * @param body (required)
     * @return ApiResponse&lt;AuthenticationInitResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SignatureResponse authenticateByKSeFToken(AuthKsefTokenRequest body) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(AUTH_KSEF_TOKEN.getUrl(), body, headers);

        validateResponseStatus(AUTH_KSEF_TOKEN.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SignatureResponse.class))
                    .getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie statusu operacji uwierzytelniania
     * Zwraca status trwającej operacji uwierzytelniania
     *
     * @param referenceNumber numer referencyjny związany z procesem uwierzytelnienia
     * @return ApiResponse&lt;AuthenticationOperationStatusResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public AuthStatus getAuthStatus(String referenceNumber, String authenticationToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(400, "Missing the required parameter 'tokenReferenceNumber' when calling apiV2AuthTokenTokenReferenceNumberGet");
        }

        String uri = buildUrlWithParams(AUTH_TOKEN_STATUS.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + authenticationToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(AUTH_TOKEN_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            AuthStatus.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie tokenów uwierzytelnienia
     * Zwraca token dostępowy oraz token odswieżający
     *
     * @return ApiResponse&lt;AuthenticationOperationStatusResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public AuthOperationStatusResponse redeemToken(String authenticationToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + authenticationToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(AUTH_TOKEN_REEDEM.getUrl(), null, headers);

        validateResponseStatus(AUTH_TOKEN_REEDEM.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            AuthOperationStatusResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Odświeżenie tokena autoryzacyjnego
     *
     * @return ApiResponse&lt;AuthenticationTokenRefreshResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public AuthenticationTokenRefreshResponse refreshAccessToken(String refreshToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + refreshToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(JWT_TOKEN_REFRESH.getUrl(), null, headers);

        validateResponseStatus(JWT_TOKEN_REFRESH.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            AuthenticationTokenRefreshResponse.class))
                    .getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Unieważnienie tokena autoryzacyjnego
     * Unieważnia aktualny (przekazany w nagłówku wywołania tej metody) token.
     * Po unieważnieniu tokena nie będzie można za jego pomocą wykonywać żadnych operacji.
     *
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeAccessToken(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = post(JWT_TOKEN_REVOKE.getUrl(), null, headers);

        validateResponseStatus(JWT_TOKEN_REVOKE.getOperationId(), response);

    }

    /**
     * Pobranie statusu operacji
     *
     * @param referenceNumber Numer referencyjny operacji (required)
     * @return ApiResponse&lt;PermissionsOperationStatusResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public PermissionStatusInfo permissionOperationStatus(String referenceNumber, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(PERMISSION_STATUS.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(PERMISSION_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            PermissionStatusInfo.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
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
    @Override
    public QueryPersonPermissionsResponse searchGrantedPersonPermissions(PersonPermissionsQueryRequest personPermissionsQueryRequest,
                                                                         int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_PERSON_PERMISSION.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, personPermissionsQueryRequest, headers);

        validateResponseStatus(PERMISSION_SEARCH_PERSON_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryPersonPermissionsResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
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
    @Override
    public QuerySubunitPermissionsResponse searchSubunitAdminPermissions(SubunitPermissionsQueryRequest subunitPermissionsQueryRequest,
                                                                         int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_SUBUNIT_GRANT.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, subunitPermissionsQueryRequest, headers);

        validateResponseStatus(PERMISSION_SEARCH_SUBUNIT_GRANT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QuerySubunitPermissionsResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Zwraca listę uprawnień przysługujących uwierzytelnionemu podmiotowi.
     *
     * @param request    QueryPersonalGrantRequest
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QueryPersonalGrantResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    @Override
    public QueryPersonalGrantResponse searchPersonalGrantPermission(QueryPersonalGrantRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_PERSONAL_GRANTS.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, request, headers);

        validateResponseStatus(PERMISSION_SEARCH_PERSONAL_GRANTS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryPersonalGrantResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie listy uprawnień do obsługi faktur nadanych podmiotom
     *
     * @param pageOffset (optional)
     * @param pageSize   (optional)
     * @return ApiResponse&lt;QueryEntityRolesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public QueryEntityRolesResponse searchEntityInvoiceRoles(int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_ENTITY_ROLES.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(PERMISSION_SEARCH_ENTITY_ROLES.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryEntityRolesResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
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
    @Override
    public SubordinateEntityRolesQueryResponse searchSubordinateEntityInvoiceRoles(SubordinateEntityRolesQueryRequest subordinateEntityRolesQueryRequest,
                                                                                   int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_SUBORDINATE_PERMISSION.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, subordinateEntityRolesQueryRequest, headers);

        validateResponseStatus(PERMISSION_SEARCH_SUBORDINATE_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SubordinateEntityRolesQueryResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
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
    @Override
    public QueryEntityAuthorizationPermissionsResponse searchEntityAuthorizationGrants(EntityAuthorizationPermissionsQueryRequest entityAuthorizationPermissionsQueryRequest,
                                                                                       int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_AUTHORIZATIONS_GRANT.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, entityAuthorizationPermissionsQueryRequest, headers);

        validateResponseStatus(PERMISSION_SEARCH_AUTHORIZATIONS_GRANT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryEntityAuthorizationPermissionsResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
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
    @Override
    public QueryEuEntityPermissionsResponse searchGrantedEuEntityPermissions(EuEntityPermissionsQueryRequest euEntityPermissionsQueryRequest,
                                                                             int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_EU_ENTITY_GRANT.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, euEntityPermissionsQueryRequest, headers);

        validateResponseStatus(PERMISSION_SEARCH_EU_ENTITY_GRANT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryEuEntityPermissionsResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Nadanie uprawnień administratora podmiotu unijnego
     *
     * @param euEntityPermissionsGrantRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionEUEntity(EuEntityPermissionsGrantRequest euEntityPermissionsGrantRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_EU_ADMINISTRATOR_PERMISSION.getUrl(), euEntityPermissionsGrantRequest, headers);

        validateResponseStatus(GRANT_EU_ADMINISTRATOR_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Nadanie uprawnień reprezentanta podmiotu unijnego
     *
     * @param grantEUEntityRepresentativePermissionsRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionEUEntityRepresentative(GrantEUEntityRepresentativePermissionsRequest grantEUEntityRepresentativePermissionsRequest,
                                                                    String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_EU_REPRESENTATIVE.getUrl(), grantEUEntityRepresentativePermissionsRequest, headers);

        validateResponseStatus(GRANT_EU_REPRESENTATIVE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie dokumentu po numerze KSeF
     * Zwraca dokument o podanym numerze KSeF.
     *
     * @param ksefReferenceNumber Numer KSeF dokumentu (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public byte[] getInvoice(String ksefReferenceNumber, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(INVOICE_DOWNLOAD_BY_KSEF.getUrl(), new HashMap<>())
                .replace(PATH_KSEF_REFERENCE_NUMBER, ksefReferenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(INVOICE_DOWNLOAD_BY_KSEF.getOperationId(), response);

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body()
        ).getData();
    }

    /**
     * Pobranie faktury  na podstawie numeru KSeF oraz danych faktury
     * Faktura zostanie zwrócona wyłącznie, jeśli wszystkie dane wejściowe są zgodne z danymi faktury w systemie.
     *
     * @param downloadInvoiceRequest (optional)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public byte[] getInvoice(DownloadInvoiceRequest downloadInvoiceRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(INVOICE_DOWNLOAD.getUrl(), downloadInvoiceRequest, headers);

        validateResponseStatus(INVOICE_DOWNLOAD.getOperationId(), response);

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body())
                .getData();
    }

    /**
     * Pobranie listy metadanych faktur
     * Zwraca listę metadanych faktur spełniające podane kryteria wyszukiwania. Wymagane uprawnienia: `InvoiceRead`.",
     *
     * @param pageOffset          Indeks pierwszej strony wyników (domyślnie 0). (optional)
     * @param pageSize            Rozmiar strony wyników(domyślnie 10). (optional)
     * @param invoiceQueryFilters Zestaw filtrów dla wyszukiwania metadanych. (optional)
     * @return ApiResponse&lt;QueryInvoicesReponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public QueryInvoiceMetadataResponse queryInvoiceMetadata(Integer pageOffset, Integer pageSize,
                                                             InvoiceQueryFilters invoiceQueryFilters,
                                                             String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(INVOICE_QUERY_METADATA.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, invoiceQueryFilters, headers);

        validateResponseStatus(INVOICE_QUERY_METADATA.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryInvoiceMetadataResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Inicjalizuje asynchroniczne zapytanie o pobranie faktur
     * Rozpoczyna asynchroniczny proces wyszukiwania faktur w systemie KSeF na podstawie przekazanych filtrów. Wymagane jest przekazanie informacji o szyfrowaniu w polu &#x60;Encryption&#x60;, które służą do zaszyfrowania wygenerowanych paczek z fakturami.
     *
     * @param invoiceExportRequest Zestaw filtrów dla wyszukiwania faktur. (optional)
     * @return ApiResponse&lt;InitAsyncInvoicesQueryResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public InitAsyncInvoicesQueryResponse initAsyncQueryInvoice(InvoiceExportRequest invoiceExportRequest,
                                                                String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(INVOICE_EXPORT_INIT.getUrl(), invoiceExportRequest, headers);

        validateResponseStatus(INVOICE_EXPORT_INIT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            InitAsyncInvoicesQueryResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Sprawdza status asynchronicznego zapytania o pobranie faktur
     * Pobiera status wcześniej zainicjalizowanego zapytania asynchronicznego na podstawie identyfikatora operacji. Umożliwia śledzenie postępu przetwarzania zapytania oraz pobranie gotowych paczek z wynikami, jeśli są już dostępne.
     *
     * @param referenceNumber Unikalny identyfikator operacji zwrócony podczas inicjalizacji zapytania. (required)
     * @return ApiResponse&lt;AsyncInvoicesQueryStatus&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public InvoiceExportStatus checkStatusAsyncQueryInvoice(String referenceNumber, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'operationReferenceNumber' when calling checkStatusAsyncQueryInvoice");
        }

        String uri = buildUrlWithParams(INVOICE_EXPORT_STATUS.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(INVOICE_EXPORT_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            InvoiceExportStatus.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    @Deprecated
    /**
     * Sprawdza status asynchronicznego zapytania o pobranie faktur
     * Metoda dostępna tymczasowo w celu testów odbioru paczki faktur z plikiem metadata.json
     * @param referenceNumber Unikalny identyfikator operacji zwrócony podczas inicjalizacji zapytania. (required)
     * @return ApiResponse&lt;AsyncInvoicesQueryStatus&gt;
     * @throws ApiException if fails to make API call
     */
    public InvoiceExportStatus checkStatusAsyncQueryInvoiceWithMetadata(String referenceNumber,
                                                                        String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'operationReferenceNumber' when calling checkStatusAsyncQueryInvoice");
        }

        String uri = buildUrlWithParams(INVOICE_EXPORT_STATUS.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);
        headers.put(X_KSEF_FEATURE, "include-metadata");

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(INVOICE_EXPORT_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            InvoiceExportStatus.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Nadanie podmiotom uprawnień do obsługi faktur
     *
     * @param grantEntityPermissionsRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionEntity(GrantEntityPermissionsRequest grantEntityPermissionsRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_INVOICE_SUBJECT_PERMISSION.getUrl(), grantEntityPermissionsRequest, headers);

        validateResponseStatus(GRANT_INVOICE_SUBJECT_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie statusu sesji
     * Sprawdza bieżący status sesji o podanym numerze referencyjnym.
     *
     * @param referenceNumber Numer referencyjny sesji. (required)
     * @return ApiResponse&lt;SessionStatusResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SessionStatusResponse getSessionStatus(String referenceNumber, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(SESSION_STATUS.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SessionStatusResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie statusu faktury z sesji
     * Zwraca fakturę przesłaną w sesji wraz ze statusem.
     *
     * @param referenceNumber        Numer referencyjny sesji. (required)
     * @param invoiceReferenceNumber Numer referencyjny faktury. (required)
     * @return ApiResponse&lt;SessionInvoice&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SessionInvoiceStatusResponse getSessionInvoiceStatus(String referenceNumber,
                                                                String invoiceReferenceNumber,
                                                                String accessToken) throws ApiException {
        String uri = buildUrlWithParams(SESSION_INVOICE_GET_BY_REFERENCE_NUMBER.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber)
                .replace(PATH_INVOICE_NUMBER, invoiceReferenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_INVOICE_GET_BY_REFERENCE_NUMBER.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SessionInvoiceStatusResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie UPO faktury z sesji na podstawie numeru referencyjnego faktury
     * Zwraca UPO faktury przesłanego w sesji na podstawie jego numeru KSeF.
     *
     * @param referenceNumber        Numer referencyjny sesji. (required)
     * @param invoiceReferenceNumber Numer referencyjny faktury. (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public byte[] getSessionInvoiceUpoByReferenceNumber(String referenceNumber,
                                                        String invoiceReferenceNumber,
                                                        String accessToken) throws ApiException {
        String uri = buildUrlWithParams(SESSION_INVOICE_UPO_BY_INVOICE_REFERENCE.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber)
                .replace(PATH_INVOICE_NUMBER, invoiceReferenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_INVOICE_UPO_BY_INVOICE_REFERENCE.getOperationId(), response);

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body()
        ).getData();
    }

    /**
     * Pobranie UPO faktury z sesji na podstawie numeru KSeF
     * Zwraca UPO faktury przesłanego w sesji na podstawie jego numeru KSeF.
     *
     * @param referenceNumber Numer referencyjny sesji. (required)
     * @param ksefNumber      Numer KSeF faktury. (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public byte[] getSessionInvoiceUpoByKsefNumber(String referenceNumber,
                                                   String ksefNumber,
                                                   String accessToken) throws ApiException {
        String uri = buildUrlWithParams(SESSION_INVOICE_UPO_BY_KSEF.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber)
                .replace(PATH_KSEF_NUMBER, ksefNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_INVOICE_UPO_BY_KSEF.getOperationId(), response);

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body()
        ).getData();
    }

    /**
     * Pobranie UPO dla sesji
     * Zwraca XML zawierający zbiorcze UPO dla sesji.
     *
     * @param referenceNumber    Numer referencyjny sesji. (required)
     * @param upoReferenceNumber Numer referencyjny UPO. (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public byte[] getSessionUpo(String referenceNumber,
                                String upoReferenceNumber,
                                String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling getSessionUpo");
        }
        if (upoReferenceNumber == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'upoReferenceNumber' when calling getSessionUpo");
        }

        String uri = buildUrlWithParams(SESSION_UPO.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber)
                .replace(PATH_UPO_REFERENCE_NUMBER, upoReferenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_UPO.getOperationId(), response);

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body())
                .getData();
    }

    /**
     * Pobranie faktur sesji
     * Zwraca listę faktur przesłanych w sesji wraz z ich statusami, oraz informacje na temat ilości poprawnie i niepoprawnie przetworzonych faktur.
     *
     * @param referenceNumber   Numer referencyjny sesji. (required)
     * @param continuationToken Token służący do pobrania kolejnej strony wyników. (optional)
     * @param pageSize          Rozmiar strony wyników. (optional, default to 10)
     * @return ApiResponse&lt;SessionInvoicesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SessionInvoicesResponse getSessionInvoices(String referenceNumber,
                                                      String continuationToken,
                                                      Integer pageSize,
                                                      String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));

        String uri = buildUrlWithParams(SESSION_INVOICE.getUrl(), params)
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_INVOICE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SessionInvoicesResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie niepoprawnie przetworzonych faktur sesji
     * Zwraca listę niepoprawnie przetworzonych faktur przesłanych w sesji wraz z ich statusami.
     *
     * @param referenceNumber   Numer referencyjny sesji. (required)
     * @param continuationToken Token służący do pobrania kolejnej strony wyników. (optional)
     * @param pageSize          Rozmiar strony wyników. (optional, default to 10)
     * @return ApiResponse&lt;SessionInvoicesResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SessionInvoicesResponse getSessionFailedInvoices(String referenceNumber,
                                                            String continuationToken,
                                                            Integer pageSize,
                                                            String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));

        String uri = buildUrlWithParams(SESSION_INVOICE_FAILED.getUrl(), params)
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_INVOICE_FAILED.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SessionInvoicesResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie listy sesji
     * Zwraca listę sesji spełniających podane kryteria wyszukiwania.
     *
     * @param request           enkapsulowane wszystkie pola requesta
     * @param pageSize          page size
     * @param continuationToken continuation token
     * @return ApiResponse&lt;SessionsQueryResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public SessionsQueryResponse getSessions(SessionsQueryRequest request,
                                             Integer pageSize,
                                             String continuationToken,
                                             String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));

        if (Objects.nonNull(request.getSessionType())) {
            params.put(SESSION_TYPE, request.getSessionType().getValue());
        }

        if (Objects.nonNull(request.getReferenceNumber())) {
            params.put(REFERENCE_NUMBER, request.getReferenceNumber());
        }

        if (Objects.nonNull(request.getDateCreatedFrom())) {
            params.put(DATE_CREATED_FROM, request.getDateCreatedFrom().toString());
        }

        if (Objects.nonNull(request.getDateCreatedTo())) {
            params.put(DATE_CREATED_TO, request.getDateCreatedTo().toString());
        }

        if (Objects.nonNull(request.getDateClosedFrom())) {
            params.put(DATE_CLOSED_FROM, request.getDateClosedFrom().toString());
        }

        if (Objects.nonNull(request.getDateClosedTo())) {
            params.put(DATE_CLOSED_TO, request.getDateClosedTo().toString());
        }

        if (Objects.nonNull(request.getDateModifiedFrom())) {
            params.put(DATE_MODIFIED_FROM, request.getDateModifiedFrom().toString());
        }

        if (Objects.nonNull(request.getDateModifiedFrom())) {
            params.put(DATE_MODIFIED_TO, request.getDateModifiedTo().toString());
        }

        if (Objects.nonNull(request.getStatuses())) {
            List<String> stringList = request.getStatuses()
                    .stream()
                    .map(CommonSessionStatus::getValue)
                    .collect(Collectors.toList());
            params.put(STATUSES, String.join(", ", stringList));
        }

        String uri = buildUrlWithParams(SESSION_LIST.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_LIST.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            SessionsQueryResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie listy aktywnych sesji
     * Zwraca listę aktywnych sesji uwierzytelnienia.
     *
     * @param pageSize          Rozmiar strony wyników. (optional, default to 10)
     * @param continuationToken
     * @return ApiResponse&lt;AuthenticationListResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public AuthenticationListResponse getActiveSessions(Integer pageSize,
                                                        String continuationToken,
                                                        String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        String uri = buildUrlWithParams(SESSION_ACTIVE_SESSIONS.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);

        }
        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(SESSION_ACTIVE_SESSIONS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            AuthenticationListResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Unieważnienie aktualnej sesji uwierzytelnienia
     * Unieważnia sesję powiązaną z tokenem użytym do wywołania tej operacji.  Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów. **Aktywne access tokeny działają do czasu minięcia ich termin ważności.**  Sposób uwierzytelnienia: &#x60;RefreshToken&#x60; lub &#x60;ContextToken&#x60;.
     *
     * @return ApiResponse&lt;Void&gt;
     */
    @Override
    public void revokeCurrentSession(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(SESSION_REVOKE_CURRENT_SESSION.getUrl(), headers);

        validateResponseStatus(SESSION_REVOKE_CURRENT_SESSION.getOperationId(), response);
    }

    /**
     * Unieważnienie sesji uwierzytelnienia
     * Unieważnia sesję o podanym numerze referencyjnym.  Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów. **Aktywne access tokeny działają do czasu minięcia ich termin ważności.**
     *
     * @param referenceNumber Numer referencyjny sesji uwierzytelnienia. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeSession(String referenceNumber, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(SESSION_REVOKE_SESSION.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validateResponseStatus(SESSION_REVOKE_SESSION.getOperationId(), response);
    }

    /**
     * Zwraca listę dostawców usług Peppol zarejestrowanych w systemie.
     *
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public PeppolProvidersListResponse getPeppolProvidersList(int pageOffset, int pageSize) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PEPPOL_QUERY.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(PEPPOL_QUERY.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            PeppolProvidersListResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * @param accessToken
     * @return
     * @throws ApiException
     */
    @Override
    public GetContextLimitResponse getContextSessionLimit(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(LIMIT_CONTEXT.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(LIMIT_CONTEXT.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            GetContextLimitResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Zwraca wartoście aktualnie obowiązujących limitów dla bieżącego podmiotu.
     *
     * @param accessToken
     * @return GetContextLimitResponse
     * @throws ApiException
     */
    @Override
    public GetSubjectLimitResponse getSubjectCertificateLimit(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(LIMIT_SUBJECT_CERTIFICATE.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(LIMIT_SUBJECT_CERTIFICATE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            GetSubjectLimitResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Udostępnione w nabliższym czasie, aktualnie wyłączone
     * Zmienia wartości aktualnie obowiązujących limitów dla bieżącego kontekstu. Tylko na środowiskach testowych.
     *
     * @param changeContextLimitRequest
     * @return
     * @throws ApiException
     */
    @Override
    public void changeContextLimitTest(ChangeContextLimitRequest changeContextLimitRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_CHANGE_TEST.getUrl();
        HttpResponse<byte[]> response = post(url, changeContextLimitRequest, headers);

        validateResponseStatus(LIMIT_CONTEXT_CHANGE_TEST.getOperationId(), response);
    }

    /**
     * Udostępnione w nabliższym czasie, aktualnie wyłączone
     * Zmienia wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu. Tylko na środowiskach testowych.
     *
     * @param changeSubjectCertificateLimitRequest
     * @return
     * @throws ApiException
     */
    @Override
    public void changeSubjectLimitTest(ChangeSubjectCertificateLimitRequest changeSubjectCertificateLimitRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST.getUrl();
        HttpResponse<byte[]> response = post(url, changeSubjectCertificateLimitRequest, headers);

        validateResponseStatus(LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST.getOperationId(), response);
    }

    /**
     * Udostępnione w nabliższym czasie, aktualnie wyłączone
     * Przywraca wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu do wartości domyślnych. Tylko na środowiskach testowych.
     *
     * @param accessToken
     * @return
     * @throws ApiException
     */
    @Override
    public void resetContextLimitTest(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(LIMIT_SUBJECT_CERTIFICATE_RESET_TEST.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validateResponseStatus(LIMIT_SUBJECT_CERTIFICATE_RESET_TEST.getOperationId(), response);
    }

    /**
     * @param accessToken
     * @throws ApiException
     */
    @Override
    public void resetSubjectCertificateLimit(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(LIMIT_CONTEXT_RESET_TEST.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validateResponseStatus(LIMIT_CONTEXT_RESET_TEST.getOperationId(), response);
    }

    /**
     * Tworzenie nowego podmiotu testowego. W przypadku grupy VAT i JST istnieje możliwość stworzenia jednostek podrzędnych. W wyniku takiego działania w systemie powstanie powiązanie między tymi podmiotami.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void createTestSubject(TestDataSubjectCreateRequest testDataSubjectCreateRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_SUBJECT_CREATE.getUrl();
        HttpResponse<byte[]> response = post(url, testDataSubjectCreateRequest, headers);

        validateResponseStatus(TEST_SUBJECT_CREATE.getOperationId(), response);
    }

    /**
     * Usuwanie podmiotu testowego. W przypadku grupy VAT i JST usunięte zostaną również jednostki podrzędne.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void removeTestSubject(TestDataSubjectRemoveRequest testDataSubjectRemoveRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_SUBJECT_DELETE.getUrl();
        HttpResponse<byte[]> response = post(url, testDataSubjectRemoveRequest, headers);

        validateResponseStatus(TEST_SUBJECT_DELETE.getOperationId(), response);
    }

    /**
     * Tworzenie nowej osoby fizycznej, której system nadaje uprawnienia właścicielskie. Można również określić, czy osoba ta jest komornikiem – wówczas otrzyma odpowiednie uprawnienie egzekucyjne.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void createTestPerson(TestDataPersonCreateRequest testDataPersonCreateRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_PERSON_CREATE.getUrl();
        HttpResponse<byte[]> response = post(url, testDataPersonCreateRequest, headers);

        validateResponseStatus(TEST_PERSON_CREATE.getOperationId(), response);
    }

    /**
     * Usuwanie testowej osoby fizycznej. System automatycznie odbierze jej wszystkie uprawnienia.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void removeTestPerson(TestDataPersonRemoveRequest testDataPersonRemoveRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_PERSON_DELETE.getUrl();
        HttpResponse<byte[]> response = post(url, testDataPersonRemoveRequest, headers);

        validateResponseStatus(TEST_PERSON_DELETE.getOperationId(), response);
    }

    /**
     * Nadawanie uprawnień testowemu podmiotowi lub osobie fizycznej, a także w ich kontekście.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void addTestPermission(TestDataPermissionRequest testDataPermissionRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_PERMISSION.getUrl();
        HttpResponse<byte[]> response = post(url, testDataPermissionRequest, headers);

        validateResponseStatus(TEST_PERMISSION.getOperationId(), response);
    }

    /**
     * Odbieranie uprawnień nadanych testowemu podmiotowi lub osobie fizycznej, a także w ich kontekście.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void removeTestPermission(TestDataPermissionRemoveRequest testDataPermissionRemoveRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_PERMISSION_REVOKE.getUrl();
        HttpResponse<byte[]> response = post(url, testDataPermissionRemoveRequest, headers);

        validateResponseStatus(TEST_PERMISSION_REVOKE.getOperationId(), response);
    }

    /**
     * Dodaje możliwość wysyłania faktur z załącznikiem przez wskazany podmiot
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void addAttachmentPermissionTest(TestDataAttachmentRequest testDataAttachmentRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_ATTACHMENT.getUrl();
        HttpResponse<byte[]> response = post(url, testDataAttachmentRequest, headers);

        validateResponseStatus(TEST_ATTACHMENT.getOperationId(), response);
    }

    /**
     * Odbiera możliwość wysyłania faktur z załącznikiem przez wskazany podmiot
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    @Override
    public void removeAttachmentPermissionTest(TestDataAttachmentRemoveRequest testDataAttachmentRemoveRequest) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = TEST_ATTACHMENT_REVOKE.getUrl();
        HttpResponse<byte[]> response = post(url, testDataAttachmentRemoveRequest, headers);

        validateResponseStatus(TEST_ATTACHMENT_REVOKE.getOperationId(), response);
    }

    /**
     * Nadanie osobom fizycznym uprawnień do pracy w KSeF
     *
     * @param grantPersonPermissionsRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionPerson(GrantPersonPermissionsRequest grantPersonPermissionsRequest,
                                                    String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_PERSON_PERMISSION.getUrl(), grantPersonPermissionsRequest, headers);

        validateResponseStatus(GRANT_PERSON_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Nadanie uprawnień administratora podmiotu podrzędnego
     *
     * @param subunitPermissionsGrantRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse grantsPermissionSubUnit(SubunitPermissionsGrantRequest subunitPermissionsGrantRequest,
                                                     String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_SUBUNIT_PERMISSION.getUrl(), subunitPermissionsGrantRequest, headers);

        validateResponseStatus(GRANT_SUBUNIT_PERMISSION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Odebranie uprawnień
     * Rozpoczyna asynchroniczną operacje odbierania uprawnienia o podanym identyfikatorze.  Ta metoda służy do odbierania uprawnień takich jak: - nadanych osobom fizycznym do pracy w KSeF - nadanych podmiotom do obsługi faktur - nadanych w sposób pośredni - administratora podmiotu podrzędnego - administratora podmiotu unijnego - reprezentanta podmiotu unijnego
     *
     * @param permissionId Id uprawnienia. (required)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse revokeCommonPermission(String permissionId, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(PERMISSION_REVOKE_COMMON.getUrl(), new HashMap<>())
                .replace(PATH_PERMISSION_ID, permissionId);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = delete(uri, headers);

        validateResponseStatus(PERMISSION_REVOKE_COMMON.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Odebranie uprawnień o charakterze upoważnień
     * Rozpoczyna asynchroniczną operacje odbierania uprawnienia o podanym identyfikatorze. Ta metoda służy do odbierania uprawnień o charakterze upoważnień.
     *
     * @param permissionId Id uprawnienia. (required)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OperationResponse revokeAuthorizationsPermission(String permissionId, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(PERMISSION_REVOKE_AUTHORIZATION.getUrl(), new HashMap<>())
                .replace(PATH_PERMISSION_ID, permissionId);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = delete(uri, headers);

        validateResponseStatus(PERMISSION_REVOKE_AUTHORIZATION.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            OperationResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Sprawdzenie czy obecny kontekst posiada zgodę na wystawianie faktur z załącznikiem.
     * Wymagane uprawnienia: CredentialsManage, CredentialsRead.
     *
     * @param accessToken - token sesyjny
     * @return PermissionAttachmentStatusResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    @Override
    public PermissionAttachmentStatusResponse checkPermissionAttachmentInvoiceStatus(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = delete(PERMISSION_ATTACHMENT_STATUS.getUrl(), headers);

        validateResponseStatus(PERMISSION_ATTACHMENT_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            PermissionAttachmentStatusResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Wygenerowanie nowego tokena
     *
     * @param ksefTokenRequest (optional)
     * @return ApiResponse&lt;GenerateTokenResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public GenerateTokenResponse generateKsefToken(KsefTokenRequest ksefTokenRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(TOKEN_GENERATE.getUrl(), ksefTokenRequest, headers);

        validateResponseStatus(TOKEN_GENERATE.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            GenerateTokenResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie listy wygenerowanych tokenów
     *
     * @param statuses          Status tokenów do zwrócenia. W przypadku braku parametru zwracane są wszystkie tokeny. Parametr można przekazać wielokrotnie. (optional)
     * @param continuationToken Token służący do pobrania kolejnej strony wyników. (optional)
     * @param pageSize          Rozmiar strony wyników. (optional, default to 10)
     * @return ApiResponse&lt;QueryTokensResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public QueryTokensResponse queryKsefTokens(List<AuthenticationTokenStatus> statuses,
                                               String description,
                                               String authorIdentifier,
                                               AuthorTokenIdentifier.IdentifierType authorIdentifierType,
                                               String continuationToken,
                                               Integer pageSize,
                                               String accessToken) throws ApiException {

        HashMap<String, String> params = new HashMap<>();
        statuses.forEach(stat -> params.put(STATUS, stat.toString()));
        if (pageSize != null) {
            params.put(PAGE_SIZE, String.valueOf(pageSize));
        }

        if (StringUtils.isNotBlank(description)) {
            params.put(DESCRIPTION, description);
        }

        if (StringUtils.isNotBlank(authorIdentifier)) {
            params.put(AUTHOR_IDENTIFIER, authorIdentifier);
        }

        if (Objects.nonNull(authorIdentifierType)) {
            params.put(AUTHOR_IDENTIFIER_TYPE, authorIdentifierType.getValue());
        }
        String uri = buildUrlWithParams(TOKEN_LIST.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);

        }
        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(TOKEN_LIST.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            QueryTokensResponse.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Pobranie statusu tokena
     *
     * @param referenceNumber Numer referencyjny tokena. (required)
     * @return ApiResponse&lt;AuthenticationToken&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public AuthenticationToken getKsefToken(String referenceNumber, String accessToken) throws ApiException {
        String uri = TOKEN_STATUS.getUrl()
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        validateResponseStatus(TOKEN_STATUS.getOperationId(), response);

        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(),
                            AuthenticationToken.class
                    )).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Unieważnienie tokena
     * Unieważniony token nie pozwoli już na uwierzytelnienie się za jego pomocą. Unieważnienie nie może zostać cofnięte.
     *
     * @param referenceNumber Numer referencyjny tokena do unieważeniania. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeKsefToken(String referenceNumber, String accessToken) throws ApiException {
        String uri = TOKEN_REVOKE.getUrl()
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validateResponseStatus(TOKEN_REVOKE.getOperationId(), response);
    }

    /**
     * Pobranie certyfikatów
     * Zwraca informacje o kluczach publicznych używanych do szyfrowania danych przesyłanych do systemu KSeF.
     *
     * @return ApiResponse&lt;List&lt;PublicKeyCertificate&gt;&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public List<PublicKeyCertificate> retrievePublicKeyCertificate() throws ApiException {
        Map<String, String> headers = new HashMap<>();

        headers.put(ACCEPT, APPLICATION_JSON);
        HttpResponse<byte[]> response = get(SECURITY_PUBLIC_KEY_CERTIFICATE.getUrl(), headers);

        validateResponseStatus(SECURITY_PUBLIC_KEY_CERTIFICATE.getOperationId(), response);
        try {
            return new ApiResponse<>(
                    response.statusCode(),
                    response.headers(),
                    response.body() == null ? null : objectMapper.readValue(response.body(), new TypeReference<List<PublicKeyCertificate>>() {
                    })).getData();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    private HttpResponse<byte[]> get(String uri, Map<String, String> headers) {
        HttpRequest request = buildRequest(uri, GET, null, headers);

        return sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
    }

    private HttpResponse<byte[]> post(String uri, Object body, Map<String, String> headers) throws SystemKSeFSDKException {
        try {
            HttpRequest request = buildRequest(uri, POST, body, headers);

            return sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    private HttpResponse<byte[]> delete(String uri, Map<String, String> headers) throws SystemKSeFSDKException {
        HttpRequest request = buildRequest(uri, DELETE, null, headers);
        return sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
    }

    private HttpRequest buildRequest(String uri, String method, byte[] body, Map<String, String> additionalHeaders) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + uri))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);

        additionalHeaders.forEach(builder::header);
        switch (method.toUpperCase()) {
            case GET:
                builder.GET();
                break;
            case POST:
                builder.POST(HttpRequest.BodyPublishers.ofByteArray(body));
                break;
            case PUT:
                builder.PUT(HttpRequest.BodyPublishers.ofByteArray(body));
                break;
            case DELETE:
                builder.DELETE();
                break;
            default:
                builder.method(method, HttpRequest.BodyPublishers.ofByteArray(body));
        }

        return builder.build();
    }

    private HttpRequest buildRequest(String uri, String method, Object body, Map<String, String> additionalHeaders) throws JsonProcessingException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + uri))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);

        additionalHeaders.forEach(builder::header);

        if (body instanceof String) {
            String stringBody = (String) body;
            switch (method.toUpperCase()) {
                case GET:
                    builder.GET();
                    break;
                case POST:
                    builder.POST(HttpRequest.BodyPublishers.ofString(stringBody));
                    break;
                case PUT:
                    builder.PUT(HttpRequest.BodyPublishers.ofString(stringBody));
                    break;
                case DELETE:
                    builder.DELETE();
                    break;
                default:
                    builder.method(method, HttpRequest.BodyPublishers.ofString(stringBody));
            }
        } else {
            switch (method.toUpperCase()) {
                case GET:
                    builder.GET();
                    break;
                case POST:
                    builder.POST(body != null ?
                            HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(body)) :
                            HttpRequest.BodyPublishers.noBody());
                    break;
                case PUT:
                    builder.PUT(body != null ?
                            HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(body)) :
                            HttpRequest.BodyPublishers.noBody());
                    break;
                case DELETE:
                    builder.DELETE();
                    break;
                default:
                    builder.method(method, body != null ?
                            HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(body)) :
                            HttpRequest.BodyPublishers.noBody());
            }

        }

        return builder.build();
    }


    /**
     * Wysyłka strumieniowa pojedyńczego partu
     *
     * @param part         (required)
     * @param responsePart (required)
     * @param errors       (required)
     */
    @Override
    public void singleBatchPartSendingProcessByStream(BatchPartStreamSendingInfo part,
                                                      PackagePartSignatureInitResponseType responsePart,
                                                      List<String> errors) {
        InputStream dataStream = part.getDataStream();

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, OCTET_STREAM);
        headers.put(X_MS_BLOB_TYPE, BLOCK_BLOB);

        String url = responsePart.getUrl().toString().replace(baseURl, "");

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + url))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);
        headers.forEach(builder::header);

        builder.PUT(HttpRequest.BodyPublishers.ofInputStream(() -> dataStream));

        HttpRequest request = builder.build();

        HttpResponse<byte[]> responseResult = sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());

        if (HttpStatus.getErrorCodes().contains(responseResult.statusCode())) {
            errors.add("Error sends part " + responsePart.getOrdinalNumber() + ": " + responseResult.statusCode());
        }
    }

    /**
     * Wysyłka pojedyńczego partu
     *
     * @param part         (required)
     * @param responsePart (required)
     * @param errors       (required)
     */
    @Override
    public void singleBatchPartSendingProcess(BatchPartSendingInfo part,
                                              PackagePartSignatureInitResponseType responsePart,
                                              List<String> errors) {
        byte[] fileBytes = part.getData();

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, OCTET_STREAM);
        headers.put(X_MS_BLOB_TYPE, BLOCK_BLOB);

        String url = responsePart.getUrl().toString().replace(baseURl, "");

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + url))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);

        headers.forEach(builder::header);
        builder.PUT(HttpRequest.BodyPublishers.ofByteArray(fileBytes));
        HttpRequest request = builder.build();

        HttpResponse<byte[]> responseResult = sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
        if (HttpStatus.getErrorCodes().contains(responseResult.statusCode())) {
            errors.add("Error sends part " + responsePart.getOrdinalNumber() + ": " + responseResult.statusCode());
        }
    }

    protected HttpResponse<byte[]> sendHttpRequest(HttpRequest request, HttpResponse.BodyHandler<byte[]> bodyHandler) {
        try {
            return apiClient.send(request, bodyHandler);
        } catch (IOException | InterruptedException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }
}
