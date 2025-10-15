package pl.akmf.ksef.sdk.client.model.testdata;

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
