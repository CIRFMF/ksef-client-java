package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.invoices.InvoiceQueryFiltersBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsGrantRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceMetadata;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionPersonById;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QuerySubunitPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRole;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubordinateEntityRolesQueryResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsAuthorizedIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.SubunitPermissionsSubunitIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.PermissionsSubunitPersonByIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.PermissionsSubunitSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitSubjectDetails;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.client.model.testdata.SubjectTypeTestData;
import pl.akmf.ksef.sdk.client.model.testdata.Subunit;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.util.SortOrder;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils.getInternalIdCheckSum;

class SubUnitPermissionIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    // Test end-to-end pełnego cyklu zarządzania uprawnieniami jednostki podrzędnej:
    // 1. Inicjalizacja i uwierzytelnienie jednostki głównej
    // 2. Nadanie uprawnień do zarządzania jednostką podrzędną
    // 3. Uwierzytelnienie w kontekście jednostki podrzędnej
    // 4. Nadanie uprawnień administratora podmiotu podrzędnego
    // 5. Weryfikacja nadanych uprawnień
    // 6. Odwołanie uprawnień i weryfikacja
    @Test
    void subUnitPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String unitNip = IdentifierGeneratorUtils.generateRandomNIP();
        Long internalIdValue = 1234L;
        String internalNip = unitNip + "-" + internalIdValue + getInternalIdCheckSum(unitNip, internalIdValue);
        String subUnitNip = IdentifierGeneratorUtils.generateRandomNIP();
        String subUnitAdmin = IdentifierGeneratorUtils.generateRandomNIP();

        //Inicjalizuje uwierzytelnienie jednostki głównej.
        String unitAccessToken = authWithCustomNip(unitNip, unitNip).accessToken();

        //Nadanie uprawnienia SubunitManage, CredentialsManage do zarządzania jednostką podrzędną
        String grantReferenceNumber = grantPermissionToAdministrateSubUnit(subUnitNip, unitAccessToken);

        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(grantReferenceNumber, unitAccessToken));

        //Uwierzytelnia w kontekście jednostki głównej jako jednostka podrzędna przy użyciu certyfikatu osobistego.
        String subunitAccessToken = authWithCustomNip(unitNip, subUnitNip).accessToken();

        //Nadanie uprawnień administratora podmiotu podrzędnego jako jednostka podrzędna
        String operationGrantNumber = grantPermissionSubunit(subUnitAdmin, internalNip, subunitAccessToken);

        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(operationGrantNumber, subunitAccessToken));

        //Wyszukaj uprawnienia nadane administratorowi jednostki podrzędnej
        List<SubunitPermission> subUnitPermission = searchGrantedRole(subunitAccessToken);
        Assertions.assertTrue(subUnitPermission.size() > 0);

        //Pobierz listę podmiotów podrzędnych jeżeli podmiot bieżącego kontekstu ma rolę podmiotu nadrzędnego
        List<SubordinateEntityRole> subordinateEntities = searchSubordinateEntity(unitAccessToken);
        Assertions.assertNotNull(subordinateEntities);

        //Cofnij uprawnienia nadane administratorowi jednostki podrzędnej
        String revokeOperationReferenceNumber = revokePermission(subUnitPermission.getFirst().getId(), subunitAccessToken);

        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(revokeOperationReferenceNumber, subunitAccessToken));
    }

    // Nadanie uprawnień jednostce podrzędnej (przedszkolu) oraz dyrektorowi tej jednostki
    @Test
    void subUnitPermissionWorkflow() throws JAXBException, IOException, ApiException {
        String invoiceCreatorNip = IdentifierGeneratorUtils.generateRandomNIP();

        String municipalOfficeNip = IdentifierGeneratorUtils.generateRandomNIP();
        Long internalIdValue = 1234L;
        String kindergartenId = municipalOfficeNip + "-" + internalIdValue + getInternalIdCheckSum(municipalOfficeNip, internalIdValue);
        String directorPesel = IdentifierGeneratorUtils.getRandomPesel();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        // --- Etap 1: Wystawienie faktury przez wykonawcę dla przedszkola (jednostki podrzędnej) ---
        String invoiceCreatorAuthToken = authWithCustomNip(invoiceCreatorNip, invoiceCreatorNip).accessToken();
        String invoiceCreatorSessionReferenceNumber = openOnlineSession(encryptionData, invoiceCreatorAuthToken);
        String invoiceReferenceNumber = sendInvoiceOnlineSession(invoiceCreatorNip, municipalOfficeNip, invoiceCreatorSessionReferenceNumber,
                encryptionData, "/xml/invoices/sample/invoice-template-fa-3-with-custom-subject_2.xml", invoiceCreatorAuthToken);
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(invoiceCreatorSessionReferenceNumber, invoiceCreatorAuthToken));
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> waitForStoringInvoice(invoiceCreatorSessionReferenceNumber, invoiceReferenceNumber, invoiceCreatorAuthToken));

        // --- Etap 2: Gmina nadaje uprawnienia dyrektorowi przedszkola do zarządzania uprawnieniami w kontekście przedszkola---
        String municipalOfficeAuthToken = authWithCustomNip(municipalOfficeNip, municipalOfficeNip).accessToken();
        SubunitPermissionsGrantRequest request = new SubunitPermissionsGrantRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.PESEL, directorPesel))
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.INTERNALID, kindergartenId))
                .withDescription("Sub-unit permission grant")
                .withSubunitName("Przedszkole Testowe")
                .withSubjectDetails(
                        new SubunitSubjectDetails(PermissionsSubunitSubjectDetailsType.PersonByIdentifier,
                                new PermissionsSubunitPersonByIdentifier("Jan", "Kowalski"),
                                null,
                                null
                        )
                )
                .build();
        String grantReferenceNumber = grantPermissionSubunit(request, municipalOfficeAuthToken);
        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(grantReferenceNumber, municipalOfficeAuthToken));

        // --- Etap 3: dyrektor w kontekście przedszkola nadaje sobie prawo do odczytu faktur ---
        AuthTokensPair kindergartenAuthResult = authAsInternalId(kindergartenId, directorPesel);
        String kindergartenAuthToken = kindergartenAuthResult.accessToken();
        GrantPersonPermissionsRequest personRequest = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.PESEL, directorPesel))
                .withPermissions(List.of(PersonPermissionType.INVOICEREAD))
                .withDescription("GrantPermissionToDirector")
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Jan", "Testowy"),
                                null,
                                null
                        )
                )
                .build();
        String grantInvReadReferenceNumber = grantPermissionToAdministrateSubUnit(personRequest, kindergartenAuthToken);
        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(grantInvReadReferenceNumber, kindergartenAuthToken));

        // --- Etap 4: Dyrektor przedszkola wyszukuje faktury ---
        String refreshedAccessTokenResponse = refreshAccessToken(kindergartenAuthResult.refreshToken());
        List<InvoiceMetadata> invoiceMetadata = getInvoiceMetadata(InvoiceQuerySubjectType.SUBJECT3, refreshedAccessTokenResponse);
        Assertions.assertTrue(invoiceMetadata.stream()
                .anyMatch(invoice -> invoice.getThirdSubjects().stream()
                        .anyMatch(thirdSubject -> kindergartenId.equals(thirdSubject.getIdentifier().getValue()))
                ));

        //Sprawdzanie dostępu z poziomu gminy
        invoiceMetadata = getInvoiceMetadata(InvoiceQuerySubjectType.SUBJECT2, refreshedAccessTokenResponse);
        Assertions.assertTrue(invoiceMetadata.stream()
                .anyMatch(invoice -> municipalOfficeNip.equals(invoice.getBuyer().getIdentifier().getValue()))
        );
    }

    // Pobranie listy uprawnień w jednostkach podrzędnych jako jednostka nadrzędna grupy VAT.
    // Scenariusz:
    // 1) Utworzenie podmiotu typu Grupa VAT z jednostką podrzędną
    // 2) Uwierzytelnienie się jako jednostka nadrzędna (Grupa VAT)
    // 3) Uwierzytelnienie się w kontekście jednostki podrzędnej (certyfikat osobisty)
    // 4) Nadanie uprawnienienia administratora jednostki podrzędnej (w kontekście jednostki podrzędnej)
    // 5) Jako jednostka nadrzędna pobranie listy uprawnień w jednostkach podrzędnych i weryfikacja wyniku
    // 6) Cofnięcie nadanych uprawnień i sprzątnięcie danych testowych
    @Test
    void subunitPermissionsAsVatGroupParentShouldReturnList() throws JAXBException, IOException, ApiException {
        String vatGroupNip = IdentifierGeneratorUtils.generateRandomNIP();
        Long internalIdValue = 1234L;
        String parentInternalId = vatGroupNip + "-" + internalIdValue + getInternalIdCheckSum(vatGroupNip, internalIdValue);
        String subunitNip = IdentifierGeneratorUtils.generateRandomNIP();

        // Arrange: utworzenie grupy VAT z jednostką podrzędną
        createVatGroupWithSubunit(vatGroupNip, subunitNip, "Grupa VAT testowa");

        // Arrange: uwierzytelnienie jednostki nadrzędnej
        String parentAccessToken = authWithCustomNip(vatGroupNip, vatGroupNip).accessToken();

        // Arrange: nadanie jednostce podrzędnej uprawnień SubunitManage i CredentialsManage w kontekście jednostki nadrzędnej
        String grantReferenceNumber = grantPermissionToAdministrateSubUnit(subunitNip, parentAccessToken);
        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(grantReferenceNumber, parentAccessToken));

        // Act: uwierzytelnienie jako jednostka podrzędna w kontekście jednostki nadrzędnej (certyfikat osobisty)
        String subunitAccessToken = authWithCustomNip(vatGroupNip, subunitNip).accessToken();

        // Act: nadanie uprawnienia administratora jednostki podrzędnej
        String grantedAdminSubjectNip = IdentifierGeneratorUtils.generateRandomNIP();
        String operationGrantNumber = grantPermissionSubunit(grantedAdminSubjectNip, parentInternalId, subunitAccessToken);

        await().atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> isPermissionStatusReady(operationGrantNumber, subunitAccessToken));

        // Act: jako jednostka nadrzędna pobierz listę uprawnień w jednostkach podrzędnych
        SubunitPermissionsQueryRequest queryRequest = new SubunitPermissionsQueryRequestBuilder()
                .withSubunitIdentifier(new SubunitPermissionsSubunitIdentifier(SubunitPermissionsSubunitIdentifier.IdentifierType.INTERNALID, parentInternalId))
                .build();
        List<SubunitPermission> permissions = searchGrantedRole(queryRequest, parentAccessToken);
        // Assert: lista uprawnień nie jest pusta i zawiera oczekiwane uprawnienie
        Assertions.assertNotNull(permissions);
        Assertions.assertTrue(permissions.stream().anyMatch(p ->
                p.getAuthorizedIdentifier() != null
                        && p.getSubunitIdentifier() != null
                        && SubunitPermissionsAuthorizedIdentifier.IdentifierType.NIP.equals(p.getAuthorizedIdentifier().getType())
                        && grantedAdminSubjectNip.equals(p.getAuthorizedIdentifier().getValue())
                        && SubunitPermissionsSubunitIdentifier.IdentifierType.INTERNALID.equals(p.getSubunitIdentifier().getType())
                        && parentInternalId.equals(p.getSubunitIdentifier().getValue())
        ));

        // Act: cofnięcie nadanych uprawnień (jako jednostka nadrzędna)
        permissions.forEach(e -> {
            String revokeOperationReferenceNumber = revokePermission(e.getId(), parentAccessToken);

            await().atMost(30, SECONDS)
                    .pollInterval(2, SECONDS)
                    .until(() -> isPermissionStatusReady(revokeOperationReferenceNumber, parentAccessToken));
        });
    }

    private void createVatGroupWithSubunit(String vatGroupNip, String subunitNip, String description) throws ApiException {
        TestDataSubjectCreateRequest request = new TestDataSubjectCreateRequest();
        request.setSubjectNip(vatGroupNip);
        request.setSubjectType(SubjectTypeTestData.VAT_GROUP);
        request.setSubunits(List.of(new Subunit(subunitNip, "Jednostka podrzedna - Grupa VAT")));
        request.setDescription(description);

        ksefClient.createTestSubject(request);
    }

    private List<SubordinateEntityRole> searchSubordinateEntity(String unitAccessToken) throws ApiException {
        SubordinateEntityRolesQueryRequest queryRequest = new SubordinateEntityRolesQueryRequest();
        return searchSubordinateEntity(queryRequest, unitAccessToken);
    }

    private List<SubordinateEntityRole> searchSubordinateEntity(SubordinateEntityRolesQueryRequest queryRequest, String unitAccessToken) throws ApiException {
        SubordinateEntityRolesQueryResponse response = ksefClient.searchSubordinateEntityInvoiceRoles(queryRequest, 0, 10, unitAccessToken);

        return response.getRoles();
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) {
        try {
            PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
            return status != null && status.getStatus().getCode() == 200;
        } catch (ApiException e) {
            return false;
        }
    }

    private List<SubunitPermission> searchGrantedRole(String accessToken) throws ApiException {
        SubunitPermissionsQueryRequest request = new SubunitPermissionsQueryRequestBuilder()
                .build();

        return searchGrantedRole(request, accessToken);
    }

    private List<SubunitPermission> searchGrantedRole(SubunitPermissionsQueryRequest request, String accessToken) throws ApiException {
        QuerySubunitPermissionsResponse response = ksefClient.searchSubunitAdminPermissions(request, 0, 10, accessToken);

        return response.getPermissions();
    }

    private String grantPermissionToAdministrateSubUnit(String subjectNip, String accessToken) throws ApiException {
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, subjectNip))
                .withPermissions(List.of(PersonPermissionType.CREDENTIALSMANAGE, PersonPermissionType.SUBUNITMANAGE))
                .withDescription("GrantPermissionToDirector")
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Jan", "Testowy"),
                                null,
                                null
                        )
                )
                .build();

        return grantPermissionToAdministrateSubUnit(request, accessToken);
    }

    private String grantPermissionToAdministrateSubUnit(GrantPersonPermissionsRequest request, String accessToken) throws ApiException {
        OperationResponse response = ksefClient.grantsPermissionPerson(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private String grantPermissionSubunit(String subjectNip, String contextNip, String accessToken) throws ApiException {
        SubunitPermissionsGrantRequest request = new SubunitPermissionsGrantRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, subjectNip))
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.INTERNALID, contextNip))
                .withDescription("E2E - nadanie uprawnień administratora w kontekście jednostki podrzędnej")
                .withSubunitName("E2E VATGroup Jednostka podrzędna")
                .withSubjectDetails(
                        new SubunitSubjectDetails(PermissionsSubunitSubjectDetailsType.PersonByIdentifier,
                                new PermissionsSubunitPersonByIdentifier("Jan", "Kowalski"),
                                null,
                                null
                        )
                )
                .build();

        return grantPermissionSubunit(request, accessToken);
    }

    private String grantPermissionSubunit(SubunitPermissionsGrantRequest request, String accessToken) throws ApiException {
        OperationResponse response = ksefClient.grantsPermissionSubUnit(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private String openOnlineSession(EncryptionData encryptionData, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendInvoiceOnlineSession(String nip, String recipientNip, String sessionReferenceNumber,
                                            EncryptionData encryptionData, String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#subject2nip#", recipientNip)
                .replace("#invoicing_date#",
                        LocalDate.of(2025, 9, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());

        byte[] invoice = invoiceTemplate.getBytes(StandardCharsets.UTF_8);

        byte[] encryptedInvoice = defaultCryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        FileMetadata invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        FileMetadata encryptedInvoiceMetadata = defaultCryptographyService.getMetaData(encryptedInvoice);

        SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .build();

        SendInvoiceResponse sendInvoiceResponse = ksefClient.onlineSessionSendInvoice(sessionReferenceNumber, sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        return sendInvoiceResponse.getReferenceNumber();
    }

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                    statusResponse.getSuccessfulInvoiceCount() != null &&
                    statusResponse.getSuccessfulInvoiceCount() > 0;
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private boolean waitForStoringInvoice(String sessionReferenceNumber, String invoiceReferenceNumber, String accessToken) {
        try {
            SessionInvoiceStatusResponse statusResponse = ksefClient.getSessionInvoiceStatus(sessionReferenceNumber, invoiceReferenceNumber, accessToken);
            return Objects.nonNull(statusResponse.getPermanentStorageDate());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }

    private String refreshAccessToken(String refreshToken) throws ApiException {
        return ksefClient.refreshAccessToken(refreshToken).getAccessToken().getToken();
    }

    private List<InvoiceMetadata> getInvoiceMetadata(InvoiceQuerySubjectType subjectType, String accessToken) throws ApiException {
        InvoiceQueryFilters request = new InvoiceQueryFiltersBuilder()
                .withSubjectType(subjectType)
                .withDateRange(
                        new InvoiceQueryDateRange(InvoiceQueryDateType.INVOICING,
                                OffsetDateTime.now().minusMonths(2),
                                OffsetDateTime.now().plusDays(1)))
                .build();

        QueryInvoiceMetadataResponse response = ksefClient.queryInvoiceMetadata(0, 10, SortOrder.ASC, request, accessToken);

        Assertions.assertNotNull(response);
        return response.getInvoices();
    }
}

