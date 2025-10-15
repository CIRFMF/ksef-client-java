package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.interfaces.QrCodeService;
import pl.akmf.ksef.sdk.client.interfaces.VerificationLinkService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;
import pl.akmf.ksef.sdk.client.model.qrcode.QrCodeResult;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.Base64;

@RequiredArgsConstructor
@RestController
@RequestMapping("/qr")
public class QrCodeController {
    private final VerificationLinkService linkSvc;
    private final QrCodeService qrSvc;
    private final DefaultKsefClient ksefClient;
    private final DefaultCryptographyService defaultCryptographyService;

    // 1. Faktura z numerem KSeF (online)
    @GetMapping("/invoice/ksef")
    public QrCodeResult getInvoiceQrWithKsef(
            @RequestParam String nip,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
            @RequestParam String invoiceHash,
            @RequestParam String ksefNumber
    ) throws ApiException {
        String url = linkSvc.buildInvoiceVerificationUrl(nip, issueDate, invoiceHash);
        byte[] qrCode = qrSvc.generateQrCode(url);
        byte[] labeledQr = qrSvc.addLabelToQrCode(qrCode, ksefNumber);

        return new QrCodeResult(url, Base64.getEncoder().encodeToString(labeledQr));
    }

    // 2. Faktura offline (przed wysyłką)
    @GetMapping("/invoice/offline")
    public ResponseEntity<QrCodeResult> getInvoiceQrOffline(
            @RequestParam String nip,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
            @RequestParam String invoiceHash
    ) throws ApiException {
        String url = linkSvc.buildInvoiceVerificationUrl(nip, issueDate, invoiceHash);
        byte[] qrCode = qrSvc.generateQrCode(url);
        byte[] labeledQr = qrSvc.addLabelToQrCode(qrCode, "OFFLINE");

        return ResponseEntity.ok(new QrCodeResult(url, Base64.getEncoder().encodeToString(labeledQr)));
    }

    // 3. Weryfikacja certyfikatu (Kod II)
    @PostMapping("/certificate")
    public QrCodeResult getCertificateQr(
            @RequestParam String sellerNip,
            @RequestParam ContextIdentifierType contextIdentifierType,
            @RequestParam String contextIdentifierValue,
            @RequestParam String certSerial,
            @RequestParam String invoiceHash,
            @RequestBody String privateKeyPemBase64
    ) throws ApiException {
        PrivateKey privateKey = parsePrivateKeyFromBase64(privateKeyPemBase64);

        String url = linkSvc.buildCertificateVerificationUrl(sellerNip, contextIdentifierType, contextIdentifierValue, certSerial, invoiceHash, privateKey);
        byte[] qrCode = qrSvc.generateQrCode(url);
        byte[] labeledQr = qrSvc.addLabelToQrCode(qrCode, "CERTYFIKAT");

        return new QrCodeResult(url, Base64.getEncoder().encodeToString(labeledQr));
    }

    private PrivateKey parsePrivateKeyFromBase64(String base64) {
        String pem = base64.replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(pem);
        return defaultCryptographyService.parseRsaPrivateKeyFromPem(keyBytes);
    }
}
