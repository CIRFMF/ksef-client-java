package pl.akmf.ksef.sdk.client.model.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Wewnętrzny strumień wejściowy klienta SDK opakowujący dane archiwum ZIP wraz
 * z jego znanym rozmiarem (potrzebnym przy przesyłaniu strumieniowym).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ZipInputStreamWithSize {

    private final ByteArrayInputStream byteArrayInputStream;

    private final int zipLength;

    public ZipInputStreamWithSize(ByteArrayInputStream byteArrayInputStream, int zipLength) {
        this.byteArrayInputStream = byteArrayInputStream;
        this.zipLength = zipLength;
    }

    public InputStream getInputStream() {
        return byteArrayInputStream;
    }

    public int getZipLength() {
        return zipLength;
    }
}
