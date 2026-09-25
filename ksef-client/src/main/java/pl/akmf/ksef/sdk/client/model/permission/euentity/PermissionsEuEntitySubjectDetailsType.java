package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * EuEntityPermissionSubjectDetailsType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code PersonByFingerprintWithIdentifier}</li>
 *   <li>{@code PersonByFingerprintWithoutIdentifier}</li>
 *   <li>{@code EntityByFingerprint}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum PermissionsEuEntitySubjectDetailsType {

    PersonByFingerprintWithoutIdentifier, PersonByFingerprintWithIdentifier, EntityByFingerprint
}
