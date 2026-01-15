package pl.akmf.ksef.sdk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import pl.akmf.ksef.sdk.client.interfaces.KSeFClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ApiResponse;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
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
import pl.akmf.ksef.sdk.client.model.invoice.InitAsyncInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportStatus;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackagePart;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.limit.ChangeContextLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.ChangeSubjectCertificateLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.GetContextLimitResponse;
import pl.akmf.ksef.sdk.client.model.limit.GetRateLimitResponse;
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
import pl.akmf.ksef.sdk.client.model.util.SortOrder;
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

import static pl.akmf.ksef.sdk.api.HttpStatus.ACCEPTED;
import static pl.akmf.ksef.sdk.api.HttpStatus.CREATED;
import static pl.akmf.ksef.sdk.api.HttpStatus.NO_CONTENT;
import static pl.akmf.ksef.sdk.api.HttpStatus.OK;
import static pl.akmf.ksef.sdk.api.HttpUtils.buildUrlWithParams;
import static pl.akmf.ksef.sdk.api.HttpUtils.formatExceptionMessage;
import static pl.akmf.ksef.sdk.api.HttpUtils.isValidResponse;
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
import static pl.akmf.ksef.sdk.api.Url.GET_RATE_LIMIT;
import static pl.akmf.ksef.sdk.api.Url.GRANT_AUTHORIZED_SUBJECT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_EU_ADMINISTRATOR_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_EU_REPRESENTATIVE;
import static pl.akmf.ksef.sdk.api.Url.GRANT_INDIRECT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_INVOICE_SUBJECT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_PERSON_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.GRANT_SUBUNIT_PERMISSION;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_DOWNLOAD_BY_KSEF;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_EXPORT_INIT;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_EXPORT_STATUS;
import static pl.akmf.ksef.sdk.api.Url.INVOICE_QUERY_METADATA;
import static pl.akmf.ksef.sdk.api.Url.JWT_TOKEN_REFRESH;
import static pl.akmf.ksef.sdk.api.Url.JWT_TOKEN_REVOKE;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_CHANGE_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_RESET_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_SET_PRODUCTION;
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
import static pl.akmf.ksef.sdk.client.Headers.CONTENT_TYPE;
import static pl.akmf.ksef.sdk.client.Headers.CONTINUATION_TOKEN;
import static pl.akmf.ksef.sdk.client.Headers.OCTET_STREAM;
import static pl.akmf.ksef.sdk.client.Headers.X_KSEF_FEATURE;
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
import static pl.akmf.ksef.sdk.client.Parameter.SORT_ORDER;
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
    private final String suffixURl;
    private final Duration timeout;
    private final Map<String, String> defaultHeaders;

    public DefaultKsefClient(HttpClient apiClient,
                             KsefApiProperties ksefApiProperties,
                             ObjectMapper objectMapper) {
        this.apiClient = apiClient;
        this.defaultHeaders = ksefApiProperties.getDefaultHeaders();
        this.timeout = ksefApiProperties.getRequestTimeout();
        this.baseURl = ksefApiProperties.getBaseUri();
        this.suffixURl = ksefApiProperties.getSuffixUri();
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

        return getResponse(response, ACCEPTED, GRANT_AUTHORIZED_SUBJECT_PERMISSION, OperationResponse.class);
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

        return getResponse(response, ACCEPTED, GRANT_INDIRECT_PERMISSION, OperationResponse.class);
    }

    /**
     * Otwarcie sesji wsadowej
     * Otwiera sesję do wysyłki wsadowej faktur.
     *
     * @param openBatchSessionRequest - OpenBatchSessionRequest - schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * @param upoVersion              - Opcjonalna wersja formatu UPO. Dostępne wartości: "upo-v4-3". Generuje nagłówek X-KSeF-Feature z odpowiednią wartością. Domyślnie: v4-2 (v4-3 od 05.01.2026).
     * @return OpenBatchSessionResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    @Override
    public OpenBatchSessionResponse openBatchSession(OpenBatchSessionRequest openBatchSessionRequest, UpoVersion upoVersion, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);
        headers.put(X_KSEF_FEATURE, upoVersion.value());

        HttpResponse<byte[]> response = post(BATCH_SESSION_OPEN.getUrl(), openBatchSessionRequest, headers);

        return getResponse(response, CREATED, BATCH_SESSION_OPEN, OpenBatchSessionResponse.class);
    }


    /**
     * Zamknięcie sesji wsadowej
     * Informuje system o tym, że wszystkie pliki zostały przekazane i można rozpocząć ich przetwarzanie.
     *
     * @param referenceNumber Numer referencyjny sesji (required)
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

        validResponse(response, NO_CONTENT, BATCH_SESSION_OPEN);
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
     * @param upoVersion               - Opcjonalna wersja formatu UPO. Dostępne wartości: "upo-v4-3". Generuje nagłówek X-KSeF-Feature z odpowiednią wartością. Domyślnie: v4-2 (v4-3 od 05.01.2026).
     * @return ApiResponse&lt;OpenOnlineSessionResponse&gt;
     * @throws ApiException if fails to make API call
     */
    @Override
    public OpenOnlineSessionResponse openOnlineSession(OpenOnlineSessionRequest openOnlineSessionRequest, UpoVersion upoVersion, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);
        headers.put(X_KSEF_FEATURE, upoVersion.value());

        HttpResponse<byte[]> response = post(SESSION_OPEN.getUrl(), openOnlineSessionRequest, headers);

        return getResponse(response, CREATED, SESSION_OPEN, OpenOnlineSessionResponse.class);
    }

    /**
     * Zamknięcie sesji interaktywnej
     * Zamyka sesję interaktywną i rozpoczyna generowanie zbiorczego UPO.
     *
     * @param referenceNumber Numer referencyjny sesji (required)
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

        validResponse(response, NO_CONTENT, SESSION_CLOSE);
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

        return getResponse(response, ACCEPTED, SESSION_INVOICE_SEND, SendInvoiceResponse.class);
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

        return getResponse(response, OK, CERTIFICATE_LIMIT, CertificateLimitsResponse.class);
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

        return getResponse(response, OK, CERTIFICATE_ENROLLMENT_DATA, CertificateEnrollmentsInfoResponse.class);
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

        return getResponse(response, ACCEPTED, CERTIFICATE_ENROLLMENT, CertificateEnrollmentResponse.class);
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

        return getResponse(response, OK, CERTIFICATE_STATUS, CertificateEnrollmentStatusResponse.class);
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

        return getResponse(response, OK, CERTIFICATE_RETRIEVE, CertificateListResponse.class);
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
        validResponse(response, NO_CONTENT, CERTIFICATE_REVOKE);
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

        return getResponse(response, OK, CERTIFICATE_METADATA, CertificateMetadataListResponse.class);
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

        return getResponse(response, OK, AUTH_CHALLENGE, AuthenticationChallengeResponse.class);
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

        return getResponse(response, ACCEPTED, AUTH_TOKEN_SIGNATURE, SignatureResponse.class);
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

        return getResponse(response, ACCEPTED, AUTH_KSEF_TOKEN, SignatureResponse.class);
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

        return getResponse(response, OK, AUTH_TOKEN_STATUS, AuthStatus.class);
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

        return getResponse(response, OK, AUTH_TOKEN_REEDEM, AuthOperationStatusResponse.class);
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

        return getResponse(response, OK, JWT_TOKEN_REFRESH, AuthenticationTokenRefreshResponse.class);
    }

    /**
     * Unieważnienie tokena autoryzacyjnego
     * Unieważnia aktualny (przekazany w nagłówku wywołania tej metody) token.
     * Po unieważnieniu tokena nie będzie można za jego pomocą wykonywać żadnych operacji.
     *
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeAccessToken(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = post(JWT_TOKEN_REVOKE.getUrl(), null, headers);

        validResponse(response, OK, JWT_TOKEN_REVOKE);
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

        return getResponse(response, OK, PERMISSION_STATUS, PermissionStatusInfo.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_PERSON_PERMISSION, QueryPersonPermissionsResponse.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_SUBUNIT_GRANT, QuerySubunitPermissionsResponse.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_PERSONAL_GRANTS, QueryPersonalGrantResponse.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_ENTITY_ROLES, QueryEntityRolesResponse.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_SUBORDINATE_PERMISSION, SubordinateEntityRolesQueryResponse.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_AUTHORIZATIONS_GRANT, QueryEntityAuthorizationPermissionsResponse.class);
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

        return getResponse(response, OK, PERMISSION_SEARCH_EU_ENTITY_GRANT, QueryEuEntityPermissionsResponse.class);
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

        return getResponse(response, ACCEPTED, GRANT_EU_ADMINISTRATOR_PERMISSION, OperationResponse.class);
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

        return getResponse(response, ACCEPTED, GRANT_EU_REPRESENTATIVE, OperationResponse.class);
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

        validResponse(response, OK, INVOICE_DOWNLOAD_BY_KSEF);

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body()
        ).getData();
    }

    /**
     * Zwraca listę metadanych faktur spełniające podane kryteria wyszukiwania.
     *
     * @param pageOffset          - Index strony wyników (domyślnie 0)
     * @param pageSize            - Ilość elementów na stronie (domyślnie 10)
     * @param sortOrder           - Kolejność sortowania wyników.
     * @param invoiceQueryFilters InvoicesQueryRequest - zestaw filtrów
     * @return QueryInvoicesReponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    @Override
    public QueryInvoiceMetadataResponse queryInvoiceMetadata(Integer pageOffset,
                                                             Integer pageSize,
                                                             SortOrder sortOrder,
                                                             InvoiceQueryFilters invoiceQueryFilters,
                                                             String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        params.put(SORT_ORDER, sortOrder.getValue());
        String uri = buildUrlWithParams(INVOICE_QUERY_METADATA.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, invoiceQueryFilters, headers);

        return getResponse(response, OK, INVOICE_QUERY_METADATA, QueryInvoiceMetadataResponse.class);
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

        return getResponse(response, CREATED, INVOICE_EXPORT_INIT, InitAsyncInvoicesQueryResponse.class);
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

        return getResponse(response, OK, INVOICE_EXPORT_STATUS, InvoiceExportStatus.class);
    }

    /**
     * Nadanie podmiotom uprawnień do obsługi faktur
     *
     * @param grantEntityPermissionsRequest (optional)
     * @return ApiResponse&lt;PermissionsOperationResponse&gt;
     * @throws ApiException if fails to make API call
     **/
    @Override
    public OperationResponse grantsPermissionEntity(GrantEntityPermissionsRequest grantEntityPermissionsRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(GRANT_INVOICE_SUBJECT_PERMISSION.getUrl(), grantEntityPermissionsRequest, headers);

        return getResponse(response, ACCEPTED, GRANT_INVOICE_SUBJECT_PERMISSION, OperationResponse.class);
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

        return getResponse(response, OK, SESSION_STATUS, SessionStatusResponse.class);
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

        return getResponse(response, OK, SESSION_INVOICE_GET_BY_REFERENCE_NUMBER, SessionInvoiceStatusResponse.class);
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

        validResponse(response, OK, SESSION_INVOICE_UPO_BY_INVOICE_REFERENCE);

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

        validResponse(response, OK, SESSION_INVOICE_UPO_BY_KSEF);

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

        validResponse(response, OK, SESSION_UPO);

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

        return getResponse(response, OK, SESSION_INVOICE, SessionInvoicesResponse.class);
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

        return getResponse(response, OK, SESSION_INVOICE_FAILED, SessionInvoicesResponse.class);
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
        List<HttpUtils.KeyValue> keyValues = new ArrayList<>();
        keyValues.add(new HttpUtils.KeyValue(PAGE_SIZE, String.valueOf(pageSize)));

        if (Objects.nonNull(request.getSessionType())) {
            keyValues.add(new HttpUtils.KeyValue(SESSION_TYPE, request.getSessionType().getValue()));
        }

        if (Objects.nonNull(request.getReferenceNumber())) {
            keyValues.add(new HttpUtils.KeyValue(REFERENCE_NUMBER, request.getReferenceNumber()));
        }

        if (Objects.nonNull(request.getDateCreatedFrom())) {
            keyValues.add(new HttpUtils.KeyValue(DATE_CREATED_FROM, request.getDateCreatedFrom().toString()));
        }

        if (Objects.nonNull(request.getDateCreatedTo())) {
            keyValues.add(new HttpUtils.KeyValue(DATE_CREATED_TO, request.getDateCreatedTo().toString()));
        }

        if (Objects.nonNull(request.getDateClosedFrom())) {
            keyValues.add(new HttpUtils.KeyValue(DATE_CLOSED_FROM, request.getDateClosedFrom().toString()));
        }

        if (Objects.nonNull(request.getDateClosedTo())) {
            keyValues.add(new HttpUtils.KeyValue(DATE_CLOSED_TO, request.getDateClosedTo().toString()));
        }

        if (Objects.nonNull(request.getDateModifiedFrom())) {
            keyValues.add(new HttpUtils.KeyValue(DATE_MODIFIED_FROM, request.getDateModifiedFrom().toString()));
        }

        if (Objects.nonNull(request.getDateModifiedFrom())) {
            keyValues.add(new HttpUtils.KeyValue(DATE_MODIFIED_TO, request.getDateModifiedTo().toString()));
        }

        if (Objects.nonNull(request.getStatuses())) {
            request.getStatuses().forEach(status -> keyValues.add(new HttpUtils.KeyValue(STATUSES, status.getValue())));
        }

        String uri = buildUrlWithParams(SESSION_LIST.getUrl(), keyValues);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }

        HttpResponse<byte[]> response = get(uri, headers);
        return getResponse(response, OK, SESSION_LIST, SessionsQueryResponse.class);
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

        return getResponse(response, OK, SESSION_ACTIVE_SESSIONS, AuthenticationListResponse.class);
    }

    /**
     * Unieważnienie aktualnej sesji uwierzytelnienia
     * Unieważnia sesję powiązaną z tokenem użytym do wywołania tej operacji.  Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów. **Aktywne access tokeny działają do czasu minięcia ich termin ważności.**  Sposób uwierzytelnienia: &#x60;RefreshToken&#x60; lub &#x60;ContextToken&#x60;.
     *
     */
    @Override
    public void revokeCurrentSession(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(SESSION_REVOKE_CURRENT_SESSION.getUrl(), headers);

        validResponse(response, NO_CONTENT, SESSION_REVOKE_CURRENT_SESSION);
    }

    /**
     * Unieważnienie sesji uwierzytelnienia
     * Unieważnia sesję o podanym numerze referencyjnym.  Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów. **Aktywne access tokeny działają do czasu minięcia ich termin ważności.**
     *
     * @param referenceNumber Numer referencyjny sesji uwierzytelnienia. (required)
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeSession(String referenceNumber, String accessToken) throws ApiException {
        String uri = buildUrlWithParams(SESSION_REVOKE_SESSION.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validResponse(response, NO_CONTENT, SESSION_REVOKE_SESSION);
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
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        return getResponse(response, OK, PEPPOL_QUERY, PeppolProvidersListResponse.class);
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
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        return getResponse(response, OK, LIMIT_CONTEXT, GetContextLimitResponse.class);
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
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        return getResponse(response, OK, LIMIT_SUBJECT_CERTIFICATE, GetSubjectLimitResponse.class);
    }

    /**
     * Udostępnione w nabliższym czasie, aktualnie wyłączone
     * Zmienia wartości aktualnie obowiązujących limitów dla bieżącego kontekstu. Tylko na środowiskach testowych.
     *
     * @param changeContextLimitRequest
     * @throws ApiException
     */
    @Override
    public void changeContextLimitTest(ChangeContextLimitRequest changeContextLimitRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_CHANGE_TEST.getUrl();
        HttpResponse<byte[]> response = post(url, changeContextLimitRequest, headers);

        validResponse(response, OK, LIMIT_CONTEXT_CHANGE_TEST);
    }

    /**
     * Ustawia w bieżącym kontekście wartości limitów api zgodne z profilem produkcyjnym. Dostępny tylko na środowisku TE.
     *
     * @param accessToken
     * @throws ApiException
     */
    @Override
    public void restoreProductionRateLimitsAsync(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_SET_PRODUCTION.getUrl();
        HttpResponse<byte[]> response = post(url, null, headers);

        validResponse(response, OK, LIMIT_CONTEXT_SET_PRODUCTION);
    }

    /**
     * Udostępnione w nabliższym czasie, aktualnie wyłączone
     * Zmienia wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu. Tylko na środowiskach testowych.
     *
     * @param changeSubjectCertificateLimitRequest
     * @throws ApiException
     */
    @Override
    public void changeSubjectLimitTest(ChangeSubjectCertificateLimitRequest changeSubjectCertificateLimitRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST.getUrl();
        HttpResponse<byte[]> response = post(url, changeSubjectCertificateLimitRequest, headers);

        validResponse(response, OK, LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST);
    }

    /**
     * Udostępnione w nabliższym czasie, aktualnie wyłączone
     * Przywraca wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu do wartości domyślnych. Tylko na środowiskach testowych.
     *
     * @param accessToken
     * @throws ApiException
     */
    @Override
    public void resetContextLimitTest(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(LIMIT_CONTEXT_RESET_TEST.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validResponse(response, OK, LIMIT_CONTEXT_RESET_TEST);
    }

    /**
     * @param accessToken
     * @throws ApiException
     */
    @Override
    public void resetSubjectCertificateLimit(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(LIMIT_SUBJECT_CERTIFICATE_RESET_TEST.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validResponse(response, OK, LIMIT_SUBJECT_CERTIFICATE_RESET_TEST);
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

        validResponse(response, OK, TEST_SUBJECT_CREATE);
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

        validResponse(response, OK, TEST_SUBJECT_DELETE);
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

        validResponse(response, OK, TEST_PERSON_CREATE);
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

        validResponse(response, OK, TEST_PERSON_DELETE);
    }

    /**
     * Zwraca wartości aktualnie obowiązujących limitów ilości żądań przesyłanych do API.
     *
     * @param accessToken
     * @return GetRateLimitResponse
     */
    @Override
    public GetRateLimitResponse getRateLimit(String accessToken) throws ApiException {
        String uri = buildUrlWithParams(GET_RATE_LIMIT.getUrl(), new HashMap<>());

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = get(uri, headers);

        return getResponse(response, OK, GET_RATE_LIMIT, GetRateLimitResponse.class);
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

        validResponse(response, OK, TEST_PERMISSION);
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

        validResponse(response, OK, TEST_PERMISSION_REVOKE);
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

        validResponse(response, OK, TEST_ATTACHMENT);
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

        validResponse(response, OK, TEST_ATTACHMENT_REVOKE);
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

        return getResponse(response, ACCEPTED, GRANT_PERSON_PERMISSION, OperationResponse.class);
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

        return getResponse(response, ACCEPTED, GRANT_SUBUNIT_PERMISSION, OperationResponse.class);
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

        return getResponse(response, ACCEPTED, PERMISSION_REVOKE_COMMON, OperationResponse.class);
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

        return getResponse(response, ACCEPTED, PERMISSION_REVOKE_AUTHORIZATION, OperationResponse.class);
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

        HttpResponse<byte[]> response = get(PERMISSION_ATTACHMENT_STATUS.getUrl(), headers);

        return getResponse(response, OK, PERMISSION_ATTACHMENT_STATUS, PermissionAttachmentStatusResponse.class);
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

        return getResponse(response, ACCEPTED, TOKEN_GENERATE, GenerateTokenResponse.class);
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
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);

        }
        HttpResponse<byte[]> response = get(uri, headers);

        return getResponse(response, OK, TOKEN_LIST, QueryTokensResponse.class);
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

        return getResponse(response, OK, TOKEN_STATUS, AuthenticationToken.class);
    }

    /**
     * Unieważnienie tokena
     * Unieważniony token nie pozwoli już na uwierzytelnienie się za jego pomocą. Unieważnienie nie może zostać cofnięte.
     *
     * @param referenceNumber Numer referencyjny tokena do unieważeniania. (required)
     * @throws ApiException if fails to make API call
     */
    @Override
    public void revokeKsefToken(String referenceNumber, String accessToken) throws ApiException {
        String uri = TOKEN_REVOKE.getUrl()
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = delete(uri, headers);

        validResponse(response, NO_CONTENT, TOKEN_REVOKE);
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

        validResponse(response, OK, SECURITY_PUBLIC_KEY_CERTIFICATE);

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
                .uri(buildUri(baseURl, suffixURl, uri))
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
                .uri(buildUri(baseURl, suffixURl, uri))
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
        String url = responsePart.getUrl().toString().replace(baseURl, "");

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + url))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);
        headers.forEach(builder::header);
        responsePart.getHeaders().forEach(builder::header);

        builder.PUT(HttpRequest.BodyPublishers.fromPublisher(HttpRequest.BodyPublishers.ofInputStream(() -> dataStream), part.getMetadata().getFileSize()));
        HttpRequest request = builder.build();

        HttpResponse<byte[]> responseResult = sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
        if (CREATED.getCode() != responseResult.statusCode()) {
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

        String url = responsePart.getUrl().toString().replace(baseURl, "");

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + url))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);
        responsePart.getHeaders().forEach(builder::header);

        headers.forEach(builder::header);
        builder.PUT(HttpRequest.BodyPublishers.ofByteArray(fileBytes));
        HttpRequest request = builder.build();

        HttpResponse<byte[]> responseResult = sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
        if (CREATED.getCode() != responseResult.statusCode()) {
            errors.add("Error sends part " + responsePart.getOrdinalNumber() + ": " + responseResult.statusCode());
        }
    }

    /**
     * Pobiera pojedynczą część paczki eksportu z URL.
     *
     * @param part - Część paczki do pobrania.
     * @return Tablica bajtów zawierająca pobraną część.
     */
    @Override
    public byte[] downloadPackagePart(InvoicePackagePart part) {
        String url = part.getUrl().toString().replace(baseURl, "");

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseURl + url))
                .timeout(timeout);

        defaultHeaders.forEach(builder::header);

        builder.GET();
        HttpRequest request = builder.build();

        HttpResponse<byte[]> response = sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());

        return new ApiResponse<>(
                response.statusCode(),
                response.headers(),
                response.body()
        ).getData();
    }

    protected HttpResponse<byte[]> sendHttpRequest(HttpRequest request, HttpResponse.BodyHandler<byte[]> bodyHandler) {
        try {
            return apiClient.send(request, bodyHandler);
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

    private URI buildUri(String baseUrl, String suffix, String url) {
        URI urlWithSuffix = URI.create(baseUrl + "/").resolve(suffix);
        return URI.create(urlWithSuffix + "/").resolve(url);
    }
}
