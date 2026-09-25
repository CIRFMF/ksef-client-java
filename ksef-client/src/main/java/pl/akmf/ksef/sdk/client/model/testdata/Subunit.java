package pl.akmf.ksef.sdk.client.model.testdata;

/**
 * Subunit.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
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
