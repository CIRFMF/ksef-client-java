package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.OffsetDateTime;
import java.util.List;

public class TestDataSubjectCreateRequest {
    private String subjectNip;
    private SubjectTypeTestData subjectType;
    private List<Subunit> subunits;
    private String description;
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

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
