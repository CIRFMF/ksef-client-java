package pl.akmf.ksef.sdk.client.model.session.batch;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenBatchSessionResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class OpenBatchSessionResponse {

    /**
     * Numer referencyjny sesji.
     */
    private String referenceNumber;

    /**
     * Dane wymagane do poprawnego przesłania poszczególnych części pliku paczki faktur.
     * <p>
     * Każdą część pliku paczki zadeklarowaną w &lt;b&gt;fileParts&lt;/b&gt; należy przesłać zgodnie z odpowiadającym jej obiektem w &lt;b&gt;partUploadRequests&lt;/b&gt;. Łącznikiem pomiędzy deklaracją a instrukcją wysyłki jest pole &lt;b&gt;ordinalNumber&lt;/b&gt;.
     * <p>
     * Dla każdej części należy: * zastosować metodę HTTP wskazaną w &lt;b&gt;method&lt;/b&gt;, * ustawić adres z &lt;b&gt;url&lt;/b&gt;, * dołączyć nagłówki z &lt;b&gt;headers&lt;/b&gt;, * dołączyć treść części pliku w korpusie żądania.
     * <p>
     * {@code Uwaga: nie należy dodawać do nagłówków token dostępu (accessToken).}
     * <p>
     * Każdą część przesyła się oddzielnym żądaniem HTTP.Zwracane kody odpowiedzi: * &lt;b&gt;201&lt;/b&gt; – poprawne przyjęcie pliku, * &lt;b&gt;400&lt;/b&gt; – błędne dane, * &lt;b&gt;401&lt;/b&gt; – nieprawidłowe uwierzytelnienie, * &lt;b&gt;403&lt;/b&gt; – brak uprawnień do zapisu (np.upłynął czas na zapis).
     */
    private List<PackagePartSignatureInitResponseType> partUploadRequests;

    public OpenBatchSessionResponse() {
    }

    public OpenBatchSessionResponse(String referenceNumber, List<PackagePartSignatureInitResponseType> partUploadRequests) {
        this.referenceNumber = referenceNumber;
        this.partUploadRequests = partUploadRequests;
    }

    /**
     * Numer referencyjny sesji.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny sesji.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Dane wymagane do poprawnego przesłania poszczególnych części pliku paczki faktur.
     * <p>
     * Każdą część pliku paczki zadeklarowaną w &lt;b&gt;fileParts&lt;/b&gt; należy przesłać zgodnie z odpowiadającym jej obiektem w &lt;b&gt;partUploadRequests&lt;/b&gt;. Łącznikiem pomiędzy deklaracją a instrukcją wysyłki jest pole &lt;b&gt;ordinalNumber&lt;/b&gt;.
     * <p>
     * Dla każdej części należy: * zastosować metodę HTTP wskazaną w &lt;b&gt;method&lt;/b&gt;, * ustawić adres z &lt;b&gt;url&lt;/b&gt;, * dołączyć nagłówki z &lt;b&gt;headers&lt;/b&gt;, * dołączyć treść części pliku w korpusie żądania.
     * <p>
     * {@code Uwaga: nie należy dodawać do nagłówków token dostępu (accessToken).}
     * <p>
     * Każdą część przesyła się oddzielnym żądaniem HTTP.Zwracane kody odpowiedzi: * &lt;b&gt;201&lt;/b&gt; – poprawne przyjęcie pliku, * &lt;b&gt;400&lt;/b&gt; – błędne dane, * &lt;b&gt;401&lt;/b&gt; – nieprawidłowe uwierzytelnienie, * &lt;b&gt;403&lt;/b&gt; – brak uprawnień do zapisu (np.upłynął czas na zapis).
     */
    public List<PackagePartSignatureInitResponseType> getPartUploadRequests() {
        return partUploadRequests;
    }

    /**
     * Dane wymagane do poprawnego przesłania poszczególnych części pliku paczki faktur.
     * <p>
     * Każdą część pliku paczki zadeklarowaną w &lt;b&gt;fileParts&lt;/b&gt; należy przesłać zgodnie z odpowiadającym jej obiektem w &lt;b&gt;partUploadRequests&lt;/b&gt;. Łącznikiem pomiędzy deklaracją a instrukcją wysyłki jest pole &lt;b&gt;ordinalNumber&lt;/b&gt;.
     * <p>
     * Dla każdej części należy: * zastosować metodę HTTP wskazaną w &lt;b&gt;method&lt;/b&gt;, * ustawić adres z &lt;b&gt;url&lt;/b&gt;, * dołączyć nagłówki z &lt;b&gt;headers&lt;/b&gt;, * dołączyć treść części pliku w korpusie żądania.
     * <p>
     * {@code Uwaga: nie należy dodawać do nagłówków token dostępu (accessToken).}
     * <p>
     * Każdą część przesyła się oddzielnym żądaniem HTTP.Zwracane kody odpowiedzi: * &lt;b&gt;201&lt;/b&gt; – poprawne przyjęcie pliku, * &lt;b&gt;400&lt;/b&gt; – błędne dane, * &lt;b&gt;401&lt;/b&gt; – nieprawidłowe uwierzytelnienie, * &lt;b&gt;403&lt;/b&gt; – brak uprawnień do zapisu (np.upłynął czas na zapis).
     */
    public void setPartUploadRequests(List<PackagePartSignatureInitResponseType> partUploadRequests) {
        this.partUploadRequests = partUploadRequests;
    }

    public void addPartUploadRequests(PackagePartSignatureInitResponseType partUploadRequest) {
        if (this.partUploadRequests == null) {
            this.partUploadRequests = new ArrayList<>();
        }
        this.partUploadRequests.add(partUploadRequest);
    }
}
