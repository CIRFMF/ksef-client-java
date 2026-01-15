package pl.akmf.ksef.sdk.client.peppol;

import java.time.OffsetDateTime;

public class PeppolProvider {
    private String id;
    private String name;
    private OffsetDateTime dateCreated;

    public PeppolProvider() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
