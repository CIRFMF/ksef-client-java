# Changelog zmian – `## 2.0.0 (2025-07-17)`

> Info: 🔧 zmienione • ➕ dodane • ➖ usunięte

---

## 1. ksef-client

### 1.1 api.services

- **DefaultCertificateGenerator.java**: 🔧 metodę `generateSelfSignedCertificate` zastąpiono
  dwoma `generateSelfSignedCertificateRsa` i `generateSelfSignedCertificateEcdsa`
- **DefaultCryptographyService.java**: 🔧 wprowadzono zmiany w pobieraniu kluczy w konstruktorze; dodano dwie pomocnocze
  metody `parsePublicKeyFromPem`
  i `parsePrivateKeyFromPem`; dodano `encryptWithECDsaUsingPublicKey(byte[] content)` — domyślna metoda szyfrowania
  ECIES (ECDH + AES-GCM) na krzywej P-256.
- **DefaultKsefClient.java**: 🔧 zmieniono nazwę metody z `batchOpen` na `openBatchSession`; do metody `sendBatchParts`
  dodano nagłowek `Content-Type`; zmieniono sygnaturę metody `submitAuthTokenRequest` - dodano
  parametr `boolean verifyCertificateChain` i zmieniono `body` na `signedXml`; zmieniono nazwę metody `operations`
  na `permissionOperationStatus`; zmieniono nazwę metody `getInvoiceMetadane` na `queryInvoices`; dodano
  metody `getSessions`, `getActiveSessions`, `revokeCurrentSession`, `revokeSession`, `retrievePublicKeyCertificate`;
  dodano parametr `continuationToken` do
  metody `AuthenticationListResponse getActiveSessions(Integer pageSize, String continuationToken)`  
  dodano parametr `continuationToken` do
  metody `SessionInvoicesResponse getSessionFailedInvoices(String referenceNumber, String continuationToken, Integer pageSize)`
- **DefaultQrCodeService.java**: ➕ nowa usługa do generowania QrCodes
- **DefaultVerificationLinkService.java**: ➕ nowa usługa generowania linków do weryfikacji faktury

### 1.2 api.client.interfaces

- **CertificateGenerator.java**: 🔧 zmiany zgodnie z implementacja w `DefaultCertificateGenerator.java`
- **CryptographyService.java**: 🔧 zmiany zgodnie z implementacja w `DefaultCryptographyService.java`
- **KSeFClient.java**: 🔧 dodanie opisów do metod; zmiany zgodnie z implementacja w `DefaultKsefClient.java`
- **QrCodeService.java**: ➕ nowy interfejs do generowania QRcodes zgodnie z implementacją w `DefaultQrCodeService.java`
- **VerificationLinkService.java**: ➕ nowy interfejs do tworzenia linków weryfikacyjnych do faktury zgodnie z
  implementacją w `DefaultVerificationLinkService.java`

### 1.3 api.client.model

- **AuthenticationChallengeResponse.java**: 🔧 zmiany typu pola `timestamp` z `OffsetDateTime` na `Instant`
- **EntityAuthorizationGrant.java**: 🔧 dodanie pola `String id` i zmiana typu pola `authorizationScope` z `String`
  na `EntityAuthorizationScope`
- **EuEntityPermission.java**: 🔧 dodanie pola `String id`, zmiana pola `permissionType` na `permissionScope`
- **PersonPermission.java**: 🔧 dodanie pola `String id`
- **SubunitPermission.java**: 🔧 dodanie pola `String id` i usunięcie pola `canDelegate`
- **QrCodeResult.java**: ➕ nowa klasa
- **ContextIdentifierType.java**: ➕ nowa klasa w pakiecie qrcode
- **AuthenticationListResponse.java**: ➕ nowa klasa
- **AuthenticationMethod.java**: ➕ nowa klasa
- **AuthenticationOperationStatusResponse.java**: ➕ nowa klasa
- **CommonSessionStatus.java**: ➕ nowa klasa
- **SessionInvoice.java**: 🔧 dodanie pola `String invoiceFileName`
- **SessionsQueryRequest.java**: ➕ nowa klasa
- **SessionsQueryResponse.java**: ➕ nowa klasa
- **SessionsQueryResponseItem.java**: ➕ nowa klasa
- **SessionType.java**: ➕ nowa klasa
- **ApiException.java**: 🔧 dodanie metody `getApiException`
- **EncryptionMethod.java**: ➕ nowy enum
- **PersonPermissionQueryType.java**: ➕ nowy enum
- **SystemCode.java**: ➕ nowy enum
- **EntityAuthorizationScope.java**: ➕ nowy enum
- **CertificateInfo.java**: 🔧 usunięcie pola `thumbprint`
- **PersonPermissionsQueryRequest**: 🔧 dodanie pola `PersonPermissionQueryType queryType`

### 1.4 api.builders

- **PersonPermissionsQueryRequestBuilder.java**: 🔧 dodanie pola `PersonPermissionQueryType queryType`
- **OpenBatchSessionRequestBuilder.java**: 🔧 z `withBatchFile` usunięcie parametru `boolean offlineMode` i wydzielenie
  do osobnej metody `withOfflineMode(boolean offlineMode)` oraz zmiana typu przyjmowanego argumentu
  metody `withFormCode` z `String` na `SystemCode`

---

## 2. demo-web-app

### 2.1 integrationTest

- Wspólne: 🔧 `Thread.Sleep` → `org.awaitility.Awaitility.await`;
- **EntityPermissionIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym
- **EuEntityPermissionIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym
- **EuEntityRepresentativeE2EPermissionTest.java**: 🔧 zmiany w scenariuszu testowym
- **IndirectPermissionIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym
- **PersonPermissionIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym
- **ProxyPermissionIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym
- **SubUnitPermissionIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym
- **OnlineSessionV2E2EIntegrationTest.java**: 🔧 zmiany w scenariuszu testowym oraz dodano testy end-to-end dla faktury w
  wersji 3

- ➖ usunięto `publicKey.pem` z resources
- ➕ dodano klasę testową `SessionIntegrationTest.java`
- ➕ dodano plik `invoice-template_v3.xml` zawierający przykładową fakturę w wersji 3
-
- **AuthorizationIntegrationTest.java**: dodano testy end-to-end dla tokenu w wariantach `ECDsa` i `Rsa`.

### 2.2 api

- ➕ dodano usługi w `ActiveSessionController.java` które wywołują bezpośrednio klienta ksef.
- **AuthController.java**: ➕ `POST auth-with-ksef-certificate`
- **QrCodeController.java**: ➕`POST /qr/certificate` ➕`GET /qr/invoice/ksef` ➕`GET /qr/invoice/offline`
- ➖ usunięto `publicKey.pem` z resources

### 2.3 test - api.services

- ➕ dodano `QrCodeTests.java`
- ➕ dodano `VerificationLinkServiceTests.java`

---

## 3. .http

- 🔧 do `auth.http` dodano wywołanie usługi `POST auth-with-ksef-certificate`
- ➕ w `qr-code.http` dodano wywołanie usług ➕`POST /qr/certificate` ➕`GET /qr/invoice/ksef` ➕`GET /qr/invoice/offline`
- 🔧 w `searchPermissions.http` zaktualizowano payload
- 🔧 w `session.http` dodano wywołanie usług ➕ `POST /session/query/` ➕ `POST /session/active/{pageSize}`
  ➕ `DELETE /session/revoke/current` ➕ `DELETE /session/revoke/{sessionReferenceNumber}`
- 🔧 w `subunit-subject-permission.http` zaktualizowano payload
- ➖ usunięto `scenario/BatchSession_E2E_WorksCorrectly.http`

---

## 4. Podsumowanie

| Typ zmiany   | Liczba plików |
|--------------|---------------|
| ➕ dodane     | 30            |
| 🔧 zmienione | 80            |
| ➖ usunięte   | 3             |

---------------------------------------------------------------------------------------------------------------------------------

# Changelog zmian – `## 2.0.1 (2025-09-02)`

---

##  

-- **LICENSE.md**: ➕ dodano licencję

## 1. ksef-client

- **PublicKeyEnvironmentApi**: ➖ usunięto klasę

### 1.1 api.services

- **DefaultCryptographyService.java**: 🔧 wprowadzono zmiany w pobieraniu kluczy w konstruktorze; wydzielono/dodano
  metody do szyfrowania tokenu ksef
- **DefaultKsefClient.java**: 🔧 zmieniono nazwę metody z `queryInvoices` na `queryInvoiceMetadata`; usunięcie
  metody `byte[] getPublicKey()`; uwspólnienie zwracanych typów - zmiana z `String` na `byte[]`
  dla `byte[] getInvoice(String ksefReferenceNumber)` i `byte[] getInvoice(DownloadInvoiceRequest request)`

### 1.2 api.client.interfaces

- **KSeFClient.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **KsefEnviroments.java**: ➕ usunięto klasę
- **CryptographyService.java**: 🔧 zmiany zgodnie z implementacją w `DefaultCryptographyService.java`

### 1.3 api.client.model

- **InvoiceMetadata.java**: 🔧 dodanie pola `issueDate`, zmiana nazwwy pola z `invoiceDate` na `invoicingDate` i zmiana
  typu pola `currency` z `CurrencyCode`
  na `String`
- **InvoiceMetadataInvoiceType.java**: 🔧 Zmiana wartości enumów
- **InvoiceMetadataQueryRequest.java**: 🔧 zmiana nazwy z `InvoicesQueryRequest`
- **InvoiceQueryAmount.java**: 🔧 zmiana nazwy z `InvoicesAsynqQueryRequestAmount `
- **InvoiceQueryBuyer.java**: 🔧 zmiana nazwy z `InvoicesAsynqQueryRequestBuyer `
- **InvoiceQuerySeller.java**: 🔧 zmiana nazwy z `InvoicesAsynqQueryRequestSeller`
- **QueryInvoiceMetadataReponse.java**: 🔧 zmiana nazwy z `QueryInvoicesReponse`
- **InvoiceQueryDateType.java**: 🔧 Zmiana enuma z `ACQUSITION` na  `ACQUISITION`
- **BaseStatusInfo**: ➖ usunięto nieużywaną klasę
- **AuthStatus **: ➖ usunięto nieużywaną klasę
- **InvoiceQuerySystemCode.java **: ➕ dodano klasę według kontraktu
- **InvoiceMetadataQueryRequest.java**: 🔧 dodanie pól `systemCode` i `hasAttachment`
- **InvoicesAsynqQueryRequest.java**: 🔧 dodanie pola `hasAttachment` i zmiana `formCode` na `systemCode`
- **InvoiceQueryDocumentStructure.java**: 🔧 zmiana nazwy z `InvoiceQuerySystemCode` oraz dodanie wartości `FA1`
- **InvoiceMetadata.java**: 🔧 dodanie pola `hasAttachment`
- **InvoiceMetadataQueryRequest.java**: 🔧 zmiana nazwy pola z `systemCode` na `documentStructure`
- **InvoicesAsynqQueryRequest.java**: 🔧 zmiana nazwy pola z `systemCode` na `documentStructure`
- **OpenOnlineSessionResponse.java**: 🔧 dodanie pola `validUntil`
- **SessionsQueryResponseItem.java**: 🔧 dodanie pola `validUntil`
- **SessionStatusResponse.java**: 🔧 dodanie pola `validUntil`
- **Headers.java**: ➕ dodanie klasy zawierającej używane nagłówki
- **PublicKeyCertificate.java**: ➕ zmiana pola z `certificatePem` na `certificate`
- **Parameter.java**: ➕ dodanie klasy zawierającej używane w kliencie parametry zapytań
- **ApiClient.java**: ➖ usunięto klasę
- **HttpApiClient.java**: ➕ dodanie klasy zawierającej konfigurowalnego klienta http
- **CertificateType.java**: ➕ dodanie enuma
- **CertificateInfo.java**: ➕ dodanie pola `CertificateType type`
- **CertificateResponse.java**: ➕ dodanie pola `CertificateType certificateType`
- **SendCertificateEnrollmentRequest.java**: ➕ dodanie pola `CertificateType certificateType`
- **QueryInvoiceMetadataResponse.java**: ➕ dodanie pola `Boolean hasMore`
- **SessionInvoicesResponse.java**: ➕ dodanie pola `Boolean hasMore`

### 1.4 api.builders

- **InvoiceMetadataQueryRequestBuilder.java**: 🔧 zmiana nazwy z `InvoicesQueryRequestBuilder`
- **InvoiceMetadataQueryRequestBuilder.java**: 🔧 usunięcie pola `isHidden` i dodanie `hasAttachment`
- **InvoiceMetadataQueryRequestBuilder.java**: 🔧 zmiana pola z `formCode` na `systemCode`
- **InvoicesAsynqQueryRequestBuilder.java**: 🔧 zmiana nazwy pola z `systemCode` na `documentStructure`
- **AuthTokenRequestBuilder.java**: 🔧zmiana typu pola `context` z `ContextIdentifier` na `TContextIdentifier`
- **UrlQueryParamsBuilder.java**: ➕ dodanie klasy obsługującej tworzenie zapytań
- **SendCertificateEnrollmentRequestBuilder.java**: ➕ dodanie pola `certificateType`
-

### 1.5 api

- **BaseApi.java**: ➖ usunięto klasę
- **ActiveSessionApi.**: 🔧 refactor klasy umożliwiający wielowątkowość
- **AuthenticationApi.java**: 🔧 refactor klasy umożliwiający obsługę wielu wątków
- **BatchInvoiceApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **CertificateApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **DownloadInvoiceApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **EuSubjectAdministratorApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **EuSubjectRepresentationApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **ForAuthorizedSubjectApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **EGrantDirectlyApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **NaturalPersonKseFApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **OperationApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **PublicKeyCertificateApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **SearchPermissionApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **SendStatusAndUpoApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **SubjectForInvoiceApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **SubUnitSubjectAdministratorApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **TokensApi.java**: 🔧 refactor klasy umożliwiający wielowątkowość

### 1.6  resources

- **AuthTokenRequest.xsd**: 🔧 aktualizacja pliku AuthTokenRequest.xsd

## 2. demo-web-app

### 2.1 integrationTest

- **OnlineSessionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego oraz refactor klasy zgodnie z nową
  konfiguracją klienta
- **QueryInvoiceIntegrationTest.java**: dodano nową klasę testową
- **AuthorizationIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **BaseIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **BatchIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **CertificateIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **EntityPermissionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **EuEntityPermissionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **EuEntityRepresentativeE2EPermissionTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **IndirectPermissionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **OnlineSessionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **PersonPermissionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **ProxyPermissionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **QueryInvoiceIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **SessionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **SubUnitPermissionIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta
- **TokensIntegrationTest.java**: 🔧 refactor klasy zgodnie z nową konfiguracją klienta

### 2.2 api

- **TokensController.java**: 🔧 refactor refactor zgodnie z nową implementacja klienta
- **SubUnitPermissionsController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **SessionController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **SearchPermissionTestEndpoint.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **ProxyPermissionsEntityController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **PersonPermissionController.java**: 🔧 refactor klasy umożliwiający wielowątkowość
- **OperationStatusController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **OnlineSessionController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **InvoicesController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **IndirectPermissionsEntityController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **EuEntityRepresentativePermissionsController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **EuEntityPermissionsController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **EntityPermissionsController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **CertificateController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **BatchSessionController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **AuthController.java**: 🔧 refactor zgodnie z nową implementacja klienta
- **KsefClientConfig.java**: 🔧 zmiana konfiguracji zgodnie z nową implementacja klienta

### 2.3 test - api.services

---

## 3. .http

## 4. Podsumowanie

| Typ zmiany   | Liczba plików |
|--------------|---------------|
| ➕ dodane     | 22            |
| 🔧 zmienione | 112           |
| ➖ usunięte   | 12            |

---

# Changelog zmian – `## 3.0.0 (2025-09-26)`

## 1. ksef-client

### 1.1 api.builders

- **AuthTokenRequestBuilder.java**: 🔧 usunięcie nadmiarowego sprawdzania
- **CertificateBuilders.java**: 🔧 refaktory klasy
- **RevokeCertificateRequestBuilder.java**: 🔧 zmiana nazwy na `CertificateRevokeRequestBuilder.java` i zmiana var na
  konkretny typ (strong-type)
- **SendCertificateEnrollmentRequestBuilder.java**: 🔧 dodanie `CertificateType certificateType`
- **GrantEUEntityPermissionsRequestBuilder.java**: 🔧 dodanie `String subjectName`
- **GrantIndirectEntityPermissionsRequestBuilder.java**: 🔧 zmiana typu `IndirectPermissionsGrantRequest`
  na `GrantIndirectEntityPermissionsRequest`
- **GrantProxyEntityPermissionsRequestBuilder.java**: 🔧 zmiana nazwy
  na `GrantAuthorizationPermissionsRequestBuilder.java`, zmiana typu `GrantProxyEntityPermissionsRequest`
  na `GrantAuthorizationPermissionsRequest`
- **SendInvoiceRequestBuilder.java**: 🔧 zmiana nazwy na `SendInvoiceOnlineSessionRequestBuilder.java`, zmiana
  typu `SendInvoiceRequest` na `SendInvoiceOnlineSessionRequest`
- **GenerateTokenRequestBuilder.java**: 🔧 zmiana nazwy na `KsefTokenRequestBuilder.java`, zmiana
  typu `GenerateTokenRequest` na `KsefTokenRequest`
- **InvoicesAsyncQueryRequestBuilder.java**: 🔧 zmiana nazwy na `InvoicesAsyncQueryFiltersBuilder.java`, dodanie
  pola `Boolean hasAttachment`, zmiana `InvoiceQuerySchemaType schemaType` na `InvoiceMetadataSchema formType`

### 1.2 api.services

- **DefaultCertificateGenerator.java**: 🔧 zmiana nazwy na `DefaultCertificateService`, dodanie implementacji
  metod `String getSha256Fingerprint(X509Certificate certificate)`, `SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName)`, `SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName)`
- **DefaultCryptographyService.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, dodanie
  metod `CsrResult generateCsrWithEcdsa(CertificateEnrollmentsInfoResponse certificateInfo)`
- **DefaultQrCodeService.java**: 🔧 dodanie parametru `String fontName` do metody `addLabelToQrCode`, obsługa wyjątków
- **DefaultVerificationLinkService.java**: 🔧 obsługa wyjątków
- **DefaultKsefClient.java**: 🔧 przeniesienie do pakietu wyżej

### 1.3 api

- **DefaultKsefClient.java**: 🔧 przeniesienie do głownego pakietu api, refaktoryzacja klienta, aktualizacja typów
  zgodnie ze zmianami w modelu api
- **HttpStatus.java**: ➕ dodano klase zawierającą używane statusy HTTP
- **HttpUtils.java**: ➕ dodano klase pomocniczą
- **KsefApiProperties.java**: ➕ dodano klase z konfiguracją
- **Url.java.**: 🔧 zmiana adresu usługi
  z `INVOICE_QUERY_STATUS("/api/v2/invoices/async-query/{operationReferenceNumber}"`
  na `INVOICE_EXPORT_STATUS("/api/v2/invoices/exports/{operationReferenceNumber}"`
- **ActiveSessionApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **AuthenticationApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **BatchInvoiceApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **CertificateApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **DownloadInvoiceApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **EuSubjectAdministratorApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **EuSubjectRepresentationApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **ForAuthorizedSubjectApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **GrantDirectlyApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **InteractiveSessionApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **NaturalPersonKseFApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **OperationApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **PublicKeyCertificateApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **SearchPermissionApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **SendStatusAndUpoApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **SubjectForInvoiceApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **SubUnitSubjectAdministratorApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **TokensApi.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`
- **UrlQueryParamsBuilder.java**: ➖ usunięcie klasy, przeniesienie metod do `DefaultKsefClient.java`

### 1.4 client.interfaces

- **CertificateGenerator.java**: 🔧 zmiana nazwy na `CertificateService`, dodanie
  metod `String getSha256Fingerprint(X509Certificate certificate)`, `SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName)`, `SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName)`
- **CryptographyService.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, dodanie
  metod `CsrResult generateCsrWithEcdsa(CertificateEnrollmentsInfoResponse certificateInfo)`
- **QrCodeService.java**: 🔧 dodanie parametru `String fontName` do metody `addLabelToQrCode`, obsługa wyjątków
- **VerificationLinkService.java**: 🔧 obsługa wyjątków
- **KSeFClient.java**: 🔧 refaktoryzacja nazw parametrów, aktualizacja typów zgodnie ze zmianami w modelu api

### 1.5 client.model

- **auth.AuthenticationOperationStatusResponse.java**: 🔧 zmieniono nazwę klasy na `AuthOperationStatusResponse.java`
- **AuthStatus.java**: ➕ dodano klasę
- **GenerateTokenRequest.java**: 🔧 zmieniono nazwę klasy na `KsefTokenRequest.java`
- **AuthenticationInitResponse.java**: 🔧 zmieniono nazwę klasy na `SignatureResponse.java`
- **CertificateInfo.java**: 🔧 dodano parametr `CertificateType type`
- **CertificateResponse.java**: 🔧 dodano parametr `CertificateType type`
- **CertificateType.java**: ➕ dodano enuma
- **SendCertificateEnrollmentRequest.java**: 🔧 dodano parametr `CertificateType type`
- **AsyncInvoicesQueryStatus.java**: ➖ usunięto klasę
- **AuthorizedSubject.java**: ➕ dodano klasę
- **BuyerIdentifierType.java**: ➖ usunięto klasę
- **DownloadInvoiceBuyer.java**: 🔧 zmieniono typ parametru z `BuyerIdentifierType identifierType`
  na `IdentifierType identifierType`
- **IdentifierType.java**: ➕ dodano klasę
- **InvoiceMetadataQueryRequest.java**: 🔧 zmieniono nazwę klasy na `InvoiceExportFilters.java`
- **InvoiceExportPackage.java**: ➕ dodano klasę
- **InvoiceExportRequest.java**: ➕ dodano klasę
- **InvoiceExportStatus.java**: ➕ dodano klasę
- **InvoiceMetadata.java**: ➖ usunięto klasę
- **InvoiceMetadataBuyer.java**: 🔧 zmieniono `BuyerIdentifierType identifierType` na `IdentifierType identifierType`
- **InvoiceMetadataSeller.java**: 🔧 dodano pole `String nip`
- **InvoicePackagePart.java**: 🔧 dodano nowe pola
- **InvoiceQueryBuyer.java**: 🔧 zmieniono `BuyerIdentifierType identifierType` na `IdentifierType identifierType`
- **InvoicesAsyncQueryRequest.java**: 🔧 zmieniono nazwę klasy na `InvoiceSummary.java`, dodano dodatkowe pola
- **QueryInvoiceMetadataResponse.java**: 🔧 dodano pola klasy `Boolean hasMore; List<InvoiceSummary> invoices`
  usunięto `List<InvoiceMetadata> invoices`
- **ThirdSubject.java**: ➕ dodano klasę
- **EuEntityPermissionsGrantRequest.java**: 🔧 dodano pole `String subjectName`
- **IndirectPermissionsGrantRequest.java**: 🔧 zmieniono nazwę klasy na `GrantIndirectEntityPermissionsRequest.java`
- **GrantProxyEntityPermissionsRequest.java**: 🔧 zmieniono nazwę klasy na `GrantAuthorizationPermissionsRequest.java`
- **PermissionsOperationResponse.java**: 🔧 zmieniono nazwę klasy na `OperationResponse.java`
- **SendInvoiceRequest.java**: 🔧 zmieniono nazwę klasy na `SendInvoiceOnlineSessionRequest.java`
- **session.AuthenticationOperationStatusResponse.java**: 🔧 zmieniono nazwę klasy na `AuthenticationListItem.java`
- **AuthenticationListResponse.java**: 🔧 zmieniono typ pola `List<AuthenticationOperationStatusResponse> items`
  na `List<AuthenticationListItem> items`
- **SessionInvoicesResponse.java**: 🔧 dodano pole `Boolean hasMore`
- **SessionInvoiceStatusResponse.java**: 🔧 dodano pola `OffsetDateTime permanentStorageDate; String upoDownloadUrl;`

### 1.6 client

- **Headers.java**: 🔧 dodano stałe dla nagłówków
- **HttpApiClient.java**: ➖ usunięto klasę

### 1.7 system

- **KSeFNumberValidator.java**: ➖ usunięto klasę

## 2. demo-web-app

### 2.1 integrationTest

- **BaseIntegrationTest.java**: 🔧 dodanie przeciążonej metody `authWithCustomNip` w której subject przekazujemy w formie
  certyfikatów
- **AuthorizationIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego
- **BatchIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem serwisów i
  kliena KSeF, zmiany w nazwenictwie zmiennych
- **CertificateIntegrationTest.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **EntityPermissionIntegrationTest.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **EuEntityPermissionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
- **EuEntityRepresentativeE2EPermissionTest.java**: 🔧 rename na `EuEntityRepresentativePermissionIntegrationTest.java`,
  aktualizacja scenariusza testowego, aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **IndirectPermissionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
- **TokensIntegrationTest.java**: 🔧 rename na `KsefTokenIntegrationTest.java`, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
- **OnlineSessionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
- **PersonPermissionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
- **ProxyPermissionIntegrationTest.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **QrCodeOfflineIntegrationTest.java**: ➕ dodano klasę zawierającą scenariusze testowe z kodami qr do fv offline
- **QrCodeOnlineIntegrationTest.java**: ➕ dodano klasę zawierającą scenariusze testowe z kodami qr do fv online
- **QueryInvoiceIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
- **SessionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem serwisów i
  kliena KSeF, zmiany w nazwenictwie zmiennych
- **SubUnitPermissionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego, aktualizacja związana z refaktorem
  serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych
-

### 2.1.1 integrationTest.resources

- **invoice-template_v3.xml**: 🔧 - dodanie placeholdera do pola p_1 na fakturze
- **invoice-template.xml**: 🔧 - dodanie placeholdera do pola p_1 na fakturze

### 2.2 api

- **ActiveSessionController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **AuthController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie zmiennych,
  dodanie usługi `GET prepare-sample-cert-auth-request` przygotowującą testowe dane do
  usługi `POST auth-with-ksef-certificate`
- **BatchSessionController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **CertificateController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **EntityPermissionsController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **EuEntityPermissionsController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **IndirectPermissionsEntityController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **InvoicesController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych, zmiany typów zmiennych wynikajacych z aktualizacji modelu api
- **OnlineSessionController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **OperationStatusController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **PersonPermissionController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **ProxyPermissionsEntityController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **QrCodeController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych
- **SearchPermissionTestEndpoint.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **SessionController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych, użycie `response.getContinuationToken()` przy pobieraniu fv/statusów w sesji
- **SubUnitPermissionsController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w
  nazwenictwie zmiennych
- **TokensController.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF, zmiany w nazwenictwie
  zmiennych, użycie `response.getContinuationToken()` przy pobieraniu tokenów
- **StatusWaitingException.java**: ➕ dodano klasę
- **ExampleApiProperties.java**: ➕ dodano klasę
- **HttpClientBuilder.java**: ➕ dodano klasę
- **IdentifierGeneratorUtils.java**: ➕ dodano testową klasę do generowania identyfikatorów (np. nip, pesel)
- **KsefClientConfig.java**: 🔧 zmiana konfiguracji zgodnie z nową implementacja klienta

### 2.2.1 resources

- **invoice-template.xml**: 🔧 - dodanie placeholdera do pola p_1 na fakturze

### 2.3 test - api.services

- **QrCodeTests.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF
- **VerificationLinkServiceTests.java**: 🔧 aktualizacja związana z refaktorem serwisów i kliena KSeF

## 3. .http

- 🔧 do `auth.http` dodano wywołanie usługi `GET prepare-sample-cert-auth-request` przygotowującą testowe dane do
  usługi `POST auth-with-ksef-certificate`

## 4. Podsumowanie

| Typ zmiany   | Liczba plików |
|--------------|---------------|
| ➕ dodane     | 25            |
| 🔧 zmienione | 75            |
| ➖ usunięte   | 31            |

---

# Changelog zmian – `## 3.0.1 (2025-10-09)`

## 1. ksef-client

### 1.1 api.builders

- **CertificateBuilders.java**: 🔧 zmiana typów zwracanych metod
- **CertificateMetadataListRequestBuilder.java**: 🔧 dodanie `private CertificateType type;` wraz z metodą ustawiającą
  wartość tego pola
- **InvoiceMetadataQueryRequestBuilder.java**: ➖ usunięto klasę
- **InvoiceQueryFiltersBuilder.java**: 🔧 zmiana nazwy z `InvoicesAsyncQueryFiltersBuilder`; zmiany
  pól `InvoiceQuerySeller seller;` na `String sellerNip;` i `InvoiceQueryBuyer buyer;`
  na `InvoiceBuyerIdentifier buyerIdentifier;`, zmiana `InvoiceMetadataSchema formType` na `InvoiceFormType formType`,
  dodanie `Boolean hasAttachment = false;`
- **InvoicesAsyncQueryFiltersBuilder.java**: 🔧 zmiany pól `InvoiceQuerySeller seller;` na `String sellerNip;`
  i `InvoiceQueryBuyer buyer;` na `InvoiceBuyerIdentifier buyerIdentifier;`, zmiana `InvoiceMetadataSchema formType`
  na `InvoiceFormType formType`
- **GrantEUEntityPermissionsRequestBuilder.java**: 🔧 zmiany pól `subjectName;` na `euEntityName`
- **PersonPermissionsQueryRequestBuilder.java**: 🔧 dodanie
  pola `private PersonPermissionsContextIdentifier contextIdentifier;` wraz z metodą ustawiającą wartość tego pola
- **QueryPersonalGrantRequestBuilder.java**: ➕ dodano klasę
- **GrantAuthorizationPermissionsRequestBuilder.java**: 🔧 zmiana pola `private ProxyEntityPermissionType permission;`
  na `private InvoicePermissionType permission;`
- **SubunitPermissionsGrantRequestBuilder.java**: 🔧 dodanie pola `String subunitName;` wraz z metodą ustawiającą wartość
  tego pola

### 1.2 api.services

- **DefaultCertificateService.java**: 🔧 zmiany w przechwytywaniu wyjątków i zwracaniu typów
- **DefaultCryptographyService.java**: 🔧 zmiany w przechwytywaniu wyjątków i zwracaniu typów, zmiana nazwy metody
  z `generateCsr` na `generateCsrWithRsa`, `parsePrivateKeyFromPem` na `parseRsaPrivateKeyFromPem`; dodanie
  metod ` FileMetadata getMetaData(InputStream inputStream)`, `PublicKey parsePublicKeyFromCertificatePem(String certificatePem)`, `PrivateKey parseEcdsaPrivateKeyFromPem(byte[] privateKey)`
- **DefaultKsefClient.java**: 🔧 aktualizacje związane z modelem, dodanie usług zgodnie z api
- **HttpStatus.java**: 🔧 dodanie kodów http ` UNSUPPORTED_MEDIA_TYPE(415), INTERNAL_ERROR(500);`
- **Url.java**: 🔧 dodanie usług zgodnie z api

### 1.3 api

### 1.4 client.interfaces

- **CertificateService.java**: 🔧 zmiany w przechwytywaniu wyjątków i zwracaniu typów
- **CryptographyService.java**: 🔧 zmiany w przechwytywaniu wyjątków i zwracaniu typów, zmiana nazwy metody
  z `generateCsr` na `generateCsrWithRsa`, `parsePrivateKeyFromPem` na `parseRsaPrivateKeyFromPem`; dodanie
  metod ` FileMetadata getMetaData(InputStream inputStream)`, `PublicKey parsePublicKeyFromCertificatePem(String certificatePem)`, `PrivateKey parseEcdsaPrivateKeyFromPem(byte[] privateKey)`
- **KSeFClient.java**: 🔧 aktualizacje związane z modelem, dodanie usług zgodnie z api

### 1.5 client.model

- **auth/AuthenticationToken.java**: 🔧 zmieniono typ dla pola `SubjectIdentifier authorIdentifier`
  na `AuthorTokenIdentifier authorIdentifier`, dodano pola `OffsetDateTime lastUseDate` i `List<String> statusDetails`
- **auth/AuthorTokenIdentifier.java**: ➕ dodano klasę
- **auth/ContextIdentifier.java**: 🔧 zmieniono typ dla pola `ContextIdentifierType type` na `IdentifierType type;`
  zdefiniowano enuma `IdentifierType`
- **auth/ContextIdentifierType.java**: ➖ usunięto klasę
- **auth/SubjectIdentifier.java**: 🔧 zmieniono typ dla pola `SubjectIdentifierType type` na `IdentifierType type;`
  zdefiniowano enuma `IdentifierType`
- **auth/TokenPermissionType.java**: 🔧 zmieniono nazwy enumów (wartości pozostają bez zmian), dodano 2 nowe
  wartości `SUBUNIT_MANAGE("SubunitManage")` i `ENFORCEMENT_OPERATION("EnforcementOperations")`
- **certificate/CertificateEnrollmentsInfoResponse.java**: 🔧 zmieniono pole `List<String> givenNames`
  na `String givenName;`
- **certificate/SubjectCertificateIdentifier.java**: ➕ dodano klasę
- **certificate/CertificateInfo.java**: 🔧 pola `String subjectIdentifier` i `String subjectIdentifierType`
  zastąpiono `SubjectCertificateIdentifier subjectIdentifier`
- **certificate/CertificateListResponse.java**: 🔧 zmieniono `List<CertificateResponse> certificates`
  na `List<RetrieveCertificatesListItem> certificates`
- **certificate/CertificateType.java**: 🔧 dodanie do enuma pola z wartością
- **certificate/QueryCertificatesRequest.java**: 🔧 dodanie pola `CertificateType type`
- **certificate/CertificateResponse.java**: 🔧 zmiana nazwy na `RetrieveCertificatesListItem`
- **certificate/SubjectCertificateIdentifierType.java**: ➕ dodano klasę
- **invoice/InitAsyncInvoicesQueryResponse.java**: 🔧 usunięto pole `StatusInfo status`
- **invoice/InvoiceBuyerIdentifier.java**: ➕ dodano klasę
- **invoice/InvoiceExportFilters.java**: 🔧 zmieniono pole `InvoiceMetadataSchema formType` na `InvoiceFormType formType`
  i `InvoiceQuerySeller seller` na `String sellerNip`, usunięto `InvoiceQueryBuyer buyer`
- **invoice/InvoiceExportPackage.java**: 🔧 dodano pole `OffsetDateTime lastInvoicingDate`
- **invoice/InvoiceFormCode.java**: ➕ dodano klasę
- **invoice/InvoiceMetadataSchema.java**: 🔧 zmieniono nazwę na `InvoiceFormType` i dodano wartości
  enuma `PEF("PEF"), RR("RR");`
- **invoice/InvoiceSummary.java**: 🔧 zmieniono nazwę na `InvoiceMetadata`, dodano pole `String hashOfCorrectedInvoice`,
  zmieniono `FormCode formCode` na `InvoiceFormCode formCode`
- **invoice/InvoiceMetadataBuyer.java**: 🔧 pola `IdentifierType identifierType` i `String identifier`
  zastąpiono `InvoiceBuyerIdentifier identifier`
- **invoice/InvoiceMetadataInvoiceType.java**: 🔧 dodano nowe wartości
  enumów `VAT_PEF("VatPef"), KOR_PEF("KorPef"), VAT_RR("VatRr"), KOR_VAT_SP("KorVatRr");`
- **invoice/InvoiceMetadataSeller.java**: 🔧 usunięto pole `String identifier`
- **invoice/InvoiceQueryBuyer.java**: ➖ usunięto klasę
- **invoice/InvoiceMetadataQueryRequest.java**: 🔧 zmieniono nazwę klasy na `InvoiceQueryFilters`,
  pola `InvoiceQuerySchemaType schemaType` i `InvoiceMetadataSchema invoiceSchema`
  zastąpiono `InvoiceFormType formType`, `InvoiceQuerySeller seller` zmieniono
  na `String sellerNip`, `InvoiceQueryBuyer buyer` zmieniono na `InvoiceBuyerIdentifier buyerIdentifier`
- **invoice/InvoiceQuerySeller.java**: ➖ usunięto klasę
- **invoice/InvoicingMode.java**: 🔧 zmieniono zmienną na final
- **invoice/QueryInvoiceMetadataResponse.java**: 🔧 `List<InvoiceSummary> invoices` zmieniono
  na `List<InvoiceMetadata> invoices`, dodano pole `Boolean isTruncated`
- **invoice/ThirdSubjectIdentifier.java**: ➕ dodano klasę
- **invoice/ThirdSubjectIdentifierType.java**: ➕ dodano klasę
- **invoice/ThirdSubject.java**: 🔧 pola `IdentifierType identifierType` i `String identifier`
  zastąpiono `ThirdSubjectIdentifier identifier`
- **permission/entity/EntityPermissionType.java**: 🔧 zmiana nazw enumów (wartości pozostają bez zmian)
- **permission/entity/SubjectIdentifier.java**: 🔧 `SubjectIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/entity/SubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/euentity/ContextIdentifier.java**: 🔧 `ContextIdentifierType type` zastąpiono `IdentifierType type` i
  zdefiniowano enuma `IdentifierType`
- **permission/euentity/ContextIdentifierType.java**: ➖ usunięto klasę
- **permission/euentity/EuEntityPermissionsGrantRequest.java**: 🔧 `String subjectName` zmieniono
  na `String euEntityName`
- **permission/euentity/EuEntityPermissionType.java**: 🔧 zmiana nazw enumów (wartości pozostają bez zmian)
- **permission/euentity/SubjectIdentifier.java**: 🔧 `SubjectIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/euentity/SubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/indirect/IndirectPermissionType.java**: 🔧 zmiana nazw enumów (wartości pozostają bez zmian)
- **permission/indirect/SubjectIdentifier.java**: 🔧 `SubjectIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/indirect/SubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/indirect/TargetIdentifier.java**: 🔧 `TargetIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/indirect/TargetIdentifierType.java**: ➖ usunięto klasę
- **permission/person/PersonPermissionsSubjectIdentifier.java**: 🔧 `PersonPermissionsSubjectIdentifierType type`
  zmieniono na `IdentifierType type`, zdefiniowano enuma `IdentifierType`
- **permission/person/PersonPermissionsSubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/proxy/GrantAuthorizationPermissionsRequest.java**: 🔧 `ProxyEntityPermissionType permission` zmieniono
  na `InvoicePermissionType permission;`
- **permission/proxy/SubjectIdentifier.java**: 🔧 `SubjectIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/proxy/SubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/search/EntityAuthorizationGrant.java**: 🔧 `String authorIdentifier`
  i `EntityAuthorizationsAuthorIdentifierType authorIdentifierType` zmieniono
  na `EntityAuthorizationsAuthorIdentifier authorIdentifier`, `String authorizedEntityIdentifier`
  i `EntityAuthorizationsAuthorizedEntityIdentifierType authorizedEntityIdentifierType` zmieniono
  na `EntityAuthorizationsAuthorizedEntityIdentifier authorizedEntityIdentifier`, `String authorizingEntityIdentifier`
  i `EntityAuthorizationsAuthorizingEntityIdentifierType authorizingEntityIdentifierType` zmieniono
  na `EntityAuthorizationsAuthorizingEntityIdentifier authorizingEntityIdentifier`
- **permission/search/EntityAuthorizationsAuthorIdentifier.java**: ➕ dodano klasę
- **permission/search/EntityAuthorizationsAuthorIdentifierType.java**: ➖ usunięto klasę
- **permission/search/EntityAuthorizationsAuthorizedEntityIdentifier.java**:
  🔧 `EntityAuthorizationsAuthorizedEntityIdentifierType type` zmieniono na `IdentifierType type`, zdefiniowano
  enuma `IdentifierType`
- **permission/search/EntityAuthorizationsAuthorizedEntityIdentifierType.java**: ➖ usunięto klasę
- **permission/search/EntityAuthorizationsAuthorizingEntityIdentifier.java**:
  🔧 `EntityAuthorizationsAuthorizingEntityIdentifierType type` zmieniono na `IdentifierType type`, zdefiniowano
  enuma `IdentifierType`
- **permission/search/EntityAuthorizationsAuthorizingEntityIdentifierType.java**: ➖ usunięto klasę
- **permission/search/EntityPermissionsSubordinateEntityIdentifier.java**:
  🔧 `EntityPermissionsSubordinateEntityIdentifierType type` zmieniono na `IdentifierType type`, zdefiniowano
  enuma `IdentifierType`
- **permission/search/EntityPermissionsSubordinateEntityIdentifierType.java**: ➖ usunięto klasę
- **permission/search/EntityRole.java**: 🔧 `String parentEntityIdentifier`
  i `EntityRolesParentEntityIdentifierType parentEntityIdentifierType` zmieniono
  na `EntityRoleQueryParentEntityIdentifier parentEntityIdentifier`
- **permission/search/EntityRoleQueryParentEntityIdentifier.java**: ➕ dodano klasę
- **permission/search/EntityRolesParentEntityIdentifierType.java**: ➖ usunięto klasę
- **permission/search/EntityRoleType.java**: 🔧 zmiana nazw enumów (wartości pozostają bez zmian)
- **permission/search/EuEntityPermission.java**: 🔧 `String authorIdentifier`
  i `EuEntityPermissionsAuthorIdentifierType authorIdentifierType` zmieniono
  na `EuEntityPermissionsAuthorIdentifier authorIdentifier`
- **permission/search/EuEntityPermissionsAuthorIdentifier.java**: ➕ dodano klasę
- **permission/search/EuEntityPermissionsAuthorIdentifierType.java**: ➖ usunięto klasę
- **permission/search/InvoicePermissionType.java**: 🔧 zmiana nazw enumów (wartości pozostają bez zmian), dodano nową
  wartość enuma `PEF_INVOICING("PefInvoicing")`
- **permission/search/PersonPermission.java**: 🔧 `String authorizedIdentifier`
  i `PersonPermissionsAuthorizedIdentifierType authorizedIdentifierType` zmieniono
  na `PersonPermissionsAuthorizedIdentifier authorizedIdentifier`, `String targetIdentifier`
  i `PersonPermissionsTargetIdentifierType targetIdentifierType` zmieniono
  na `PersonPermissionsTargetIdentifier targetIdentifier`, `String authorIdentifier`
  i `PersonPermissionsAuthorIdentifierType authorIdentifierType` zmieniono
  na `PersonPermissionsAuthorIdentifier authorIdentifier`,
  dodano `PersonPermissionsContextIdentifier contextIdentifier`, `PersonPermissionScope permissionScope` zmieniono
  na `PersonPermissionType permissionScope`
- **permission/search/PersonPermissionsAuthorIdentifier.java**: 🔧 `PersonPermissionsAuthorIdentifierType type` zmieniono
  na `IdentifierType type`, zdefiniowano enuma `IdentifierType`
- **permission/search/PersonPermissionsAuthorIdentifierType.java**: ➖ usunięto klasę
- **permission/search/PersonPermissionsAuthorizedIdentifier.java**: 🔧 `PersonPermissionsAuthorizedIdentifierType type`
  zmieniono na `IdentifierType type`, zdefiniowano enuma `IdentifierType`
- **permission/search/PersonPermissionsAuthorizedIdentifierType.java**: ➖ usunięto klasę
- **permission/search/PersonPermissionsContextIdentifier.java**: ➕ dodano klasę
- **permission/search/PersonPermissionsQueryRequest.java**: 🔧 dodano
  pole `PersonPermissionsContextIdentifier contextIdentifier`
- **permission/search/PersonPermissionsTargetIdentifier.java**: 🔧 `PersonPermissionsTargetIdentifierType type` zmieniono
  na `IdentifierType type`, zdefiniowano enuma `IdentifierType`
- **permission/search/PersonPermissionsTargetIdentifierType.java**: ➖ usunięto klasę
- **permission/search/QueryPersonalGrantAuthorizedIdentifier.java**: ➕ dodano klasę
- **permission/search/QueryPersonalGrantContextIdentifier.java**: ➕ dodano klasę
- **permission/search/QueryPersonalGrantItem.java**: ➕ dodano klasę
- **permission/search/QueryPersonalGrantRequest.java**: ➕ dodano klasę
- **permission/search/QueryPersonalGrantResponse.java**: ➕ dodano klasę
- **permission/search/QueryPersonalGrantTargetIdentifier.java**: ➕ dodano klasę
- **permission/search/PersonPermissionScope.java**: 🔧 zmiana nazwy klasy na `QueryPersonalPermissionTypes`, zmiana nazw
  enumów (wartości pozostają bez zmian), dodano nową wartość enuma `VAT_UE_MANAGE("VatUeManage")`, usunięto wartość
  enuma `OWNER("Owner")`
- **permission/search/SubordinateEntityRole.java**: 🔧 `String subordinateEntityIdentifier`
  i `SubordinateRoleSubordinateEntityIdentifierType subordinateEntityIdentifierType` zmieniono
  na `EntityRoleQueryParentEntityIdentifier subordinateEntityIdentifier`
- **permission/search/QuerySubordinateEntityRolesResponse.java**: 🔧 zmiana nazwy klasy
  na `SubordinateEntityRolesQueryResponse`
- **permission/search/SubordinateEntityRoleType.java**: 🔧 zmiana nazw enumów (wartości pozostają bez zmian)
- **permission/search/SubordinateRoleSubordinateEntityIdentifierType.java**: ➖ usunięto klasę
- **permission/search/SubunitPermission.java**: 🔧 `String authorizedIdentifier`
  i `SubunitPermissionsSubjectIdentifierType authorizedIdentifierType` zmieniono
  na `SubunitPermissionsAuthorizedIdentifier authorizedIdentifier`, `String subunitIdentifier`
  i `SubunitPermissionsSubunitIdentifierType subunitIdentifierType` zmieniono
  na `SubunitPermissionsSubunitIdentifier subunitIdentifier`, `String authorIdentifier`
  i `SubunitPermissionsAuthorIdentifierType authorIdentifierType` zmieniono
  na `SubunitPermissionsAuthorIdentifier authorIdentifier`, `SubunitPermissionScope permissionScope` zmieniono
  na `SubunitPermissionType permissionScope`, dodano pole `String subunitName`
- **permission/search/SubunitPermissionsAuthorIdentifier.java**: ➕ dodano klasę
- **permission/search/SubunitPermissionsAuthorIdentifierType.java**: ➖ usunięto klasę
- **permission/search/SubunitPermissionsAuthorizedIdentifier.java**: ➕ dodano klasę
- **permission/search/SubunitPermissionsSubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/search/SubunitPermissionsSubunitIdentifier.java**: 🔧 `SubunitPermissionsSubunitIdentifierType type`
  zmieniono na `IdentifierType type`, zdefiniowano enuma `IdentifierType`
- **permission/search/SubunitPermissionsSubunitIdentifierType.java**: ➖ usunięto klasę
- **permission/search/SubunitPermissionScope.java**: 🔧 nazwę klasy zmieniono na `SubunitPermissionType `, zmiana nazwy
  enuma (wartości pozostają bez zmian)
- **permission/subunit/ContextIdentifier.java**: 🔧 `ContextIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/subunit/ContextIdentifierType.java**: ➖ usunięto klasę
- **permission/subunit/SubjectIdentifier.java**: 🔧 `SubjectIdentifierType type` zmieniono na `IdentifierType type`,
  zdefiniowano enuma `IdentifierType`
- **permission/subunit/SubjectIdentifierType.java**: ➖ usunięto klasę
- **permission/subunit/SubunitPermissionsGrantRequest.java**: 🔧 dodano pole `String subunitName`
- **session/AuthenticationMethod.java**: 🔧 dodano wartość enuma `PEPPOL_SIGNATURE("PeppolSignature")`
- **session/BatchSessionLimit.java**: ➕ dodano klasę
- **session/ChangeContextLimitRequest.java**: ➕ dodano klasę
- **session/FormCode.java**: 🔧 `String schemaVersion` zmiana na `SchemaVersion schemaVersion`, `String value` zmiana
  na `SessionValue value`
- **session/GetContextLimitResponse.java**: ➕ dodano klasę
- **session/OnlineSessionLimit.java**: ➕ dodano klasę
- **session/SchemaVersion.java**: ➕ dodano klasę
- **session/SessionInvoicesResponse.java**: 🔧 usunięto pola `Integer totalCount` i `Boolean hasMore`
- **session/SessionInvoiceStatusResponse.java**: 🔧 dodano pola `InvoicingMode invoicingMode`
- **session/SessionValue.java**: ➕ dodano klasę
- **session/SystemCode.java**: 🔧 dodano wartości enuma `FA_PEF_3("FA_PEF (3)"), FA_KOR_PEF_3("FA_KOR_PEF (3)")`
- **testdata/SubjectTypeTestData.java**: ➕ dodano klasę
- **testdata/Subunit.java**: ➕ dodano klasę
- **testdata/TestDataAttachmentRemoveRequest.java**: ➕ dodano klasę
- **testdata/TestDataAttachmentRequest.java**: ➕ dodano klasę
- **testdata/TestDataAuthorizedIdentifier.java**: ➕ dodano klasę
- **testdata/TestDataContextIdentifier.java**: ➕ dodano klasę
- **testdata/TestDataPermission.java**: ➕ dodano klasę
- **testdata/TestDataPermissionRemoveRequest.java**: ➕ dodano klasę
- **testdata/TestDataPermissionRequest.java**: ➕ dodano klasę
- **testdata/TestDataPersonCreateRequest.java**: ➕ dodano klasę
- **testdata/TestDataPersonRemoveRequest.java**: ➕ dodano klasę
- **testdata/TestDataSubjectCreateRequest.java**: ➕ dodano klasę
- **testdata/TestDataSubjectRemoveRequest.java**: ➕ dodano klasę
- **pepol/PeppolProvider.java**: ➕ dodano klasę
- **pepol/PeppolProvidersListResponse.java**: ➕ dodano klasę

### 1.6 client

- **Parameter.java**: 🔧 dodano
  stałe `String DESCRIPTION = "description"`, `String AUTHOR_IDENTIFIER = "authorIdentifier"`, `String AUTHOR_IDENTIFIER_TYPE = "authorIdentifierType"`

### 1.7 system

### 1.8 resources

- **ksefApi.yaml**: ➖ usunięto plik

### 1.8 test

- **AuthTokenRequestSerializerTest.java**: 🔧 zmiana wartości w `EXPECTED_XML_VALUE`

- 🔧 build.gradle.kts - podbicie wersji bibliotek

## 2. demo-web-app

### 2.1 integrationTest

- **BaseIntegrationTest.java**: 🔧 ksefClient jako bean
- **AuthorizationIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **BatchIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`
- **CertificateIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`
- **ContextLimitIntegrationTest.java**: ➕ dodano klasę (tymczasowo test wyłączony)
- **EntityPermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **EuEntityPermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **EuEntityRepresentativePermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **IndirectPermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu, poprawa testów
- **KsefTokenIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **OnlineSessionIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **PeppolProviderIntegrationTest.java**: ➕ dodano nowe scenariusze testowe
- **SearchEntityInvoiceRoleIntegrationTest.java**: ➕ dodano nowe scenariusze testowe
- **SearchSubordinateQueryIntegrationTest.java**: ➕ dodano nowe scenariusze testowe
- **PersonPermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **ProxyPermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **QrCodeOfflineIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianami w `cryptographyService`, dodanie parametrów do tesótów z
  fa(2) i fa(3) z RSA i ECDSA
- **QrCodeOnlineIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **QueryInvoiceIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **SearchPersonalGrantPermissionIntegrationTest.java**: ➕ dodano nowe scenariusze testowe
- **SessionIntegrationTest.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **SubUnitPermissionIntegrationTest.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **EntityPermissionAccountingIntegrationTest.java**: ➕ dodano nowe scenariusze testowe

### 2.1.1 integrationTest.resources
-

### 2.2 api

- **ActiveSessionController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`; aktualizacje
  związane ze zmianą w modelu
- **AuthController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`; aktualizacje związane
  ze zmianą w modelu
- **BatchSessionController.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **CertificateController.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **EntityPermissionsController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **EuEntityPermissionsController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **EuEntityRepresentativePermissionsController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **IndirectPermissionsEntityController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **InvoicesController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`; aktualizacje
  związane ze zmianą w modelu
- **OnlineSessionController.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją
  w `KsefClientConfig.java`; aktualizacje związane ze zmianą w modelu
- **OperationStatusController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **PersonPermissionController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **ProxyPermissionsEntityController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`;
  aktualizacje związane ze zmianą w modelu
- **QrCodeController.java**: 🔧 ksefClient i cryptographyService jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **SearchPermissionTestEndpoint.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **SessionController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **SubUnitPermissionsController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`
- **TokensController.java**: 🔧 ksefClient jako bean zgodnie z deklaracją w `KsefClientConfig.java`; aktualizacje
  związane ze zmianą w modelu
- **ExampleApiProperties.java**: 🔧 dodanie adresu testowego środowiska
- **HttpClientBuilder.java**: 🔧 użycie konfiguracji z `HttpClientConfig.java`
- **HttpClientConfig.java**: ➕ dodanie konfiguracji do klienta http
- **KsefClientConfig.java**: 🔧 inicjalizacja beanów dla KsefClient i CryptographyService

### 2.2.1 resources
-

### 2.3 test - api.services

- **QrCodeTests.java**: 🔧 zmiana typów
- **VerificationLinkServiceTests.java**: 🔧 zmiana typów
-

## 3. .http

- 🔧 do `auth.http` w usługach dodano nagłówek z tokenem
- 🔧 do `batch.http` w usługach dodano nagłówek z tokenem
- 🔧 do `certificate.http` w usługach dodano nagłówek z tokenem
- 🔧 do `entity-permission.http` w usługach dodano nagłówek z tokenem
- 🔧 do `eu-entity-permission.http` w usługach dodano nagłówek z tokenem
- 🔧 do `eu-entity-representative-permission.http` w usługach dodano nagłówek z tokenem
- 🔧 do `grantPermission.http` w usługach dodano nagłówek z tokenem
- 🔧 do `invoice.http` w usługach dodano nagłówek z tokenem
- 🔧 do `personalPremissions.http` w usługach dodano nagłówek z tokenem
- 🔧 do `searchPremissions.http` w usługach dodano nagłówek z tokenem; zmieniono pageSize na 10
- 🔧 do `session.http` w usługach dodano nagłówek z tokenem
- 🔧 do `sessionAndUpo.http` w usługach dodano nagłówek z tokenem
- 🔧 do `subunit-subject-permission.http` w usługach dodano nagłówek z tokenem
- 🔧 do `tokens.http` w usługach dodano nagłówek z tokenem

- 🔧 build.gradle.kts - podbicie wersji bibliotek

## 4. Podsumowanie

| Typ zmiany   | Liczba plików |
|--------------|---------------|
| ➕ dodane     | 42            |
| 🔧 zmienione | 128           |
| ➖ usunięte   | 26            |

---

---
# Changelog zmian – `## 3.0.2 (2025-10-14)` - API 2.0.0 RC5.3
## 1. ksef-client

### 1.1 api.builders

### 1.2 api.services

- **DefaultKsefClient.java**: 🔧
  - aktualizacje związane z modelem
  - dodanie usług związanych z limitami podmiotu
  - dodanie usługi sprawdzającej czy posiadane jest uprawnienie do wysłania faktury z załacznikami
  - upublicznie metod związanych z wysyłaniem pojedyńczych części w procesie batchowym
  - zmiana konstruktora w celu umożliwienie współdzielenia klienta http
  - poprawiono implementacje metody getSessions
- **Url.java**: 🔧 dodanie usług zgodnie z api

### 1.3 api

### 1.4 client.interfaces

- **KSeFClient.java**: 🔧 aktualizacje związane z modelem, dodanie usług zgodnie z api

### 1.5 client.model

- **limit/CertificateLimit.java**: ➕ dodano klasę
- **limit/ChangeSubjectCertificateLimitRequest.java**: ➕ dodano klasę
- **limit/EnrollmentLimit.java**: ➕ dodano klasę
- **limit/GetSubjectLimitResponse.java**: 🔧 ➕ dodano klasę
- **limit/BatchSessionLimit.java**: 🔧 przeniesiono klasę do pakietu, dodano nowe pola `maxInvoiceSizeInMB` oraz `maxInvoiceWithAttachmentSizeInMB`, pola `maxInvoiceSizeInMib` oraz `maxInvoiceWithAttachmentSizeInMib` oznaczono joko deprecated
- **limit/ChangeContextLimitRequest.java**: 🔧 przeniesiono klasę do pakietu
- **limit/GetContextLimitResponse.java**: 🔧 przeniesiono klasę do pakietu
- **limit/OnlineSessionLimit.java**: 🔧 przeniesiono klasę do pakietu, , dodano nowe pola `maxInvoiceSizeInMB` oraz `maxInvoiceWithAttachmentSizeInMB`, pola `maxInvoiceSizeInMib` oraz `maxInvoiceWithAttachmentSizeInMib` oznaczono joko deprecated
- **invoice/InitAsyncInvoicesQueryResponse.java**: 🔧 dodano nowe pole `referenceNumber`, pole `operationReferenceNumber` oznaczono jako deprecated, będzie wycofane w następnej wersji
- **permission/OperationResponse.java**: 🔧 dodano nowe pole `referenceNumber`, pole `operationReferenceNumber` oznaczono jako deprecated, będzie wycofane w następnej wersji
- **permission/PermissionAttachmentStatusResponse.java**:➕ dodano klasę
- **session/SystemCode.java**: 🔧 zmiana wartości enumów dla faktury PEF
- **Pair.java**: ➖ usunięto klasę
- **xml/*.java**: 🔧 zmiany związane z kodowaniem

### 1.6 client

- **Headers.java**: 🔧 dodano stałą `String X_KSEF_FEATURE = "X-KSeF-Feature"`,
- **Parameter.java**: 🔧 dodano stałą `String DATE_MODIFIED_TO = "dateModifiedTo` oraz `String STATUSES = "statuses"`,

### 1.7 system

### 1.8 resources

### 1.8 test

- 🔧 build.gradle.kts
  - cofnięcie wersji javy do 11
  - dodanie domyślnego kodowania UTF-8

## 2. demo-web-app

### 2.1 integrationTest

- **BaseIntegrationTest.java**: 🔧 zmiana nazwy metody `isSessionStatusReady` na `isAuthProcessReady`
- **ContextLimitIntegrationTest.java**: 🔧 włączono testy i zaktualizowano scenariusz
- **EntityPermissionAccountingIntegrationTest.java**: ➕ dodano nowe scenariusze testowe
- **EntityPermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **EuEntityPermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **EuEntityRepresentativePermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **IndirectPermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **PeppolProviderIntegrationTest.java**: 🔧 zmieniono nazwę klasy na `PeppolIIntegrationTest.java` i zmieniono nazwę metody testu
- **PermissionAttachmentStatusIntegrationTest.java**: ➕ dodano nowe scenariusze testowe
- **PersonPermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **ProxyPermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **QueryInvoiceIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych
- **SessionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego
- **SubjectLimitIntegrationTest.java**:  ➕ dodano klasę
- **SubUnitPermissionIntegrationTest.java**: 🔧 zmiany w testach związane z modelem danych

### 2.1.1 integrationTest.resources
-

### 2.2 api
- **KsefClientConfig.java**: 🔧 apiClient przekazywane do DefaultKsefClient

### 2.2.1 resources
-

### 2.3 test - api.services

## 3. .http

## 4. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 8             |
| 🔧 zmienione | 36            |
| ➖ usunięte  | 1             |

---
# Changelog zmian - `## 3.0.3 (2025-10-22)` - `API: 2.0.0 RC5.3`
## 1. ksef-client

### 1.1 api.builders
- **AuthTokenRequestBuilder.java**: 🔧 - aktualizacja zgodnie z modelem, zmiana pola `AuthTokenRequest.IpAddressPolicy ipPolicy` na `AuthTokenRequest.AuthorizationPolicy authorizationPolicy`, dodanie metody ` AuthTokenRequestBuilder withPeppolId(String value)`

### 1.2 api.services
- **DefaultCertificateService.java**: 🔧 - przeciążenie metod `SelfSignedCertificate getPersonalCertificate` i `SelfSignedCertificate getCompanySeal` o dodatkowy parametr `EncryptionMethod encryptionMethod` (Rsa i ECDsa)
- **DefaultCryptographyService.java**: 🔧 - dodanie metod `byte[] decryptBytesWithAes256(byte[] encryptedPackagePart, byte[] cipherKey, byte[] cipherIv)` i `void decryptStreamBytesWithAes256(InputStream encryptedPackagePart, OutputStream output, byte[] cipherKey, byte[] cipherIv)`
- **DefaultSignatureService.java**: 🔧 - zmiany związane z podpisywaniem dokumentów dla ECDsa

### 1.3 api
- **DefaultKsefClient.java**: 🔧
  - w `InitAsyncInvoicesQueryResponse initAsyncQueryInvoice` dodano nagłówek `"x-ksef-feature", "include-metadata"` który w API będzie domyślnym od 2025-10-27
  - dla metody `SessionsQueryResponse getSessions(...)` zmieniono obsługę parametrów dla urla aby przyjmowała duplikaty kluczy, np `?statuses=InProgress&statuses=Succeeded`
  - poprawki w urlach dla metod `resetContextLimitTest`, `resetSubjectCertificateLimit`
  - dla metod `singleBatchPartSendingProcessByStream` i `singleBatchPartSendingProcess` wprowadzono poprawkę umożliwiającą przesyłanie dużych plików
  - dodano metodę do pobierania części paczek eksportu ` byte[] downloadPackagePart(InvoicePackagePart part)`
- **HttpUtils.java**: 🔧 - zmiany związane z obsługą parametrów dla urla aby przyjmowała duplikaty kluczy

### 1.4 client.interfaces
- **CertificateService.java**: 🔧 - przeciążenie metod `SelfSignedCertificate getPersonalCertificate` i `SelfSignedCertificate getCompanySeal` o dodatkowy parametr `EncryptionMethod encryptionMethod` (Rsa i ECDsa)
- **CryptographyService.java**: 🔧 - dodanie definicji metod `byte[] decryptBytesWithAes256(byte[] encryptedPackagePart, byte[] cipherKey, byte[] cipherIv)` i `void decryptStreamBytesWithAes256(InputStream encryptedPackagePart, OutputStream output, byte[] cipherKey, byte[] cipherIv)`
- **KSeFClient.java**: 🔧 - dodano definicję metody do pobierania części paczek eksportu ` byte[] downloadPackagePart(InvoicePackagePart part)`

### 1.5 client.model

- **invoice/InvoicePackageMetadata.java**: ➕ dodano klasę
- **permission/proxy/SubjectIdentifier.java**: 🔧 dodano nową wartośc enuma `PEPPOL_ID("PeppolId")`
- **session/SessionValue.java**: 🔧 zmiana wartości enumów dla faktury PEF
- **session/SystemCode.java**: 🔧 zmiana wartości enumów dla faktury PEF
- **xml/*.java**: 🔧 zmiany związane z aktualizacją xsd

### 1.6 client
- **Headers.java**: 🔧 usunięto `String BLOCK_BLOB = "BlockBlob"` i `String X_MS_BLOB_TYPE = "x-ms-blob-type"`

### 1.7 sign
- **CertUtil.java**: ➕ dodano klasę pomocniczą
- **LocalSigningContext.java**: 🔧 zmiany związane z podpisywaniem dokumentów dla ECDsa

### 1.8 system

### 1.9 resources
- **AuthTokenRequest.xsd**: 🔧 aktualizacja xsd

### 1.10 test
- **AuthTokenRequestSerializerTest.java**: 🔧 aktualizacja po zmianach w xsd

## 2. demo-web-app

### 2.1 integrationTest

- **BaseIntegrationTest.java**: 🔧
  - dodano pole `ObjectMapper objectMapper`
  - przeciążenie metody `AuthTokensPair authWithCustomNip(...)` o dodanie parametry `EncryptionMethod encryptionMethod` (Rsa i ECDsa)
  - dodanie metody `AuthTokensPair authAsPeppolProvider(String peppolId)`
- **BaseIntegrationTest.java**: 🔧 dodanie beana `ObjectMapper objectMapper()`
- **AuthorizationIntegrationTest.java**: 🔧 dodanie testu z autentykacją dla ECDsa
- **BatchIntegrationTest.java**: 🔧 rozbudowa scenariuszy testowych
- **CertificateIntegrationTest.java**: 🔧 rozbudowa scenariuszy testowych
- **ContextLimitIntegrationTest.java**: 🔧 zmiany w asercjach
- **PeppolIIntegrationTest.java**: 🔧 zastąpiono `PeppolInvoiceIntegrationTest.java` + rozbudowa scenariuszy testowych
- **QueryInvoiceIntegrationTest.java**: 🔧 rozbudowa scenariuszy testowych o pobranie części paczek
- **SessionIntegrationTest.java**: 🔧 rozbudowa scenariuszy testowych o obsługę duplikatów w parametrze statuses dla `/api/v2/sessions` (`SessionsQueryResponse getSessions(...)`)
- **SubjectLimitIntegrationTest.java**: 🔧 zmiany w asercjach

### 2.1.1 integrationTest.resources
- **invoice_template_pef_attachment.xml**:  ➕ dodano plik
- **invoice_template_pef_correction.xml**:  ➕ dodano plik
- **invoice_template_pef.xml**:  ➕ dodano plik

### 2.2 api
- **ExampleApiProperties.java**: 🔧 zmieniono wartość timeoutu (dla przesyłania dużych plików)
- **FilesUtil.java**:  ➕ dodano klasę pomocniczą do obsługi plików/zipów
- **IdentifierGeneratorUtils.java**: 🔧 dodano metody `String generatePeppolId()`, `String generateRandomPolishAccountNumber()`, `String generatePolishAccountNumber(String bankCode)`, `String generateIban()` + prywatne metody pomocnicze

### 2.2.1 resources

### 2.3 test - api.services

## 3. .http

- uwspólnienie wersji z demo-web-app z ksef-client

## 4. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 7             |
| 🔧 zmienione | 30            |
| ➖ usunięte  | 1             |

---
# Changelog zmian - `## 3.0.4 (2025-11-06)` - `API: 2.0.0 RC5.6`
## 1. ksef-client
- **build.gradle.kts**: 🔧 dodano publikację artefaktów na github package
- **maven-package.md**: 🔧 dodano opis publikacji artefaktów na github package
- **README.md**: 🔧 dodano publikację artefaktów na github package

### 1.1 api.builders

### 1.2 api.services
- **DefaultCryptographyService.java**: 🔧 dodano implementację metody `X509Certificate parseCertificateFromBytes(byte[] certBytes)`
- **DefaultSignatureService.java**: 🔧 rozszerzono exception message

### 1.3 api
- **DefaultKsefClient.java**: 🔧 refaktor sposobu walidacji responsów, usunięcie nieistniejącej w API metody `byte[] getInvoice(DownloadInvoiceRequest downloadInvoiceRequest, String accessToken)`, dodanie `SortOrder` do `QueryInvoiceMetadataResponse queryInvoiceMetadata` i oznaczenie poprzedniej wersji jako deprecated, dodanie `GetRateLimitResponse getRateLimit(String accessToken)`
- **HttpStatus.java**: 🔧 rozszerzenie enuma o nowe kody http
- **HttpUtils.java**: 🔧 drobne zmiany w walidacji responsów
- **Url.java**: 🔧 dodanie ` GET_RATE_LIMIT("/api/v2/rate-limits", "apiV2RateLimit"),`, usunięcie `INVOICE_DOWNLOAD("/api/v2/invoices/download", "apiV2InvoicesDownloadPost"),`

### 1.4 client.interfaces
- **CryptographyService.java**: 🔧 dodano implementację metody `X509Certificate parseCertificateFromBytes(byte[] certBytes)`
- **KsefClient.java**: 🔧 usunięcie nieistniejącej w API metody `byte[] getInvoice(DownloadInvoiceRequest downloadInvoiceRequest, String accessToken)`, dodanie `SortOrder` do `QueryInvoiceMetadataResponse queryInvoiceMetadata` i oznaczenie poprzedniej wersji jako deprecated, dodanie `GetRateLimitResponse getRateLimit(String accessToken)`

### 1.5 client.model
- **certificate/CertificateInfo.java**: 🔧 dodanie `OffsetDateTime requestDate`
- **invoice/DownloadInvoiceRequest.java**: ➖ usunięcie klasy
- **invoice/DwonloadInvoiceMetadata.java**: ➖ usunięcie klasy
- **invoice/InitAsyncInvoicesQueryResponse.java**: 🔧 usunięcie pola `String operationReferenceNumber` wcześniej oznaczonego jako deprecated 
- **invoice/InvoiceExportStatus.java**: 🔧 dodanie `OffsetDateTime packageExpirationDate` 
- **invoice/InvoiceMetadata.java**: 🔧 dodanie metod do porównywania obiektu 
- **invoice/InvoicePackagePart.java**: 🔧 drobne zmiany 
- **limit/BatchSessionLimit.java**: 🔧 usunięcie pól `int maxInvoiceSizeInMib`, `int maxInvoiceWithAttachmentSizeInMib` wcześniej oznaczonych jako deprecated
- **limit/BatchSessionRateLimit.java**: ➕ dodanie klasy
- **limit/GetRateLimitResponse.java**: ➕ dodanie klasy
- **limit/InvoiceDownloadRateLimit.java**: ➕ dodanie klasy
- **limit/InvoiceExportRateLimit.java**: ➕ dodanie klasy
- **limit/InvoiceMetadataRateLimit.java**: ➕ dodanie klasy
- **limit/InvoiceSendRateLimit.java**: ➕ dodanie klasy
- **limit/InvoiceStatusRateLimit.java**: ➕ dodanie klasy
- **limit/OnlineSessionLimit.java**: 🔧 usunięcie pól `int maxInvoiceSizeInMib`, `int maxInvoiceWithAttachmentSizeInMib` wcześniej oznaczonych jako deprecated
- **limit/OnlineSessionRateLimit.java**: ➕ dodanie klasy
- **limit/OtherRateLimit.java**: ➕ dodanie klasy
- **limit/SessionInvoiceListRateLimit.java**: ➕ dodanie klasy
- **limit/SessionListRateLimit.java**: ➕ dodanie klasy
- **limit/SessionMiscRateLimits.java**: ➕ dodanie klasy
- **permission/OperationResponse.java**: 🔧 usunięcie pola `String operationReferenceNumber` wcześniej oznaczonego jako deprecated 
- **session/SessionInvoiceStatusResponse.java**: 🔧 dodanie pola `String upoDownloadUrlExpirationDate` 
- **session/UpoPageResponse.java**: 🔧 dodanie pola `OffsetDateTime downloadUrlExpirationDate` 
- **util/SortOrder.java**: ➕ dodanie klasy 
- **Headers.java**: ➕ usunięcie `String X_KSEF_FEATURE = "X-KSeF-Feature"`
- **Parameter.java**: ➕ dodanie `String SORT_ORDER = "sortOrder"`

### 1.6 client
- **peppol/PeppolProvider.java**: ➕ dodanie klasy

### 1.7 sign
- **CertUtil.java**: 🔧 drobne zmiany kosmetyczne
- **LocalSigningContext.java**: 🔧 rozszerzono exception message

### 1.8 system

### 1.9 resources

### 1.10 test
- **CertUtilTest.java**: 🔧 dodanie testów

## 2. demo-web-app
- **KsefClientConfig.java**: 🔧 dodano do `ObjectMapper` jako domyślny property `DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES` na `false`

### 2.1 integrationTest
- **model/ExportTask.java**: ➕ dodano klasę modelu w testach do przyrostowego pobierania faktur
- **model/PackageProcessingResult.java**: ➕ dodano klasę modelu w testach do przyrostowego pobierania faktur
- **model/TimeWindows.java**: ➕ dodano klasę modelu w testach do przyrostowego pobierania faktur
- **AuthorizationIntegrationTest.java**: 🔧 dodano nowe scenariusze testowe
- **BatchIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **EuEntityPermissionIntegrationTest.java**: 🔧 poprawki w scenariuszu
- **GetRateLimitIntegrationTest.java**: ➕ dodano klasę ze scenariuszem do limitów API
- **IncrementalInvoiceRetrieveIntegrationTest.java**: ➕ dodano klasę ze scenariuszami przyrostowego pobierania faktur
- **KsefTokenIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **OnlineSessionIntegrationTest.java**: 🔧 poprawki w scenariuszu
- **QueryInvoiceIntegrationTest.java**: 🔧 poprawki w scenariuszu, dodanie parametru z sortowaniem
- **SearchPersonalGrantPermissionIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **SearchSubordinateQueryIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **SubUnitPermissionIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
 
### 2.1.1 integrationTest.resources
- **KsefClientConfig.java**: 🔧 `KsefApiProperties` jako parametr dla `DefaultVerificationLinkService` 

### 2.2 api
- **InvoicesController.java**: 🔧 usunięcie użycia nieistniejącego endpointu w API, dodanie parametru z sortowaniem

### 2.2.1 resources

### 2.3 test - api.services
- **QrCodeTests.java**: 🔧 `KsefApiProperties` jako parametr dla `DefaultVerificationLinkService` 
- **VerificationLinkServiceTests.java**: 🔧 `KsefApiProperties` jako parametr dla `DefaultVerificationLinkService` 

## 3. .http


## 4. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 21           |
| 🔧 zmienione | 43           |
| ➖ usunięte  | 2            |

---
# Changelog zmian - `## 3.0.5 (2025-11-20)` - `API: 2.0.0 RC5.7`
## 1. ksef-client

### 1.1 api.builders
- **OpenBatchSessionRequestBuilder.java**: 🔧 oznaczenie metody `addBatchFilePart` z polem `String fileName` jako deprecated zgodnie z kontraktem

### 1.2 api.services
- **DefaultCryptographyService.java**: 🔧 inicjalizacja beana (pobieranie certyfikatu API KSeF) w przypadku niepowodzenia przestawia `KsefIntegrationMode` na `OFFLINE`, jeśli `KsefIntegrationMode getKsefIntegrationMode()` zwróci `FALSE` to można ponowić inicjalizację poprzez `initCryptographyService()`   
- **DefaultSignatureService.java**: 🔧 zmiana pakietów dla `CommonCertificateVerifier` związana z aktualizacją zależności   
- **DefaultVerificationLinkService.java**: 🔧 poprawki dla generowanych linków weryfikacyjnych   

### 1.3 api
- **DefaultKsefClient.java**: 🔧 kosmetyczne zmiany w walidacji odpowiedzi i walidacja kodów http
- **HttpUtils.java**: 🔧 kosmetyczne zmiany w walidacji odpowiedzi

### 1.4 client.interfaces
- **CryptographyService.java**: 🔧 dodanie metod `void initCryptographyService()` i `KsefIntegrationMode getKsefIntegrationMode()` pomocnych przy inicjalizacji `DefaultCryptographyService`

### 1.5 client.model
- **session/batch/BatchFilePartInfo.java**: 🔧 oznaczenie pola `String fileName` jako `@Deprecated(since = "planowane usunięcie: 2025-12-05")`
- **ApiException.java**: 🔧 oznaczenie pola `String responseBody` jako `@Deprecated`, dodanie pola `ExceptionResponse exceptionResponse`, dodanie getterów do `ExceptionResponse getExceptionResponse()` i `HttpHeaders getResponseHeaders()` 
- **ExceptionObject.java**: ➕ dodanie klasy  
- **ExceptionResponse.java**: ➕ dodanie klasy  
- **ExceptionResponse.java**: ➕ dodanie klasy  

### 1.6 client
- **ExceptionDetails.java**: ➕ dodanie klasy  

### 1.7 sign

### 1.8 system
- **CryptographyException.java**: ➕ dodanie klasy  
- **KsefIntegrationMode.java**: ➕ dodanie klasy  
- **SystemKSeFSDKException .java**: 🔧 dodanie konstruktora  

### 1.9 resources

### 1.10 test

- podbicie wersji bibliotek
## 2. demo-web-app

### 2.1 integrationTest
- **BaseIntegrationTest.java**: 🔧 dodanie pomocniczej metody `byte[] readBytesFromPath(String path)`
- **BatchIntegrationTest.java**: 🔧 aktualizacje w asercjach dot. exception response
- **IncrementalInvoiceRetrieveIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **OnlineSessionIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **PeppolInvoiceIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **QrCodeOfflineIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego o weryfikację z linków (certyfikatu i faktury), dodanie testu z wczytaniem certyfikatu z dysku i pomocniczo wysyłki csr
- **QrCodeOnlineIntegrationTest.java**: 🔧 drobne zmiany kosmetyczne
- **QueryInvoiceIntegrationTest.java**: 🔧 aktualizacje w asercjach dot. exception response
- **SelfInvoicingIntegrationTest.java**: ➕ dodanie nowego scenariusza testowego
- **SessionIntegrationTest.java**: 🔧 aktualizacje w asercjach dot. exception response
- **SearchInvoiceForSubject2IntegrationTest.java**: ➕ dodanie nowego scenariusza testowego
- **SearchInvoiceForSubject3IntegrationTest.java**: ➕ dodanie nowego scenariusza testowego

### 2.1.1 integrationTest.resources
- **keys/private/rsa/sample/private-key.pem**: ➕ dodanie przykładowego klucza prywatnego RSA
- **keys/private/rsa/sample/public-key.pem**: ➕ dodanie przykładowego klucza prywatnego RSA
- **xml/invoices/sample/invoice_template_v3_self_invoicing.xml**: ➕ dodanie przykładowej faktury z samofakturowaniem
- **xml/invoices/sample/invoice-template-fa-3-with-custom-subject_2.xml**: ➕ dodanie przykładowej faktury z zmieniającym się podmiotem 2
- **xml/invoices/sample/invoice-template-fa-3-with-custom-subject_3.xml**: ➕ dodanie przykładowej faktury z zmieniającym się podmiotem 3

### 2.2 api

### 2.2.1 resources

### 2.3 test - api.services

## 3. .http

- podbicie wersji spring boot
## 4. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 15            |
| 🔧 zmienione | 22            |
| ➖ usunięte  | 0             |
---
# Changelog zmian - `## 3.0.6 (2025-12-04)` - `API: 2.0.0 RC6.0`

## 1. ksef-client

### 1.1 api.builders
- Dodano właściwość subjectDetails - "Dane podmiotu, któremu nadawane są uprawnienia" do wszystkich endpointów nadających uprawnienia **/permissions/.../grants. W RC6.0 pole jest opcjonalne; od 2025-12-19 będzie wymagane.)
- **GrantEntityPermissionsRequestBuilder.java**: 🔧 dodanie pola `subjectDetails`
- **GrantEUEntityPermissionsRequestBuilder.java**: 🔧 dodanie pól `subjectDetails` i `euEntityDetails`
- **GrantEUEntityRepresentativePermissionsRequestBuilder.java**: 🔧 dodanie pola `subjectDetails`
- **GrantIndirectEntityPermissionsRequestBuilder.java**: 🔧 dodanie pola `subjectDetails`
- **GrantPersonPermissionsRequestBuilder.java**: 🔧 dodanie pola `subjectDetails`
- **GrantAuthorizationPermissionsRequestBuilder.java**: 🔧 dodanie pola `subjectDetails`
- **SubunitPermissionsGrantRequestBuilder.java**: 🔧 dodanie pola `subjectDetails`

### 1.2 api.services
- **DefaultCryptographyService.java**: 🔧 w `initCryptographyService` przechwycenie `SystemKSeFSDKException`
- **DefaultVerificationLinkService.java**: 🔧 konstruktor z polem `KsefApiProperties` oznaczony jako deprecated, dodano nowy konstruktor z polem `String appUrl`

### 1.3 api
- **DefaultKsefClient.java**: 🔧 w metodzie `validResponse` dodano pomijanie parsowania jsona w przypadku odpowiedzi z API typów innych niż json,
  - Dodano parametr `upoVersion` w metodach `openBatchSession` i `openOnlineSession`
    - Pozwala wybrać wersję UPO (dostępne wartości: `"upo-v4-3"`)
    - Ustawia nagłówek `X-KSeF-Feature` z odpowiednią wersją
    - Domyślnie: v4-2, od 5.01.2026 → v4-3
    - poprzednie wersje metod oznaczone jako deprecated
- **HttpUtils.java**: 🔧 w metodzie `formatExceptionMessage` dodano dodatkowe sprawdzanie dla pustego body 

### 1.4 client.interfaces
- **KSeFClient.java**: 🔧 metody `openBatchSession` i `openOnlineSession` zgodnie z opisem w implementacji `DefaultKsefClient` 

### 1.5 client.model
- **auth/TokenPermissionType.java**: 🔧 dodano `PEPPOL_ID("PeppolId")`
- **invoice/InitAsyncInvoicesQueryResponse.java**: 🔧 zmiana modyfikatora dostępu na prywatny dla pola `String referenceNumber`
- **invoice/InvoiceExportPackage.java**: 🔧 dodanie opisów dla pól i dodanie pola `OffsetDateTime permanentStorageHwmDate`
- **invoice/InvoiceQueryDateRange.java**: 🔧 dodanie pola `Boolean restrictToPermanentStorageHwmDate`
- **invoice/InvoiceQueryFilters.java**: 🔧 poprawka w mapowaniu pola isSelfInvoicing (`@JsonProperty("isSelfInvoicing")`)
- **invoice/QueryInvoiceMetadataResponse.java**: 🔧 dodanie pola `OffsetDateTime permanentStorageHwmDate`
- Dodano właściwość subjectDetails - "Dane podmiotu, któremu nadawane są uprawnienia" do wszystkich endpointów nadających uprawnienia **/permissions/.../grants. W RC6.0 pole jest opcjonalne; od 2025-12-19 będzie wymagane.)
- **permission/entity/GrantEntityPermissionsRequest.java**: 🔧 dodanie pola `subjectDetails`
- **permission/euentity/EuEntityPermissionsGrantRequest.java**: 🔧 dodanie pól `subjectDetails`, `euEntityDetails` i podtypów 
- **permission/euentity/GrantEUEntityRepresentativePermissionsRequest.java**: 🔧 dodanie pola `subjectDetails` i podtypów 
- **permission/indirect/GrantIndirectEntityPermissionsRequest.java**: 🔧 dodanie pola `subjectDetails` i podtypów 
- **permission/person/GrantPersonPermissionsRequest.java**: 🔧 dodanie pola `subjectDetails` i podtypów 
- **permission/proxy/GrantAuthorizationPermissionsRequest.java**: 🔧 dodanie pola `subjectDetails` 
- **permission/subunit/SubunitPermissionsGrantRequest.java**: 🔧 dodanie pola `subjectDetails` i podtypów 
- **TestDataAttachmentRemoveRequest.java**: 🔧 dodanie pola `OffsetDateTime expectedEndDate` 
- **TestDataPersonCreateRequest.java**: 🔧 dodanie pola `Boolean isDeceased` 
- **ZipInputStreamWithSize.java**: ➕ dodanie nowego modelu
- **StatusInfo.java**: 🔧 dodanie nowego pola `Map<String, String> extensions`

### 1.6 client
- **Headers.java**: 🔧 dodanie nowego pola `String X_KSEF_FEATURE = "X-KSeF-Feature"`

### 1.7 system
- **FilesUtil.java**: 🔧 przeniesione z modułu demo

## 2. demo-web-app

### 2.1 integrationTest
- **BaseIntegrationTest.java**: 🔧 dodanie metody `AuthTokensPair authWithCustomPesel(String context, String pesel, EncryptionMethod encryptionMethod)`
- **IntegrationConfig.java**: 🔧 przekazanie do ObjectMappera domyślnej wartości property `DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false`
- **BatchIntegrationTest.java**: 🔧 kosmetyczne zmiany związane ze zmianą pakietów `FilesUtil.java`
- **DuplicateInvoiceIntegrationTest.java**: ➕ dodanie nowego scenariusza testowego
- **EnforcementOperationIntegrationTest.java**: ➕ dodanie nowego scenariusza testowego
- **EnforcementOperationNegativeIntegrationTest.java**: ➕ dodanie nowego scenariusza testowego
- **IncrementalInvoiceRetrieveIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego
- **PersonalPermissionAuthorizedPeselInNipContext.java**: ➕ dodanie nowego scenariusza testowego
- **QueryInvoiceIntegrationTest.java**: 🔧 kosmetyczne zmiany związane ze zmianą pakietów `FilesUtil.java`
- **SearchEntityInvoiceRoleIntegrationTest.java**: 🔧 poprawki w scenariuszu testowym
- **SearchInvoiceForSubject2IntegrationTest.java**: 🔧 aktualizacja scenariusza testowego
- **SearchInvoiceForSubject3IntegrationTest.java**: 🔧 aktualizacja scenariusza testowego
- **SubUnitPermissionIntegrationTest.java**: 🔧 aktualizacja scenariusza testowego

### 2.2 api
- **ExampleApiProperties.java**: 🔧 zmiany w sposobie dostarczania konfiguracji w module demo `application.yaml`
- **KsefClientConfig.java**: 🔧 wczytanie urla aplikacji z `application.yaml` do `DefaultVerificationLinkService`

### 2.3 resources
- **application.yaml**: 🔧 dodanie konfiguracji wymaganej w `ApiProperties.java`

### 2.4 test - api.services
- **ModelSerializationTest.java**: ➕ dodanie testu weryfikującego zgodność nazw pól/getterów/JsonProperty
- **VerificationLinkServiceTests.java**: 🔧 aktualizacja testu

## 3. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 34            |
| 🔧 zmienione | 42            |
| ➖ usunięte  | 0             |
---
# Changelog zmian - `## 3.0.7 (2025-12-05)` - `API: 2.0.0 RC6.0`

## 1. ksef-client

### 1.1 system
- **FilesUtil.java**: 🔧 usunięcie `Map<String, byte[]> generateInvoicesInMemory(int invoicesCount, String contextNip, LocalDate invoicingDate, String invoiceNumber, String invoiceTemplate)`

## 2. demo-web-app

### 2.1 integrationTest
- **DuplicateInvoiceIntegrationTest.java**: 🔧 poprawka scenariusza testowego
- **SearchEntityInvoiceRoleIntegrationTest.java**: 🔧 usunięcie zbędnych importów

## 3. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 0             |
| 🔧 zmienione | 3             |
| ➖ usunięte  | 0             |

---
# Changelog zmian - `## 3.0.8 (2025-12-10)` - `API: 2.0.0 RC6.0`

## 1. ksef-client

### 1.1 api
- **DefaultKsefClient.java**: 🔧 Dodano parametr `UpoVersion upoVersion` w metodach `openBatchSession` i `openOnlineSession`, pozostałe oznaczone jako deprecated

### 1.2 client.interfaces
- **KSeFClient.java**: 🔧 metody `openBatchSession` i `openOnlineSession` zgodnie z opisem w implementacji `DefaultKsefClient`
- **CryptographyService.java**: 🔧 dodanie metody `PrivateKey parseEncryptedEcdsaPrivateKeyFromPem(byte[] pemBytes, char[] password)`

### 1.3 api.services
- **DefaultCryptographyService.java**: 🔧 dodanie metody `PrivateKey parseEncryptedEcdsaPrivateKeyFromPem(byte[] pemBytes, char[] password)`
- **DefaultVerificationLinkService.java**: 🔧 poprawka w generowaniu linku weryfikacyjnego

### 1.4 client.model
- **limit/GetRateLimitResponse.java**: 🔧 aktualizacja kontraktu - zmiana nazwy pola z `otherRateLimit` na `other`
- **UpoVersion.java**: ➕ enum z wartościami dla nagłówka `X-KSeF-Feature` dla zwracanego UPO

### 1.5 test - api.services
- **QrCodeTests.java**: ➕ poprawki testów

## 2. demo-web-app

### 2.1 integrationTest
- **QrCodeOfflineIntegrationTest.java**: 🔧 rozbudowa scenariusza testowego dla klucza prywatnego ECC wygenerowanego z aplikacji podatnika 

### 2.2 integrationTest.resources
- **keys/private/ecdsa/sample/testowy_klucz_sdk.key**: ➕ dodanie przykładowego klucza prywatnego ECC wygenerowanego z aplikacji podatnika

## 3. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 2             |
| 🔧 zmienione | 8             |
| ➖ usunięte  | 0             |

---
# Changelog zmian - `## 3.0.9 (2025-12-16)` - `API: 2.0.0 RC6.1`

## 1. ksef-client

### 1.1 api
- **DefaultKsefClient.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated`, dodanie metody `restoreProductionRateLimitsAsync(String accessToken)` która ustawia w bieżącym kontekście wartości limitów api zgodne z profilem produkcyjnym. Dostępny tylko na środowisku TE.
- **KsefApiProperties.java**: 🔧 dodanie metody `String getQrUri()`
- **Url.java**: 🔧 dodanie `LIMIT_CONTEXT_SET_PRODUCTION("/api/v2/testdata/rate-limits/production", "apiV2LimitsSetProduction")`

### 1.2 client.interfaces
- **KSeFClient.java**: 🔧 zgodnie z opisem w implementacji `DefaultKsefClient`

### 1.3 api.builders
- **OpenBatchSessionRequestBuilder.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated`

### 1.4 client.model
- **auth/AuthenticationChallengeResponse.java**: 🔧 aktualizacja kontraktu - dodanie pola `long timestampMs`
- **limit/ChangeSubjectCertificateLimitRequest.java**: 🔧 aktualizacja kontraktu - usunięcie wartości enuma `TOKEN("Token")`
- **limit/GetRateLimitResponse.java**: 🔧 aktualizacja kontraktu - dodanie pola `InvoiceExportStatusRateLimit invoiceStatusExport`
- **limit/InvoiceExportStatusRateLimit.java**: ➕ dodanie klasy
- **permission/indirect/TargetIdentifier.java**: 🔧 aktualizacja kontraktu - w `IdentifierType` poprawa literówki enuma `ALL_PARNERS` i dodanie `INTERNAL_ID("InternalId")`
- **batch/BatchFilePartInfo.java**: 🔧 aktualizacja kontraktu - usunięcie pola `String fileName`
- **testdata/TestDataAttachmentRemoveRequest.java**: 🔧 aktualizacja kontraktu - zmiana typu pola `OffsetDateTime expectedEndDate` na `LocalDate`
- **ApiException.java**: 🔧 aktualizacja kontraktu - usunięcie pola `String responseBody`

### 1.5 api.services
- **DefaultVerificationLinkService.java**: 🔧 aktualizacje związane z generowaniem linku weryfikacyjnego

## 2. demo-web-app

### 2.1 integrationTest
- **BatchIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **DuplicateInvoiceIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **EnforcementOperationNegativeIntegrationTest.java**: 🔧 poprawka dziedziczenia po BaseIntegrationTest 
- **IncrementalInvoiceRetrieveIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **OnlineSessionIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **PeppolInvoiceIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **PermissionAttachmentStatusIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu 
- **QrCodeOfflineIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu, usunięciem pól/metod oznaczonych `deprecated` i poprawa literówki w nazwie metody 
- **QrCodeOnlineIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **QueryInvoiceIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **SearchInvoiceForSubject2IntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **SearchInvoiceForSubject3IntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **SelfInvoicingIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **SessionIntegrationTest.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 

### 2.2 api
- **BatchSessionController.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **OnlineSessionController.java**: 🔧 aktualizacje związane ze zmianą modelu i usunięciem pól/metod oznaczonych `deprecated` 
- **ExampleApiProperties.java**: 🔧 dodano qrUri
- **KsefClientConfig.java**: 🔧 wczytanie urla qr z `application.yaml` do `DefaultVerificationLinkService`

### 2.3 resources
- **application-demo.yaml**: ➕ dodanie konfiguracji dla środowiska demo
- **application-prod.yaml**: ➕ dodanie konfiguracji dla środowiska produkcyjnego
- **application.yaml**: 🔧 dodanie konfiguracji do qr kodów wymaganej w `ApiProperties.java`

### 2.4 test - api.services
- **QrCodeTests.java**: 🔧 aktualizacja testu
- **VerificationLinkServiceTests.java**: 🔧 aktualizacja testu

## 3. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|--------------|
| ➕ dodane    | 3            |
| 🔧 zmienione | 34           |
| ➖ usunięte  | 0            |

---
# Changelog zmian - `## 3.0.10 (2025-12-22)` - `API: 2.0.0 RC6.1`

## 1. ksef-client

### 1.1 api
- **DefaultKsefClient.java**: 🔧 dodanie pola i obsługi `String suffixURl`
- **KsefApiProperties.java**: 🔧 dodanie metody `String getSuffixUri()`
- **Url.java**: 🔧 usunięcie prefixów `/api/v2/` w urlach i przeniesienie do `KsefApiProperties` 

### 1.2 client.model
- **permission/search/QueryPersonalGrantTargetIdentifier.java**: 🔧 dodanie brakującej wartości enuma `IdentifierType` `INTERNAL_ID("InternalId")`

### 1.3 api.services
- **DefaultCryptographyService.java**: 🔧 dodanie pola `String secureRandomAlgorithm` wraz z konstruktorem który pozwala w metodach `generateRandom256BitsKey` i `generateRandom16BytesIv` używać wskazanego algorytmu

## 2. demo-web-app

### 2.1 integrationTest
- **BatchIntegrationTest.java**: 🔧 zmiany w użyciu SecureRandom 
- **QrCodeOnlineIntegrationTest.java**: 🔧 poprawka związana z błędnym invoicingDate 

### 2.2 api
- **ExampleApiProperties.java**: 🔧 dodanie konfiguracji pola `String suffixUri` wraz z getterami i setterami 

### 2.3 resources
- **application.yaml**: 🔧 dodanie konfiguracji `suffix-uri`

## 3. Podsumowanie

| Typ zmiany  | Liczba plików |
|-------------|---------------|
| ➕ dodane    | 0             |
| 🔧 zmienione | 9             |
| ➖ usunięte  | 0             |
