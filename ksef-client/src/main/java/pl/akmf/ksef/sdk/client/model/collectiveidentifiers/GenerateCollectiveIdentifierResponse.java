package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

/**
 * GenerateCollectiveIdentifierResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GenerateCollectiveIdentifierResponse {

    /**
     * Wygenerowany identyfikator zbiorczy
     * <p>
     * Identyfikator zbiorczy ma format: {@code 9999999999-IZRRRRMM-FFFFFFFFFFFF-FF}, gdzie: - {@code 9999999999} – 10-cyfrowy NIP sprzedawcy, - {@code IZ} – stały prefiks, - {@code RRRRMM} – rok i miesiąc utworzenia, - {@code FFFFFFFFFFFF} –  część techniczna składająca się z 12 znaków w zapisie szesnastkowym, tylko [0–9 A–F], wielkie litery, - {@code FF} –  suma kontrolna CRC-8 - 2 znaki w zapisie szesnastkowym, tylko [0–9 A–F], wielkie litery.
     * <p>
     * Do obliczenia sumy kontrolnej stosowany jest algorytm CRC-8 z parametrami: - Polinom: 0x07 - Wartość początkowa: 0x00 - Format wyniku: 2-znakowy zapis szesnastkowy(HEX, wielkie litery)
     * <p>
     * Przykład: {@code 1111111111-IZ202607-65ED02180000-E7}.
     */
    private String collectiveIdentifierNumber;

    public GenerateCollectiveIdentifierResponse() {
    }

    public GenerateCollectiveIdentifierResponse(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    /**
     * Wygenerowany identyfikator zbiorczy
     * <p>
     * Identyfikator zbiorczy ma format: {@code 9999999999-IZRRRRMM-FFFFFFFFFFFF-FF}, gdzie: - {@code 9999999999} – 10-cyfrowy NIP sprzedawcy, - {@code IZ} – stały prefiks, - {@code RRRRMM} – rok i miesiąc utworzenia, - {@code FFFFFFFFFFFF} –  część techniczna składająca się z 12 znaków w zapisie szesnastkowym, tylko [0–9 A–F], wielkie litery, - {@code FF} –  suma kontrolna CRC-8 - 2 znaki w zapisie szesnastkowym, tylko [0–9 A–F], wielkie litery.
     * <p>
     * Do obliczenia sumy kontrolnej stosowany jest algorytm CRC-8 z parametrami: - Polinom: 0x07 - Wartość początkowa: 0x00 - Format wyniku: 2-znakowy zapis szesnastkowy(HEX, wielkie litery)
     * <p>
     * Przykład: {@code 1111111111-IZ202607-65ED02180000-E7}.
     */
    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    /**
     * Wygenerowany identyfikator zbiorczy
     * <p>
     * Identyfikator zbiorczy ma format: {@code 9999999999-IZRRRRMM-FFFFFFFFFFFF-FF}, gdzie: - {@code 9999999999} – 10-cyfrowy NIP sprzedawcy, - {@code IZ} – stały prefiks, - {@code RRRRMM} – rok i miesiąc utworzenia, - {@code FFFFFFFFFFFF} –  część techniczna składająca się z 12 znaków w zapisie szesnastkowym, tylko [0–9 A–F], wielkie litery, - {@code FF} –  suma kontrolna CRC-8 - 2 znaki w zapisie szesnastkowym, tylko [0–9 A–F], wielkie litery.
     * <p>
     * Do obliczenia sumy kontrolnej stosowany jest algorytm CRC-8 z parametrami: - Polinom: 0x07 - Wartość początkowa: 0x00 - Format wyniku: 2-znakowy zapis szesnastkowy(HEX, wielkie litery)
     * <p>
     * Przykład: {@code 1111111111-IZ202607-65ED02180000-E7}.
     */
    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }
}
