package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.permission.entity.EntityAuthorizationPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.proxy.GrantAuthorizationPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.proxy.GrantAuthorizationPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.proxy.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityAuthorizationGrant;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityAuthorizationPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityAuthorizationsAuthorizedEntityIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.EntityAuthorizationsAuthorizingEntityIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEntityAuthorizationPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryType;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.client.model.testdata.SubjectTypeTestData;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectRemoveRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class RrInvoiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;
    private EncryptionData encryptionData;

    private static final String templateFileName = "/xml/invoices/sample/invoice-template-fa-rr-1.xml";
    private static final String grantorDescriptionPrefix = "E2E-RR-Grantor";
    private static final String authorizedDescriptionPrefix = "E2E-RR-Authorized";
    private static final String permissionDescriptionPrefix = "E2E-RRInvoicing";
    private static final String authorizedSubjectFullName = "Podmiot Testowy RR";
    private static final int expectedSuccessfulInvoiceCount = 1;
    private static final int pageOffset = 0;
    private static final int pageSize = 50;

    // Weryfikacja poprawności nadawania uprawnienia RRInvoicing i wysyłania faktury FA-RR.
    // Scenariusz:
    // 1. Utworzenie dwóch podmiotów testowych (grantor i authorized)
    // 2. Nadanie uprawnienia RRInvoicing przez grantor dla authorized
    // 3. Otwarcie sesji online przez authorized z kodem systemu FA_RR
    // 4. Wysłanie faktury FA-RR przez authorized w imieniu grantor
    // 5. Weryfikacja przetworzenia faktury
    // 6. Zamknięcie sesji i wyszukanie nadanego uprawnienia
    // 7. Odebranie uprawnienia RRInvoicing
    // 8. Usunięcie podmiotów testowych

    @Test
    void sendingFaRrInvoiceWithGrantPermission() throws JAXBException, IOException, ApiException {
        // Przygotowanie podmiotów testowych
        String grantorNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedNip = IdentifierGeneratorUtils.generateRandomNIP();

        TestDataSubjectCreateRequest grantorRequest = new TestDataSubjectCreateRequest();
        grantorRequest.setSubjectNip(grantorNip);
        grantorRequest.setSubjectType(SubjectTypeTestData.ENFORCEMENT_AUTHORITY);
        grantorRequest.setDescription(grantorDescriptionPrefix + "-" + grantorNip);
        ksefClient.createTestSubject(grantorRequest);

        TestDataSubjectCreateRequest authorizedRequest = new TestDataSubjectCreateRequest();
        authorizedRequest.setSubjectNip(authorizedNip);
        authorizedRequest.setSubjectType(SubjectTypeTestData.ENFORCEMENT_AUTHORITY);
        authorizedRequest.setDescription(authorizedDescriptionPrefix + "-" + authorizedNip);
        ksefClient.createTestSubject(authorizedRequest);

        // Uwierzytelnienie jako podmiot nadający uprawnienie (grantor)
        String grantorAccessToken = authWithCustomNip(grantorNip, grantorNip).accessToken();

        // Nadanie uprawnienia RRInvoicing
        grantRRPermission(authorizedNip, grantorAccessToken);

        // Uwierzytelnienie jako podmiot uprawniony (authorized)
        String authorizedAccessToken = authWithCustomNip(authorizedNip, authorizedNip).accessToken();

        encryptionData = defaultCryptographyService.getEncryptionData();

        // Otwarcie sesji online z kodem systemu FA_RR
        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_RR, SchemaVersion.VERSION_1_0E, SessionValue.RR, authorizedAccessToken);

        // Wysłanie faktury FA-RR
        sendRrInvoice(sessionReferenceNumber, encryptionData, grantorNip, authorizedNip, templateFileName, authorizedAccessToken);

        // Weryfikacja przetworzenia faktury
        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, authorizedAccessToken));

        // Zamknięcie sesji online
        closeSession(sessionReferenceNumber, authorizedAccessToken);

        // Wyszukanie nadanego uprawnienia RRInvoicing
        List<String> permissionIds = searchRrPermissions(grantorNip, authorizedNip, 1, grantorAccessToken);

        // Odebranie uprawnienia RRInvoicing
        permissionIds.forEach(e -> {
            String revokeReferenceNumber = revokePermission(e, grantorAccessToken);

            // Weryfikacja odebrania uprawnienia
            await().atMost(30, SECONDS)
                    .pollInterval(2, SECONDS)
                    .until(() -> isPermissionStatusReady(revokeReferenceNumber, grantorAccessToken));
        });
        searchRrPermissions(grantorNip, authorizedNip, 0, grantorAccessToken);

        // Usunięcie podmiotów testowych
        ksefClient.removeTestSubject(new TestDataSubjectRemoveRequest(authorizedNip));
        ksefClient.removeTestSubject(new TestDataSubjectRemoveRequest(grantorNip));
    }

    private void grantRRPermission(String authorizedNip, String grantorAccessToken) throws ApiException {
        GrantAuthorizationPermissionsRequest.PermissionsAuthorizationSubjectDetails subjectDetails =
                new GrantAuthorizationPermissionsRequest.PermissionsAuthorizationSubjectDetails();
        subjectDetails.setFullName(authorizedSubjectFullName);
        GrantAuthorizationPermissionsRequest grantRequest =
                new GrantAuthorizationPermissionsRequestBuilder()
                        .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, authorizedNip))
                        .withPermission(InvoicePermissionType.RR_INVOICING)
                        .withDescription(permissionDescriptionPrefix + "-" + authorizedNip)
                        .withSubjectDetails(subjectDetails)
                        .build();

        OperationResponse grantOperation = ksefClient.grantsPermissionsProxyEntity(grantRequest, grantorAccessToken);

        await().atMost(10, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isPermissionStatusReady(grantOperation.getReferenceNumber(), grantorAccessToken));
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode, SchemaVersion schemaVersion, SessionValue value, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendRrInvoice(String sessionReferenceNumber, EncryptionData encryptionData,
                                 String supplierNip, String buyerNip, String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#nip#", supplierNip)
                .replace("#invoice_number#", UUID.randomUUID().toString())
                .replace("#buyer_nip#", buyerNip);

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
                    statusResponse.getFailedInvoiceCount() == null &&
                    statusResponse.getSuccessfulInvoiceCount() != null &&
                    statusResponse.getSuccessfulInvoiceCount() > 0 &&
                    statusResponse.getSuccessfulInvoiceCount() == expectedSuccessfulInvoiceCount;
        } catch (Exception e) {
            return false;
        }
    }

    private void closeSession(String sessionReferenceNumber, String accessToken) throws ApiException {
        ksefClient.closeOnlineSession(sessionReferenceNumber, accessToken);
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
        return status != null && status.getStatus().getCode() == 200;
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeAuthorizationsPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private List<String> searchRrPermissions(String grantorNip, String authorizedNip, int expectedPermissionCount, String accessToken) throws ApiException {
        EntityAuthorizationPermissionsQueryRequest request = new EntityAuthorizationPermissionsQueryRequestBuilder()
                .withAuthorizingIdentifier(new EntityAuthorizationsAuthorizingEntityIdentifier(EntityAuthorizationsAuthorizingEntityIdentifier.IdentifierType.NIP, grantorNip))
                .withAuthorizedIdentifier(new EntityAuthorizationsAuthorizedEntityIdentifier(EntityAuthorizationsAuthorizedEntityIdentifier.IdentifierType.NIP, authorizedNip))
                .withQueryType(QueryType.GRANTED)
                .withPermissionTypes(List.of(InvoicePermissionType.RR_INVOICING))
                .build();

        QueryEntityAuthorizationPermissionsResponse response = ksefClient.searchEntityAuthorizationGrants(request, pageOffset, pageSize, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPermissionCount, response.getAuthorizationGrants().size());

        // Weryfikacja znalezienia nadanego uprawnienia
        boolean marching = response.getAuthorizationGrants().stream()
                .anyMatch(g -> g.getAuthorizationScope().equals(InvoicePermissionType.RR_INVOICING)
                        && g.getAuthorizedEntityIdentifier().getValue().equals(authorizedNip)
                );
        if (expectedPermissionCount > 0) {
            Assertions.assertTrue(marching);
        }

        return response.getAuthorizationGrants()
                .stream()
                .map(EntityAuthorizationGrant::getId)
                .toList();
    }
}
