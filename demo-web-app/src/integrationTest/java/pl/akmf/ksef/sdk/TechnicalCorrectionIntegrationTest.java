package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
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
import java.util.Base64;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

// Testy end-to-end dla funkcjonalności korekty technicznej faktur w systemie KSeF.
// Korekta techniczna umożliwia ponowne przesłanie faktury wystawionej w trybie offline,
// która została odrzucona z powodu błędów technicznych (np. błąd walidacji semantycznej).
//
// Zgodnie z dokumentacją KSeF, korekta techniczna:
// - Może być przesyłana wyłącznie w sesji interaktywnej
// - Dotyczy faktur offline odrzuconych zarówno w sesji interaktywnej, jak i wsadowej
// - Nie pozwala na korygowanie treści faktury - dotyczy tylko problemów technicznych
// - Wymaga podania HashOfCorrectedInvoice z pierwotnej, odrzuconej faktury
class TechnicalCorrectionIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    // Testuje scenariusz korekty technicznej faktury w ramach tej samej sesji interaktywnej.
    // Scenariusz testu:
    // 1. Otwiera sesję interaktywną z szyfrowaniem AES-256
    // 2. Wysyła fakturę z błędem semantycznym (data w przyszłości)
    // 3. Oczekuje na odrzucenie faktury z kodem błędu 450
    // 4. Wysyła korektę techniczną z poprawną fakturą, podając hash odrzuconej faktury
    // 5. Weryfikuje pomyślne przyjęcie korekty (kod 200 - Success)
    // </summary>
    // <param name="systemCode">Kod systemu KSeF (np. FA3 dla standardowych faktur)</param>
    // <param name="invoiceTemplatePath">Ścieżka do pliku szablonu XML faktury w katalogu Templates</param>
    @Test
    void invoiceTechnicalCorrection() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);
        // Przygotowanie i wysłanie faktury z błędem semantycznym (data w przyszłości)
        String incorrectInvoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, LocalDate.now().plusDays(2), sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template_v3.xml", null, accessToken);

        // Weryfikacja odrzucenia z błędem semantycznym (kod 450)
        Integer invoiceSemanticValidationErrorStatusCode = 450;
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> checkInvoiceStatus(sessionReferenceNumber, incorrectInvoiceReferenceNumber, invoiceSemanticValidationErrorStatusCode, accessToken));
        SessionInvoiceStatusResponse incorrectInvoiceStatusResponse = ksefClient.getSessionInvoiceStatus(sessionReferenceNumber, incorrectInvoiceReferenceNumber, accessToken);
        String rejectedInvoiceHash = incorrectInvoiceStatusResponse.getInvoiceHash();

        // Wysłanie korekty technicznej z poprawną fakturą
        String technicalCorrectionInvoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, LocalDate.now(), sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template_v3.xml", rejectedInvoiceHash, accessToken);

        // Weryfikacja pomyślnego przyjęcia korekty technicznej
        Integer invoiceSuccessStatusCode = 200;
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> checkInvoiceStatus(sessionReferenceNumber, technicalCorrectionInvoiceReferenceNumber, invoiceSuccessStatusCode, accessToken));

        ksefClient.closeOnlineSession(sessionReferenceNumber, accessToken);
    }

    // Testuje scenariusz korekty technicznej faktury w ramach nowej sesji interaktywnej.
    // Ten test weryfikuje, że korekta techniczna może być wysłana w innej sesji niż ta,
    // w której faktura została pierwotnie odrzucona.
    //
    // Scenariusz testu:
    // 1. Otwiera pierwszą sesję interaktywną z szyfrowaniem AES-256
    // 2. Wysyła fakturę z błędem semantycznym (data w przyszłości)
    // 3. Oczekuje na odrzucenie faktury z kodem błędu 450
    // 4. Zamyka pierwszą sesję
    // 5. Otwiera nową, drugą sesję interaktywną
    // 6. Wysyła korektę techniczną z poprawną fakturą w nowej sesji
    // 7. Weryfikuje pomyślne przyjęcie korekty (kod 200 - Success)
    // 8. Zamyka drugą sesję
    @Test
    void shouldSuccessfullyProcessTechnicalCorrectionInDifferentSession() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessToken);
        // Przygotowanie i wysłanie faktury z błędem semantycznym (data w przyszłości)
        String incorrectInvoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, LocalDate.now().plusDays(2), sessionReferenceNumber, encryptionData, "/xml/invoices/sample/invoice-template_v3.xml", null, accessToken);

        // Weryfikacja odrzucenia z błędem semantycznym (kod 450)
        Integer invoiceSemanticValidationErrorStatusCode = 450;
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> checkInvoiceStatus(sessionReferenceNumber, incorrectInvoiceReferenceNumber, invoiceSemanticValidationErrorStatusCode, accessToken));
        SessionInvoiceStatusResponse incorrectInvoiceStatusResponse = ksefClient.getSessionInvoiceStatus(sessionReferenceNumber, incorrectInvoiceReferenceNumber, accessToken);
        String rejectedInvoiceHash = incorrectInvoiceStatusResponse.getInvoiceHash();

        // Zamknięcie pierwszej sesji
        ksefClient.closeOnlineSession(sessionReferenceNumber, accessToken);

        // Otwarcie nowej, drugiej sesji dla wysłania korekty technicznej
        EncryptionData encryptionDataSecondSession = defaultCryptographyService.getEncryptionData();
        String accessTokenSecondSession = authWithCustomNip(contextNip, contextNip).accessToken();
        String sessionReferenceNumberSecondSession = openOnlineSession(encryptionDataSecondSession, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, accessTokenSecondSession);

        // Wysłanie korekty technicznej z poprawną fakturą
        String technicalCorrectionInvoiceReferenceNumber = sendInvoiceOnlineSession(contextNip, LocalDate.now(), sessionReferenceNumberSecondSession, encryptionDataSecondSession, "/xml/invoices/sample/invoice-template_v3.xml", rejectedInvoiceHash, accessTokenSecondSession);

        // Weryfikacja pomyślnego przyjęcia korekty technicznej
        Integer invoiceSuccessStatusCode = 200;
        await().atMost(50, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> checkInvoiceStatus(sessionReferenceNumberSecondSession, technicalCorrectionInvoiceReferenceNumber, invoiceSuccessStatusCode, accessTokenSecondSession));

        ksefClient.closeOnlineSession(sessionReferenceNumberSecondSession, accessTokenSecondSession);
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode,
                                     SchemaVersion schemaVersion,
                                     SessionValue value,
                                     String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }

    private String sendInvoiceOnlineSession(String nip, LocalDate date, String sessionReferenceNumber, EncryptionData encryptionData,
                                            String path, String hashOfCorrectedInvoice, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#",
                        date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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
                .withOfflineMode(true)
                .build();

        if (hashOfCorrectedInvoice != null && !hashOfCorrectedInvoice.isBlank()) {
            sendInvoiceOnlineSessionRequest.setHashOfCorrectedInvoice(hashOfCorrectedInvoice);
        }

        SendInvoiceResponse sendInvoiceResponse = ksefClient.onlineSessionSendInvoice(sessionReferenceNumber, sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        return sendInvoiceResponse.getReferenceNumber();
    }

    private boolean checkInvoiceStatus(String sessionReferenceNumber, String invoiceReferenceNumber, Integer expectedCode, String accessToken) {
        try {
            SessionInvoiceStatusResponse statusResponse = ksefClient.getSessionInvoiceStatus(sessionReferenceNumber, invoiceReferenceNumber, accessToken);
            return statusResponse != null &&
                    expectedCode.equals(statusResponse.getStatus().getCode());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return false;
    }
}
