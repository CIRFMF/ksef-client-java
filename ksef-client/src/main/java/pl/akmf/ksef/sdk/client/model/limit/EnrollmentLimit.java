package pl.akmf.ksef.sdk.client.model.limit;

/**
 * EnrollmentLimit.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EnrollmentEffectiveSubjectLimits, EnrollmentSubjectLimitsOverride}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
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
