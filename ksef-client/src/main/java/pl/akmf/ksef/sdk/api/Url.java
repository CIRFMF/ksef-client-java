package pl.akmf.ksef.sdk.api;

public enum Url {
    AUTH_CHALLENGE("auth/challenge", "apiV2AuthChallengePost"),
    JWT_TOKEN_REVOKE("auth/token", "apiV2AuthTokenDelete"),
    JWT_TOKEN_REFRESH("auth/token/refresh", "apiV2AuthTokenRefreshPost"),
    AUTH_TOKEN_SIGNATURE("auth/xades-signature", "apiV2AuthTokenSignaturePost"),
    AUTH_TOKEN_STATUS("auth/{referenceNumber}", "apiV2AuthTokenTokenReferenceNumberGet"),
    AUTH_KSEF_TOKEN("auth/ksef-token", "apiV2AuthTokenTokenReferenceNumberGet"),
    AUTH_TOKEN_REEDEM("auth/token/redeem", "apiV2AuthTokenTokenRedeemPost"),

    BATCH_SESSION_CLOSE("sessions/batch/{referenceNumber}/close", "apiV2SessionsBatchReferenceNumberClosePost"),
    BATCH_SESSION_OPEN("sessions/batch", "batchOpen"),

    CERTIFICATE_REVOKE("certificates/{certificateSerialNumber}/revoke", "apiV2CertificatesCertificateSerialNumberRevokePost"),
    CERTIFICATE_ENROLLMENT_DATA("certificates/enrollments/data", "apiV2CertificatesEnrollmentsDataGet"),
    CERTIFICATE_ENROLLMENT("certificates/enrollments", "apiV2CertificatesEnrollmentsPost"),
    CERTIFICATE_STATUS("certificates/enrollments/{referenceNumber}", "apiV2CertificatesEnrollmentsReferenceNumberGet"),
    CERTIFICATE_LIMIT("certificates/limits", "apiV2CertificatesLimitsGet"),
    CERTIFICATE_METADATA("certificates/query", "apiV2CertificatesQueryPost"),
    CERTIFICATE_RETRIEVE("certificates/retrieve", "apiV2CertificatesRetrievePost"),

    INVOICE_DOWNLOAD_BY_KSEF("invoices/ksef/{ksefReferenceNumber}", "apiV2InvoicesKsefKsefReferenceNumberGet"),
    INVOICE_EXPORT_STATUS("invoices/exports/{referenceNumber}", "apiV2InvoicesExportDownload"),
    INVOICE_EXPORT_INIT("invoices/exports", "apiV2InvoicesExportPost"),
    INVOICE_QUERY_METADATA("invoices/query/metadata", "apiV2InvoicesQueryMetadataPost"),

    GRANT_EU_ADMINISTRATOR_PERMISSION("permissions/eu-entities/administration/grants", "apiV2PermissionsEuEntitiesAdministrationGrantsPost"),
    GRANT_EU_REPRESENTATIVE("permissions/eu-entities/grants", "apiV2PermissionsEuEntitiesGrantsPost"),
    GRANT_AUTHORIZED_SUBJECT_PERMISSION("permissions/authorizations/grants", "apiV2PermissionsAuthorizationsGrantsPost"),
    GRANT_INDIRECT_PERMISSION("permissions/indirect/grants", "apiV2PermissionsIndirectGrantsPost"),
    GRANT_PERSON_PERMISSION("permissions/persons/grants", "apiV2PermissionsPersonsGrantsPost"),
    GRANT_INVOICE_SUBJECT_PERMISSION("permissions/entities/grants", "apiV2PermissionsEntitiesGrantsPost"),
    GRANT_SUBUNIT_PERMISSION("permissions/subunits/grants", "apiV2PermissionsSubunitsGrantsGet"),

    SESSION_CLOSE("sessions/online/{referenceNumber}/close", "apiV2SessionsOnlineReferenceNumberClosePost"),
    SESSION_INVOICE_SEND("sessions/online/{referenceNumber}/invoices", "apiV2SessionsOnlineReferenceNumberInvoicesPost"),
    SESSION_OPEN("sessions/online", "onlineSessionOpen"),

    PERMISSION_STATUS("permissions/operations/{referenceNumber}", "apiV2PermissionsOperationsReferenceNumberGet"),
    PERMISSION_REVOKE_AUTHORIZATION("permissions/authorizations/grants/{permissionId}", "apiV2PermissionsAuthorizationsGrantsPermissionIdDelete"),
    PERMISSION_REVOKE_COMMON("permissions/common/grants/{permissionId}", "apiV2PermissionsCommonGrantsPermissionIdDelete"),
    PERMISSION_ATTACHMENT_STATUS("permissions/attachments/status", "apiV2PermissionAttachmentStatus"),
    SECURITY_PUBLIC_KEY_CERTIFICATE("security/public-key-certificates", "apiV2SecurityPublicKeyCertificatesGet"),
    PUBLIC_KEY("/public-keys/publicKey.pem", "apiV2SPublicKeyGet"),

    PERMISSION_SEARCH_PERSONAL_GRANTS("permissions/query/personal/grants", "apiV2PermissionsQueryPersonalGrants"),
    PERMISSION_SEARCH_AUTHORIZATIONS_GRANT("permissions/query/authorizations/grants", "apiV2PermissionsQueryAuthorizationsGrantsPost"),
    PERMISSION_SEARCH_ENTITY_ROLES("permissions/query/entities/roles", "apiV2PermissionsQueryEntitiesRolesGet"),
    PERMISSION_SEARCH_EU_ENTITY_GRANT("permissions/query/eu-entities/grants", "apiV2PermissionsQueryEuEntitiesGrantsPost"),
    PERMISSION_SEARCH_PERSON_PERMISSION("permissions/query/persons/grants", "apiV2PermissionsQueryPersonsGrantsPost"),
    PERMISSION_SEARCH_SUBORDINATE_PERMISSION("permissions/query/subordinate-entities/roles", "apiV2PermissionsQuerySubordinateEntitiesRolesPost"),
    PERMISSION_SEARCH_SUBUNIT_GRANT("permissions/query/subunits/grants", "apiV2PermissionsQuerySubunitsGrantsPost"),

    SESSION_INVOICE_FAILED("sessions/{referenceNumber}/invoices/failed", "apiV2SessionsReferenceNumberInvoicesFailedGet"),
    SESSION_INVOICE("sessions/{referenceNumber}/invoices", "apiV2SessionsReferenceNumberInvoicesGet"),
    SESSION_INVOICE_GET_BY_REFERENCE_NUMBER("sessions/{referenceNumber}/invoices/{invoiceReferenceNumber}", "apiV2SessionsReferenceNumberInvoicesInvoiceReferenceNumberGet"),
    SESSION_INVOICE_UPO_BY_KSEF("sessions/{referenceNumber}/invoices/ksef/{ksefNumber}/upo", "apiV2SessionsReferenceNumberInvoicesKsefKsefNumberUpoGet"),
    SESSION_INVOICE_UPO_BY_INVOICE_REFERENCE("sessions/{referenceNumber}/invoices/{invoiceReferenceNumber}/upo", "apiV2SessionsReferenceNumberInvoicesGet"),
    SESSION_STATUS("sessions/{referenceNumber}", "apiV2SessionsReferenceNumberGet"),
    SESSION_UPO("sessions/{referenceNumber}/upo/{upoReferenceNumber}", "apiV2SessionsReferenceNumberUpoUpoReferenceNumberGet"),
    SESSION_LIST("sessions", "apiV2SessionsListGet"),

    SESSION_REVOKE_CURRENT_SESSION("auth/sessions/current", "apiV2AuthSessionsCurrentDelete"),
    SESSION_ACTIVE_SESSIONS("auth/sessions", "apiV2AuthSessionsGet"),
    SESSION_REVOKE_SESSION("auth/sessions/{referenceNumber}", "apiV2AuthSessionsReferenceNumberDelete"),

    LIMIT_CONTEXT("limits/context", "apiV2LimitsGet"),
    LIMIT_SUBJECT_CERTIFICATE("limits/subject", "apiV2LimitsGet"),
    GET_RATE_LIMIT("rate-limits", "apiV2RateLimit"),

    LIMIT_CONTEXT_SET_PRODUCTION("testdata/rate-limits/production", "apiV2LimitsSetProduction"),
    LIMIT_CONTEXT_CHANGE_TEST("testdata/limits/context/session", "apiV2LimitsChange"),
    LIMIT_CONTEXT_RESET_TEST("testdata/limits/context/session", "apiV2LimitsReset"),
    LIMIT_SUBJECT_CERTIFICATE_CHANGE_TEST("testdata/limits/subject/certificate", "apiV2LimitsChange"),
    LIMIT_SUBJECT_CERTIFICATE_RESET_TEST("testdata/limits/subject/certificate", "apiV2LimitsReset"),
    TEST_SUBJECT_CREATE("testdata/subject", "subjectTestCreate"),
    TEST_SUBJECT_DELETE("testdata/subject/remove", "subjectTestRemove"),
    TEST_PERSON_CREATE("testdata/person", "personTestCreate"),
    TEST_PERSON_DELETE("testdata/person/remove", "personTestDelete"),
    TEST_PERMISSION("testdata/permissions", "permissionTestCreate"),
    TEST_PERMISSION_REVOKE("testdata/permissions/revoke", "permissionTestRevoke"),
    TEST_ATTACHMENT("testdata/attachment", "attachmentTestCreate"),
    TEST_ATTACHMENT_REVOKE("testdata/attachment/revoke", "attachmentTestRevoke"),

    PEPPOL_QUERY("peppol/query", "peppolQuery"),

    TOKEN_LIST("tokens", "apiV2TokensGet"),
    TOKEN_GENERATE("tokens", "apiV2TokensPost"),
    TOKEN_REVOKE("tokens/{referenceNumber}", "apiV2TokensReferenceNumberDelete"),
    TOKEN_STATUS("tokens/{referenceNumber}", "apiV2TokensReferenceNumberGet");

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
