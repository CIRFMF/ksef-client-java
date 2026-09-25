package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;

import java.security.PrivateKey;
import java.time.LocalDate;

public interface VerificationLinkService {

    /**
     * Buduje link do weryfikacji faktury w systemie KSeF (do zakodowania w kodzie QR).
     *
     * @param nip         NIP sprzedawcy/wystawcy faktury.
     * @param issueDate   Data wystawienia faktury.
     * @param invoiceHash Skrót (hash) faktury w formacie Base64.
     * @return Adres URL do weryfikacji faktury.
     */
    String buildInvoiceVerificationUrl(String nip, LocalDate issueDate, String invoiceHash);

    /**
     * Buduje link do weryfikacji certyfikatu wystawcy faktury offline (tryb bez połączenia z KSeF),
     * podpisując treść linku wskazanym kluczem prywatnym.
     *
     * @param sellerNip              NIP sprzedawcy/wystawcy faktury.
     * @param contextIdentifierType  Typ identyfikatora kontekstu użytego przy wystawianiu faktury.
     * @param contextIdentifierValue Wartość identyfikatora kontekstu.
     * @param certificateSerial      Numer seryjny certyfikatu użytego do podpisu faktury.
     * @param invoiceHash            Skrót (hash) faktury w formacie Base64.
     * @param privateKey             Klucz prywatny odpowiadający certyfikatowi, użyty do podpisania linku weryfikacyjnego.
     * @return Podpisany adres URL do weryfikacji certyfikatu wystawcy.
     */
    String buildCertificateVerificationUrl(String sellerNip, ContextIdentifierType contextIdentifierType, String contextIdentifierValue, String certificateSerial, String invoiceHash, PrivateKey privateKey);
}
