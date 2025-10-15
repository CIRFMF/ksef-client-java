package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
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
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class QrCodeOnlineIntegrationTest extends BaseIntegrationTest {
    private static final int SESSION_SUCCESSFUL_STATUS_CODE = 200;
    private static final int SESSION_FAILED_STATUS_CODE = 445;
    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    /**
     * End-to-end test weryfikujący pełny, zakończony sukcesem przebieg wystawienia kodu QR do faktury w trybie interaktywnym (online session).
     * Test używa faktury FA(2) oraz szyfrfowania RSA.
     * Kroki:
     * 1. Autoryzacja, pozyskanie tokenu dostępu.
     * 2. Otwarcie sesji online z szyfrowaniem RSA.
     * 3. Utworzenie i wysłanie pojedynczej faktury FA(2).
     * 4. Weryfikacja, czy faktura została dodana do sesji.
     * 5. Zamknięcie sesji online.
     * 6. Sprawdzenie statusu sesji, oczekiwanie na zakończenie przetwarzania faktur.
     * 7. Sprawdzenie statusu faktury.
     * 8. Pobranie metadanych faktur z sesji.
     * 9. Znalezienie metadanych faktury wśród metadanych wszystkich faktur z sesji.
     * 10. Stworzenie linku weryfikacyjnego do faktury za pomoca certyfikatu oraz hashu faktury.
     * 11. Utworzenie kodu QR dla trybu online.
     * 12. Dodanie napisu z numerem faktury do kodu QR (Label).
     */
    @Test
    public void qrCodeOnlineE2ETest() throws JAXBException, IOException, ApiException {
        //Autoryzacja, pozyskanie tokenu dostępu
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        //Otwarcie sesji online z szyfrowaniem RSA.
        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);

        //Utworzenie i wysłanie faktury FA(2)
        String invoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template.xml", accessToken);

        //Weryfikacja, czy faktura została dodana do sesji.
        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isInvoicesInSessionAdded(sessionReferenceNumber, accessToken));

        checkOnlineSessionStatus(sessionReferenceNumber, 100, accessToken);

        //Zamknięcie sesji online
        closeOnlineSession(sessionReferenceNumber, accessToken);

        //Sprawdzenie statusu sesji, oczekiwanie na zakończenie przetwarzania faktur
        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> checkOnlineSessionStatus(sessionReferenceNumber, SESSION_SUCCESSFUL_STATUS_CODE, accessToken));

        Assertions.assertNotEquals(SESSION_FAILED_STATUS_CODE, (int) getSessionStatusResponse(sessionReferenceNumber, accessToken).getStatus().getCode());

        //Sprawdzenie statusu faktury
        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionInvoiceStatusResponse invoiceStatus = getSessionInvoiceStatus(sessionReferenceNumber, invoiceReferenceNumber, accessToken);
                    Assertions.assertNotNull(invoiceStatus);
                    return invoiceStatus.getStatus().getCode() == 200;
                });

        //Pobranie metadanych faktur z sesji
        SessionInvoicesResponse invoicesMetadata = getOnlineSessionDocuments(sessionReferenceNumber, accessToken);

        //Znalezienie metadanych faktury wśród metadanych wszystkich faktur z sesji
        SessionInvoiceStatusResponse invoiceMetadata = invoicesMetadata.getInvoices()
                .stream()
                .filter(x -> x.getReferenceNumber().equals(invoiceReferenceNumber))
                .findFirst()
                .orElseThrow();
        String invoiceKsefNumber = invoiceMetadata.getKsefNumber();
        String invoiceHash = invoiceMetadata.getInvoiceHash();
        OffsetDateTime invoicingDate = invoiceMetadata.getInvoicingDate();

        //Stworzenie linku weryfikacyjnego do faktury za pomoca certyfikatu oraz hashu faktury
        String invoiceForOnlineUrl = verificationLinkService.buildInvoiceVerificationUrl(contextNip, invoicingDate.toLocalDate(), invoiceHash);

        Assertions.assertNotNull(invoiceForOnlineUrl);
        Assertions.assertTrue(invoiceForOnlineUrl.contains(Base64.getUrlEncoder().withoutPadding().encodeToString(Base64.getDecoder().decode(invoiceHash))));
        Assertions.assertTrue(invoiceForOnlineUrl.contains(contextNip));
        Assertions.assertTrue(invoiceForOnlineUrl.contains(invoicingDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        //Utworzenie kodu QR dla trybu online
        byte[] qrOnline = qrCodeService.generateQrCode(invoiceForOnlineUrl);

        Assertions.assertNotNull(qrOnline);

        //Dodanie napisu z numerem faktury do kodu QR (Label)
        qrOnline = qrCodeService.addLabelToQrCode(qrOnline, invoiceKsefNumber);

        Assertions.assertNotNull(qrOnline);
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode, SchemaVersion schemaVersion, SessionValue value, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendInvoiceOnlineSession(String nip, String sessionReferenceNumber, EncryptionData encryptionData,
                                            String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(Objects.requireNonNull(BaseIntegrationTest.class.getResourceAsStream(path))
                .readAllBytes(), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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

    private boolean isInvoicesInSessionAdded(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = getSessionStatusResponse(sessionReferenceNumber, accessToken);
            return statusResponse != null && statusResponse.getInvoiceCount() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkOnlineSessionStatus(String sessionReferenceNumber, int expectedSessionStatus, String accessToken) throws ApiException {
        SessionStatusResponse statusResponse = getSessionStatusResponse(sessionReferenceNumber, accessToken);
        Assertions.assertNotNull(statusResponse);
        Assertions.assertEquals(expectedSessionStatus, (int) statusResponse.getStatus().getCode());
        return true;
    }

    private SessionStatusResponse getSessionStatusResponse(String sessionReferenceNumber, String accessToken) throws ApiException {
        return ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
    }

    private void closeOnlineSession(String sessionReferenceNumber, String accessToken) throws ApiException {
        ksefClient.closeOnlineSession(sessionReferenceNumber, accessToken);
    }

    private SessionInvoiceStatusResponse getSessionInvoiceStatus(String sessionReferenceNumber, String invoiceReferenceNumber, String accessToken) throws ApiException {
        return ksefClient.getSessionInvoiceStatus(sessionReferenceNumber, invoiceReferenceNumber, accessToken);
    }

    private SessionInvoicesResponse getOnlineSessionDocuments(String sessionReferenceNumber, String accessToken) throws ApiException {
        SessionInvoicesResponse sessionInvoices = ksefClient.getSessionInvoices(sessionReferenceNumber, null, 10, accessToken);
        Assertions.assertEquals(1, sessionInvoices.getInvoices().size());

        return sessionInvoices;
    }
}
