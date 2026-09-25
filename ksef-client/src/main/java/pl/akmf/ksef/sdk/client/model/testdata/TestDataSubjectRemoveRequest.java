package pl.akmf.ksef.sdk.client.model.testdata;

/**
 * TestDataSubjectRemoveRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code SubjectRemoveRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataSubjectRemoveRequest {

    private String subjectNip;

    public TestDataSubjectRemoveRequest() {
    }

    public TestDataSubjectRemoveRequest(String subjectNip) {
        this.subjectNip = subjectNip;
    }

    public String getSubjectNip() {
        return subjectNip;
    }

    public void setSubjectNip(String subjectNip) {
        this.subjectNip = subjectNip;
    }
}
