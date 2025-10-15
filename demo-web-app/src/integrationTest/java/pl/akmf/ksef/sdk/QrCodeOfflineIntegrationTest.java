package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.certificate.SendCertificateEnrollmentRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateType;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.certificate.RetrieveCertificatesListItem;
import pl.akmf.ksef.sdk.client.model.certificate.SendCertificateEnrollmentRequest;
import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
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
     * 2. Utworzenie Certificate Signing Request (csr) oraz klucz prywatny za pomocą RSA.
     * 3. Zapisanie klucza prywatnego (private key).
     * 4. Utworzenie i wysłanie żądania wystawienia certyfikatu KSeF.
     * 5. Sprawdzenie statusu żądania, oczekiwanie na zakończenie przetwarzania CSR.
     * 6. Pobranie certyfikatu KSeF.
     * 7. Odfiltrowanie i zapisanie właściwego certyfikatu.
     * Następnie cały proces odbywa się offline, bez kontaktu z KSeF:
     * 8. Przygotowanie faktury FA(2) w formacie XML.
     * 9. Zapisanie skrótu faktury (hash).
     * 10. Utworzenie odnośnika (Url) do weryfikacji faktury (KOD I).
     * 11. Utworzenie kodu QR faktury (KOD I) dla trybu offline.
     * 12. Utworzenie odnośnika (Url) do weryfikacji certyfikatu (KOD II).
     * 13. Utworzenie kodu QR do weryfikacji certyfikatu (KOD II) dla trybu offline.
     */
    static Stream<Arguments> inputTestParameters() {
        return Stream.of(
                Arguments.of("invoice-template.xml", EncryptionMethod.Rsa),
                Arguments.of("invoice-template_v3.xml", EncryptionMethod.Rsa),
                Arguments.of("invoice-template.xml", EncryptionMethod.ECDsa),
                Arguments.of("invoice-template_v3.xml", EncryptionMethod.ECDsa)
        );
    }

    @ParameterizedTest
    @MethodSource("inputTestParameters")
    public void qrCodeOfflineE2ETest(String invoiceTemplate, EncryptionMethod encryptionMethod) throws ApiException, JAXBException, IOException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        //Autoryzacja, pozyskanie tokenu dostępu
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //Utworzenie Certificate Signing Request (csr) oraz klucz prywatny za pomocą RSA
        CertificateEnrollmentsInfoResponse enrollmentInfo = getEnrolmentInfo(accessToken);
        CsrResult csr = EncryptionMethod.Rsa.equals(encryptionMethod)
                ? defaultCryptographyService.generateCsrWithRsa(enrollmentInfo)
                : defaultCryptographyService.generateCsrWithEcdsa(enrollmentInfo);

        // Zapisanie klucza prywatnego (private key) do pamięci tylko na potrzeby testu, w rzeczywistości powinno być bezpiecznie przechowywane
        byte[] privateKey = csr.privateKey();

        //Utworzenie i wysłanie żądania wystawienia certyfikatu KSeF
        String referenceNumber = sendEnrollment(csr, CertificateType.OFFLINE, accessToken);

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

        //Przygotowanie faktury FA(2) w formacie XML
        //gotową fakturę należy zapisać, aby wysłać do KSeF później (zgodnie z obowiązującymi przepisami), oznaczoną jako offlineMode = true
        LocalDate invoicingDate = LocalDate.of(2025, 10, 1);
        byte[] invoice = prepareInvoice(contextNip, invoicingDate, "/xml/invoices/sample/" + invoiceTemplate);

        FileMetadata invoiceMetadata = defaultCryptographyService.getMetaData(invoice);
        //Zapisanie skrótu faktury (hash)
        String invoiceHash = invoiceMetadata.getHashSHA();

        //Utworzenie odnośnika (Url) do weryfikacji faktury (KOD I)
        String invoiceForOfflineUrl = verificationLinkService.buildInvoiceVerificationUrl(contextNip, invoicingDate, invoiceHash);

        Assertions.assertNotNull(invoiceForOfflineUrl);
        Assertions.assertTrue(invoiceForOfflineUrl.contains(Base64.getUrlEncoder().withoutPadding().encodeToString(Base64.getDecoder().decode(invoiceHash))));
        Assertions.assertTrue(invoiceForOfflineUrl.contains(contextNip));
        Assertions.assertTrue(invoiceForOfflineUrl.contains(invoicingDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        //Utworzenie kodu QR faktury (KOD I) dla trybu offline
        byte[] qrOffline = qrCodeService.generateQrCode(invoiceForOfflineUrl);

        //Dodanie etykiety OFFLINE
        qrOffline = qrCodeService.addLabelToQrCode(qrOffline, "OFFLINE");

        Assertions.assertNotNull(qrOffline);

        //Utworzenie odnośnika (Url) do weryfikacji certyfikatu (KOD II)
        String url = verificationLinkService.buildCertificateVerificationUrl(
                contextNip,
                ContextIdentifierType.NIP,
                contextNip,
                certificate.getCertificateSerialNumber(),
                invoiceHash,
                EncryptionMethod.Rsa.equals(encryptionMethod)
                        ? defaultCryptographyService.parseRsaPrivateKeyFromPem(privateKey)
                        : defaultCryptographyService.parseEcdsaPrivateKeyFromPem(privateKey));

        //Utworzenie kodu QR do weryfikacji certyfikatu (KOD II) dla trybu offline
        byte[] qrOfflineCertificate = qrCodeService.generateQrCode(url);

        //Dodanie etykiety CERTYFIKAT
        qrOfflineCertificate = qrCodeService.addLabelToQrCode(qrOfflineCertificate, "CERTYFIKAT");

        Assertions.assertNotNull(qrOfflineCertificate);
    }

    private CertificateEnrollmentsInfoResponse getEnrolmentInfo(String accessToken) throws ApiException {
        CertificateEnrollmentsInfoResponse response = ksefClient.getCertificateEnrollmentInfo(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getOrganizationIdentifier());

        return response;
    }

    private String sendEnrollment(CsrResult csr, CertificateType certificateType, String accessToken) throws ApiException {
        SendCertificateEnrollmentRequest request = new SendCertificateEnrollmentRequestBuilder()
                .withValidFrom(LocalDateTime.now().toString())
                .withCsr(csr.csr())
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

    private static byte[] prepareInvoice(String nip, LocalDate invoicingDate, String path) throws IOException {
        String invoiceTemplate = new String(Objects.requireNonNull(BaseIntegrationTest.class.getResourceAsStream(path))
                .readAllBytes(), StandardCharsets.UTF_8)
                .replace("#nip#", nip)
                .replace("#invoicing_date#", invoicingDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());

        return invoiceTemplate.getBytes(StandardCharsets.UTF_8);
    }
}
