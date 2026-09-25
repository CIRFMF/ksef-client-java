package pl.akmf.ksef.sdk.client.model.qrcode;

/**
 * Wynik lokalnego (klienckiego) wygenerowania kodu QR do oznaczenia faktury
 * (adres weryfikacyjny oraz zakodowany obraz kodu QR). Operacja wykonywana
 * wyłącznie po stronie SDK.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QrCodeResult {

    public String url;

    public String qrCode;

    public QrCodeResult() {
    }

    public QrCodeResult(String url, String qrCode) {
        this.url = url;
        this.qrCode = qrCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
