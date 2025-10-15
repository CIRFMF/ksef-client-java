package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.ApiException;
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

import java.util.List;

public interface KSeFClient {

    /**
     * Otwarcie sesji wsadowej
     * Otwiera sesję do wysyłki wsadowej faktur.
     *
     * @param body - OpenBatchSessionRequest - schemat wysyłanych faktur, informacje o paczce faktur oraz informacje o kluczu używanym do szyfrowania.
     * @return OpenBatchSessionResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OpenBatchSessionResponse openBatchSession(OpenBatchSessionRequest body, String accessToken) throws ApiException;

    /**
     * Zamknięcie sesji wsadowej.
     * Zamyka sesję wsadową, rozpoczyna procesowanie paczki faktur i generowanie UPO dla prawidłowych faktur oraz zbiorczego UPO dla sesji.
     *
     * @param referenceNumber - Numer referencyjny sesji wsadowej.
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void closeBatchSession(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Wysłanie części paczki faktur
     *
     * @param openBatchSessionResponse
     * @param parts                    - Kolekcja trzymająca informacje o partach
     * @throws ApiException
     */
    void sendBatchParts(OpenBatchSessionResponse openBatchSessionResponse, List<BatchPartSendingInfo> parts) throws ApiException;

    /**
     * Wysłanie części paczki faktur z wykorzystaniem strumienia.
     *
     * @param openBatchSessionResponse
     * @param parts                    - Kolekcja trzymająca informacje o partach
     * @return
     * @throws ApiException
     */
    void sendBatchPartsWithStream(OpenBatchSessionResponse openBatchSessionResponse, List<BatchPartStreamSendingInfo> parts) throws ApiException;

    /**
     * Otwarcie sesji interaktywnej
     * Inicjalizacja wysyłki interaktywnej faktur.
     *
     * @param body
     * @return OpenOnlineSessionResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OpenOnlineSessionResponse openOnlineSession(OpenOnlineSessionRequest body, String accessToken) throws ApiException;

    /**
     * Zamknięcie sesji interaktywnej
     * Zamyka sesję interaktywną i rozpoczyna generowanie zbiorczego UPO.
     *
     * @param referenceNumber - Numer referencyjny sesji
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void closeOnlineSession(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Wysłanie faktury
     * Przyjmuje zaszyfrowaną fakturę oraz jej metadane i rozpoczyna jej przetwarzanie.
     *
     * @param referenceNumber - Numer referencyjny sesji
     * @param body            - SendInvoiceRequest - Zaszyfrowana faktura wraz z metadanymi.
     * @return SendInvoiceResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SendInvoiceResponse onlineSessionSendInvoice(String referenceNumber, SendInvoiceOnlineSessionRequest body, String accessToken) throws ApiException;

    /**
     * Pobranie danych o limitach certyfikatów.
     * Zwraca informacje o limitach certyfikatów oraz informacje czy użytkownik może zawnioskować o certyfikat.
     *
     * @return CertificateLimitsResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    CertificateLimitsResponse getCertificateLimits(String accessToken) throws ApiException;

    /**
     * Zwraca dane wymagane do przygotowania wniosku certyfikacyjnego.
     *
     * @return CertificateEnrollmentsInfoResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    CertificateEnrollmentsInfoResponse getCertificateEnrollmentInfo(String accessToken) throws ApiException;

    /**
     * Przyjmuje wniosek certyfikacyjny i rozpoczyna jego przetwarzanie.
     *
     * @param body SendCertificateEnrollmentRequest
     * @return CertificateEnrollmentResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    CertificateEnrollmentResponse sendCertificateEnrollment(SendCertificateEnrollmentRequest body, String accessToken) throws ApiException;

    /**
     * Zwraca informacje o statusie wniosku certyfikacyjnego.
     *
     * @param referenceNumber - Numer refrencyjny wniosku certyfikacyjnego.
     * @return CertificateEnrollmentStatusResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    CertificateEnrollmentStatusResponse getCertificateEnrollmentStatus(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Zwraca certyfikaty o podanych numerach seryjnych w formacie DER zakodowanym w Base64.
     *
     * @param body CertificateListRequest
     * @return CertificateListResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    CertificateListResponse getCertificateList(CertificateListRequest body, String accessToken) throws ApiException;

    /**
     * Unieważnia certyfikat o podanym numerze seryjnym.
     *
     * @param body         CertificateRevokeRequest
     * @param serialNumber - Numer seryjny certyfikatu
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void revokeCertificate(CertificateRevokeRequest body, String serialNumber, String accessToken) throws ApiException;

    /**
     * Zwraca listę certyfikatów spełniających podane kryteria wyszukiwania. W przypadku braku podania kryteriów wyszukiwania zwrócona zostanie nieprzefiltrowana lista.
     *
     * @param body       CertificateMetadataListRequest
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @return CertificateMetadataListResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    CertificateMetadataListResponse getCertificateMetadataList(QueryCertificatesRequest body, int pageSize, int pageOffset, String accessToken) throws ApiException;

    /**
     * Inicjalizacja mechanizmu uwierzytelnienia i autoryzacji
     *
     * @return AuthenticationChallengeResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    AuthenticationChallengeResponse getAuthChallenge() throws ApiException;

    /**
     * Rozpoczyna operację uwierzytelniania za pomocą dokumentu XML podpisanego podpisem elektroniczny XAdES.
     * Rozpoczyna proces uwierzytelnienia na podstawie podpisanego XML-a.
     *
     * @param signedXml              - Podpisany XML z żądaniem uwierzytelnienia.
     * @param verifyCertificateChain - Flaga określająca, czy sprawdzić łańcuch certyfikatów. (Domyślnie false)
     * @return AuthenticationInitResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    SignatureResponse submitAuthTokenRequest(String signedXml, boolean verifyCertificateChain) throws ApiException;

    /**
     * Rozpoczyna operację uwierzytelniania z wykorzystaniem wcześniej wygenerowanego tokena KSeF.
     *
     * @param body AuthKsefTokenRequest
     * @return AuthenticationInitResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    SignatureResponse authenticateByKSeFToken(AuthKsefTokenRequest body) throws ApiException;

    /**
     * Sprawdza bieżący status operacji uwierzytelniania dla podanego tokena.
     *
     * @param referenceNumber - Numer referencyjny otrzymany w wyniku inicjalizacji uwierzytelnienia.
     * @return AuthStatus
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    AuthStatus getAuthStatus(String referenceNumber, String authenticationToken) throws ApiException;

    /**
     * Pobranie tokena dostępowego.
     * Zwraca accessToken i refreshToken
     *
     * @return AuthenticationOperationStatusResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    AuthOperationStatusResponse redeemToken(String authenticationToken) throws ApiException;

    /**
     * Odświeżanie tokenu dostępu
     * Zwraca odświezony access token
     *
     * @param refreshToken - Refresh token.
     * @return AuthenticationTokenRefreshResponse
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     * @throws ApiException - W przypadku podania accessToken zamiast refreshToken. (403 Forbidden)
     */
    AuthenticationTokenRefreshResponse refreshAccessToken(String refreshToken) throws ApiException;

    /**
     * Unieważnia aktualny token autoryzacyjny przekazany w nagłówku żądania. Po unieważnieniu token nie może być używany do żadnych operacji.
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void revokeAccessToken(String accessToken) throws ApiException;

    /**
     * Nadanie podmiotom uprawnień o charakterze upoważnień
     *
     * @param body GrantProxyEntityPermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionsProxyEntity(GrantAuthorizationPermissionsRequest body, String accessToken) throws ApiException;

    /**
     * Nadanie uprawnień w sposób pośredni
     *
     * @param body GrantIndirectEntityPermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionIndirectEntity(GrantIndirectEntityPermissionsRequest body, String accessToken) throws ApiException;

    /**
     * Pobranie statusu operacji - uprawnienia
     *
     * @param referenceNumber - Numer referencyjny operacji.
     * @return PermissionStatusInfo
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    PermissionStatusInfo permissionOperationStatus(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Pobranie listy uprawnień do pracy w KSeF nadanych osobom fizycznym lub podmiotom.
     *
     * @param request    PersonPermissionsQueryRequest
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QueryPersonPermissionsResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QueryPersonPermissionsResponse searchGrantedPersonPermissions(PersonPermissionsQueryRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException;

    /**
     * Pobranie listy uprawnień administratora podmiotu podrzędnego.
     *
     * @param request    SubunitPermissionsQueryRequest
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QuerySubunitPermissionsResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QuerySubunitPermissionsResponse searchSubunitAdminPermissions(SubunitPermissionsQueryRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException;

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
    QueryPersonalGrantResponse searchPersonalGrantPermission(QueryPersonalGrantRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException;


    /**
     * Pobranie listy uprawnień administratora podmiotu podrzędnego.
     *
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QueryEntityRolesResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QueryEntityRolesResponse searchEntityInvoiceRoles(int pageOffset, int pageSize, String accessToken) throws ApiException;

    /**
     * Pobranie listy uprawnień do obsługi faktur nadanych podmiotom.
     *
     * @param request    SubordinateEntityRolesQueryRequest
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QuerySubordinateEntityRolesResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SubordinateEntityRolesQueryResponse searchSubordinateEntityInvoiceRoles(SubordinateEntityRolesQueryRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException;

    /**
     * Pobranie listy uprawnień o charakterze uprawnień nadanych podmiotom.
     *
     * @param request    EntityAuthorizationPermissionsQueryRequest
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QueryEntityAuthorizationPermissionsResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QueryEntityAuthorizationPermissionsResponse searchEntityAuthorizationGrants(EntityAuthorizationPermissionsQueryRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException;

    /**
     * Pobranie listy uprawnień nadanych podmiotom unijnym.
     *
     * @param request    EuEntityPermissionsQueryRequest
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @return QueryEuEntityPermissionsResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QueryEuEntityPermissionsResponse searchGrantedEuEntityPermissions(EuEntityPermissionsQueryRequest request, int pageOffset, int pageSize, String accessToken) throws ApiException;

    /**
     * Nadanie uprawnień administratora podmiotu unijnego
     *
     * @param body GrantEUEntityPermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionEUEntity(EuEntityPermissionsGrantRequest body, String accessToken) throws ApiException;

    /**
     * Nadanie uprawnień administratora podmiotu unijnego
     *
     * @param body GrantEUEntityRepresentativePermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionEUEntityRepresentative(GrantEUEntityRepresentativePermissionsRequest body, String accessToken) throws ApiException;

    /**
     * Pobranie statusu sesji
     * Zwraca informacje o statusie sesji.
     *
     * @param referenceNumber - Numer referencyjny sesji
     * @return SessionStatusResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SessionStatusResponse getSessionStatus(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Pobranie statusu faktury z sesji
     * Zwraca fakturę przesłaną w sesji wraz ze statusem.
     *
     * @param referenceNumber        - Numer referencyjny sesji.
     * @param invoiceReferenceNumber - Numer referencyjny faktury.
     * @return SessionInvoice
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SessionInvoiceStatusResponse getSessionInvoiceStatus(String referenceNumber, String invoiceReferenceNumber, String accessToken) throws ApiException;

    /**
     * Pobranie UPO faktury z sesji na podstawie numeru referencyjnego faktury.
     * Zwraca UPO faktury przesłanego w sesji na podstawie jego numeru KSeF.
     *
     * @param referenceNumber        - Numer referencyjny sesji
     * @param invoiceReferenceNumber - Numer referencyjny faktury
     * @return UPO w formie XML
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    byte[] getSessionInvoiceUpoByReferenceNumber(String referenceNumber, String invoiceReferenceNumber, String accessToken) throws ApiException;

    /**
     * Pobranie UPO faktuy z sesji na podstawie numeru KSeF
     * Zwraca UPO faktuy przesłanej w sesji na podstawie jego numeru KSeF.
     *
     * @param referenceNumber - Numer referencyjny sesji
     * @param ksefNumber      - Numer KSeF faktuy
     * @return UPO w formie XML
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    byte[] getSessionInvoiceUpoByKsefNumber(String referenceNumber, String ksefNumber, String accessToken) throws ApiException;

    /**
     * Pobranie UPO dla sesji
     * Zwraca XML zawierający zbiorcze UPO dla sesji.
     *
     * @param referenceNumber    - Numer referencyjny sesji
     * @param upoReferenceNumber - Numer referencyjny UPO
     * @return Zbiorcze UPO w formie XML
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    byte[] getSessionUpo(String referenceNumber, String upoReferenceNumber, String accessToken) throws ApiException;

    /**
     * Pobranie statusów dokumentów sesji
     * Zwraca listę faktur przesłanych w sesji wraz z ich statusami, oraz informacje na temat ilości poprawnie i niepoprawnie przetworzonych faktur.
     *
     * @param referenceNumber   - Numer referencyjny sesji
     * @param continuationToken - token kontynuacji
     * @param pageSize          - Ilość elementów na stronie (domyślnie 10)
     * @return SessionInvoicesResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SessionInvoicesResponse getSessionInvoices(String referenceNumber, String continuationToken, Integer pageSize, String accessToken) throws ApiException;

    /**
     * Pobranie niepoprawnie przetworzonych dokumentów sesji
     * Zwraca listę niepoprawnie przetworzonych dokumentów przesłanych w sesji wraz z ich statusami.
     *
     * @param referenceNumber   - Numer referencyjny sesji
     * @param continuationToken - token kontynuacji
     * @param pageSize          - Ilość elementów na stronie (domyślnie 10)
     * @return SessionInvoicesResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SessionInvoicesResponse getSessionFailedInvoices(String referenceNumber, String continuationToken, Integer pageSize, String accessToken) throws ApiException;

    /**
     * Nadanie osobom fizycznym uprawnień do pracy w KSeF
     *
     * @param request GrantPersonPermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionPerson(GrantPersonPermissionsRequest request, String accessToken) throws ApiException;

    /**
     * Pobranie faktury po numerze KSeF
     *
     * @param ksefReferenceNumber - Numer KSeF faktury
     * @return Faktura w formie XML.
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    byte[] getInvoice(String ksefReferenceNumber, String accessToken) throws ApiException;

    /**
     * Pobranie faktury na podstawie numeru KSeF oraz danych faktury.
     *
     * @param request DownloadInvoiceRequest
     * @return Faktura w formie XML.
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    byte[] getInvoice(DownloadInvoiceRequest request, String accessToken) throws ApiException;

    /**
     * Zwraca listę metadanych faktur spełniające podane kryteria wyszukiwania.
     *
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @param request    InvoicesQueryRequest - zestaw filtrów
     * @return QueryInvoicesReponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QueryInvoiceMetadataResponse queryInvoiceMetadata(Integer pageOffset, Integer pageSize, InvoiceQueryFilters request, String accessToken) throws ApiException;

    /**
     * Rozpoczyna asynchroniczny proces wyszukiwania faktur w systemie KSeF na podstawie przekazanych filtrów
     *
     * @param request InvoicesAsynqQueryRequest - zestaw filtrów i informacja o szyfrowaniu
     * @return InitAsyncInvoicesQueryResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    InitAsyncInvoicesQueryResponse initAsyncQueryInvoice(InvoiceExportRequest request, String accessToken) throws ApiException;

    /**
     * Pobiera status wcześniej zainicjalizowanego zapytania asynchronicznego na podstawie identyfikatora operacji.
     * Umożliwia śledzenie postępu przetwarzania zapytania oraz pobranie gotowych paczek z wynikami, jeśli są już dostępne.
     *
     * @param referenceNumber - Numer referencyjny operacji.
     * @return AsyncInvoicesQueryStatus
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    InvoiceExportStatus checkStatusAsyncQueryInvoice(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Nadanie podmiotom uprawnień do pracy w KSeF
     *
     * @param body GrantEntityPermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionEntity(GrantEntityPermissionsRequest body, String accessToken) throws ApiException;

    // SUB ENTITY

    /**
     * Nadanie uprawnień administratora podmiotu podrzędnego
     *
     * @param body GrantSubUnitPermissionsRequest
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse grantsPermissionSubUnit(SubunitPermissionsGrantRequest body, String accessToken) throws ApiException;

    /**
     * Rozpoczyna asynchroniczną operację odbierania uprawnienia o podanym identyfikatorze.
     *
     * @param permissionId - Id uprawnienia.
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse revokeCommonPermission(String permissionId, String accessToken) throws ApiException;

    /**
     * Rozpoczyna asynchroniczną operacje odbierania uprawnienia o podanym identyfikatorze.
     * Ta metoda służy do odbierania uprawnień o charakterze upoważnień.
     *
     * @param permissionId - Id uprawnienia.
     * @return PermissionsOperationResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    OperationResponse revokeAuthorizationsPermission(String permissionId, String accessToken) throws ApiException;

    /**
     * Sprawdzenie czy obecny kontekst posiada zgodę na wystawianie faktur z załącznikiem.
     * Wymagane uprawnienia: CredentialsManage, CredentialsRead.
     *
     * @param accessToken - token sesyjny
     * @return PermissionAttachmentStatusResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    PermissionAttachmentStatusResponse checkPermissionAttachmentInvoiceStatus(String accessToken) throws ApiException;

    /**
     * Generuje nowy token KSeF.
     *
     * @param tokenRequest GenerateTokenRequest
     * @return GenerateTokenResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    GenerateTokenResponse generateKsefToken(KsefTokenRequest tokenRequest, String accessToken) throws ApiException;

    /**
     * Pobranie listy wygenerowanych tokenów.
     *
     * @param statuses          - Statusy
     * @param continuationToken - Token do kontynuowania paginacji wyników
     * @param pageSize          - Ilość elementów na stronie (domyślnie 10)
     * @return QueryTokensResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    QueryTokensResponse queryKsefTokens(List<AuthenticationTokenStatus> statuses,
                                        String description,
                                        String authorIdentifier,
                                        AuthorTokenIdentifier.IdentifierType authorIdentifierType,
                                        String continuationToken,
                                        Integer pageSize,
                                        String accessToken) throws ApiException;

    /**
     * Pobranie statusu tokena
     *
     * @param referenceNumber - Numer referencyjny tokena.
     * @return AuthenticationToken
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    AuthenticationToken getKsefToken(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Unieważnienie tokena.
     *
     * @param referenceNumber - Numer referencyjny tokena.
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void revokeKsefToken(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Zwraca informacje o kluczach publicznych używanych do szyfrowania danych przesyłanych do systemu KSeF.
     *
     * @return List<PublicKeyCertificate>
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    List<PublicKeyCertificate> retrievePublicKeyCertificate() throws ApiException;

    /**
     * Zwraca listę sesji spełniających podane kryteria wyszukiwania.
     *
     * @param request           SessionsQueryRequest
     * @param pageSize          - Ilość elementów na stronie (domyślnie 10)
     * @param continuationToken - Token do kontynuowania paginacji wyników
     * @return SessionsQueryResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    SessionsQueryResponse getSessions(SessionsQueryRequest request, Integer pageSize, String continuationToken, String accessToken) throws ApiException;

    /**
     * Pobranie listy aktywnych sesji uwierzytelnienia.
     *
     * @param pageSize          - Ilość elementów na stronie (domyślnie 10)
     * @param continuationToken - Token do kontynuowania paginacji wyników
     * @return ActiveSessionsResponse
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    AuthenticationListResponse getActiveSessions(Integer pageSize, String continuationToken, String accessToken) throws ApiException;

    /**
     * Unieważnia sesję powiązaną z tokenem użytym do wywołania tej operacji.
     * Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów.
     * Aktywne access tokeny działają do czasu minięcia ich termin ważności.
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void revokeCurrentSession(String accessToken) throws ApiException;

    /**
     * Unieważnia sesję o podanym numerze referencyjnym.
     * Unieważnienie sesji sprawia, że powiązany z nią refresh token przestaje działać i nie można już za jego pomocą uzyskać kolejnych access tokenów.
     * Aktywne access tokeny działają do czasu minięcia ich termin ważności.
     *
     * @param referenceNumber - Numer referencyjny sesji.
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     * @throws ApiException - Brak autoryzacji. (401 Unauthorized)
     */
    void revokeSession(String referenceNumber, String accessToken) throws ApiException;

    /**
     * Zwraca listę dostawców usług Peppol zarejestrowanych w systemie.
     *
     * @param pageOffset - Index strony wyników (domyślnie 0)
     * @param pageSize   - Ilość elementów na stronie (domyślnie 10)
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    PeppolProvidersListResponse getPeppolProvidersList(int pageOffset, int pageSize) throws ApiException;

    /**
     * Zwraca wartości aktualnie obowiązujących limitów dla bieżącego kontekstu.
     *
     * @param accessToken
     * @return GetContextLimitResponse
     * @throws ApiException
     */
    GetContextLimitResponse getContextSessionLimit(String accessToken) throws ApiException;

    /**
     * Zwraca wartoście aktualnie obowiązujących limitów dla bieżącego podmiotu.
     *
     * @param accessToken
     * @return GetContextLimitResponse
     * @throws ApiException
     */
    GetSubjectLimitResponse getSubjectCertificateLimit(String accessToken) throws ApiException;

    /**
     * Zmienia wartości aktualnie obowiązujących limitów dla bieżącego kontekstu. Tylko na środowiskach testowych.
     *
     * @param changeContextLimitRequest
     * @param accessToken
     * @return
     * @throws ApiException
     */
    void changeContextLimitTest(ChangeContextLimitRequest changeContextLimitRequest, String accessToken) throws ApiException;

    /**
     * Zmienia wartości aktualnie obowiązujących limitów certyfikatów dla bieżącego podmiotu. Tylko na środowiskach testowych.
     *
     * @param changeSubjectCertificateLimitRequest
     * @param accessToken
     * @return
     * @throws ApiException
     */
    void changeSubjectLimitTest(ChangeSubjectCertificateLimitRequest changeSubjectCertificateLimitRequest, String accessToken) throws ApiException;

    /**
     * Przywraca wartości aktualnie obowiązujących limitów dla bieżącego kontekstu do wartości domyślnych. Tylko na środowiskach testowych.
     *
     * @param accessToken
     * @return
     * @throws ApiException
     */
    void resetContextLimitTest(String accessToken) throws ApiException;

    /**
     * Przywraca wartości aktualnie obowiązujących limitów dla bieżącego kontekstu do wartości domyślnych. Tylko na środowiskach testowych.
     *
     * @param accessToken
     * @return
     * @throws ApiException
     */
    void resetSubjectCertificateLimit(String accessToken) throws ApiException;

    /**
     * Tworzenie nowego podmiotu testowego. W przypadku grupy VAT i JST istnieje możliwość stworzenia jednostek podrzędnych. W wyniku takiego działania w systemie powstanie powiązanie między tymi podmiotami.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void createTestSubject(TestDataSubjectCreateRequest testDataSubjectCreateRequest) throws ApiException;

    /**
     * Usuwanie podmiotu testowego. W przypadku grupy VAT i JST usunięte zostaną również jednostki podrzędne.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void removeTestSubject(TestDataSubjectRemoveRequest testDataSubjectRemoveRequest) throws ApiException;

    /**
     * Tworzenie nowej osoby fizycznej, której system nadaje uprawnienia właścicielskie. Można również określić, czy osoba ta jest komornikiem – wówczas otrzyma odpowiednie uprawnienie egzekucyjne.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void createTestPerson(TestDataPersonCreateRequest testDataPersonCreateRequest) throws ApiException;

    /**
     * Usuwanie testowej osoby fizycznej. System automatycznie odbierze jej wszystkie uprawnienia.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void removeTestPerson(TestDataPersonRemoveRequest testDataPersonRemoveRequest) throws ApiException;

    /**
     * Nadawanie uprawnień testowemu podmiotowi lub osobie fizycznej, a także w ich kontekście.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void addTestPermission(TestDataPermissionRequest testDataPermissionRequest) throws ApiException;

    /**
     * Odbieranie uprawnień nadanych testowemu podmiotowi lub osobie fizycznej, a także w ich kontekście.
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void removeTestPermission(TestDataPermissionRemoveRequest testDataPermissionRemoveRequest) throws ApiException;

    /**
     * Dodaje możliwość wysyłania faktur z załącznikiem przez wskazany podmiot
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void addAttachmentPermissionTest(TestDataAttachmentRequest testDataAttachmentRequest) throws ApiException;

    /**
     * Odbiera możliwość wysyłania faktur z załącznikiem przez wskazany podmiot
     * Metoda dostępna tylko na środowiskach testowych
     *
     * @throws ApiException - Nieprawidłowe żądanie. (400 Bad request)
     */
    void removeAttachmentPermissionTest(TestDataAttachmentRemoveRequest testDataAttachmentRemoveRequest) throws ApiException;

    void singleBatchPartSendingProcess(BatchPartSendingInfo part,
                                       PackagePartSignatureInitResponseType responsePart,
                                       List<String> errors);

    void singleBatchPartSendingProcessByStream(BatchPartStreamSendingInfo part,
                                               PackagePartSignatureInitResponseType responsePart,
                                               List<String> errors);
}