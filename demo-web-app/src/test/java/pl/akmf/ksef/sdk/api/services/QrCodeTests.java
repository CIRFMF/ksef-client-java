package pl.akmf.ksef.sdk.api.services;

import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.interfaces.QrCodeService;
import pl.akmf.ksef.sdk.client.interfaces.VerificationLinkService;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

class QrCodeTests {

    private String qrUrl = "https://ksef-test.mf.gov.pl/";

    // =============================================
    // Testy RSA; NIEZALECANE):
    // - Klucze RSA 2048-bit:
    //   • Bezpieczeństwo porównywalne z ECC P-256, ale wyższy rozmiar
    //   • Dłuższe linki QR (więcej miejsca w wizualizacji)
    //   • Wolniejsze generowanie kluczy, podpis i weryfikacja
    //   • Większe zużycie pamięci i miejsca na dysku
    // =============================================
    @Test
    void buildCertificateQr_WithEmbeddedPrivateKey_ShouldReturnBase64Png() throws Exception {
        VerificationLinkService linkSvc = new DefaultVerificationLinkService(qrUrl);
        QrCodeService qrSvc = new DefaultQrCodeService();
        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-1111111111", "Kowalski", "PL");
        SelfSignedCertificate cert = new DefaultCertificateService().generateSelfSignedCertificateRsa(x500);

        String nip = "0000000000";
        String serial = UUID.randomUUID().toString();
        String xml = "<x/>";
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);

        // Act
        String url = linkSvc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, cert.getPrivateKey());
        byte[] qrBytes = qrSvc.generateQrCode(url, 5, 300);
        byte[] labeled = qrSvc.addLabelToQrCode(qrBytes, "CERTYFIKAT");

        String base64 = Base64.getEncoder().encodeToString(labeled);

        // Assert
        assertTrue(base64.startsWith("iVBOR"));
    }

    @Test
    void buildCertificateQr_PublicOnlyWithoutPrivateKey_ShouldThrow() throws Exception {
        VerificationLinkService linkSvc = new DefaultVerificationLinkService(qrUrl);
        String nip = "0000000000";
        String xml = "<x/>";
        String serial = UUID.randomUUID().toString();
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);

        // Act & Assert
        assertThrows(SystemKSeFSDKException.class, () ->
                linkSvc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, null)
        );
    }

    // =============================================
    // Rekomendowane testy ECC (ECDSA P-256):
    // • Bezpieczeństwo jak RSA-2048 przy mniejszych kluczach i podpisach
    // • Krótsze linki QR i mniejsze zużycie zasobów
    // • Szybsze operacje: generowanie, podpis, weryfikacja
    // =============================================
    @Test
    void buildCertificateQr_WithEmbeddedEccPrivateKey_ShouldReturnBase64Png() throws Exception {
        VerificationLinkService linkSvc = new DefaultVerificationLinkService(qrUrl);
        QrCodeService qrSvc = new DefaultQrCodeService();
        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-1111111111", "TestEcc", "PL");
        SelfSignedCertificate cert = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500);

        String nip = "0000000000";
        String serial = UUID.randomUUID().toString();
        String xml = "<x/>";
        String invoiceHash = computeUrlEncodedBase64Sha256(xml);

        // Act
        String url = linkSvc.buildCertificateVerificationUrl(nip, ContextIdentifierType.NIP, nip, serial, invoiceHash, cert.getPrivateKey());
        byte[] qrBytes = qrSvc.generateQrCode(url, 5, 300);
        byte[] labeled = qrSvc.addLabelToQrCode(qrBytes, "CERTYFIKAT");

        String base64 = Base64.getEncoder().encodeToString(labeled);

        // Assert
        assertTrue(base64.startsWith("iVBOR"));
    }

    private String computeUrlEncodedBase64Sha256(String xml) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(xml.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
