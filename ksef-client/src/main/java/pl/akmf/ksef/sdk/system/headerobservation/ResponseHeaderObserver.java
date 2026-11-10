package pl.akmf.ksef.sdk.system.headerobservation;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;

/**
 * Observer (callback) wywoływany po każdej odpowiedzi API, która zawiera co najmniej jeden z nagłówków
 * zasubskrybowanych w {@link ResponseHeaderCaptureHandler#subscribe(String)}.
 * <p>
 * Rejestrowany przez {@link ResponseHeaderCaptureHandler#addObserver(ResponseHeaderObserver)} (albo przekazany
 * do konstruktora {@code DefaultKsefClient}). W odróżnieniu od bufora ({@code getAndClear()}) observer pozwala
 * powiązać nagłówki z konkretnym wywołaniem biznesowym, bo jest wołany <b>synchronicznie, w tym samym wątku, który
 * wywołał metodę klienta</b> - tuż po zakończeniu żądania HTTP. Dzięki temu dostępny jest {@link ThreadLocal}
 * (lub {@code ScopedValue}) ustawiony przez aplikację, np.:
 * <pre>{@code
 * client.getResponseHeaderCaptureHandler().addObserver((request, headers) -> {
 *     String packageId = MyCallContext.current().packageId();   // ThreadLocal wątku, który wykonał wywołanie
 *     headers.allValues("x-system-warning").forEach(w -> warnings.register(packageId, request, w));
 * });
 * }</pre>
 * <p>
 * Zasady:
 * <ul>
 *   <li>wywołanie jest synchroniczne - wolny observer wydłuża wywołanie metody klienta, więc powinien kończyć się szybko;</li>
 *   <li>wyjątek rzucony przez observera jest logowany i ignorowany (nie przerywa wywołania i nie blokuje pozostałych observerów);</li>
 *   <li>observer nie jest wołany, gdy żądanie nie zakończyło się odpowiedzią (np. błąd połączenia, otwarty circuit breaker);</li>
 *   <li>implementacja musi być bezpieczna wielowątkowo - klient jest zwykle współdzielony między wątkami.</li>
 * </ul>
 */
@FunctionalInterface
public interface ResponseHeaderObserver {

    /**
     * Wywoływane w wątku, który wykonał żądanie, po otrzymaniu odpowiedzi zawierającej zasubskrybowany nagłówek.
     *
     * @param request żądanie, na które przyszła odpowiedź
     * @param headers wszystkie nagłówki odpowiedzi (nie tylko zasubskrybowane)
     */
    void onHeadersCaptured(HttpRequest request, HttpHeaders headers);

    /**
     * Wariant z identyfikatorem wywołania. Domyślnie deleguje do {@link #onHeadersCaptured(HttpRequest, HttpHeaders)};
     * nadpisz go, jeśli potrzebujesz identyfikatora.
     * <p>
     * {@code requestKey} to dokładnie ten klucz, pod którym wpis tego samego wywołania znajduje się w buforze
     * ({@link ResponseHeaderCaptureHandler#getAndClear()}), co pozwala skorelować callback z wpisem bufora.
     *
     * @param request    żądanie, na które przyszła odpowiedź
     * @param headers    wszystkie nagłówki odpowiedzi
     * @param requestKey klucz wywołania w formacie {@code "METODA /sciezka?zapytanie :: UUID"}
     */
    default void onHeadersCaptured(HttpRequest request, HttpHeaders headers, String requestKey) {
        onHeadersCaptured(request, headers);
    }
}
