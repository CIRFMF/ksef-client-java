package pl.akmf.ksef.sdk.api;

public enum Url {
    AUTH_CHALLENGE("/api/v2/auth/challenge", "apiV2AuthChallengePost"),
    JWT_TOKEN_REVOKE("/api/v2/auth/token", "apiV2AuthTokenDelete"),
    JWT_TOKEN_REFRESH("/api/v2/auth/token/refresh", "apiV2AuthTokenRefreshPost"),
    AUTH_TOKEN_SIGNATURE("/api/v2/auth/xades-signature", "apiV2AuthTokenSignaturePost"),
    AUTH_TOKEN_STATUS("/api/v2/auth/{referenceNumber}", "apiV2AuthTokenTokenReferenceNumberGet"),
    AUTH_KSEF_TOKEN("/api/v2/auth/ksef-token", "apiV2AuthTokenTokenReferenceNumberGet"),
    AUTH_TOKEN_REEDEM("/api/v2/auth/token/redeem", "apiV2AuthTokenTokenRedeemPost"),

    BATCH_SESSION_CLOSE("/api/v2/sessions/batch/{referenceNumber}/close", "apiV2SessionsBatchReferenceNumberClosePost"),
    BATCH_SESSION_OPEN("/api/v2/sessions/batch", "batchOpen"),

    CERTIFICATE_REVOKE("/api/v2/certificates/{certificateSerialNumber}/revoke", "apiV2CertificatesCertificateSerialNumberRevokePost"),
    CERTIFICATE_ENROLLMENT_DATA("/api/v2/certificates/enrollments/data", "apiV2CertificatesEnrollmentsDataGet"),
    CERTIFICATE_ENROLLMENT("/api/v2/certificates/enrollments", "apiV2CertificatesEnrollmentsPost"),
    CERTIFICATE_STATUS("/api/v2/certificates/enrollments/{referenceNumber}", "apiV2CertificatesEnrollmentsReferenceNumberGet"),
    CERTIFICATE_LIMIT("/api/v2/certificates/limits", "apiV2CertificatesLimitsGet"),
    CERTIFICATE_METADATA("/api/v2/certificates/query", "apiV2CertificatesQueryPost"),
    CERTIFICATE_RETRIEVE("/api/v2/certificates/retrieve", "apiV2CertificatesRetrievePost"),

    INVOICE_DOWNLOAD_BY_KSEF("/api/v2/invoices/ksef/{ksefReferenceNumber}", "apiV2InvoicesKsefKsefReferenceNumberGet"),
    INVOICE_EXPORT_STATUS("/api/v2/invoices/exports/{referenceNumber}", "apiV2InvoicesExportDownload"),
    INVOICE_EXPORT_INIT("/api/v2/invoices/exports", "apiV2InvoicesExportPost"),
    INVOICE_DOWNLOAD("/api/v2/invoices/download", "apiV2InvoicesDownloadPost"),
    INVOICE_QUERY_METADATA("/api/v2/invoices/query/metadata", "apiV2InvoicesQueryMetadataPost"),

    GRANT_EU_ADMINISTRATOR_PERMISSION("/api/v2/permissions/eu-entities/administration/grants", "apiV2PermissionsEuEntitiesAdministrationGrantsPost"),
    GRANT_EU_REPRESENTATIVE("/api/v2/permissions/eu-entities/grants", "apiV2PermissionsEuEntitiesGrantsPost"),
    GRANT_AUTHORIZED_SUBJECT_PERMISSION("/api/v2/permissions/authorizations/grants", "apiV2PermissionsAuthorizationsGrantsPost"),
    GRANT_INDIRECT_PERMISSION("/api/v2/permissions/indirect/grants", "apiV2PermissionsIndirectGrantsPost"),
    GRANT_PERSON_PERMISSION("/api/v2/permissions/persons/grants", "apiV2PermissionsPersonsGrantsPost"),
    GRANT_INVOICE_SUBJECT_PERMISSION("/api/v2/permissions/entities/grants", "apiV2PermissionsEntitiesGrantsPost"),
    GRANT_SUBUNIT_PERMISSION("/api/v2/permissions/subunits/grants", "apiV2PermissionsSubunitsGrantsGet"),

    SESSION_CLOSE("/api/v2/sessions/online/{referenceNumber}/close", "apiV2SessionsOnlineReferenceNumberClosePost"),
    SESSION_INVOICE_SEND("/api/v2/sessions/online/{referenceNumber}/invoices", "apiV2SessionsOnlineReferenceNumberInvoicesPost"),
    SESSION_OPEN("/api/v2/sessions/online", "onlineSessionOpen"),

    PERMISSION_STATUS("/api/v2/permissions/operations/{referenceNumber}", "apiV2PermissionsOperationsReferenceNumberGet"),
    PERMISSION_REVOKE_AUTHORIZATION("/api/v2/permissions/authorizations/grants/{permissionId}", "apiV2PermissionsAuthorizationsGrantsPermissionIdDelete"),
    PERMISSION_REVOKE_COMMON("/api/v2/permissions/common/grants/{permissionId}", "apiV2PermissionsCommonGrantsPermissionIdDelete"),
    PERMISSION_ATTACHMENT_STATUS("/api/v2/permissions/attachments/status", "apiV2PermissionAttachmentStatus"),
    SECURITY_PUBLIC_KEY_CERTIFICATE("/api/v2/security/public-key-certificates", "apiV2SecurityPublicKeyCertificatesGet"),
    PUBLIC_KEY("/public-keys/publicKey.pem", "apiV2SPublicKeyGet"),

    PERMISSION_SEARCH_PERSONAL_GRANTS("/api/v2/permissions/query/personal/grants", "apiV2PermissionsQueryPersonalGrants"),
    PERMISSION_SEARCH_AUTHORIZATIONS_GRANT("/api/v2/permissions/query/authorizations/grants", "apiV2PermissionsQueryAuthorizationsGrantsPost"),
    PERMISSION_SEARCH_ENTITY_ROLES("/api/v2/permissions/query/entities/roles", "apiV2PermissionsQueryEntitiesRolesGet"),
    PERMISSION_SEARCH_EU_ENTITY_GRANT("/api/v2/permissions/query/eu-entities/grants", "apiV2PermissionsQueryEuEntitiesGrantsPost"),
    PERMISSION_SEARCH_PERSON_PERMISSION("/api/v2/permissions/query/persons/grants", "apiV2PermissionsQueryPersonsGrantsPost"),
    PERMISSION_SEARCH_SUBORDINATE_PERMISSION("/api/v2/permissions/query/subordinate-entities/roles", "apiV2PermissionsQuerySubordinateEntitiesRolesPost"),
    PERMISSION_SEARCH_SUBUNIT_GRANT("/api/v2/permissions/query/subunits/grants", "apiV2PermissionsQuerySubunitsGrantsPost"),

    SESSION_INVOICE_FAILED("/api/v2/sessions/{referenceNumber}/invoices/failed", "apiV2SessionsReferenceNumberInvoicesFailedGet"),
    SESSION_INVOICE("/api/v2/sessions/{referenceNumber}/invoices", "apiV2SessionsReferenceNumberInvoicesGet"),
    SESSION_INVOICE_GET_BY_REFERENCE_NUMBER("/api/v2/sessions/{referenceNumber}/invoices/{invoiceReferenceNumber}", "apiV2SessionsReferenceNumberInvoicesInvoiceReferenceNumberGet"),
    SESSION_INVOICE_UPO_BY_KSEF("/api/v2/sessions/{referenceNumber}/invoices/ksef/{ksefNumber}/upo", "apiV2SessionsReferenceNumberInvoicesKsefKsefNumberUpoGet"),
    SESSION_INVOICE_UPO_BY_INVOICE_REFERENCE("/api/v2/sessions/{referenceNumber}/invoices/{invoiceReferenceNumber}/upo", "apiV2SessionsReferenceNumberInvoicesGet"),
    SESSION_STATUS("/api/v2/sessions/{referenceNumber}", "apiV2SessionsReferenceNumberGet"),
    SESSION_UPO("/api/v2/sessions/{referenceNumber}/upo/{upoReferenceNumber}", "apiV2SessionsReferenceNumberUpoUpoReferenceNumberGet"),
    SESSION_LIST("/api/v2/sessions", "apiV2SessionsListGet"),

    SESSION_REVOKE_CURRENT_SESSION("/api/v2/auth/sessions/current", "apiV2AuthSessionsCurrentDelete"),
    SESSION_ACTIVE_SESSIONS("/api/v2/auth/sessions", "apiV2AuthSessionsGet"),
    SESSION_REVOKE_SESSION("/api/v2/auth/sessions/{referenceNumber}", "apiV2AuthSessionsReferenceNumberDelete"),

    LIMIT_CONTEXT("/api/v2/limits/context", "apiV2LimitsGet"),
    LIMIT_SUBJECT_CERTIFICATE("/api/v2/limits/subject", "apiV2LimitsGet"),

    LIMIT_CONTEXT_CHANGE_TEST("/api/v2/testdata/limits/context/session", "apiV2LimitsChange"),
    LIMIT_CONTEXT_RESET_TEST("/api/v2/testdata/limits/context/session", "apiV2LimitsReset"),
    LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST("/api/v2/testdata/limits/subject/certificate", "apiV2LimitsChange"),
    LIMIT_SUBJECT_CERTIFICATE_RESET_TEST("/api/v2/testdata/limits/subject/certificate", "apiV2LimitsReset"),
    TEST_SUBJECT_CREATE("/api/v2/testdata/subject", "subjectTestCreate"),
    TEST_SUBJECT_DELETE("/api/v2/testdata/subject/remove", "subjectTestRemove"),
    TEST_PERSON_CREATE("/api/v2/testdata/person", "personTestCreate"),
    TEST_PERSON_DELETE("/api/v2/testdata/person/remove", "personTestDelete"),
    TEST_PERMISSION("/api/v2/testdata/permissions", "permissionTestCreate"),
    TEST_PERMISSION_REVOKE("/api/v2/testdata/permissions/revoke", "permissionTestRevoke"),
    TEST_ATTACHMENT("/api/v2/testdata/attachment", "attachmentTestCreate"),
    TEST_ATTACHMENT_REVOKE("/api/v2/testdata/attachment/revoke", "attachmentTestRevoke"),

    PEPPOL_QUERY("/api/v2/peppol/query", "peppolQuery"),

    TOKEN_LIST("/api/v2/tokens", "apiV2TokensGet"),
    TOKEN_GENERATE("/api/v2/tokens", "apiV2TokensPost"),
    TOKEN_REVOKE("/api/v2/tokens/{referenceNumber}", "apiV2TokensReferenceNumberDelete"),
    TOKEN_STATUS("/api/v2/tokens/{referenceNumber}", "apiV2TokensReferenceNumberGet");

    private final String url;
    private final String operationId;

    Url(String url, String operationId) {
        this.url = url;
        this.operationId = operationId;
    }

    public String getUrl() {
        return url;
    }

    public String getOperationId() {
        return operationId;
    }
}
