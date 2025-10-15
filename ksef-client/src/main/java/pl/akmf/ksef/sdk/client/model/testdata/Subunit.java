package pl.akmf.ksef.sdk.client.model.testdata;

public class Subunit {
    private String subjectNip;
    private String description;

    public Subunit(String subjectNip, String description) {
        this.subjectNip = subjectNip;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectNip() {
        return subjectNip;
    }

    public void setSubjectNip(String subjectNip) {
        this.subjectNip = subjectNip;
    }
}
