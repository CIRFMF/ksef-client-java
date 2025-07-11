package pl.akmf.ksef.sdk.client.model.session;

public class FormCode {
    private String systemCode;
    private String schemaVersion;
    private String value;

    public FormCode() {
    }

    public FormCode(String systemCode, String schemaVersion, String value) {
        this.systemCode = systemCode;
        this.schemaVersion = schemaVersion;
        this.value = value;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

