package pl.akmf.ksef.sdk.client.model.session.batch;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FormCode;

/**
 * OpenBatchSessionRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class OpenBatchSessionRequest {

    /**
     * Schemat faktur wysyłanych w ramach sesji.
     * <p>
     * Obsługiwane schematy:
     */
    private FormCode formCode;

    /**
     * Informacje o przesyłanej paczce faktur.
     */
    private BatchFileInfo batchFile;

    /**
     * Symetryczny klucz szyfrujący plik paczki, zaszyfrowany kluczem publicznym Ministerstwa Finansów.
     */
    private EncryptionInfo encryption;

    /**
     * Określa, czy podatnik deklaruje tryb fakturowania "offline" dla dokumentów przesyłanych w sesji wsadowej.
     */
    private boolean offlineMode = false;

    public OpenBatchSessionRequest() {
    }

    public OpenBatchSessionRequest(FormCode formCode, BatchFileInfo batchFile, EncryptionInfo encryption, boolean offlineMode) {
        this.formCode = formCode;
        this.batchFile = batchFile;
        this.encryption = encryption;
        this.offlineMode = offlineMode;
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
     * Informacje o przesyłanej paczce faktur.
     */
    public BatchFileInfo getBatchFile() {
        return batchFile;
    }

    /**
     * Informacje o przesyłanej paczce faktur.
     */
    public void setBatchFile(BatchFileInfo batchFile) {
        this.batchFile = batchFile;
    }

    /**
     * Symetryczny klucz szyfrujący plik paczki, zaszyfrowany kluczem publicznym Ministerstwa Finansów.
     */
    public EncryptionInfo getEncryption() {
        return encryption;
    }

    /**
     * Symetryczny klucz szyfrujący plik paczki, zaszyfrowany kluczem publicznym Ministerstwa Finansów.
     */
    public void setEncryption(EncryptionInfo encryption) {
        this.encryption = encryption;
    }

    public boolean isOfflineMode() {
        return offlineMode;
    }

    /**
     * Określa, czy podatnik deklaruje tryb fakturowania "offline" dla dokumentów przesyłanych w sesji wsadowej.
     */
    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }
}
