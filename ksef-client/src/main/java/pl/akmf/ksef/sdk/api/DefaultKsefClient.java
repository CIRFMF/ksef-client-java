package pl.akmf.ksef.sdk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import pl.akmf.ksef.sdk.client.interfaces.KSeFClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ApiResponse;
import pl.akmf.ksef.sdk.client.model.KsefApiException;
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
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifierInvoicesQueryRequest;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifierInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersByKsefNumberQueryResponse;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersQueryRequest;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersQueryResponse;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.GenerateCollectiveIdentifierRequest;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.GenerateCollectiveIdentifierResponse;
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
import pl.akmf.ksef.sdk.client.model.limit.SetRateLimitsRequest;
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
import pl.akmf.ksef.sdk.client.model.permission.search.EntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityAuthorizationPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityPermissionsResponse;
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
import pl.akmf.ksef.sdk.client.model.testdata.TestDataContextIdentifier;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermissionRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPermissionRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectRemoveRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataUpdateCertificateRequest;
import pl.akmf.ksef.sdk.client.model.util.SortOrder;
import pl.akmf.ksef.sdk.client.peppol.PeppolProvidersListResponse;
import pl.akmf.ksef.sdk.system.ExceptionHandler;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerHandler;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerOptions;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerProperties;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderCaptureHandler;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderObservationProperties;

import java.io.IOException;
import java.io.InputStream;
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
import static pl.akmf.ksef.sdk.api.HttpUtils.buildUri;
import static pl.akmf.ksef.sdk.api.HttpUtils.buildUrlWithParams;
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
import static pl.akmf.ksef.sdk.api.Url.COLLECTIVE_IDENTIFIERS_GENERATE;
import static pl.akmf.ksef.sdk.api.Url.COLLECTIVE_IDENTIFIERS_QUERY;
import static pl.akmf.ksef.sdk.api.Url.COLLECTIVE_IDENTIFIERS_QUERY_BY;
import static pl.akmf.ksef.sdk.api.Url.COLLECTIVE_IDENTIFIERS_QUERY_INVOICES;
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
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_BLOCK;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_CHANGE_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_RESET_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_RESTORE;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_SET;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_SET_PRODUCTION;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_CONTEXT_UNBLOCK;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_SUBJECT_CERTIFICATE;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST;
import static pl.akmf.ksef.sdk.api.Url.LIMIT_SUBJECT_CERTIFICATE_RESET_TEST;
import static pl.akmf.ksef.sdk.api.Url.PEPPOL_QUERY;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_ATTACHMENT_STATUS;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_REVOKE_AUTHORIZATION;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_REVOKE_COMMON;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_AUTHORIZATIONS_GRANT;
import static pl.akmf.ksef.sdk.api.Url.PERMISSION_SEARCH_ENTITIES_GRANTS;
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
import static pl.akmf.ksef.sdk.api.Url.UPDATE_CERTIFICATE_DATA;
import static pl.akmf.ksef.sdk.client.Headers.ACCEPT;
import static pl.akmf.ksef.sdk.client.Headers.APPLICATION_JSON;
import static pl.akmf.ksef.sdk.client.Headers.APPLICATION_XML;
import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;
import static pl.akmf.ksef.sdk.client.Headers.BEARER;
import static pl.akmf.ksef.sdk.client.Headers.CONTENT_TYPE;
import static pl.akmf.ksef.sdk.client.Headers.CONTINUATION_TOKEN;
import static pl.akmf.ksef.sdk.client.Headers.ENFORCE_XADES_COMPLIANCE;
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
    private final ExceptionHandler exceptionHandler;
    private final KsefCircuitBreakerHandler circuitBreakerHandler;
    private ResponseHeaderCaptureHandler responseHeaderCaptureHandler = null;

    public DefaultKsefClient(HttpClient apiClient,
                             KsefApiProperties ksefApiProperties,
                             ObjectMapper objectMapper) {
        this.apiClient = apiClient;
        this.defaultHeaders = ksefApiProperties.getDefaultHeaders();
        this.timeout = ksefApiProperties.getRequestTimeout();
        this.baseURl = ksefApiProperties.getBaseUri();
        this.suffixURl = ksefApiProperties.getSuffixUri();
        this.objectMapper = objectMapper;
        this.exceptionHandler = new ExceptionHandler(objectMapper);
        // domyślna implementacja z włączonym circuit breakerem
        this.circuitBreakerHandler = new KsefCircuitBreakerHandler(this.apiClient, new KsefCircuitBreakerOptions());
    }

    public DefaultKsefClient(HttpClient apiClient,
                             KsefApiProperties ksefApiProperties,
                             KsefCircuitBreakerProperties circuitBreakerProperties,
                             ObjectMapper objectMapper) {
        this.apiClient = apiClient;
        this.defaultHeaders = ksefApiProperties.getDefaultHeaders();
        this.timeout = ksefApiProperties.getRequestTimeout();
        this.baseURl = ksefApiProperties.getBaseUri();
        this.suffixURl = ksefApiProperties.getSuffixUri();
        this.objectMapper = objectMapper;
        this.exceptionHandler = new ExceptionHandler(objectMapper);
        if (circuitBreakerProperties != null && circuitBreakerProperties.getCircuitBreakerOptions() != null) {
            this.circuitBreakerHandler = (circuitBreakerProperties.getCircuitBreakerOptions().isEnabled()) ?
                    new KsefCircuitBreakerHandler(this.apiClient, circuitBreakerProperties.getCircuitBreakerOptions()) :
                    null;
        } else {
            // domyślna implementacja z włączonym circuit breakerem
            this.circuitBreakerHandler = new KsefCircuitBreakerHandler(this.apiClient, new KsefCircuitBreakerOptions());
        }
    }

    public DefaultKsefClient(HttpClient apiClient,
                             KsefApiProperties ksefApiProperties,
                             KsefCircuitBreakerProperties circuitBreakerProperties,
                             ResponseHeaderObservationProperties responseHeaderObservationProperties,
                             ObjectMapper objectMapper) {
        this(apiClient, ksefApiProperties, circuitBreakerProperties, objectMapper);
        if (responseHeaderObservationProperties != null
                && responseHeaderObservationProperties.getResponseHeaderObservationOptions() != null
                && responseHeaderObservationProperties.getResponseHeaderObservationOptions().isEnabled()) {
            this.responseHeaderCaptureHandler = new ResponseHeaderCaptureHandler();
            responseHeaderObservationProperties.getResponseHeaderObservationOptions()
                    .getHeaderNames().forEach(h -> this.responseHeaderCaptureHandler.subscribe(h));
        }
    }

    @Override
    public ResponseHeaderCaptureHandler getResponseHeaderCaptureHandler() {
        if (this.responseHeaderCaptureHandler == null) {
            // mimo wyłączonego w property handlera możemy w runtime go utworzyć wywołując tą metodę, pozostanie zasubskrybować się na nagłówek
            this.responseHeaderCaptureHandler = new ResponseHeaderCaptureHandler();
        }
        return this.responseHeaderCaptureHandler;
    }

    /**
     * Pobranie kopii domyślnych nagłówków
     *
     * @return {@code Map<String, String>}
     */
    @Override
    public Map<String, String> getDefaultHeaders() {
        return new HashMap<>(defaultHeaders);
    }

    /**
     * Usunięcie domyślnego nagłówka po kluczu
     *
     * @param key Nazwa (klucz) domyślnego nagłówka do usunięcia.
     */
    @Override
    public void removeDefaultHeader(String key) {
        defaultHeaders.remove(key);
    }

    /**
     * Dodanie domyślnego nagłówka
     *
     * @param key   Nazwa (klucz) domyślnego nagłówka.
     * @param value Wartość domyślnego nagłówka.
     */
    @Override
    public void addDefaultHeader(String key, String value) {
        defaultHeaders.put(key, value);
    }

    /**
     * Nadanie podmiotom uprawnień o charakterze upoważnień
     * <p>
     * Metoda pozwala na nadanie jednego z uprawnień podmiotowych do obsługi podmiotu kontekstu  podmiotowi wskazanemu w żądaniu.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadanie-uprawnie%C5%84-podmiotowych">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code CredentialsManage}.
     *
     * @param entityAuthorizationPermissionsGrantRequest Treść żądania — patrz opis pól klasy {@link GrantAuthorizationPermissionsRequest}.
     * @param accessToken                                Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * <p>
     * Metoda pozwala na nadanie w sposób pośredni osobie wskazanej w żądaniu uprawnień do obsługi faktur innego podmiotu – klienta. Może to być jedna z możliwości: - nadanie uprawnień generalnych – do obsługi wszystkich klientów - nadanie uprawnień selektywnych – do obsługi wskazanego klienta
     * <p>
     * Uprawnienie selektywne może być nadane wyłącznie wtedy, gdy klient nadał wcześniej podmiotowi bieżącego kontekstu dowolne uprawnienie z prawem do jego dalszego przekazywania (patrz [POST /v2/permissions/entities/grants](/docs/v2/index.html#tag/Nadawanie-uprawnien/paths/~1permissions~1entities~1grants/post)).
     * <p>
     * W żądaniu określane są nadawane uprawnienia ze zbioru: - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur
     * <p>
     * Metoda pozwala na wybór dowolnej kombinacji powyższych uprawnień.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadanie-uprawnie%C5%84-w-spos%C3%B3b-po%C5%9Bredni">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code CredentialsManage}.
     *
     * @param grantIndirectEntityPermissionsRequest Treść żądania — patrz opis pól klasy {@link GrantIndirectEntityPermissionsRequest}.
     * @param accessToken                           Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * <p>
     * Otwiera sesję do wysyłki wsadowej faktur. Należy przekazać schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-wsadowa.md">Przygotowanie paczki faktur</a> [Klucz publiczny Ministerstwa Finansów](/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego)
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * {@code X-KSeF-Feature: subject-identifier-validation} - Włącza walidację numerów NIP oraz identyfikatorów wewnętrznych podmiotów wskazanych na fakturze.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/batch
     *
     * @param openBatchSessionRequest - OpenBatchSessionRequest - schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * @param upoVersion              - Opcjonalna wersja formatu UPO. Dostępne wartości: "upo-v4-3". Generuje nagłówek X-KSeF-Feature z odpowiednią wartością. Domyślnie: v4-2 (v4-3 od 05.01.2026).
     * @param accessToken             Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OpenBatchSessionResponse
     */
    @Deprecated
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
     * Otwarcie sesji wsadowej
     * <p>
     * Otwiera sesję do wysyłki wsadowej faktur. Należy przekazać schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-wsadowa.md">Przygotowanie paczki faktur</a> [Klucz publiczny Ministerstwa Finansów](/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego)
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * {@code X-KSeF-Feature: subject-identifier-validation} - Włącza walidację numerów NIP oraz identyfikatorów wewnętrznych podmiotów wskazanych na fakturze.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/batch
     *
     * @param openBatchSessionRequest - OpenBatchSessionRequest - schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * @param accessToken             Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OpenBatchSessionResponse
     */
    @Override
    public OpenBatchSessionResponse openBatchSession(OpenBatchSessionRequest openBatchSessionRequest, String accessToken) throws ApiException {

        return openBatchSession(openBatchSessionRequest, accessToken, null);
    }

    /**
     * Otwarcie sesji wsadowej
     * <p>
     * Otwiera sesję do wysyłki wsadowej faktur. Należy przekazać schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-wsadowa.md">Przygotowanie paczki faktur</a> [Klucz publiczny Ministerstwa Finansów](/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego)
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * {@code X-KSeF-Feature: subject-identifier-validation} - Włącza walidację numerów NIP oraz identyfikatorów wewnętrznych podmiotów wskazanych na fakturze.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/batch
     *
     * @param openBatchSessionRequest - OpenBatchSessionRequest - schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * @param accessToken             Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @param feature                 - Opcjonalna wartość nagłówka X-KSeF-Feature.
     * @return OpenBatchSessionResponse
     */
    @Override
    public OpenBatchSessionResponse openBatchSession(OpenBatchSessionRequest openBatchSessionRequest, String accessToken, String feature) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);
        if (feature != null) {
            headers.put(X_KSEF_FEATURE, feature);
        }

        HttpResponse<byte[]> response = post(BATCH_SESSION_OPEN.getUrl(), openBatchSessionRequest, headers);

        return getResponse(response, CREATED, BATCH_SESSION_OPEN, OpenBatchSessionResponse.class);
    }

    /**
     * Zamknięcie sesji wsadowej
     * Zamyka sesję wsadową, rozpoczyna procesowanie paczki faktur i generowanie UPO dla prawidłowych faktur oraz zbiorczego UPO dla sesji.
     * <p>
     * **Headers:**
     * <p>
     * `X-Error-Format: problem-details` - ustawienie tego nagłówka powoduje zwracanie błędów w formacie **Problem Details** (`application/problem+json`).
     * <p>
     * **Wymagane jedno z uprawnień**: `InvoiceWrite`, `EnforcementOperations`.
     *
     * @param referenceNumber Numer referencyjny sesji (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void closeBatchSession(String referenceNumber, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling closeBatchSession");
        }

        String uri = buildUrlWithParams(BATCH_SESSION_CLOSE.getUrl(), new HashMap<>())
                .replace(PATH_REFERENCE_NUMBER, referenceNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);

        HttpResponse<byte[]> response = post(uri, null, headers);

        validResponse(response, NO_CONTENT, BATCH_SESSION_CLOSE);
    }

    /**
     * Wysyłanie faktur w częściach
     * Inicjalizacja wysyłki wsadowej paczki faktur.
     *
     * @param openBatchSessionResponse Odpowiedź zwrócona przy otwarciu sesji wsadowej (zawiera m.in. numer referencyjny sesji).
     * @param parts                    Kolekcja informacji o częściach paczki do wysłania.
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
            throw new KsefApiException("Errors when sending parts:\n" + String.join("\n", errors));
        }
    }

    /**
     * Wysyłanie faktur w częściach
     * Inicjalizacja wysyłki wsadowej paczki faktur.
     *
     * @param openBatchSessionResponse Odpowiedź zwrócona przy otwarciu sesji wsadowej (zawiera m.in. numer referencyjny sesji).
     * @param parts                    Kolekcja informacji (strumieniowych) o częściach paczki do wysłania.
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
            throw new KsefApiException("Errors when sending parts:\n" + String.join("\n", errors));
        }
    }

    /**
     * Otwarcie sesji interaktywnej
     * <p>
     * Otwiera sesję do wysyłki pojedynczych faktur. Należy przekazać schemat wysyłanych faktur oraz informacje o kluczu używanym do szyfrowania.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-interaktywna.md#1-otwarcie-sesji">Otwarcie sesji interaktywnej</a> [Klucz publiczny Ministerstwa Finansów](/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego)
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * {@code X-KSeF-Feature: subject-identifier-validation} - Włącza walidację numerów NIP oraz identyfikatorów wewnętrznych podmiotów wskazanych na fakturze.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/online
     *
     * @param openOnlineSessionRequest Treść żądania — patrz opis pól klasy {@link OpenOnlineSessionRequest}.
     * @param upoVersion               - Opcjonalna wersja formatu UPO. Dostępne wartości: "upo-v4-3". Generuje nagłówek X-KSeF-Feature z odpowiednią wartością. Domyślnie: v4-2 (v4-3 od 05.01.2026).
     * @param accessToken              Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OpenOnlineSessionResponse
     */
    @Override
    @Deprecated
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
     * Otwarcie sesji interaktywnej
     * <p>
     * Otwiera sesję do wysyłki pojedynczych faktur. Należy przekazać schemat wysyłanych faktur oraz informacje o kluczu używanym do szyfrowania.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-interaktywna.md#1-otwarcie-sesji">Otwarcie sesji interaktywnej</a> [Klucz publiczny Ministerstwa Finansów](/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego)
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * {@code X-KSeF-Feature: subject-identifier-validation} - Włącza walidację numerów NIP oraz identyfikatorów wewnętrznych podmiotów wskazanych na fakturze.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/online
     *
     * @param openOnlineSessionRequest Treść żądania — patrz opis pól klasy {@link OpenOnlineSessionRequest}.
     * @param accessToken              Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OpenOnlineSessionResponse
     */
    @Override
    public OpenOnlineSessionResponse openOnlineSession(OpenOnlineSessionRequest openOnlineSessionRequest, String accessToken) throws ApiException {

        return openOnlineSession(openOnlineSessionRequest, accessToken, null);
    }

    /**
     * Otwarcie sesji interaktywnej
     * <p>
     * Otwiera sesję do wysyłki pojedynczych faktur. Należy przekazać schemat wysyłanych faktur oraz informacje o kluczu używanym do szyfrowania.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-interaktywna.md#1-otwarcie-sesji">Otwarcie sesji interaktywnej</a> [Klucz publiczny Ministerstwa Finansów](/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego)
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * {@code X-KSeF-Feature: subject-identifier-validation} - Włącza walidację numerów NIP oraz identyfikatorów wewnętrznych podmiotów wskazanych na fakturze.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/online
     *
     * @param openOnlineSessionRequest Treść żądania — patrz opis pól klasy {@link OpenOnlineSessionRequest}.
     * @param accessToken              Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @param feature                  - Opcjonalna wartość nagłówka X-KSeF-Feature.
     * @return OpenOnlineSessionResponse
     */
    @Override
    public OpenOnlineSessionResponse openOnlineSession(OpenOnlineSessionRequest openOnlineSessionRequest, String accessToken, String feature) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);
        if (feature != null) {
            headers.put(X_KSEF_FEATURE, feature);
        }

        HttpResponse<byte[]> response = post(SESSION_OPEN.getUrl(), openOnlineSessionRequest, headers);

        return getResponse(response, CREATED, SESSION_OPEN, OpenOnlineSessionResponse.class);
    }

    /**
     * Zamknięcie sesji interaktywnej
     * <p>
     * Zamyka sesję interaktywną i rozpoczyna generowanie zbiorczego UPO dla sesji.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/online/{referenceNumber}/close
     *
     * @param referenceNumber Numer referencyjny sesji (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void closeOnlineSession(String referenceNumber, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling apiV2SessionsOnlineReferenceNumberClosePost");
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
     * <p>
     * Przyjmuje zaszyfrowaną fakturę oraz jej metadane i rozpoczyna jej przetwarzanie.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/sesja-interaktywna.md#2-wys%C5%82anie-faktury">Wysłanie faktury</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: POST /sessions/online/{referenceNumber}/invoices
     *
     * @param referenceNumber                 Numer referencyjny sesji (required)
     * @param sendInvoiceOnlineSessionRequest Dane faktury
     * @param accessToken                     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SendInvoiceResponse
     */
    @Override
    public SendInvoiceResponse onlineSessionSendInvoice(String referenceNumber, SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling apiV2SessionsOnlineReferenceNumberClosePost");
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
     * <p>
     * Zwraca informacje o limitach certyfikatów oraz informacje czy użytkownik może zawnioskować o certyfikat KSeF.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /certificates/limits
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return CertificateLimitsResponse
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
     * <p>
     * Zwraca dane wymagane do przygotowania wniosku certyfikacyjnego PKCS#10.
     * <p>
     * Dane te są zwracane na podstawie certyfikatu użytego w procesie uwierzytelnienia i identyfikują podmiot, który składa wniosek o certyfikat.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/certyfikaty-KSeF.md#2-pobranie-danych-do-wniosku-certyfikacyjnego">Pobranie danych do wniosku certyfikacyjnego</a> <a href="https://github.com/CIRFMF/ksef-api/blob/main/certyfikaty-KSeF.md#3-przygotowanie-csr-certificate-signing-request">Przygotowanie wniosku</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /certificates/enrollments/data
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return CertificateEnrollmentsInfoResponse
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
     * <p>
     * Przyjmuje wniosek certyfikacyjny i rozpoczyna jego przetwarzanie.
     * <p>
     * Dozwolone typy kluczy prywatnych: RSA (OID: 1.2.840.113549.1.1.1), długość klucza równa 2048 bitów, EC (klucze oparte na krzywych eliptycznych, OID: 1.2.840.10045.2.1), krzywa NIST P-256 (secp256r1)
     * <p>
     * Zalecane jest stosowanie kluczy EC.
     * <p>
     * Dozwolone algorytmy podpisu: RSA PKCS#1 v1.5, RSA PSS, ECDSA (format podpisu zgodny z RFC 3279)
     * <p>
     * Dozwolone funkcje skrótu użyte do podpisu CSR: SHA1, SHA256, SHA384, SHA512
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/certyfikaty-KSeF.md#4-wys%C5%82anie-wniosku-certyfikacyjnego">Wysłanie wniosku certyfikacyjnego</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /certificates/enrollments
     *
     * @param enrollCertificateRequest Treść żądania — patrz opis pól klasy {@link SendCertificateEnrollmentRequest}.
     * @param accessToken              Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return CertificateEnrollmentResponse
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
     * <p>
     * Status wniosku jest dostępny przez 30 dni.
     * <p>
     * Zwraca informacje o statusie wniosku certyfikacyjnego.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /certificates/enrollments/{referenceNumber}
     *
     * @param referenceNumber Numer referencyjny wniosku certyfikacyjnego (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return CertificateEnrollmentStatusResponse
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
     * <p>
     * Zwraca certyfikaty o podanych numerach seryjnych w formacie DER zakodowanym w Base64.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /certificates/retrieve
     *
     * @param certificateListRequest Treść żądania — patrz opis pól klasy {@link CertificateListRequest}.
     * @param accessToken            Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return CertificateListResponse
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
     * <p>
     * Unieważnia certyfikat o podanym numerze seryjnym. Operacja nie jest dostępna dla podmiotu uwierzytelnionego tokenem KSeF.
     * <p>
     * Podmiot może unieważnić wyłącznie certyfikat wygenerowany na jego identyfikator uwierzytelnienia lub na identyfikator powiązany w ramach powiązania NIP–PESEL.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /certificates/{certificateSerialNumber}/revoke
     *
     * @param certificateRevokeRequest Treść żądania — patrz opis pól klasy {@link CertificateRevokeRequest}.
     * @param certificateSerialNumber  Numer seryjny certyfikatu (required)
     * @param accessToken              Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void revokeCertificate(CertificateRevokeRequest certificateRevokeRequest, String certificateSerialNumber, String accessToken) throws ApiException {
        if (certificateSerialNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'certificateSerialNumber' when calling revokeCertificate");
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
     * <p>
     * Zwraca listę certyfikatów spełniających podane kryteria wyszukiwania. W przypadku braku podania kryteriów wyszukiwania zwrócona zostanie nieprzefiltrowana lista.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * requestDate (Desc)
     * <p>
     * Endpoint: POST /certificates/query
     *
     * @param queryCertificatesRequest Kryteria filtrowania
     * @param pageSize                 Rozmiar strony wyników
     * @param pageOffset               Numner strony wyników
     * @param accessToken              Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return CertificateMetadataListResponse
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
     * <p>
     * Generuje unikalny challenge wymagany w kolejnym kroku operacji uwierzytelnienia.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @return AuthenticationChallengeResponse
     */
    @Override
    public AuthenticationChallengeResponse getAuthChallenge() throws ApiException {
        Map<String, String> headers = new HashMap<>();

        HttpResponse<byte[]> response = post(AUTH_CHALLENGE.getUrl(), null, headers);

        return getResponse(response, OK, AUTH_CHALLENGE, AuthenticationChallengeResponse.class);
    }

    /**
     * Rozpoczyna operację uwierzytelniania za pomocą dokumentu XML podpisanego podpisem elektroniczny XAdES.
     * Rozpoczyna proces uwierzytelnienia na podstawie podpisanego XML-a.
     *
     * @param signedXml              - Podpisany XML z żądaniem uwierzytelnienia.
     * @param verifyCertificateChain - Flaga określająca, czy sprawdzić łańcuch certyfikatów. (Domyślnie false)
     * @return SignatureResponse
     */
    @Override
    public SignatureResponse submitAuthTokenRequest(String signedXml, boolean verifyCertificateChain) throws ApiException {
        return submitAuthTokenRequest(signedXml, verifyCertificateChain, false);
    }

    /**
     * Uwierzytelnienie z wykorzystaniem podpisu XAdES
     * <p>
     * Rozpoczyna operację uwierzytelniania za pomocą dokumentu XML podpisanego podpisem elektronicznym XAdES.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uwierzytelnianie.md#1-przygotowanie-dokumentu-xml-authtokenrequest">Przygotowanie dokumentu XML</a> <a href="https://github.com/CIRFMF/ksef-api/blob/main/uwierzytelnianie.md#2-podpisanie-dokumentu-xades">Podpis dokumentu XML</a> Obsługiwane schematy: <a href="https://github.com/CIRFMF/ksef-api/blob/main/auth/schemy/schemat_auth_v2-0.xsd">auth v2.0</a> <a href="https://github.com/CIRFMF/ksef-api/blob/main/auth/schemy/schemat_auth_v2-1.xsd">auth v2.1</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /auth/xades-signature
     *
     * @param signedXml              - Podpisany XML z żądaniem uwierzytelnienia.
     * @param verifyCertificateChain - Flaga określająca, czy sprawdzić łańcuch certyfikatów. (Domyślnie false)
     * @param enforceXadesCompliance - Flaga umożliwiająca wcześniejsze włączenie nowych wymagań walidacji XAdES na środowiskach DEMO i PRD poprzez nagłówek `X-KSeF-Feature: enforce-xades-compliance`.
     * @return SignatureResponse
     */
    @Override
    public SignatureResponse submitAuthTokenRequest(String signedXml, boolean verifyCertificateChain, boolean enforceXadesCompliance) throws ApiException {
        if (signedXml == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'body' when calling apiV2AuthTokenSignaturePost");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put(VERIFY_CERTIFICATE_CHAIN, String.valueOf(verifyCertificateChain));

        String uri = buildUrlWithParams(AUTH_TOKEN_SIGNATURE.getUrl(), params);
        Map<String, String> headers = new HashMap<>();
        headers.put(ACCEPT, APPLICATION_JSON);
        headers.put(CONTENT_TYPE, APPLICATION_XML);
        if (enforceXadesCompliance) {
            headers.put(ENFORCE_XADES_COMPLIANCE, APPLICATION_XML);
        }
        HttpResponse<byte[]> response = post(uri, signedXml, headers);

        return getResponse(response, ACCEPTED, AUTH_TOKEN_SIGNATURE, SignatureResponse.class);
    }

    /**
     * Uwierzytelnienie z wykorzystaniem tokena KSeF
     * <p>
     * Rozpoczyna operację uwierzytelniania z wykorzystaniem wcześniej wygenerowanego tokena KSeF.
     * <p>
     * Token KSeF wraz z timestampem ze wcześniej wygenerowanego challenge'a (w formacie ``{@code token|timestamp}``) powinien zostać zaszyfrowany dedykowanym do tego celu kluczem publicznym. Timestamp powinien zostać przekazany jako <b>liczba milisekund od 1 stycznia 1970 roku (Unix timestamp)</b>. Algorytm szyfrowania: <b>RSA-OAEP (z użyciem SHA-256 jako funkcji skrótu)</b>.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /auth/ksef-token
     *
     * @param body Treść żądania — patrz opis pól klasy {@link AuthKsefTokenRequest}. (required)
     * @return SignatureResponse
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
     * Pobranie statusu uwierzytelniania
     * <p>
     * Sprawdza bieżący status operacji uwierzytelniania dla podanego tokena.
     * <p>
     * Sposób uwierzytelnienia: {@code AuthenticationToken} otrzymany przy rozpoczęciu operacji uwierzytelniania. Operacja jest dostępna przez 7 dni.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /auth/{referenceNumber}
     *
     * @param referenceNumber     numer referencyjny związany z procesem uwierzytelnienia
     * @param authenticationToken Token służący do uwierzytelnienia, zwrócony w trakcie inicjalizacji operacji uwierzytelniania, przekazywany w nagłówku Authorization jako Bearer.
     * @return AuthStatus
     */
    @Override
    public AuthStatus getAuthStatus(String referenceNumber, String authenticationToken) throws ApiException {
        if (referenceNumber == null) {
            throw new KsefApiException(400, "Missing the required parameter 'tokenReferenceNumber' when calling apiV2AuthTokenTokenReferenceNumberGet");
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
     * Pobranie tokenów dostępowych
     * <p>
     * Pobiera parę tokenów (access token i refresh token) wygenerowanych w ramach pozytywnie zakończonego procesu uwierzytelniania. <b>Tokeny można pobrać tylko raz.</b>
     * <p>
     * Sposób uwierzytelnienia: {@code AuthenticationToken} otrzymany przy rozpoczęciu operacji uwierzytelniania.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /auth/token/redeem
     *
     * @param authenticationToken Token służący do uwierzytelnienia, zwrócony w trakcie inicjalizacji operacji uwierzytelniania, przekazywany w nagłówku Authorization jako Bearer.
     * @return AuthOperationStatusResponse
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
     * <p>
     * Generuje nowy token dostępu na podstawie ważnego refresh tokena.
     * <p>
     * Sposób uwierzytelnienia: {@code RefreshToken}.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param refreshToken Token odświeżający (refresh token), przekazywany w nagłówku Authorization jako Bearer, służący do uzyskania nowego tokena dostępowego.
     * @return AuthenticationTokenRefreshResponse
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
     * Pobranie statusu operacji
     * <p>
     * Zwraca status operacji asynchronicznej związanej z nadaniem lub odebraniem uprawnień.
     * <p>
     * Status operacji jest dostępny przez 30 dni.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /permissions/operations/{referenceNumber}
     *
     * @param referenceNumber Numer referencyjny operacji (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return PermissionStatusInfo
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
     * <p>
     * Metoda pozwala na odczytanie uprawnień nadanych osobie fizycznej lub podmiotowi. Lista pobranych uprawnień może być dwóch rodzajów: - Lista wszystkich uprawnień obowiązujących w bieżącym kontekście logowania (używana, gdy administrator chce przejrzeć uprawnienia wszystkich użytkowników w bieżącym kontekście) - Lista wszystkich uprawnień nadanych w bieżącym kontekście przez uwierzytelnionego klienta API (używana, gdy administrator chce przejrzeć listę nadanych przez siebie uprawnień w bieżącym kontekście)
     * <p>
     * Dla pierwszej listy (obowiązujących uprawnień) w odpowiedzi przekazywane są: - osoby i podmioty mogące pracować w bieżącym kontekście z wyjątkiem osób uprawnionych w sposób pośredni - osoby uprawnione w sposób pośredni przez podmiot bieżącego kontekstu
     * <p>
     * Dla drugiej listy (nadanych uprawnień) w odpowiedzi przekazywane są: - uprawnienia nadane w sposób bezpośredni do pracy w bieżącym kontekście lub w kontekście jednostek podrzędnych - uprawnienia nadane w sposób pośredni do obsługi klientów podmiotu bieżącego kontekstu
     * <p>
     * Uprawnienia zwracane przez operację obejmują: - <b>CredentialsManage</b> – zarządzanie uprawnieniami - <b>CredentialsRead</b> – przeglądanie uprawnień - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur - <b>Introspection</b> – przeglądanie historii sesji - <b>SubunitManage</b> – zarządzanie podmiotami podrzędnymi - <b>EnforcementOperations</b> – wykonywanie operacji egzekucyjnych - <b>CollectiveIdentifierManage</b> – zarządzanie identyfikatorami zbiorczymi
     * <p>
     * Odpowiedź może być filtrowana na podstawie parametrów: - <b>authorIdentifier</b> – identyfikator osoby, która nadała uprawnienie - <b>authorizedIdentifier</b> – identyfikator osoby lub podmiotu uprawnionego - <b>targetIdentifier</b> – identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio - <b>permissionTypes</b> – lista rodzajów wyszukiwanych uprawnień - <b>permissionState</b> – status uprawnienia - <b>queryType</b> – typ zapytania określający, która z dwóch list ma zostać zwrócona
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-uprawnie%C5%84-do-pracy-w-ksef-nadanych-osobom-fizycznym-lub-podmiotom">Pobieranie listy uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}, {@code SubunitManage}.
     *
     * @param personPermissionsQueryRequest Treść żądania — patrz opis pól klasy {@link PersonPermissionsQueryRequest}.
     * @param pageOffset                    Numer strony wyników.
     * @param pageSize                      Rozmiar strony wyników.
     * @param accessToken                   Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryPersonPermissionsResponse
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
     * <p>
     * Metoda pozwala na odczytanie uprawnień do zarządzania uprawnieniami nadanych administratorom: - jednostek podrzędnych identyfikowanych identyfikatorem wewnętrznym - podmiotów podrzędnych (podrzędnych JST lub członków grupy VAT) identyfikowanych przez NIP
     * <p>
     * Lista zwraca wyłącznie uprawnienia do zarządzania uprawnieniami nadane z kontekstu bieżącego (z podmiotu nadrzędnego). Nie są odczytywane uprawnienia nadane przez administratorów jednostek podrzędnych wewnątrz tych jednostek.
     * <p>
     * Odpowiedź może być filtrowana na podstawie parametru: - <b>subunitIdentifier</b> – identyfikator jednostki lub podmiotu podrzędnego
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-uprawnie%C5%84-administrator%C3%B3w-jednostek-i-podmiot%C3%B3w-podrz%C4%99dnych">Pobieranie listy uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}, {@code SubunitManage}.
     *
     * @param subunitPermissionsQueryRequest Treść żądania — patrz opis pól klasy {@link SubunitPermissionsQueryRequest}.
     * @param pageOffset                     Numer strony wyników.
     * @param pageSize                       Rozmiar strony wyników.
     * @param accessToken                    Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QuerySubunitPermissionsResponse
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
     * <p>
     * Metoda pozwala na odczytanie własnych uprawnień uwierzytelnionego klienta API w bieżącym kontekście logowania.
     * <p>
     * W odpowiedzi przekazywane są następujące uprawnienia: - nadane w sposób bezpośredni w bieżącym kontekście - nadane przez podmiot nadrzędny - nadane w sposób pośredni, jeżeli podmiot kontekstu logowania jest w uprawnieniu pośrednikiem lub podmiotem docelowym - nadane podmiotowi do obsługi faktur przez inny podmiot, jeśli podmiot uwierzytelniony ma w bieżącym kontekście uprawnienia właścicielskie
     * <p>
     * Uprawnienia zwracane przez operację obejmują: - <b>CredentialsManage</b> – zarządzanie uprawnieniami - <b>CredentialsRead</b> – przeglądanie uprawnień - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur - <b>Introspection</b> – przeglądanie historii sesji - <b>SubunitManage</b> – zarządzanie podmiotami podrzędnymi - <b>EnforcementOperations</b> – wykonywanie operacji egzekucyjnych - <b>VatEuManage</b> – zarządzanie uprawnieniami w ramach podmiotu unijnego - <b>CollectiveIdentifierManage</b> – zarządzanie identyfikatorami zbiorczymi
     * <p>
     * Odpowiedź może być filtrowana na podstawie następujących parametrów: - <b>contextIdentifier</b> – identyfikator podmiotu, który nadał uprawnienie do obsługi faktur - <b>targetIdentifier</b> – identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio - <b>permissionTypes</b> – lista rodzajów wyszukiwanych uprawnień - <b>permissionState</b> – status uprawnienia
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-w%C5%82asnych-uprawnie%C5%84">Pobieranie listy uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * @param request     Treść żądania — patrz opis pól klasy {@link QueryPersonalGrantRequest}.
     * @param pageOffset  - Index strony wyników (domyślnie 0)
     * @param pageSize    - Ilość elementów na stronie (domyślnie 10)
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryPersonalGrantResponse
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
     * <p>
     * Metoda pozwala na <b>odczytanie listy ról podmiotu bieżącego kontekstu logowania</b>.
     * <p>
     * #### Role podmiotów zwracane przez operację: - <b>CourtBailiff</b> – komornik sądowy - <b>EnforcementAuthority</b> – organ egzekucyjny - <b>LocalGovernmentUnit</b> – nadrzędna JST - <b>LocalGovernmentSubUnit</b> – podrzędne JST - <b>VatGroupUnit</b> – grupa VAT - <b>VatGroupSubUnit</b> – członek grupy VAT
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-r%C3%B3l-podmiotu">Pobieranie listy ról</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}.
     *
     * @param pageOffset  Numer strony wyników.
     * @param pageSize    Rozmiar strony wyników.
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryEntityRolesResponse
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
     * Wyszukiwanie uprawnień do obsługi faktur w bieżącym kontekście.
     * <p>
     * Metoda pozwala na odczytanie otrzymanych uprawnień do obsługi faktur w bieżącym kontekście logowania.
     * <p>
     * W odpowiedzi przekazywane są następujące uprawnienia: - nadane podmiotowi do obsługi faktur przez inny podmiot
     * <p>
     * Uprawnienia zwracane przez operację obejmują: - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur
     * <p>
     * Odpowiedź może być filtrowana na podstawie następujących parametrów: - <b>contextIdentifier</b> – identyfikator podmiotu, który nadał uprawnienie do obsługi faktur
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-uprawnie%C5%84-do-obs%C5%82ugi-faktur-w-bie%C5%BC%C4%85cym-kontek%C5%9Bcie">Pobieranie listy uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}.
     *
     * @param request     Treść żądania — patrz opis pól klasy {@link EntityPermissionsQueryRequest}.
     * @param pageOffset  Numer strony wyników.
     * @param pageSize    Rozmiar strony wyników.
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryEntityPermissionsResponse
     */
    @Override
    public QueryEntityPermissionsResponse searchEntityInvoiceContext(EntityPermissionsQueryRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE_OFFSET, String.valueOf(pageOffset));
        String uri = buildUrlWithParams(PERMISSION_SEARCH_ENTITIES_GRANTS.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(uri, request, headers);

        return getResponse(response, OK, PERMISSION_SEARCH_ENTITIES_GRANTS, QueryEntityPermissionsResponse.class);
    }

    /**
     * Pobranie listy uprawnień do obsługi faktur nadanych podmiotom
     * <p>
     * Metoda pozwala na odczytanie listy podmiotów podrzędnych, jeżeli podmiot bieżącego kontekstu ma rolę podmiotu nadrzędnego: - <b>nadrzędna JST</b> – odczytywane są podrzędne JST, - <b>grupa VAT</b> – odczytywane są podmioty będące członkami grupy VAT.
     * <p>
     * Role podmiotów zwracane przez operację obejmują: - <b>LocalGovernmentSubUnit</b> – podrzędne JST, - <b>VatGroupSubUnit</b> – członek grupy VAT.
     * <p>
     * Odpowiedź może być filtrowana według parametru: - <b>subordinateEntityIdentifier</b> – identyfikator podmiotu podrzędnego.
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-podmiot%C3%B3w-podrz%C4%99dnych">Pobieranie listy podmiotów podrzędnych</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}, {@code SubunitManage}.
     *
     * @param subordinateEntityRolesQueryRequest Treść żądania — patrz opis pól klasy {@link SubordinateEntityRolesQueryRequest}.
     * @param pageOffset                         Numer strony wyników.
     * @param pageSize                           Rozmiar strony wyników.
     * @param accessToken                        Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SubordinateEntityRolesQueryResponse
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
     * <p>
     * Metoda pozwala na odczytanie uprawnień podmiotowych: - otrzymanych przez podmiot bieżącego kontekstu - nadanych przez podmiot bieżącego kontekstu
     * <p>
     * Wybór listy nadanych lub otrzymanych uprawnień odbywa się przy użyciu parametru <b>queryType</b>.
     * <p>
     * Uprawnienia zwracane przez operację obejmują: - <b>SelfInvoicing</b> – wystawianie faktur w trybie samofakturowania - <b>TaxRepresentative</b> – wykonywanie operacji przedstawiciela podatkowego - <b>RRInvoicing</b> – wystawianie faktur VAT RR - <b>PefInvoicing</b> – wystawianie faktur PEF
     * <p>
     * Odpowiedź może być filtrowana na podstawie następujących parametrów: - <b>authorizingIdentifier</b> – identyfikator podmiotu uprawniającego (stosowane przy queryType = Received) - <b>authorizedIdentifier</b> – identyfikator podmiotu uprawnionego (stosowane przy queryType = Granted) - <b>permissionTypes</b> – lista rodzajów wyszukiwanych uprawnień
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-uprawnie%C5%84-podmiotowych-do-obs%C5%82ugi-faktur">Pobieranie listy uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}, {@code PefInvoiceWrite}.
     *
     * @param entityAuthorizationPermissionsQueryRequest Treść żądania — patrz opis pól klasy {@link EntityAuthorizationPermissionsQueryRequest}.
     * @param pageOffset                                 Numer strony wyników.
     * @param pageSize                                   Rozmiar strony wyników.
     * @param accessToken                                Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryEntityAuthorizationPermissionsResponse
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
     * <p>
     * Metoda pozwala na odczytanie uprawnień administratorów lub reprezentantów podmiotów unijnych: - Jeżeli kontekstem logowania jest NIP, możliwe jest odczytanie uprawnień administratorów podmiotów unijnych powiązanych z podmiotem bieżącego kontekstu, czyli takich, dla których pierwszy człon kontekstu złożonego jest równy NIP-owi kontekstu logowania. - Jeżeli kontekst logowania jest złożony (NIP-VAT UE), możliwe jest pobranie wszystkich uprawnień administratorów i reprezentantów podmiotu w bieżącym kontekście złożonym.
     * <p>
     * Uprawnienia zwracane przez operację obejmują: - <b>VatUeManage</b> – zarządzanie uprawnieniami w ramach podmiotu unijnego - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur - <b>Introspection</b> – przeglądanie historii sesji
     * <p>
     * Odpowiedź może być filtrowana na podstawie następujących parametrów: - <b>vatUeIdentifier</b> – identyfikator podmiotu unijnego - <b>authorizedFingerprintIdentifier</b> – odcisk palca certyfikatu uprawnionej osoby lub podmiotu - <b>permissionTypes</b> – lista rodzajów wyszukiwanych uprawnień
     * <p>
     * #### Stronicowanie wyników Zapytanie zwraca <b>jedną stronę wyników</b> o numerze i rozmiarze podanym w ścieżce. - Przy pierwszym wywołaniu należy ustawić parametr {@code pageOffset = 0}. - Jeżeli dostępna jest kolejna strona wyników, w odpowiedzi pojawi się flaga <b>{@code hasMore}</b>. - W takim przypadku można wywołać zapytanie ponownie z kolejnym numerem strony.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#pobranie-listy-uprawnie%C5%84-administrator%C3%B3w-lub-reprezentant%C3%B3w-podmiot%C3%B3w-unijnych-uprawnionych-do-samofakturowania">Pobieranie listy uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - startDate (Desc) - id (Asc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}, {@code VatUeManage}.
     *
     * @param euEntityPermissionsQueryRequest Treść żądania — patrz opis pól klasy {@link EuEntityPermissionsQueryRequest}.
     * @param pageOffset                      Numer strony wyników.
     * @param pageSize                        Rozmiar strony wyników.
     * @param accessToken                     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryEuEntityPermissionsResponse
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
     * <p>
     * Metoda pozwala na nadanie wskazanemu w żądaniu podmiotowi lub osobie fizycznej uprawnień administratora w kontekście złożonym z identyfikatora NIP podmiotu kontekstu bieżącego oraz numeru VAT UE podmiotu unijnego wskazanego w żądaniu. Wraz z utworzeniem administratora podmiotu unijnego tworzony jest kontekst złożony składający się z numeru NIP podmiotu kontekstu logowania oraz wskazanego numeru identyfikacyjnego VAT UE podmiotu unijnego. W żądaniu podaje się również nazwę i adres podmiotu unijnego.
     * <p>
     * Jedynym sposobem identyfikacji uprawnianego jest odcisk palca certyfikatu kwalifikowanego: - certyfikat podpisu elektronicznego dla osób fizycznych - certyfikat pieczęci elektronicznej dla podmiotów
     * <p>
     * Uprawnienia administratora podmiotu unijnego obejmują: - <b>VatEuManage</b> – zarządzanie uprawnieniami w ramach podmiotu unijnego - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur - <b>Introspection</b> – przeglądanie historii sesji
     * <p>
     * Metoda automatycznie nadaje wszystkie powyższe uprawnienia, bez konieczności ich wskazywania w żądaniu.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadanie-uprawnie%C5%84-administratora-podmiotu-unijnego">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code CredentialsManage}.
     *
     * @param euEntityPermissionsGrantRequest Treść żądania — patrz opis pól klasy {@link EuEntityPermissionsGrantRequest}.
     * @param accessToken                     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * <p>
     * Metoda pozwala na nadanie wskazanemu w żądaniu podmiotowi lub osobie fizycznej uprawnień do wystawiania i/lub przeglądania faktur w kontekście złożonym kontekstu bieżącego.
     * <p>
     * Jedynym sposobem identyfikacji uprawnianego jest odcisk palca certyfikatu kwalifikowanego: - certyfikat podpisu elektronicznego dla osób fizycznych - certyfikat pieczęci elektronicznej dla podmiotów
     * <p>
     * W żądaniu określane są nadawane uprawnienia ze zbioru: - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur
     * <p>
     * Metoda pozwala na wybór dowolnej kombinacji powyższych uprawnień.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadanie-uprawnie%C5%84-reprezentanta-podmiotu-unijnego">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code VatUeManage}.
     *
     * @param grantEUEntityRepresentativePermissionsRequest Treść żądania — patrz opis pól klasy {@link GrantEUEntityRepresentativePermissionsRequest}.
     * @param accessToken                                   Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * Pobranie faktury po numerze KSeF
     * <p>
     * Zwraca fakturę o podanym numerze KSeF.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code InvoiceRead}.
     * <p>
     * Endpoint: GET /invoices/ksef/{ksefReferenceNumber}
     *
     * @param ksefReferenceNumber Numer KSeF dokumentu (required)
     * @param accessToken         Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return Tablica bajtów zawierająca pobraną część. Faktura w formie XML.
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
     * <p>
     * Zwraca metadane faktur spełniających filtry.
     * <p>
     * Limit techniczny: ≤ 10 000 rekordów na zestaw filtrów, po jego osiągnięciu &lt;b&gt;isTruncated = true&lt;/b&gt; i należy ponownie ustawić &lt;b&gt;dateRange&lt;/b&gt;, używając ostatniej daty z wyników (tj. ustawić from/to - w zależności od kierunku sortowania, od daty ostatniego zwróconego rekordu) oraz wyzerować &lt;b&gt;pageOffset&lt;/b&gt;.
     * <p>
     * {@code Do scenariusza przyrostowego należy używać daty PermanentStorage oraz kolejność sortowania Asc}.
     * <p>
     * &lt;b&gt;Scenariusz pobierania przyrostowego (skrót):&lt;/b&gt; * Gdy &lt;b&gt;hasMore = false&lt;/b&gt;, należy zakończyć, * Gdy &lt;b&gt;hasMore = true&lt;/b&gt; i &lt;b&gt;isTruncated = false&lt;/b&gt;, należy zwiększyć &lt;b&gt;pageOffset&lt;/b&gt;, * Gdy &lt;b&gt;hasMore = true&lt;/b&gt; i &lt;b&gt;isTruncated = true&lt;/b&gt;, należy zawęzić &lt;b&gt;dateRange&lt;/b&gt; (ustawić from od daty ostatniego rekordu), wyzerować &lt;b&gt;pageOffset&lt;/b&gt; i kontynuować
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - permanentStorageDate | invoicingDate | issueDate (Asc | Desc) - pole wybierane na podstawie filtrów
     *
     * <b>Wymagane uprawnienie</b>: {@code InvoiceRead}.
     *
     * @param pageOffset          - Index strony wyników (domyślnie 0)
     * @param pageSize            - Ilość elementów na stronie (domyślnie 10)
     * @param sortOrder           - Kolejność sortowania wyników.
     * @param invoiceQueryFilters InvoicesQueryRequest - zestaw filtrów
     * @param accessToken         Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryInvoiceMetadataResponse
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
     * Eksport paczki faktur
     * <p>
     * Rozpoczyna asynchroniczny proces wyszukiwania faktur w systemie KSeF na podstawie przekazanych filtrów oraz przygotowania ich w formie zaszyfrowanej paczki. Wymagane jest przekazanie informacji o szyfrowaniu w polu &lt;b&gt;Encryption&lt;/b&gt;, które służą do zabezpieczenia przygotowanej paczki z fakturami. Maksymalnie można uruchomić 10 równoczesnych eksportów w zalogowanym kontekście.
     * <p>
     * System pobiera faktury rosnąco według daty określonej w filtrze (Invoicing, Issue, PermanentStorage) i dodaje faktury(nazwa pliku: &lt;b&gt;{ksefNumber}.xml&lt;/b&gt;) do paczki aż do osiągnięcia jednego z poniższych limitów: * Limit liczby faktur: 10 000 sztuk * Limit rozmiaru danych(skompresowanych): 1GB
     * <p>
     * Paczka eksportu zawiera dodatkowy plik z metadanymi faktur w formacie JSON ({@code _metadata.json}). Zawartość pliku to obiekt z tablicą &lt;b&gt;invoices&lt;/b&gt;, gdzie każdy element jest obiektem typu &lt;b&gt;InvoiceMetadata&lt;/b&gt; (taki jak zwracany przez endpoint {@code POST /invoices/query/metadata}).
     * <p>
     * &lt;b&gt;Plik z metadanymi(_metadata.json) nie jest wliczany do limitów algorytmu budowania paczki&lt;/b&gt;.
     * <p>
     * {@code Do realizacji pobierania przyrostowego należy stosować filtrowanie po dacie PermanentStorage}.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * permanentStorageDate | invoicingDate | issueDate (Asc) - pole wybierane na podstawie filtrów
     *
     * <b>Wymagane uprawnienie</b>: {@code InvoiceRead}.
     * <p>
     * Endpoint: POST /invoices/exports
     *
     * @param invoiceExportRequest Zestaw filtrów dla wyszukiwania faktur.
     * @param accessToken          Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return InitAsyncInvoicesQueryResponse
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
     * Pobranie statusu eksportu paczki faktur
     * <p>
     * Paczka faktur jest dzielona na części o maksymalnym rozmiarze 50 MB. Każda część jest zaszyfrowana algorytmem AES-256-CBC z dopełnieniem PKCS#7, przy użyciu klucza symetrycznego przekazanego podczas inicjowania eksportu.
     * <p>
     * W przypadku ucięcia wyniku eksportu z powodu przekroczenia limitów, zwracana jest flaga &lt;b&gt;IsTruncated = true&lt;/b&gt; oraz odpowiednia data, którą należy wykorzystać do wykonania kolejnego eksportu, aż do momentu, gdy flaga &lt;b&gt;IsTruncated = false&lt;/b&gt;.
     * <p>
     * Status eksportu paczki faktur jest dostępny przez 7 dni.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * permanentStorageDate | invoicingDate | issueDate (Asc) - pole wybierane na podstawie filtrów
     *
     * <b>Wymagane uprawnienie</b>: {@code InvoiceRead}.
     * <p>
     * Endpoint: GET /invoices/exports/{referenceNumber}
     *
     * @param referenceNumber Unikalny identyfikator operacji zwrócony podczas inicjalizacji zapytania. (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return InvoiceExportStatus
     */
    @Override
    public InvoiceExportStatus checkStatusAsyncQueryInvoice(String referenceNumber, String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'operationReferenceNumber' when calling checkStatusAsyncQueryInvoice");
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
     * <p>
     * Metoda pozwala na nadanie podmiotowi wskazanemu w żądaniu uprawnień do obsługi faktur podmiotu kontekstu. W żądaniu określane są nadawane uprawnienia ze zbioru: - <b>InvoiceWrite</b> – wystawianie faktur - <b>InvoiceRead</b> – przeglądanie faktur
     * <p>
     * Metoda pozwala na wybór dowolnej kombinacji powyższych uprawnień. Dla każdego uprawnienia może być ustawiona flaga <b>canDelegate</b>, mówiąca o możliwości jego dalszego przekazywania poprzez nadawanie w sposób pośredni.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadanie-podmiotom-uprawnie%C5%84-do-obs%C5%82ugi-faktur">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code CredentialsManage}.
     *
     * @param grantEntityPermissionsRequest Treść żądania — patrz opis pól klasy {@link GrantEntityPermissionsRequest}.
     * @param accessToken                   Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
     */
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
     * <p>
     * Sprawdza bieżący status sesji o podanym numerze referencyjnym.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}
     *
     * @param referenceNumber Numer referencyjny sesji. (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SessionStatusResponse
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
     * <p>
     * Zwraca fakturę przesłaną w sesji wraz ze statusem.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}/invoices/{invoiceReferenceNumber}
     *
     * @param referenceNumber        Numer referencyjny sesji. (required)
     * @param invoiceReferenceNumber Numer referencyjny faktury. (required)
     * @param accessToken            Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SessionInvoiceStatusResponse
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
     * <p>
     * Zwraca UPO faktury przesłanego w sesji na podstawie jego numeru KSeF.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}/invoices/{invoiceReferenceNumber}/upo
     *
     * @param referenceNumber        Numer referencyjny sesji. (required)
     * @param invoiceReferenceNumber Numer referencyjny faktury. (required)
     * @param accessToken            Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return Tablica bajtów zawierająca pobraną część. UPO w formie XML.
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
     * <p>
     * Zwraca UPO faktury przesłanego w sesji na podstawie jego numeru KSeF.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}/invoices/ksef/{ksefNumber}/upo
     *
     * @param referenceNumber Numer referencyjny sesji. (required)
     * @param ksefNumber      Numer KSeF faktury. (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return Tablica bajtów zawierająca pobraną część. UPO w formie XML.
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
     * <p>
     * Zwraca XML zawierający zbiorcze UPO dla sesji.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}/upo/{upoReferenceNumber}
     *
     * @param referenceNumber    Numer referencyjny sesji. (required)
     * @param upoReferenceNumber Numer referencyjny UPO. (required)
     * @param accessToken        Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return Tablica bajtów zawierająca pobraną część. Zbiorcze UPO w formie XML.
     */
    @Override
    public byte[] getSessionUpo(String referenceNumber,
                                String upoReferenceNumber,
                                String accessToken) throws ApiException {
        if (referenceNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'referenceNumber' when calling getSessionUpo");
        }
        if (upoReferenceNumber == null) {
            throw new KsefApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing the required parameter 'upoReferenceNumber' when calling getSessionUpo");
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
     * <p>
     * Zwraca listę faktur przesłanych w sesji wraz z ich statusami, oraz informacje na temat ilości poprawnie i niepoprawnie przetworzonych faktur.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}/invoices
     *
     * @param referenceNumber   Numer referencyjny sesji. (required)
     * @param continuationToken Token służący do pobrania kolejnej strony wyników. (optional)
     * @param pageSize          Rozmiar strony wyników.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SessionInvoicesResponse
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
     * <p>
     * Zwraca listę niepoprawnie przetworzonych faktur przesłanych w sesji wraz z ich statusami.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceWrite}, {@code Introspection}, {@code PefInvoiceWrite}, {@code EnforcementOperations}.
     * <p>
     * Endpoint: GET /sessions/{referenceNumber}/invoices/failed
     *
     * @param referenceNumber   Numer referencyjny sesji. (required)
     * @param continuationToken Token służący do pobrania kolejnej strony wyników. (optional)
     * @param pageSize          Rozmiar strony wyników.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SessionInvoicesResponse
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
     * <p>
     * Zwraca listę sesji spełniających podane kryteria wyszukiwania.
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code Introspection}/{@code EnforcementOperations} – pozwala pobrać wszystkie sesje w bieżącym kontekście uwierzytelnienia {@code (ContextIdentifier)}. {@code InvoiceWrite} – pozwala pobrać wyłącznie sesje utworzone przez podmiot uwierzytelniający, czyli podmiot inicjujący uwierzytelnienie.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * dateCreated (Desc)
     * <p>
     * Endpoint: GET /sessions
     *
     * @param request           enkapsulowane wszystkie pola requesta
     * @param pageSize          page size
     * @param continuationToken continuation token
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return SessionsQueryResponse
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
     * <p>
     * Zwraca listę aktywnych sesji uwierzytelnienia.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * startDate (Desc)
     * <p>
     * Endpoint: GET /auth/sessions
     *
     * @param pageSize          Rozmiar strony wyników.
     * @param continuationToken Token służący do pobrania kolejnej strony wyników.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return AuthenticationListResponse
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
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * <p>
     * Unieważnia sesję o podanym numerze referencyjnym.
     * <p>
     * Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów. <b>Aktywne access tokeny działają do czasu minięcia ich termin ważności.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: DELETE /auth/sessions/{referenceNumber}
     *
     * @param referenceNumber Numer referencyjny sesji uwierzytelnienia. (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * Pobranie listy dostawców usług Peppol
     * <p>
     * Zwraca listę dostawców usług Peppol zarejestrowanych w systemie.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * dateCreated (Desc) id (Asc)
     * <p>
     * Endpoint: GET /peppol/query
     *
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return PeppolProvidersListResponse
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
     * getContextSessionLimit.
     * <p>
     * Zwraca wartości aktualnie obowiązujących limitów dla bieżącego kontekstu.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return GetContextLimitResponse
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
     * <p>
     * Zwraca wartości aktualnie obowiązujących limitów dla bieżącego podmiotu.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return GetSubjectLimitResponse
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
     * Zmiana limitów sesji dla bieżącego kontekstu
     * <p>
     * Zmienia wartości aktualnie obowiązujących limitów sesji dla bieżącego kontekstu. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/limits/context/session
     *
     * @param changeContextLimitRequest Treść żądania — patrz opis pól klasy {@link ChangeContextLimitRequest}.
     * @param accessToken               Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * <p>
     * Zmienia wartości aktualnie obowiązujących limitów żądań przesyłanych do API dla bieżącego kontekstu na wartości takie jakie będą na środowisku produkcyjnym. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * Zablokowanie kontekstu
     * <p>
     * Blokuje możliwość uwierzytelniania dla wskazanego kontekstu. Uwierzytelnianie zakończy się błędem 480.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/context/block
     *
     * @param contextIdentifier Treść żądania — patrz opis pól klasy {@link TestDataContextIdentifier}.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void blockContext(TestDataContextIdentifier contextIdentifier, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_BLOCK.getUrl();
        HttpResponse<byte[]> response = post(url, contextIdentifier, headers);

        validResponse(response, OK, LIMIT_CONTEXT_BLOCK);
    }

    /**
     * Odblokowanie kontekstu
     * <p>
     * Odblokowuje możliwość uwierzytelniania dla wskazanego kontekstu.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/context/unblock
     *
     * @param contextIdentifier Treść żądania — patrz opis pól klasy {@link TestDataContextIdentifier}.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void unblockContext(TestDataContextIdentifier contextIdentifier, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_UNBLOCK.getUrl();
        HttpResponse<byte[]> response = post(url, contextIdentifier, headers);

        validResponse(response, OK, LIMIT_CONTEXT_UNBLOCK);
    }

    /**
     * Zmiana limitów API dla bieżącego kontekstu
     * <p>
     * Zmienia wartości aktualnie obowiązujących limitów żądań przesyłanych do API dla bieżącego kontekstu. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/rate-limits
     *
     * @param setRateLimitsRequest Treść żądania — patrz opis pól klasy {@link SetRateLimitsRequest}.
     * @param accessToken          Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void setRateLimits(SetRateLimitsRequest setRateLimitsRequest, String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_SET.getUrl();
        HttpResponse<byte[]> response = post(url, setRateLimitsRequest, headers);

        validResponse(response, OK, LIMIT_CONTEXT_SET);
    }

    /**
     * Przywrócenie domyślnych wartości limitów API dla bieżącego kontekstu
     * <p>
     * Przywraca wartości aktualnie obowiązujących limitów żądań przesyłanych do API dla bieżącego kontekstu do wartości domyślnych. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: DELETE /testdata/rate-limits
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void restoreRateLimits(String accessToken) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        String url = LIMIT_CONTEXT_RESTORE.getUrl();
        HttpResponse<byte[]> response = delete(url, headers);

        validResponse(response, OK, LIMIT_CONTEXT_RESTORE);
    }

    /**
     * Zmiana limitów certyfikatów dla bieżącego podmiotu
     * <p>
     * Zmienia wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/limits/subject/certificate
     *
     * @param changeSubjectCertificateLimitRequest Treść żądania — patrz opis pól klasy {@link ChangeSubjectCertificateLimitRequest}.
     * @param accessToken                          Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * Przywrócenie domyślnych wartości limitów sesji dla bieżącego kontekstu
     * <p>
     * Przywraca wartości aktualnie obowiązujących limitów sesji dla bieżącego kontekstu do wartości domyślnych. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: DELETE /testdata/limits/context/session
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * Aktualizacja certyfikatu
     * <p>
     * Aktualizuje dane wskazanego certyfikatu KSeF uwierzytelnionego podmiotu. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: PUT /testdata/certificates/{certificateSerialNumber}
     *
     * @param serialNumber Numer seryjny certyfikatu (w formacie szesnastkowym).
     * @param request      Treść żądania — patrz opis pól klasy {@link TestDataUpdateCertificateRequest}.
     * @param accessToken  Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     */
    @Override
    public void updateCertificate(String serialNumber, TestDataUpdateCertificateRequest request, String accessToken) throws ApiException {
        if (serialNumber == null || serialNumber.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        String uri = buildUrlWithParams(UPDATE_CERTIFICATE_DATA.getUrl(), new HashMap<>())
                .replace(PATH_CERTIFICATE_SERIAL_NUMBER, serialNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);

        HttpResponse<byte[]> response = put(uri, request, headers);
        validResponse(response, NO_CONTENT, UPDATE_CERTIFICATE_DATA);
    }

    /**
     * resetSubjectCertificateLimit.
     * <p>
     * Przywraca wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu do wartości domyślnych. <b>Tylko na środowiskach testowych.</b>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * Utworzenie podmiotu
     * <p>
     * Tworzenie nowego podmiotu testowego. W przypadku grupy VAT i JST istnieje możliwość stworzenia jednostek podrzędnych. W wyniku takiego działania w systemie powstanie powiązanie między tymi podmiotami.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/subject
     *
     * @param testDataSubjectCreateRequest Treść żądania — patrz opis pól klasy {@link TestDataSubjectCreateRequest}.
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
     * Usunięcie podmiotu
     * <p>
     * Usuwanie podmiotu testowego. W przypadku grupy VAT i JST usunięte zostaną również jednostki podrzędne.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/subject/remove
     *
     * @param testDataSubjectRemoveRequest Treść żądania — patrz opis pól klasy {@link TestDataSubjectRemoveRequest}.
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
     * Utworzenie osoby fizycznej
     * <p>
     * Tworzenie nowej osoby fizycznej, której system nadaje uprawnienia właścicielskie. Można również określić, czy osoba ta jest komornikiem – wówczas otrzyma odpowiednie uprawnienie egzekucyjne.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/person
     *
     * @param testDataPersonCreateRequest Treść żądania — patrz opis pól klasy {@link TestDataPersonCreateRequest}.
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
     * Usunięcie osoby fizycznej
     * <p>
     * Usuwanie testowej osoby fizycznej. System automatycznie odbierze jej wszystkie uprawnienia.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/person/remove
     *
     * @param testDataPersonRemoveRequest Treść żądania — patrz opis pól klasy {@link TestDataPersonRemoveRequest}.
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
     * Pobranie aktualnie obowiązujących limitów API
     * <p>
     * Zwraca wartości aktualnie obowiązujących limitów ilości żądań przesyłanych do API.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /rate-limits
     *
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * Nadanie uprawnień testowemu podmiotowi/osobie fizycznej
     * <p>
     * Nadawanie uprawnień testowemu podmiotowi lub osobie fizycznej, a także w ich kontekście.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/permissions
     *
     * @param testDataPermissionRequest Treść żądania — patrz opis pól klasy {@link TestDataPermissionRequest}.
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
     * Odebranie uprawnień testowemu podmiotowi/osobie fizycznej
     * <p>
     * Odbieranie uprawnień nadanych testowemu podmiotowi lub osobie fizycznej, a także w ich kontekście.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/permissions/revoke
     *
     * @param testDataPermissionRemoveRequest Treść żądania — patrz opis pól klasy {@link TestDataPermissionRemoveRequest}.
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
     * Umożliwienie wysyłania faktur z załącznikiem
     * <p>
     * Dodaje możliwość wysyłania faktur z załącznikiem przez wskazany podmiot
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/attachment
     *
     * @param testDataAttachmentRequest Treść żądania — patrz opis pól klasy {@link TestDataAttachmentRequest}.
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
     * Odebranie możliwości wysyłania faktur z załącznikiem
     * <p>
     * Odbiera możliwość wysyłania faktur z załącznikiem przez wskazany podmiot
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: POST /testdata/attachment/revoke
     *
     * @param testDataAttachmentRemoveRequest Treść żądania — patrz opis pól klasy {@link TestDataAttachmentRemoveRequest}.
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
     * <p>
     * Metoda pozwala na nadanie osobie wskazanej w żądaniu uprawnień do pracy w KSeF w kontekście bieżącym.
     * <p>
     * W żądaniu określane są nadawane uprawnienia ze zbioru: - <b>InvoiceWrite</b> – wystawianie faktur, - <b>InvoiceRead</b> – przeglądanie faktur, - <b>CredentialsManage</b> – zarządzanie uprawnieniami, - <b>CredentialsRead</b> – przeglądanie uprawnień, - <b>Introspection</b> – przeglądanie historii sesji i generowanie UPO, - <b>SubunitManage</b> – zarządzanie jednostkami podrzędnymi, - <b>EnforcementOperations</b> – wykonywanie operacji egzekucyjnych. - <b>CollectiveIdentifierManage</b> – zarządzanie identyfikatorami zbiorczymi
     * <p>
     * Metoda pozwala na wybór dowolnej kombinacji powyższych uprawnień. Uprawnienie <b>EnforcementOperations</b> może być nadane wyłącznie wtedy, gdy podmiot kontekstu ma rolę <b>EnforcementAuthority</b> (organ egzekucyjny) lub <b>CourtBailiff</b> (komornik sądowy).
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadawanie-uprawnie%C5%84-osobom-fizycznym-do-pracy-w-ksef">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code CredentialsManage}.
     *
     * @param grantPersonPermissionsRequest Treść żądania — patrz opis pól klasy {@link GrantPersonPermissionsRequest}.
     * @param accessToken                   Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * <p>
     * Metoda pozwala na nadanie wskazanemu w żądaniu podmiotowi lub osobie fizycznej uprawnień administratora w kontekście: - wskazanego NIP podmiotu podrzędnego – wyłącznie jeżeli podmiot bieżącego kontekstu logowania ma rolę podmiotu nadrzędnego: - <b>LocalGovernmentUnit</b> - <b>VatGroupUnit</b> - wskazanego lub utworzonego identyfikatora wewnętrznego
     * <p>
     * Wraz z utworzeniem administratora jednostki podrzędnej tworzony jest identyfikator wewnętrzny składający się z numeru NIP podmiotu kontekstu logowania oraz 5 cyfr unikalnie identyfikujących jednostkę wewnętrzną. Ostatnia cyfra musi być poprawną sumą kontrolną, która jest obliczana według poniższego algorytmu.
     * <p>
     * Algorytm używa naprzemiennych wag (1×, 3×, 1×, 3×, ...), sumuje wyniki i zwraca resztę z dzielenia przez 10.
     * <p>
     * Przykład: - Wejście: "6824515772-1234" (bez cyfry kontrolnej) - Pozycja 0 (1. cyfra): 6 × 1 = 6 - Pozycja 1 (2. cyfra): 8 × 3 = 24 - Pozycja 2 (3. cyfra): 2 × 1 = 2 - Pozycja 3 (4. cyfra): 4 × 3 = 12 - Pozycja 4 (5. cyfra): 5 × 1 = 5 - Pozycja 5 (6. cyfra): 1 × 3 = 3 - Pozycja 6 (7. cyfra): 5 × 1 = 5 - Pozycja 7 (8. cyfra): 7 × 3 = 21 - Pozycja 8 (9. cyfra): 7 × 1 = 7 - Pozycja 9 (10. cyfra): 2 × 3 = 6 - Pozycja 10 (11. cyfra): 1 × 1 = 1 - Pozycja 11 (12. cyfra): 2 × 3 = 6 - Pozycja 12 (13. cyfra): 3 × 1 = 3 - Pozycja 13 (14. cyfra): 4 × 3 = 12 - Suma: 6 + 24 + 2 + 12 + 5 + 3 + 5 + 21 + 7 + 6 + 1 + 6 + 3 + 12 = 113 - Cyfra kontrolna (15. cyfra): 113 % 10 = 3
     * <p>
     * W żądaniu podaje się również nazwę tej jednostki.
     * <p>
     * Uprawnienia administratora jednostki podrzędnej obejmują: - <b>CredentialsManage</b> – zarządzanie uprawnieniami
     * <p>
     * Metoda automatycznie nadaje powyższe uprawnienie, bez konieczności podawania go w żądaniu.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#nadanie-uprawnie%C5%84-administratora-podmiotu-podrz%C4%99dnego">Nadawanie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code SubunitManage}.
     *
     * @param subunitPermissionsGrantRequest Treść żądania — patrz opis pól klasy {@link SubunitPermissionsGrantRequest}.
     * @param accessToken                    Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * <p>
     * Metoda pozwala na odebranie uprawnienia o wskazanym identyfikatorze. Wymagane jest wcześniejsze odczytanie uprawnień w celu uzyskania identyfikatora uprawnienia, które ma zostać odebrane.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#odebranie-uprawnie%C5%84">Odbieranie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code VatUeManage}, {@code SubunitManage}.
     * <p>
     * Endpoint: DELETE /permissions/common/grants/{permissionId}
     *
     * @param permissionId Id uprawnienia. (required)
     * @param accessToken  Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * Odebranie uprawnień podmiotowych
     * <p>
     * Metoda pozwala na odebranie uprawnienia podmiotowego o wskazanym identyfikatorze. Wymagane jest wcześniejsze odczytanie uprawnień w celu uzyskania identyfikatora uprawnienia, które ma zostać odebrane.
     * <p>
     * Więcej informacji: <a href="https://github.com/CIRFMF/ksef-api/blob/main/uprawnienia.md#odebranie-uprawnie%C5%84-podmiotowych">Odbieranie uprawnień</a>
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane uprawnienie</b>: {@code CredentialsManage}.
     * <p>
     * Endpoint: DELETE /permissions/authorizations/grants/{permissionId}
     *
     * @param permissionId Id uprawnienia. (required)
     * @param accessToken  Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return OperationResponse
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
     * Sprawdzenie statusu zgody na wystawianie faktur z załącznikiem
     * <p>
     * Sprawdzenie czy obecny kontekst posiada zgodę na wystawianie faktur z załącznikiem.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code CredentialsManage}, {@code CredentialsRead}.
     * <p>
     * Endpoint: GET /permissions/attachments/status
     *
     * @param accessToken - token sesyjny
     * @return PermissionAttachmentStatusResponse
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
     * <p>
     * Zwraca token, który może być użyty do uwierzytelniania się w KSeF.
     * <p>
     * Token może być generowany tylko w kontekście NIP lub identyfikatora wewnętrznego. Jest zwracany tylko raz. Zaczyna być aktywny w momencie gdy jego status zmieni się na {@code Active}.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param ksefTokenRequest Treść żądania — patrz opis pól klasy {@link KsefTokenRequest}.
     * @param accessToken      Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return GenerateTokenResponse
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
     * @param statuses             Status tokenów do zwrócenia. W przypadku braku parametru zwracane są wszystkie tokeny. Parametr można przekazać wielokrotnie.
     * @param description          Umożliwia filtrowanie tokenów po opisie. Wartość parametru jest wyszukiwana w opisie tokena (operacja nie rozróżnia wielkości liter). Należy podać co najmniej 3 znaki.
     * @param authorIdentifier     Umożliwia filtrowanie tokenów po ich twórcy. Wartość parametru jest wyszukiwana w identyfikatorze (operacja nie rozróżnia wielkości liter). Należy podać co najmniej 3 znaki.
     * @param authorIdentifierType Umożliwia filtrowanie tokenów po ich twórcy. Wartość parametru określa typ identyfikatora w którym będzie wyszukiwany ciąg znaków przekazany w parametrze `authorIdentifier`.
     *                             | Wartość | Opis |
     *                             | --- | --- |
     *                             | Nip | NIP. |
     *                             | Pesel | PESEL. |
     *                             | Fingerprint | Odcisk palca certyfikatu. |
     * @param continuationToken    Token służący do pobrania kolejnej strony wyników. (optional)
     * @param pageSize             Rozmiar strony wyników. (optional, default to 10)
     * @param accessToken          Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return QueryTokensResponse
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
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * @param referenceNumber Numer referencyjny tokena. (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return AuthenticationToken
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
     * <p>
     * Unieważnia token o podanym numerze referencyjnym. Zakres tokenów dostępnych do unieważnienia zależy od sposobu uwierzytelnienia oraz uprawnień podmiotu wywołującego.
     * <p>
     * Jeżeli podmiot posiada uprawnienie <b>CredentialsManage</b>, może unieważnić dowolny aktywny token w danym kontekście niezależnie od metody uwierzytelnienia.
     * <p>
     * Jeżeli podmiot nie posiada powyższego uprawnienia, zakres unieważnienia jest ograniczony: Dla podmiotu uwierzytelnionego tokenem możliwe jest unieważnienie wyłącznie tokena użytego do uwierzytelnienia. Dla pozostałych metod uwierzytelnienia podmiot może unieważnić tokeny, których jest autorem – z uwzględnieniem powiązania NIP–PESEL.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: DELETE /tokens/{referenceNumber}
     *
     * @param referenceNumber Numer referencyjny tokena do unieważeniania. (required)
     * @param accessToken     Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
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
     * <p>
     * Zwraca informacje o kluczach publicznych używanych do szyfrowania danych przesyłanych do systemu KSeF.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     * <p>
     * Endpoint: GET /security/public-key-certificates
     *
     * @return {@code List<PublicKeyCertificate>}
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
            throw new KsefApiException(e);
        }
    }

    private HttpResponse<byte[]> put(String uri, Object body, Map<String, String> headers) throws SystemKSeFSDKException {
        try {
            HttpRequest request = buildRequest(uri, PUT, body, headers);

            return sendHttpRequest(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
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

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(responsePart.getUrl())
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

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(responsePart.getUrl())
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
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(part.getUrl())
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

    /**
     * generateCollectiveIdentifier.
     * <p>
     * Generuje identyfikator zbiorczy dla przekazanej listy numerów KSeF faktur wystawionych przez tego samego sprzedawcę.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceRead}, {@code InvoiceWrite}, {@code CollectiveIdentifierManage}.
     *
     * @param request     Treść żądania — patrz opis pól klasy {@link GenerateCollectiveIdentifierRequest}.
     * @param accessToken Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @return GenerateCollectiveIdentifierResponse
     */
    @Override
    public GenerateCollectiveIdentifierResponse generateCollectiveIdentifier(GenerateCollectiveIdentifierRequest request, String accessToken) throws ApiException {
        if (request.getInvoices() == null || request.getInvoices().size() < 2) {
            throw new IllegalArgumentException("Identyfikator zbiorczy wymaga co najmniej 2 faktur.");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);

        HttpResponse<byte[]> response = post(COLLECTIVE_IDENTIFIERS_GENERATE.getUrl(), request, headers);

        return getResponse(response, CREATED, COLLECTIVE_IDENTIFIERS_GENERATE, GenerateCollectiveIdentifierResponse.class);
    }

    /**
     * queryCollectiveIdentifiers.
     * <p>
     * Zwraca listę identyfikatorów zbiorczych wygenerowanych w kontekście. Dla kontekstu typu NIP zwracane są również identyfikatory zbiorcze, dla których podmiot występuje w roli Podmiotu 1 na fakturze.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - dateCreated (Desc) - collectiveIdentifierNumber (Desc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceRead}, {@code InvoiceWrite}, {@code CollectiveIdentifierManage}.
     *
     * @param request           Treść żądania — patrz opis pól klasy {@link CollectiveIdentifiersQueryRequest}.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @param continuationToken Token służący do pobrania kolejnej strony wyników.
     * @param pageSize          Rozmiar strony wyników.
     * @return CollectiveIdentifiersQueryResponse
     */
    @Override
    public CollectiveIdentifiersQueryResponse queryCollectiveIdentifiers(CollectiveIdentifiersQueryRequest request, String accessToken, String continuationToken, Integer pageSize) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        if (pageSize != null) {
            params.put(PAGE_SIZE, String.valueOf(pageSize));
        }
        String uri = buildUrlWithParams(COLLECTIVE_IDENTIFIERS_QUERY.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);
        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }

        HttpResponse<byte[]> response = post(uri, request, headers);

        return getResponse(response, OK, COLLECTIVE_IDENTIFIERS_QUERY, CollectiveIdentifiersQueryResponse.class);
    }

    /**
     * getCollectiveIdentifiersByKsefNumber.
     * <p>
     * Zwraca listę identyfikatorów zbiorczych związanych z podanym numerem KSeF.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - dateCreated (Desc) - collectiveIdentifierNumber (Desc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceRead}, {@code InvoiceWrite}, {@code CollectiveIdentifierManage}.
     *
     * @param ksefNumber        Numer KSeF faktury.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @param continuationToken Token służący do pobrania kolejnej strony wyników.
     * @param pageSize          Rozmiar strony wyników.
     * @return CollectiveIdentifiersByKsefNumberQueryResponse
     */
    @Override
    public CollectiveIdentifiersByKsefNumberQueryResponse getCollectiveIdentifiersByKsefNumber(String ksefNumber, String accessToken, String continuationToken, Integer pageSize) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        if (pageSize != null) {
            params.put(PAGE_SIZE, String.valueOf(pageSize));
        }
        String uri = buildUrlWithParams(COLLECTIVE_IDENTIFIERS_QUERY_BY.getUrl(), params)
                .replace(PATH_KSEF_NUMBER, ksefNumber);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(ACCEPT, APPLICATION_JSON);

        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }
        HttpResponse<byte[]> response = get(uri, headers);

        return getResponse(response, OK, COLLECTIVE_IDENTIFIERS_QUERY_BY, CollectiveIdentifiersByKsefNumberQueryResponse.class);
    }

    /**
     * getCollectiveIdentifierInvoices.
     * <p>
     * Zwraca listę numerów KSeF faktur wchodzących w skład identyfikatora zbiorczego.
     * <p>
     * Informacje o opisie, kwocie i walucie płatności są zwracane wyłącznie podmiotowi, który utworzył identyfikator zbiorczy, lub podmiotowi występującemu w roli na danej fakturze.
     * <p>
     * Jeżeli opis, kwota i waluta płatności zostały określone podczas tworzenia identyfikatora zbiorczego, a podmiot nie ma dostępu do tych informacji, pola dotyczące opisu, kwoty i waluty płatności nie są zwracane, a pole &lt;b&gt;detailsHidden&lt;/b&gt; przyjmuje wartość &lt;b&gt;true&lt;/b&gt;.
     * <p>
     * Jeżeli opis, kwota i waluta płatności nie zostały określone podczas tworzenia identyfikatora zbiorczego, ich wartości pozostają puste, a pole &lt;b&gt;detailsHidden&lt;/b&gt; przyjmuje wartość &lt;b&gt;false&lt;/b&gt;.
     *
     * <b>Headers:</b>
     * <p>
     * {@code X-Error-Format: problem-details} - ustawienie tego nagłówka powoduje zwracanie błędów w formacie <b>Problem Details</b> ({@code application/problem+json}).
     *
     * <b>Sortowanie:</b>
     * <p>
     * - ksefNumber (Desc)
     *
     * <b>Wymagane jedno z uprawnień</b>: {@code InvoiceRead}, {@code InvoiceWrite}, {@code CollectiveIdentifierManage}.
     *
     * @param request           Treść żądania — patrz opis pól klasy {@link CollectiveIdentifierInvoicesQueryRequest}.
     * @param accessToken       Token dostępowy (JWT) przekazywany w nagłówku Authorization jako Bearer.
     * @param continuationToken Token służący do pobrania kolejnej strony wyników.
     * @param pageSize          Rozmiar strony wyników.
     * @return CollectiveIdentifierInvoicesQueryResponse
     */
    @Override
    public CollectiveIdentifierInvoicesQueryResponse getCollectiveIdentifierInvoices(CollectiveIdentifierInvoicesQueryRequest request, String accessToken, String continuationToken, Integer pageSize) throws ApiException {
        HashMap<String, String> params = new HashMap<>();
        if (pageSize != null) {
            params.put(PAGE_SIZE, String.valueOf(pageSize));
        }
        String uri = buildUrlWithParams(COLLECTIVE_IDENTIFIERS_QUERY_INVOICES.getUrl(), params);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, BEARER + accessToken);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, APPLICATION_JSON);
        if (continuationToken != null) {
            headers.put(CONTINUATION_TOKEN, continuationToken);
        }
        HttpResponse<byte[]> response = post(uri, request, headers);

        return getResponse(response, OK, COLLECTIVE_IDENTIFIERS_QUERY_INVOICES, CollectiveIdentifierInvoicesQueryResponse.class);
    }

    protected HttpResponse<byte[]> sendHttpRequest(HttpRequest request, HttpResponse.BodyHandler<byte[]> bodyHandler) {
        try {
            if (circuitBreakerHandler != null) {
                if (responseHeaderCaptureHandler != null) {
                    return circuitBreakerHandler.send(request, responseInfo -> {
                        responseHeaderCaptureHandler.capture(request, responseInfo.headers());
                        return bodyHandler.apply(responseInfo);
                    });
                } else {
                    return circuitBreakerHandler.send(request, bodyHandler);
                }
            } else {
                if (responseHeaderCaptureHandler != null) {
                    return apiClient.send(request, responseInfo -> {
                        responseHeaderCaptureHandler.capture(request, responseInfo.headers());
                        return bodyHandler.apply(responseInfo);
                    });
                } else {
                    return apiClient.send(request, bodyHandler);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new SystemKSeFSDKException(request.method() + " " + request.uri() + " "
                    + (e.getMessage() != null ? e.getMessage() : ""), e);
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
            throw new KsefApiException(e);
        }
    }

    private void validResponse(HttpResponse<byte[]> response,
                               HttpStatus expectedStatus,
                               Url operation) throws ApiException {
        try {
            if (!isValidResponse(response, expectedStatus)) {
                exceptionHandler.handleException(response, operation);
            }
        } catch (IOException e) {
            throw new KsefApiException(e);
        }
    }


}
