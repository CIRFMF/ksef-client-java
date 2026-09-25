package pl.akmf.ksef.sdk.client.model.session.batch;

import pl.akmf.ksef.sdk.client.model.session.FileMetadata;

/**
 * Wewnętrzny typ klienta SDK opisujący pojedynczą, zaszyfrowaną część paczki
 * wsadowej gotową do wysłania (dane binarne, metadane pliku oraz numer porządkowy).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class BatchPartSendingInfo {

    private byte[] data;

    private FileMetadata metadata;

    private int ordinalNumber;

    public BatchPartSendingInfo() {
    }

    public BatchPartSendingInfo(byte[] data, FileMetadata metadata, int ordinalNumber) {
        this.data = data;
        this.metadata = metadata;
        this.ordinalNumber = ordinalNumber;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FileMetadata metadata) {
        this.metadata = metadata;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }
}
