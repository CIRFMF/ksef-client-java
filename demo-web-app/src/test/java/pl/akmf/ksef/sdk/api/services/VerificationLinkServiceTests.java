package pl.akmf.ksef.sdk.api.services;

import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.interfaces.VerificationLinkService;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerificationLinkServiceTests {
    public static final String QR_URL = "https://qr-test.pl";

    // =============================================
    // Testy legacy (RSA) – tylko dla zgodności wstecznej; NIEZALECANE:
    // • Użycie RSA 2048-bit:
    //    - Większy rozmiar kluczy i linków
    //    - Wolniejsze operacje kryptograficzne
    //    - Dłuższe URL-e (gorszy UX)
    // =============================================
    @Test
    void buildInvoiceVerificationUrl_encodesHashCorrectly_withSimpleXml() throws Exception {
        String xml = "<root>test</root>";
        assertInvoiceUrlIsCorrect(xml);
    }

    @Test
    void buildInvoiceVerificationUrl_encodesHashCorrectly_withSpecialChars() throws Exception {
        String xml = "<data>special & chars /?</data>";
        assertInvoiceUrlIsCorrect(xml);
    }

    // =============================================
    // Rekomendowane testy ECC (ECDSA P-256):
    // • Bezpieczeństwo jak RSA-2048, ale mniejsze i szybsze klucze
    // • Krótsze podpisane URL-e → lepszy UX w QR i linkach
    // =============================================
    @Test
    void buildCertificateVerificationUrl_WithEmbeddedEcdsaKey_ShouldSucceed_Ecc() throws Exception {
        // Arrange: wygeneruj klucz ECDSA P-256 i self-signed certyfikat
        VerificationLinkService svc = new DefaultVerificationLinkService(QR_URL);
        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-1111111111", "FullEccCert", "PL");
        SelfSignedCertificate cert = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500);

        String nip = "0000000000";
        String xml = "<x/>";
        String serial = UUID.randomUUID().toString();
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);

        // Act: budujemy URL weryfikacyjny z podpisem ECDSA
        String url = svc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, cert.getPrivateKey());

        // Assert: podpis (base64) powinien zawierać zakodowany znak '=' → "%3d"
        assertNotNull(url);

        URI uri = URI.create(url);
        String[] segments = uri.getPath().split("/");
        String signedUrl = URLEncoder.encode(segments[segments.length - 1], StandardCharsets.UTF_8);
        assertTrue(signedUrl.matches("^[A-Za-z0-9_%-]+$"), "Signed URL does not match expected Base64 URL-safe pattern");
    }

    @Test
    void BuildCertificateVerificationUrl_WithRsaCertificate_ShouldMatchFormat() throws Exception {
        String nip = "4564564567";
        String xml = "<root>foo</root>";
        String serial = UUID.randomUUID().toString();
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);
        VerificationLinkService svc = new DefaultVerificationLinkService(QR_URL);

        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-" + nip, "TestRSA", "PL");
        SelfSignedCertificate selfSignedCertificate = new DefaultCertificateService().generateSelfSignedCertificateRsa(x500);

        String url = svc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, selfSignedCertificate.getPrivateKey());

        String[] segments = new URI(url).getPath().split("/");
        assertEquals("certificate", segments[1]);
        assertEquals("Nip", segments[2]);
        assertEquals(nip, segments[3]);
        assertEquals(nip, segments[4]);
        assertEquals(serial, segments[5]);
        assertNotNull(segments[6]); // hash
        assertFalse(segments[7].isBlank());
        assertNotNull(segments[7]); // signed hash
    }

    @Test
    void buildCertificateVerificationUrl_withEcdsaCert_shouldMatchFormat() throws Exception {
        String nip = "1234567890";
        String xml = "<data>ecdsa</data>";
        String serial = UUID.randomUUID().toString();
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);
        VerificationLinkService svc = new DefaultVerificationLinkService(QR_URL);

        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-" + nip, "TestECDSA", "PL");
        SelfSignedCertificate selfSignedCertificate = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500);

        String url = svc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, selfSignedCertificate.getPrivateKey());

        String[] segments = new URI(url).getPath().split("/");
        assertEquals("certificate", segments[1]);
        assertEquals("Nip", segments[2]);
        assertEquals(nip, segments[3]);
        assertEquals(nip, segments[4]);
        assertEquals(serial, segments[5]);
        assertNotNull(segments[6]); // hash
        assertFalse(segments[7].isBlank());
    }

    @Test
    void buildCertificateVerificationUrl_withoutPrivateKey_shouldThrow() throws NoSuchAlgorithmException {
        String xml = "<x/>";
        String nip = "0000000000";
        String serial = UUID.randomUUID().toString();
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);
        VerificationLinkService svc = new DefaultVerificationLinkService(QR_URL);

        assertThrows(SystemKSeFSDKException.class, () ->
                svc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, null)
        );
    }

    private void assertInvoiceUrlIsCorrect(String xml) throws Exception {
        String nip = "1234567890";
        LocalDate issueDate = LocalDate.of(2026, 1, 5);
        VerificationLinkService svc = new DefaultVerificationLinkService(QR_URL);

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] sha = sha256.digest(xml.getBytes(StandardCharsets.UTF_8));
        String invoiceHash = Base64.getEncoder().encodeToString(sha);
        String expectedHash = Base64.getUrlEncoder().withoutPadding().encodeToString(sha);

        String expectedUrl = String.format("%s/invoice/%s/%s/%s", QR_URL, nip,
                issueDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")), expectedHash);

        String actualUrl = svc.buildInvoiceVerificationUrl(nip, issueDate, invoiceHash);
        assertEquals(expectedUrl, actualUrl);

        String[] segments = new URI(actualUrl).getPath().split("/");
        assertEquals("invoice", segments[1]);
        assertEquals(nip, segments[2]);
        assertEquals(issueDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")), segments[3]);
        assertEquals(URLDecoder.decode(expectedHash, StandardCharsets.UTF_8), segments[4]);
    }

    private String computeUrlEncodedBase64Sha256(String xml) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(xml.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}

