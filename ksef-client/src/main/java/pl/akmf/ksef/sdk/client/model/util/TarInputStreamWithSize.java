package pl.akmf.ksef.sdk.client.model.util;

import java.io.InputStream;
import java.io.PipedInputStream;

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
