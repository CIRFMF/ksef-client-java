package pl.akmf.ksef.sdk.client.model.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
