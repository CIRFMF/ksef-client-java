package pl.akmf.ksef.sdk.client.model.limit;

public class EnrollmentLimit {
    private int maxEnrollments;

    public EnrollmentLimit() {

    }

    public EnrollmentLimit(int maxEnrollments) {
        this.maxEnrollments = maxEnrollments;
    }

    public int getMaxEnrollments() {
        return maxEnrollments;
    }

    public void setMaxEnrollments(int maxEnrollments) {
        this.maxEnrollments = maxEnrollments;
    }
}
