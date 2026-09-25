package pl.akmf.ksef.sdk.client.model.permission.indirect;

/**
 * Typ identyfikatora osoby fizycznej ({@code Pesel} albo {@code Nip}) używany
 * w kontekście uprawnień pośrednich.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum PermissionsIndirectEntityIdentifierType {

    Pesel, Nip
}
