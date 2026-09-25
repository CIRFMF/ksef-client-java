package pl.akmf.ksef.sdk.client.model.util;

import java.io.InputStream;
import java.io.PipedInputStream;

/**
 * Wewnętrzny strumień wejściowy klienta SDK opakowujący dane archiwum TAR wraz
 * z jego znanym rozmiarem (potrzebnym przy przesyłaniu strumieniowym).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TarInputStreamWithSize {

    private final PipedInputStream pipedInputStream;

    private final long tarGzLength;

    public TarInputStreamWithSize(PipedInputStream pipedInputStream, long tarGzLength) {
        this.pipedInputStream = pipedInputStream;
        this.tarGzLength = tarGzLength;
    }

    public InputStream getInputStream() {
        return pipedInputStream;
    }

    public long getTarGzLength() {
        return tarGzLength;
    }
}
