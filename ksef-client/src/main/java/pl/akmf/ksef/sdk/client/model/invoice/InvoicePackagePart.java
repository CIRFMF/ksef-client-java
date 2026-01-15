package pl.akmf.ksef.sdk.client.model.invoice;

import java.net.URI;
import java.time.OffsetDateTime;


public class InvoicePackagePart {
    private Integer ordinalNumber;
    private String partName;
    private String method;
    private URI url;
    private int partSize;
    private String partHash;
    private int encryptedPartSize;
    private String encryptedPartHash;
    private OffsetDateTime expirationDate;

    public InvoicePackagePart() {
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public int getPartSize() {
      return partSize;
    }

    public void setPartSize(int partSize) {
      this.partSize = partSize;
    }

    public String getPartHash() {
      return partHash;
    }

    public void setPartHash(String partHash) {
      this.partHash = partHash;
    }

    public int getEncryptedPartSize() {
      return encryptedPartSize;
    }

    public void setEncryptedPartSize(int encryptedPartSize) {
      this.encryptedPartSize = encryptedPartSize;
    }

    public String getEncryptedPartHash() {
      return encryptedPartHash;
    }

    public void setEncryptedPartHash(String encryptedPartHash) {
      this.encryptedPartHash = encryptedPartHash;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}

