package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataAttachmentRequest;
import pl.akmf.ksef.sdk.client.peppol.PeppolProvider;
import pl.akmf.ksef.sdk.client.peppol.PeppolProvidersListResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class PeppolInvoiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;
    private EncryptionData encryptionData;

    @Test
    void pefInvoiceE2ETest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String peppolId = IdentifierGeneratorUtils.generatePeppolId();

        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //1: peppol registration (if not used earlier, is auto-registered during first use)
        String accessTokenForPefProvider = authAsPeppolProvider(peppolId).accessToken();

        //2: check if peppol has been registered
        checkPeppolProviderList(peppolId);

        //3: grant credentials to peppolProvider
        grantPefInvoicingToProvider(peppolId, accessToken);

        //4: open pef session \
        encryptionData = defaultCryptographyService.getEncryptionData();
        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.PEF_3, SchemaVersion.VERSION_2_1,
                SessionValue.FA_PEF, accessTokenForPefProvider);

        //5: send pef invoice
        sendPefInvoice(sessionReferenceNumber, encryptionData, contextNip, "/xml/invoices/sample/invoice_template_pef.xml", accessTokenForPefProvider);

        //6: check if invoice has been proccesed
        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessTokenForPefProvider));

        //7: close session
        closeSession(sessionReferenceNumber, accessTokenForPefProvider);

        //8: Get UPO
        SessionInvoiceStatusResponse sessionInvoice = getOnlineSessionDocuments(sessionReferenceNumber, accessTokenForPefProvider);
        getOnlineSessionInvoiceUpo(sessionReferenceNumber, sessionInvoice.getKsefNumber(), accessTokenForPefProvider);
    }

    @Test
    void pefAttachmentInvoiceWithCorrectionE2ETest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String peppolId = IdentifierGeneratorUtils.generatePeppolId();

        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //1: peppol registration (if not used earlier, is auto-registered during first use)
        String accessTokenForPefProvider = authAsPeppolProvider(peppolId).accessToken();

        //2: check if peppol has been registered
        checkPeppolProviderList(peppolId);

        //3: grant credentials to peppolProvider
        grantPefInvoicingToProvider(peppolId, accessToken);

        //4: and invoice attachmentCredentials
        grantAttachmentCredential(contextNip);

        //4: open pef session \
        encryptionData = defaultCryptographyService.getEncryptionData();
        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.PEF_3, SchemaVersion.VERSION_2_1, SessionValue.FA_PEF, accessTokenForPefProvider);

        //5: send pef invoice
        sendPefInvoice(sessionReferenceNumber, encryptionData, contextNip, "/xml/invoices/sample/invoice_template_pef_attachment.xml", accessTokenForPefProvider);

        //6: check if invoice has been proccesed
        // Wait for invoice to be processed
        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken));

        //7: close session
        closeSession(sessionReferenceNumber, accessTokenForPefProvider);

        //8: Get UPO
        SessionInvoiceStatusResponse sessionInvoice = getOnlineSessionDocuments(sessionReferenceNumber, accessTokenForPefProvider);
        getOnlineSessionInvoiceUpo(sessionReferenceNumber, sessionInvoice.getKsefNumber(), accessTokenForPefProvider);

        //9:Open new session to sent correction
        String correctionSessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.KOR_PEF_3, SchemaVersion.VERSION_2_1, SessionValue.FA_PEF, accessTokenForPefProvider);

        //10: Send pef correction
        sendPefInvoice(correctionSessionReferenceNumber, encryptionData, contextNip, "/xml/invoices/sample/invoice_template_pef_correction.xml", accessTokenForPefProvider);

        // Wait for invoice to be processed
        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(correctionSessionReferenceNumber, accessToken));

        //11: close correction session
        closeSession(correctionSessionReferenceNumber, accessTokenForPefProvider);

        //12: get correction upo
        SessionInvoiceStatusResponse correction = getOnlineSessionDocuments(correctionSessionReferenceNumber, accessTokenForPefProvider);
        getOnlineSessionInvoiceUpo(correctionSessionReferenceNumber, correction.getKsefNumber(), accessTokenForPefProvider);
    }

    private void grantAttachmentCredential(String contextNip) throws ApiException {
        TestDataAttachmentRequest testDataAttachmentRequest = new TestDataAttachmentRequest();
        testDataAttachmentRequest.setNip(contextNip);

        ksefClient.addAttachmentPermissionTest(testDataAttachmentRequest);
    }

    private void checkPeppolProviderList(String peppolProvider) throws ApiException {
        int pageSize = 100;
        int pageOffset = 0;

        while (true) {
            PeppolProvidersListResponse response = ksefClient.getPeppolProvidersList(pageOffset, pageSize);

            Assertions.assertNotNull(response);
            Assertions.assertFalse(response.getPeppolProviders().isEmpty());

            List<String> peppolIds = response.getPeppolProviders()
                    .stream()
                    .map(PeppolProvider::getId)
                    .toList();

            if (peppolIds.contains(peppolProvider)) {
                break;
            }

            pageOffset += 1;
        }
    }

    private void closeSession(String sessionReferenceNumber, String accessToken) throws ApiException {
        ksefClient.closeOnlineSession(sessionReferenceNumber, accessToken);
    }

    private void getOnlineSessionInvoiceUpo(String sessionReferenceNumber, String ksefNumber, String accessToken) throws ApiException {
        byte[] upoResponse = ksefClient.getSessionInvoiceUpoByKsefNumber(sessionReferenceNumber, ksefNumber, accessToken);

        Assertions.assertNotNull(upoResponse);
    }

    private void grantPefInvoicingToProvider(String peppolProvider, String accessToken) throws ApiException {
        GrantAuthorizationPermissionsRequest request = new GrantAuthorizationPermissionsRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.PEPPOL_ID, peppolProvider))
                .withPermission(InvoicePermissionType.PEF_INVOICING)
                .withDescription("pef grant")
                .build();

        OperationResponse response = ksefClient.grantsPermissionsProxyEntity(request, accessToken);
        Assertions.assertNotNull(response);

        await().atMost(10, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isPermissionStatusReady(response.getReferenceNumber(), accessToken));
    }

    private Boolean isPermissionStatusReady(String grantReferenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo status = ksefClient.permissionOperationStatus(grantReferenceNumber, accessToken);
        return status != null && status.getStatus().getCode() == 200;
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

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                   statusResponse.getSuccessfulInvoiceCount() != null &&
                   statusResponse.getSuccessfulInvoiceCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private String sendPefInvoice(String sessionReferenceNumber, EncryptionData encryptionData,
                                  String contextNip, String path, String accessToken) throws IOException, ApiException {
        String buyerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String iban = IdentifierGeneratorUtils.generateIban();
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#buyer_reference#", "PL" + buyerNip)
                .replace("#buyer_nip#", "PL" + buyerNip)
                .replace("#supplier_nip#", "PL" + contextNip)
                .replace("#nip#", "PL" + contextNip)
                .replace("#invoice_number#", UUID.randomUUID().toString())
                .replace("#iban_plain#", iban)
                .replace("#issue_date#", LocalDate.of(2025, 9, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#due_date#", LocalDate.of(2025, 9, 15).format(java.time.format.DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd")));

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


    private SessionInvoiceStatusResponse getOnlineSessionDocuments(String sessionReferenceNumber, String accessToken) throws ApiException {
        SessionInvoicesResponse sessionInvoices = ksefClient.getSessionInvoices(sessionReferenceNumber, null, 10, accessToken);
        Assertions.assertEquals(1, sessionInvoices.getInvoices().size());
        SessionInvoiceStatusResponse invoice = sessionInvoices.getInvoices().getFirst();
        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getOrdinalNumber());
        Assertions.assertNotNull(invoice.getInvoiceNumber());
        Assertions.assertNotNull(invoice.getKsefNumber());
        Assertions.assertNotNull(invoice.getReferenceNumber());
        Assertions.assertNotNull(invoice.getInvoiceHash());
        Assertions.assertNotNull(invoice.getInvoicingDate());
        Assertions.assertNotNull(invoice.getStatus());
        Assertions.assertEquals(200, invoice.getStatus().getCode());

        return invoice;
    }
}
