package pl.akmf.ksef.sdk.client.model.permission.indirect;

/**
 * Typ danych podmiotu w kontekście uprawnień pośrednich.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum PermissionsIndirectEntitySubjectDetailsType {

    PersonByIdentifier, PersonByFingerprintWithIdentifier, PersonByFingerprintWithoutIdentifier
}
