package pl.akmf.ksef.sdk.client.model.session.online;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FormCode;

/**
 * OpenOnlineSessionRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class OpenOnlineSessionRequest {

    /**
     * Schemat faktur wysyłanych w ramach sesji.
     * <p>
     * Obsługiwane schematy:
     */
    private FormCode formCode;

    /**
     * Symetryczny klucz szyfrujący pliki XML, zaszyfrowany kluczem publicznym Ministerstwa Finansów.
     */
    private EncryptionInfo encryption;

    public OpenOnlineSessionRequest() {
    }

    public OpenOnlineSessionRequest(FormCode formCode, EncryptionInfo encryption) {
        this.formCode = formCode;
        this.encryption = encryption;
    }

    /**
     * Schemat faktur wysyłanych w ramach sesji.
     * <p>
     * Obsługiwane schematy:
     */
    public FormCode getFormCode() {
        return formCode;
    }

    /**
     * Schemat faktur wysyłanych w ramach sesji.
     * <p>
     * Obsługiwane schematy:
     */
    public void setFormCode(FormCode formCode) {
        this.formCode = formCode;
    }

    /**
     * Symetryczny klucz szyfrujący pliki XML, zaszyfrowany kluczem publicznym Ministerstwa Finansów.
     */
    public EncryptionInfo getEncryption() {
        return encryption;
    }

    /**
     * Symetryczny klucz szyfrujący pliki XML, zaszyfrowany kluczem publicznym Ministerstwa Finansów.
     */
    public void setEncryption(EncryptionInfo encryption) {
        this.encryption = encryption;
    }
}
