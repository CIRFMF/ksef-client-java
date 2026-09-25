package pl.akmf.ksef.sdk.client.model.session.batch;

/**
 * CompressionType.
 * Określa typ kompresji używany do przekazania pliku wsadowego.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Zip}</li>
 *   <li>{@code TarGz}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum CompressionType {

    // Kompresja ZIP.
    Zip,
    // Kompresja TAR.GZ.
    TarGz
}
