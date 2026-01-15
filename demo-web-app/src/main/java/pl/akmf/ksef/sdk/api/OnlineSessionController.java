package pl.akmf.ksef.sdk.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
public class OnlineSessionController {
    private final DefaultKsefClient ksefClient;
    private final DefaultCryptographyService defaultCryptographyService;
    private EncryptionData encryptionData;

    /**
     * Otwarcie sesji interaktywnej
     *
     * @return OpenOnlineSessionResponse
     * @throws ApiException if fails to make API call
     */
    @PostMapping(value = "/open-session")
    public OpenOnlineSessionResponse initSession(@RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        encryptionData = defaultCryptographyService.getEncryptionData();

        //stworzenie zapytania
        var request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        //otwarcie sesji interaktywnej
        return ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, authToken);
    }

    /**
     * Wysłanie faktury
     *
     * @param referenceNumber numer referencyjny otwartej sesji
     * @return OpenOnlineSessionResponse
     * @throws ApiException if fails to make API call
     */
    @PostMapping(value = "/send-invoice/{referenceNumber}/{contextIdentifier}")
    public SendInvoiceResponse sendInvoiceOnlineSessionAsync(@PathVariable String referenceNumber,
                                                             @PathVariable String contextIdentifier,
                                                             @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException, IOException {
        //read example invoice
        String invoicePath = "demo-web-app/src/main/resources/xml/invoices/sample/invoice-template.xml";
        String invoiceTemplate = Files.readString(Paths.get(invoicePath), StandardCharsets.UTF_8)
                .replace("#nip#", contextIdentifier)
                .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());
        var invoice = invoiceTemplate.getBytes(StandardCharsets.UTF_8);

        //encrypt invoice
        var encryptedInvoice = defaultCryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        var invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        var encryptedInvoiceMetadata = defaultCryptographyService.getMetaData(encryptedInvoice);

        //prepare request
        var sendInvoiceRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .build();

        //send invoice
        return ksefClient.onlineSessionSendInvoice(referenceNumber, sendInvoiceRequest, authToken);
    }

    /**
     * Wysłanie faktury online
     *
     * @param referenceNumber numer referencyjny otwartej sesji
     * @return OpenOnlineSessionResponse
     * @throws ApiException if fails to make API call
     */
    @PostMapping(value = "/send-invoice/{referenceNumber}/{contextIdentifier}/{hashOfCorrectedInvoice}")
    public SendInvoiceResponse sendTechnicalCorrection(@PathVariable String referenceNumber,
                                                       @PathVariable String contextIdentifier,
                                                       @PathVariable String hashOfCorrectedInvoice,
                                                       @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException, IOException {
        //read example invoice
        String invoicePath = "demo-web-app/src/main/resources/xml/invoices/sample/invoice-template.xml";
        String invoiceTemplate = Files.readString(Paths.get(invoicePath), StandardCharsets.UTF_8)
                .replace("#nip#", contextIdentifier)
                .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());
        var invoice = invoiceTemplate.getBytes(StandardCharsets.UTF_8);

        //encrypt invoice
        var encryptedInvoice = defaultCryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        var invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        var encryptedInvoiceMetadata = defaultCryptographyService.getMetaData(encryptedInvoice);

        //prepare request
        var sendInvoiceRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .withOfflineMode(true)
                .withHashOfCorrectedInvoice(hashOfCorrectedInvoice)
                .build();

        //send invoice
        return ksefClient.onlineSessionSendInvoice(referenceNumber, sendInvoiceRequest, authToken);
    }

    /**
     * Zamknięcie sesji interaktywnej
     *
     * @throws ApiException if fails to make API call
     */
    @PostMapping(value = "/close-session/{referenceNumber}")
    public void sessionClose(@PathVariable String referenceNumber,
                             @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        ksefClient.closeOnlineSession(referenceNumber, authToken);
    }
}
