package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.ApiException;

public interface QrCodeService {

    /**
     * Generuje kod QR jako tablicę bajtów PNG.
     *
     * @param payloadUrl           - URL/link do zakodowania.
     * @param pixelsPerModule      - Rozmiar modułu w pikselach (domyślnie 20).
     * @param qrCodeWidthAndHeight - Rozmiar obrazka w pikselach (domyślnie 300).
     * @return Kod QR w formacie PNG jako tablica bajtów.
     */
    byte[] generateQrCode(String payloadUrl, int pixelsPerModule, int qrCodeWidthAndHeight) throws ApiException;

    /**
     * Generuje kod QR jako tablicę bajtów PNG, z domyślnym rozmiarem modułu (20 px)
     * i domyślnymi wymiarami obrazka (300x300 px).
     *
     * @param payloadUrl - URL/link do zakodowania.
     * @return Kod QR w formacie PNG jako tablica bajtów.
     */
    byte[] generateQrCode(String payloadUrl) throws ApiException;

    /**
     * Dokleja podpis (label) pod istniejącym PNG z kodem QR.
     *
     * @param qrCodePng  - Obraz PNG z kodem QR (tablica bajtów).
     * @param label      - Tekst (podpis) do umieszczenia pod kodem QR.
     * @param fontSizePx - rozmiar czcionki w pikselach (domyślnie 14).
     * @param fontName - nazwa czcionki (domyślnie Arial).
     * @return Obraz PNG z kodem QR i doklejonym podpisem pod spodem.
     */
    byte[] addLabelToQrCode(byte[] qrCodePng, String label, int fontSizePx, String fontName) throws ApiException;

    /**
     * Dokleja podpis (label) pod istniejącym PNG z kodem QR, z domyślną czcionką (Arial, 14 px).
     *
     * @param qrCodePng - Obraz PNG z kodem QR (tablica bajtów).
     * @param label     - Tekst (podpis) do umieszczenia pod kodem QR.
     * @return Obraz PNG z kodem QR i doklejonym podpisem pod spodem.
     */
    byte[] addLabelToQrCode(byte[] qrCodePng, String label) throws ApiException;
}
