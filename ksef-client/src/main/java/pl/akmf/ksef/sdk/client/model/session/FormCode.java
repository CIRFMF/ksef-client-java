package pl.akmf.ksef.sdk.client.model.session;

public class FormCode {
    private SystemCode systemCode;
    private SchemaVersion schemaVersion;
    private SessionValue value;

    public FormCode() {
    }

    public FormCode(SystemCode systemCode, SchemaVersion schemaVersion, SessionValue value) {
        this.systemCode = systemCode;
        this.schemaVersion = schemaVersion;
        this.value = value;
    }

    public SystemCode getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(SystemCode systemCode) {
        this.systemCode = systemCode;
    }

    public SchemaVersion getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(SchemaVersion schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public SessionValue getValue() {
        return value;
    }

    public void setValue(SessionValue value) {
        this.value = value;
    }
}

