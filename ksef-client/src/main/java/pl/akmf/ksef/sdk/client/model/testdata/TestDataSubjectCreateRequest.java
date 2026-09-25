package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * TestDataSubjectCreateRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code SubjectCreateRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataSubjectCreateRequest {

    private String subjectNip;

    private SubjectTypeTestData subjectType;

    private List<Subunit> subunits;

    private String description;

    /**
     * W przypadku wielokrotnego tworzenia danych testowych z tym samym identyfikatorem nie można podawać daty wcześniejszej ani takiej samej jak poprzednia.
     */
    private OffsetDateTime createdDate;

    public TestDataSubjectCreateRequest() {
    }

    public String getSubjectNip() {
        return subjectNip;
    }

    public SubjectTypeTestData getSubjectType() {
        return subjectType;
    }

    public List<Subunit> getSubunits() {
        return subunits;
    }

    public String getDescription() {
        return description;
    }

    /**
     * W przypadku wielokrotnego tworzenia danych testowych z tym samym identyfikatorem nie można podawać daty wcześniejszej ani takiej samej jak poprzednia.
     */
    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setSubjectNip(String subjectNip) {
        this.subjectNip = subjectNip;
    }

    public void setSubjectType(SubjectTypeTestData subjectType) {
        this.subjectType = subjectType;
    }

    public void setSubunits(List<Subunit> subunits) {
        this.subunits = subunits;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * W przypadku wielokrotnego tworzenia danych testowych z tym samym identyfikatorem nie można podawać daty wcześniejszej ani takiej samej jak poprzednia.
     */
    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
