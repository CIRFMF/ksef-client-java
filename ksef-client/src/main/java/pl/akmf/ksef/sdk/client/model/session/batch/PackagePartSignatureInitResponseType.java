package pl.akmf.ksef.sdk.client.model.session.batch;

import java.net.URI;
import java.util.Map;

/**
 * PackagePartSignatureInitResponseType.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PartUploadRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PackagePartSignatureInitResponseType {

    /**
     * Metoda HTTP, której należy użyć przy wysyłce części pliku paczki.
     */
    private String method;

    /**
     * Numer sekwencyjny części pliku paczki.
     */
    private int ordinalNumber;

    /**
     * Adres pod który należy wysłać część pliku paczki.
     */
    private URI url;

    /**
     * Nagłówki, których należy użyć przy wysyłce części pliku paczki.
     */
    private Map<String, String> headers;

    public PackagePartSignatureInitResponseType() {
    }

    public PackagePartSignatureInitResponseType(String method, int ordinalNumber, URI url, Map<String, String> headers) {
        this.method = method;
        this.ordinalNumber = ordinalNumber;
        this.url = url;
        this.headers = headers;
    }

    /**
     * Metoda HTTP, której należy użyć przy wysyłce części pliku paczki.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Metoda HTTP, której należy użyć przy wysyłce części pliku paczki.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Numer sekwencyjny części pliku paczki.
     */
    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    /**
     * Numer sekwencyjny części pliku paczki.
     */
    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    /**
     * Adres pod który należy wysłać część pliku paczki.
     */
    public URI getUrl() {
        return url;
    }

    /**
     * Adres pod który należy wysłać część pliku paczki.
     */
    public void setUrl(URI url) {
        this.url = url;
    }

    /**
     * Nagłówki, których należy użyć przy wysyłce części pliku paczki.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Nagłówki, których należy użyć przy wysyłce części pliku paczki.
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
