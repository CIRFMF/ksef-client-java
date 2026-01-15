package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.bouncycastle.asn1.ASN1Encoding;
import org.testcontainers.shaded.org.bouncycastle.asn1.x500.X500Name;
import org.testcontainers.shaded.org.bouncycastle.operator.ContentSigner;
import org.testcontainers.shaded.org.bouncycastle.operator.OperatorCreationException;
import org.testcontainers.shaded.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.testcontainers.shaded.org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.testcontainers.shaded.org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.testcontainers.shaded.org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateMetadataListRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.certificate.SendCertificateEnrollmentRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateInfo;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListItemStatus;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateMetadataListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateType;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.certificate.QueryCertificatesRequest;
import pl.akmf.ksef.sdk.client.model.certificate.RetrieveCertificatesListItem;
import pl.akmf.ksef.sdk.client.model.certificate.SendCertificateEnrollmentRequest;
import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;
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
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.HttpClientBuilder;
import pl.akmf.ksef.sdk.util.HttpClientConfig;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class QrCodeOfflineIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    /**
     * End-to-end test weryfikujący pełny, zakończony sukcesem przebieg wystawienia kodów QR do faktury w trybie offline (offlineMode = true).
     * Test używa faktury FA(2) oraz szyfrfowania RSA.
     * Kroki:
     * 1. Autoryzacja, pozyskanie tokenu dostępu.
     * 2. Utworzenie Certificate Signing Request (csr) oraz klucz prywatny za pomocą ${encryptionMethod}.
     * 3. Zapisanie klucza prywatnego (private key).
     * 4. Utworzenie i wysłanie żądania wystawienia certyfikatu KSeF.
     * 5. Sprawdzenie statusu żądania, oczekiwanie na zakończenie przetwarzania CSR.
     * 6. Pobranie certyfikatu KSeF.
     * 7. Odfiltrowanie i zapisanie właściwego certyfikatu.
     * Następnie cały proces odbywa się offline, bez kontaktu z KSeF:
     * 8. Przygotowanie faktury w formacie XML.
     * 9. Zapisanie skrótu faktury (hash).
     * 10. Utworzenie odnośnika (Url) do weryfikacji faktury (KOD I), weryfikacja faktury poprzez link weryfikacyjny
     * 11. Utworzenie kodu QR faktury (KOD I) dla trybu offline.
     * 12. Utworzenie odnośnika (Url) do weryfikacji certyfikatu (KOD II), weryfikujemy certyfikat poprzez link weryfikacyjny
     * 13. Utworzenie kodu QR do weryfikacji certyfikatu (KOD II) dla trybu offline.
     * Zakładamy, że jesteśmy już online
     * 14. nawiązujemy sesję, wysyłamy fakturę, weryfikujemy fakturę poprzez link weryfikacyjny
     */
    static Stream<Arguments> inputQrCodeOfflineE2ETestParameters() {
        return Stream.of(
                Arguments.of(SystemCode.FA_2, "invoice-template.xml", EncryptionMethod.Rsa),
                Arguments.of(SystemCode.FA_3, "invoice-template_v3.xml", EncryptionMethod.Rsa),
                Arguments.of(SystemCode.FA_2, "invoice-template.xml", EncryptionMethod.ECDsa),
                Arguments.of(SystemCode.FA_3, "invoice-template_v3.xml", EncryptionMethod.ECDsa)
        );
    }

    @ParameterizedTest
    @MethodSource("inputQrCodeOfflineE2ETestParameters")
    public void qrCodeOfflineE2ETest(SystemCode systemCode, String invoiceTemplate, EncryptionMethod encryptionMethod) throws ApiException, JAXBException, IOException, InterruptedException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        //Autoryzacja, pozyskanie tokenu dostępu
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //Utworzenie Certificate Signing Request (csr) oraz klucz prywatny za pomocą ${encryptionMethod}
        CertificateEnrollmentsInfoResponse enrollmentInfo = getEnrolmentInfo(accessToken);
        CsrResult csr = EncryptionMethod.Rsa.equals(encryptionMethod)
                ? defaultCryptographyService.generateCsrWithRsa(enrollmentInfo)
                : defaultCryptographyService.generateCsrWithEcdsa(enrollmentInfo);

        // Zapisanie klucza prywatnego (private key) do pamięci tylko na potrzeby testu, w rzeczywistości powinno być bezpiecznie przechowywane
        byte[] privateKey = csr.privateKey();

        //Utworzenie i wysłanie żądania wystawienia certyfikatu KSeF
        String referenceNumber = sendEnrollment(csr.csr(), CertificateType.OFFLINE, accessToken);

        //Sprawdzenie statusu żądania, oczekiwanie na zakończenie przetwarzania CSR
        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isEnrolmentStatusReady(referenceNumber, accessToken));

        CertificateEnrollmentStatusResponse enrolmentStatus = getEnrolmentStatus(referenceNumber, accessToken);

        //Pobranie certyfikatu KSeF
        List<RetrieveCertificatesListItem> certificateList = getCertificateList(enrolmentStatus.getCertificateSerialNumber(), accessToken);

        //Odfiltrowanie i zapisanie właściwego certyfikatu do pamięci, w rzeczywistości powinien być bezpiecznie przechowywany
        RetrieveCertificatesListItem certificate = certificateList.stream()
                .filter(c -> CertificateType.OFFLINE.equals(c.getCertificateType()))
                .findFirst()
                .orElseThrow();

        //=====od tego momentu jestem całkowicie offline, nie mam dostępu do KSeF=====
        //Przygotowanie faktury w formacie XML
        //gotową fakturę należy zapisać, aby wysłać do KSeF później (zgodnie z obowiązującymi przepisami), oznaczoną jako offlineMode = true
        LocalDate invoicingDate = LocalDate.of(2025, 10, 1);
        byte[] invoice = prepareInvoice(contextNip, invoicingDate, "/xml/invoices/sample/" + invoiceTemplate);

        FileMetadata invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        //Zapisanie skrótu faktury (hash)
        String invoiceHash = invoiceMetadata.getHashSHA();

        //Utworzenie odnośnika (Url) do weryfikacji faktury (KOD I), weryfikacja faktury poprzez link weryfikacyjny
        String invoiceForOfflineUrl = verificationLinkService.buildInvoiceVerificationUrl(contextNip, invoicingDate, invoiceHash);
        Assertions.assertNotNull(invoiceForOfflineUrl);
        Assertions.assertTrue(invoiceForOfflineUrl.contains(Base64.getUrlEncoder().withoutPadding().encodeToString(Base64.getDecoder().decode(invoiceHash))));
        Assertions.assertTrue(invoiceForOfflineUrl.contains(contextNip));
        Assertions.assertTrue(invoiceForOfflineUrl.contains(invoicingDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        checkInvoiceByVerificationUrl(invoiceForOfflineUrl, false);

        //Utworzenie kodu QR faktury (KOD I) dla trybu offline
        byte[] qrOffline = qrCodeService.generateQrCode(invoiceForOfflineUrl);

        //Dodanie etykiety OFFLINE
        qrOffline = qrCodeService.addLabelToQrCode(qrOffline, "OFFLINE");

        Assertions.assertNotNull(qrOffline);

        //Utworzenie odnośnika (Url) do weryfikacji certyfikatu (KOD II), weryfikujemy certyfikat poprzez link weryfikacyjny
        String url = verificationLinkService.buildCertificateVerificationUrl(
                contextNip,
                ContextIdentifierType.NIP,
                contextNip,
                certificate.getCertificateSerialNumber(),
                invoiceHash,
                EncryptionMethod.Rsa.equals(encryptionMethod)
                        ? defaultCryptographyService.parseRsaPrivateKeyFromPem(privateKey)
                        : defaultCryptographyService.parseEcdsaPrivateKeyFromPem(privateKey));
        checkIssuerMetadataByVerificationUrl(url);

        //Utworzenie kodu QR do weryfikacji certyfikatu (KOD II) dla trybu offline
        byte[] qrOfflineCertificate = qrCodeService.generateQrCode(url);

        //Dodanie etykiety CERTYFIKAT
        qrOfflineCertificate = qrCodeService.addLabelToQrCode(qrOfflineCertificate, "CERTYFIKAT");

        Assertions.assertNotNull(qrOfflineCertificate);

        // zakładam, że jestem spowrotem online
        // nawiązujemy sesję, wysyłamy fakturę, weryfikujemy faktuę poprzez link weryfikacyjny
        String accessToken2 = authWithCustomNip(contextNip, contextNip).accessToken();
        openSessionAndSendInvoice(invoice, invoiceMetadata, systemCode, accessToken2);
        checkInvoiceByVerificationUrl(invoiceForOfflineUrl, true);
    }

    @Test
    @Disabled("Tylko na potrzeby wyslania CSR do systemu")
    public void sendCsr() throws IOException, OperatorCreationException, NoSuchAlgorithmException, JAXBException, ApiException, InvalidKeySpecException {
        byte[] privateKey = readBytesFromPath("/keys/private/rsa/sample/private-key.pem");
        byte[] publicKey = readBytesFromPath("/keys/private/rsa/sample/public-key.pem");
        String contextNip = "7368335898";
        X500Name subject = new X500Name("CN=Kowalski,O=Kowalski sp. z o.o,C=PL,2.5.4.97=VATPL-7368335898");
        byte[] csr = getCsr(subject,
                parseRsaPublicKeyFromPem(publicKey),
                defaultCryptographyService.parseRsaPrivateKeyFromPem(privateKey)
        );
        Arrays.fill(privateKey, (byte) 0);
        Arrays.fill(publicKey, (byte) 0);

        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String referenceNumber = sendEnrollment(csr, CertificateType.OFFLINE, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isEnrolmentStatusReady(referenceNumber, accessToken));

        CertificateEnrollmentStatusResponse enrolmentStatus = getEnrolmentStatus(referenceNumber, accessToken);
        List<RetrieveCertificatesListItem> certificateList = getCertificateList(enrolmentStatus.getCertificateSerialNumber(), accessToken);
        RetrieveCertificatesListItem certificate = certificateList.stream()
                .filter(c -> CertificateType.OFFLINE.equals(c.getCertificateType()))
                .findFirst()
                .orElseThrow();
        Assertions.assertNotNull(certificate);
    }

    static Stream<Arguments> inputQrCodeOfflineE2ETestReadKeyFromDiscParameters() {
        return Stream.of(
                // wersja z wygenerowanym wcześniej przez SDK kluczem RSA (+ wysłanym requestem enrolment z metody wyżej sendCsr() )
                Arguments.of("7368335898", "01732D26F736E531", EncryptionMethod.Rsa, SystemCode.FA_2, "invoice-template.xml", "/keys/private/rsa/sample/private-key.pem"),
                Arguments.of("7368335898", "01732D26F736E531", EncryptionMethod.Rsa, SystemCode.FA_3, "invoice-template_v3.xml", "/keys/private/rsa/sample/private-key.pem"),
                // wersja z wygenrowanym kluczem ECC przez aplikacje podatnika, enrolment również był wysłany przez aplikacje podatnika
                // https://web2te-ksef.mf.gov.pl/web/certificates/certificate-list - logujemy sie nipem - uwaga - z racji, że każdy może nawiązać sesję tym nipem, może być sytuacja, w której inna osoba unieważni certyfikat o podanym serial number w teście i testy przestaną działać, w assercji niżej jest to sprawdzane
                Arguments.of("8096529464", "01D1732E43B9345E", EncryptionMethod.ECDsa, SystemCode.FA_2, "invoice-template.xml", "/keys/private/ecdsa/sample/testowy_klucz_sdk.key"),// wazny 2 lata od 05.12.2025
                Arguments.of("8096529464", "01D1732E43B9345E", EncryptionMethod.ECDsa, SystemCode.FA_3, "invoice-template_v3.xml", "/keys/private/ecdsa/sample/testowy_klucz_sdk.key")// wazny 2 lata od 05.12.2025
        );
    }

    @ParameterizedTest
    @MethodSource("inputQrCodeOfflineE2ETestReadKeyFromDiscParameters")
    public void qrCodeOfflineE2ETestReadKeyFromDisc(String contextNip, String certificateSerialNumber, EncryptionMethod encryptionMethod, SystemCode systemCode, String invoiceTemplate, String privateKeyPath) throws ApiException, JAXBException, IOException, InterruptedException {
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        QueryCertificatesRequest request = new CertificateMetadataListRequestBuilder()
                .build();
        CertificateMetadataListResponse response = ksefClient.getCertificateMetadataList(request, 10, 0, accessToken);
        CertificateInfo certificateInfo = response.getCertificates()
                .stream()
                .filter(e -> e.getCertificateSerialNumber().equals(certificateSerialNumber))
                .filter(c -> CertificateType.OFFLINE.equals(c.getType()))
                .findFirst()
                .orElseThrow();
        Assertions.assertTrue(certificateInfo.getStatus().equals(CertificateListItemStatus.ACTIVE)); // jeśli ktos deaktywował to należy sobie wygenerować nowy i w parametrach metody/testu podać nowy nip i serial number
        // powyzsze testowe sprawdzenie czy certyfikat w resourcach dalej jest aktywny

        //=====od tego momentu jestem całkowicie offline, nie mam dostępu do KSeF=====
        //Przygotowanie faktury w formacie XML
        //gotową fakturę należy zapisać, aby wysłać do KSeF później (zgodnie z obowiązującymi przepisami), oznaczoną jako offlineMode = true
        LocalDate invoicingDate = LocalDate.of(2025, 10, 1);
        byte[] invoice = prepareInvoice(contextNip, invoicingDate, "/xml/invoices/sample/" + invoiceTemplate);

        FileMetadata invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        //Zapisanie skrótu faktury (hash)
        String invoiceHash = invoiceMetadata.getHashSHA();

        //Utworzenie odnośnika (Url) do weryfikacji faktury (KOD I), weryfikacja faktury poprzez link weryfikacyjny
        String invoiceForOfflineUrl = verificationLinkService.buildInvoiceVerificationUrl(contextNip, invoicingDate, invoiceHash);
        Assertions.assertNotNull(invoiceForOfflineUrl);
        Assertions.assertTrue(invoiceForOfflineUrl.contains(Base64.getUrlEncoder().withoutPadding().encodeToString(Base64.getDecoder().decode(invoiceHash))));
        Assertions.assertTrue(invoiceForOfflineUrl.contains(contextNip));
        Assertions.assertTrue(invoiceForOfflineUrl.contains(invoicingDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        checkInvoiceByVerificationUrl(invoiceForOfflineUrl, false);

        //Utworzenie kodu QR faktury (KOD I) dla trybu offline
        byte[] qrOffline = qrCodeService.generateQrCode(invoiceForOfflineUrl);

        //Dodanie etykiety OFFLINE
        qrOffline = qrCodeService.addLabelToQrCode(qrOffline, "OFFLINE");

        Assertions.assertNotNull(qrOffline);

        // wczytanie klucza prywatnego z dysku (private key) do pamięci tylko na potrzeby testu, w rzeczywistości powinno być bezpiecznie przechowywane
        byte[] privateKey = readBytesFromPath(privateKeyPath);
        //Utworzenie odnośnika (Url) do weryfikacji certyfikatu (KOD II), weryfikujemy certyfikat poprzez link weryfikacyjny
        String url = verificationLinkService.buildCertificateVerificationUrl(
                contextNip,
                ContextIdentifierType.NIP,
                contextNip,
                certificateSerialNumber,
                invoiceHash,
                EncryptionMethod.Rsa.equals(encryptionMethod)
                        ? defaultCryptographyService.parseRsaPrivateKeyFromPem(privateKey)
                        : defaultCryptographyService.parseEncryptedEcdsaPrivateKeyFromPem(privateKey, "ADadADad12!@adadad".toCharArray()) // haslo przekazac w sposob bezpieczny, np z env variable, czy secret managerem
        );

        checkIssuerMetadataByVerificationUrl(url);
        Arrays.fill(privateKey, (byte) 0);

        //Utworzenie kodu QR do weryfikacji certyfikatu (KOD II) dla trybu offline
        byte[] qrOfflineCertificate = qrCodeService.generateQrCode(url);

        //Dodanie etykiety CERTYFIKAT
        qrOfflineCertificate = qrCodeService.addLabelToQrCode(qrOfflineCertificate, "CERTYFIKAT");

        Assertions.assertNotNull(qrOfflineCertificate);

        // zakładam, że jestem spowrotem online
        // nawiązujemy sesję, wysyłamy fakturę, weryfikujemy faktuę poprzez link weryfikacyjny
        accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        openSessionAndSendInvoice(invoice, invoiceMetadata, systemCode, accessToken);
        checkInvoiceByVerificationUrl(invoiceForOfflineUrl, true);
    }

    private CertificateEnrollmentsInfoResponse getEnrolmentInfo(String accessToken) throws ApiException {
        CertificateEnrollmentsInfoResponse response = ksefClient.getCertificateEnrollmentInfo(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getOrganizationIdentifier());

        return response;
    }

    private String sendEnrollment(byte[] csr, CertificateType certificateType, String accessToken) throws ApiException {
        SendCertificateEnrollmentRequest request = new SendCertificateEnrollmentRequestBuilder()
                .withValidFrom(LocalDateTime.now().minusHours(3).toString())
                .withCsr(csr)
                .withCertificateName("certificate")
                .withCertificateType(certificateType)
                .build();

        CertificateEnrollmentResponse response = ksefClient.sendCertificateEnrollment(request, accessToken);
        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }

    private Boolean isEnrolmentStatusReady(String referenceNumber, String accessToken) {
        try {
            CertificateEnrollmentStatusResponse response =
                    ksefClient.getCertificateEnrollmentStatus(referenceNumber, accessToken);
            return response != null &&
                   response.getStatus().getCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private CertificateEnrollmentStatusResponse getEnrolmentStatus(String referenceNumber, String accessToken) throws ApiException {
        CertificateEnrollmentStatusResponse response = ksefClient.getCertificateEnrollmentStatus(referenceNumber, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus().getCode());
        return response;
    }

    private List<RetrieveCertificatesListItem> getCertificateList(String certificateSerialNumber, String accessToken) throws ApiException {
        CertificateListResponse certificateResponse =
                ksefClient.getCertificateList(new CertificateListRequest(List.of(certificateSerialNumber)), accessToken);

        Assertions.assertNotNull(certificateResponse);
        Assertions.assertNotNull(certificateResponse.getCertificates());
        Assertions.assertTrue(certificateResponse.getCertificates().size() > 0);
        return certificateResponse.getCertificates();
    }

    private byte[] prepareInvoice(String nip, LocalDate invoicingDate, String path) throws IOException {
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#", invoicingDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());

        return invoiceTemplate.getBytes(StandardCharsets.UTF_8);
    }

    private void checkIssuerMetadataByVerificationUrl(String url) throws IOException, InterruptedException {
        String responseBody = makeHttpRequest(url);

        Assertions.assertTrue(responseBody.contains("Certyfikat istnieje"));
        Assertions.assertTrue(responseBody.contains("Certyfikat jest aktywny"));
        Assertions.assertTrue(responseBody.contains("Podpis wystawcy jest prawid"));
        Assertions.assertTrue(responseBody.contains("Wystawca posiada uprawnienia do wystawienia faktury"));
    }

    private void checkInvoiceByVerificationUrl(String invoiceForOfflineUrl, boolean shouldFindInvoice) throws IOException, InterruptedException {
        String responseBody = makeHttpRequest(invoiceForOfflineUrl);

        String expectedResult = shouldFindInvoice ? "Faktura znajduje się w KSeF" : "Faktura nie została znaleziona w KSeF";
        Assertions.assertTrue(responseBody.contains(expectedResult));
    }

    private String makeHttpRequest(String url) throws IOException, InterruptedException {
        HttpClientConfig config = new HttpClientConfig();
        HttpClient httpClient = HttpClientBuilder.createHttpBuilder(config)
                .build();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(config.getConnectTimeout());

        exampleApiProperties.getDefaultHeaders().forEach(builder::header);
        builder.GET();
        HttpRequest request = builder.build();

        HttpResponse<byte[]> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        Assertions.assertEquals(200, httpResponse.statusCode());
        String responseBody = new String(httpResponse.body());
        httpClient.close();

        return responseBody;
    }

    private void openSessionAndSendInvoice(byte[] invoice, FileMetadata invoiceMetadata,
                                           SystemCode systemCode, String accessToken) throws ApiException {
        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, SchemaVersion.VERSION_1_0E, SessionValue.FA))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        String sessionReferenceNumber = openOnlineSessionResponse.getReferenceNumber();
        Assertions.assertNotNull(sessionReferenceNumber);

        byte[] encryptedInvoice = defaultCryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        FileMetadata encryptedInvoiceMetadata = defaultCryptographyService.getMetaData(encryptedInvoice);

        SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .withOfflineMode(true)
                .build();

        SendInvoiceResponse sendInvoiceResponse = ksefClient.onlineSessionSendInvoice(sessionReferenceNumber, sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        await().atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, accessToken));
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

    private PublicKey parseRsaPublicKeyFromPem(byte[] publicKeyPem) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyPem);

        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private byte[] getCsr(X500Name subject, PublicKey publicKey, PrivateKey privateKey) throws IOException, OperatorCreationException {
        PKCS10CertificationRequestBuilder requestBuilder =
                new JcaPKCS10CertificationRequestBuilder(subject, publicKey);

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA")
                .build(privateKey);

        PKCS10CertificationRequest csr = requestBuilder.build(signer);

        return csr.toASN1Structure().getEncoded(ASN1Encoding.DER);
    }
}
