package pl.akmf.ksef.sdk.system.headerobservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Przechwytuje wybrane nagłówki odpowiedzi HTTP (np. {@code X-System-Warning}) i udostępnia je do odczytu.
 * <p>
 * <b>Dwa niezależne magazyny wpisów</b> (każdy wpis z {@link #capture(HttpRequest, HttpHeaders)} trafia do obu):
 * <ul>
 *   <li><b>magazyn nowy</b> ({@code drainableStore}) - obsługuje {@link #getAndClear()}, {@link #size()} oraz
 *   {@link #getEvictedCount()}. Jest bezpieczny wielowątkowo (jeden monitor), ma ograniczony rozmiar
 *   i pozwala na atomowy odczyt z wyczyszczeniem. To jest zalecany sposób odczytu;</li>
 *   <li><b>magazyn stary</b> ({@code store}) - obsługuje wyłącznie metody
 *   {@link #getCaptured()} i {@link #clear()}, oznaczone jako {@link @Deprecated} i przeznaczone do usunięcia
 *   w jednej z kolejnych wersji. Działa tak jak dotychczas ({@link ConcurrentHashMap}, odczyt i czyszczenie
 *   jako dwie osobne operacje), a jedyną różnicą jest zabezpieczenie przed nieograniczonym wzrostem pamięci.</li>
 * </ul>
 * Magazyny nie wpływają na siebie nawzajem: {@link #getAndClear()} nie czyści magazynu starego,
 * a {@link #clear()} nie czyści magazynu nowego. Nie należy mieszać obu sposobów odczytu w jednej aplikacji.
 * <p>
 * <b>Observery</b> ({@link #addObserver(ResponseHeaderObserver)}): trzeci, niezależny od buforów sposób odbioru nagłówków.
 * Observer jest wołany synchronicznie w <b>wątku, który wykonał wywołanie</b> metody klienta (po powrocie z HTTP),
 * dzięki czemu może skorzystać z {@link ThreadLocal} aplikacji i przypisać nagłówki do konkretnego wywołania
 * biznesowego - czego bufor wspólny dla całego klienta nie umożliwia. Szczegóły: {@link ResponseHeaderObserver}.
 * Jeżeli używasz wyłącznie observerów, wyłącz bufory: {@link #setBufferingEnabled(boolean) setBufferingEnabled(false)}.
 * <p>
 * Klasa jest bezpieczna wielowątkowo: {@code capture} jest wołane z wątków {@link java.net.http.HttpClient}
 * (równolegle, dla wielu żądań), a odczyt może następować z dowolnych wątków aplikacji.
 */
public class ResponseHeaderCaptureHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseHeaderCaptureHandler.class);

    /**
     * Domyślny maksymalny rozmiar każdego z magazynów wpisów.
     */
    public static final int DEFAULT_MAX_ENTRIES = 10_000;

    // Zasubskrybowane nazwy nagłówków (małymi literami). Zbiór współbieżny - subscribe/unsubscribe mogą być
    // wołane z dowolnego wątku w trakcie, gdy inne wątki wykonują capture().
    private final Set<String> subscribedHeaders = ConcurrentHashMap.newKeySet();

    // Limit liczby wpisów w każdym z magazynów.
    private final int maxEntries;

    // Zarejestrowane observery. CopyOnWriteArrayList: odczyt (iteracja przy każdej odpowiedzi) jest bez locka i na
    // stabilnej migawce, a rejestracja/wyrejestrowanie (rzadkie) kopiuje tablicę - bezpieczne wielowątkowo.
    private final CopyOnWriteArrayList<ResponseHeaderObserver> observers = new CopyOnWriteArrayList<>();

    // Czy capture() zapisuje wpisy do magazynów. Domyślnie tak (zgodność wsteczna). volatile - zmiana jest widoczna
    // od razu dla wątków HttpClient.
    private volatile boolean bufferingEnabled = true;

    // ------------------------------------------------------------------------------------------
    // MAGAZYN STARY (legacy) - wyłącznie getCaptured() i clear() (obie @Deprecated).
    // Klucz wpisu = metoda + ścieżka + zapytanie + unikalny identyfikator. Bez żadnego dodatkowego locka.
    // ------------------------------------------------------------------------------------------
    private final Map<String, Map<String, List<String>>> store = new ConcurrentHashMap<>();

    // ------------------------------------------------------------------------------------------
    // MAGAZYN NOWY - wyłącznie getAndClear(), size(), getEvictedCount().
    // Wszystkie trzy pola poniżej są chronione monitorem drainLock. Mapa zachowuje kolejność wstawiania,
    // a mapy wewnętrzne są niemodyfikowalne po wstawieniu.
    // ------------------------------------------------------------------------------------------
    private final Object drainLock = new Object();
    private Map<String, Map<String, List<String>>> drainableStore = new LinkedHashMap<>();
    private long evictedCount;

    /**
     * Tworzy handler z domyślnym limitem wpisów ({@link #DEFAULT_MAX_ENTRIES}).
     */
    public ResponseHeaderCaptureHandler() {
        this(DEFAULT_MAX_ENTRIES);
    }

    /**
     * Tworzy handler z podanym limitem wpisów (stosowanym do każdego z dwóch magazynów).
     *
     * @param maxEntries maksymalna liczba jednocześnie przechowywanych wpisów, musi być większa od zera;
     *                   w nowym magazynie po jej przekroczeniu usuwane są najstarsze wpisy,
     *                   w starym magazynie - patrz {@link #capture(HttpRequest, HttpHeaders)}.
     * @throws IllegalArgumentException gdy {@code maxEntries <= 0}
     */
    public ResponseHeaderCaptureHandler(int maxEntries) {
        if (maxEntries <= 0) {
            throw new IllegalArgumentException("maxEntries must be > 0, was: " + maxEntries);
        }
        this.maxEntries = maxEntries;
    }

    /**
     * Dodaje nagłówek do obserwowanych. Nazwa jest normalizowana do małych liter ({@link Locale#ROOT}),
     * więc wielkość liter nie ma znaczenia, a klucze w zwracanych mapach są zawsze zapisane małymi literami.
     * Ponowne zasubskrybowanie tego samego nagłówka nic nie zmienia. Metoda jest bezpieczna wielowątkowo.
     *
     * @param header nazwa nagłówka odpowiedzi, np. {@code X-System-Warning}
     * @throws NullPointerException gdy {@code header == null}
     */
    public void subscribe(String header) {
        // normalize() zamienia nazwę na małe litery niezależnie od lokalizacji JVM i dodaje do zbioru współbieżnego
        subscribedHeaders.add(normalize(header));
    }

    /**
     * Usuwa nagłówek z obserwowanych. Od tej chwili kolejne odpowiedzi nie będą go zapisywać; wpisy już
     * zapisane pozostają w magazynach do odczytu. Brak takiej subskrypcji nie jest błędem.
     *
     * @param header nazwa nagłówka odpowiedzi (wielkość liter bez znaczenia)
     * @throws NullPointerException gdy {@code header == null}
     */
    public void unsubscribe(String header) {
        // ta sama normalizacja co przy subscribe(), żeby usunąć dokładnie ten sam klucz
        subscribedHeaders.remove(normalize(header));
    }

    /**
     * Zapisuje zasubskrybowane nagłówki z odpowiedzi na dane żądanie do <b>obu</b> magazynów, pod nowo
     * wygenerowanym kluczem wpisu. Odpowiada wywołaniu {@link #capture(HttpRequest, HttpHeaders, String)}
     * z {@code requestKey == null}. Nic nie robi, gdy buforowanie jest wyłączone
     * ({@link #setBufferingEnabled(boolean)}).
     *
     * @param request żądanie, na które przyszła odpowiedź (z niego powstaje klucz wpisu)
     * @param headers nagłówki odpowiedzi
     */
    public void capture(HttpRequest request, HttpHeaders headers) {
        // klucz zostanie wygenerowany dopiero, jeśli faktycznie powstanie wpis
        capture(request, headers, null);
    }

    /**
     * Zapisuje zasubskrybowane nagłówki z odpowiedzi na dane żądanie do <b>obu</b> magazynów.
     * Wołana przez klienta HTTP, potencjalnie równolegle z wielu wątków. Nie rzuca wyjątków dla {@code null}
     * (nic wtedy nie zapisuje). Jeżeli odpowiedź nie zawiera żadnego z zasubskrybowanych nagłówków -
     * wpis nie powstaje.
     * <p>
     * Kroki: (1) odczyt wartości nagłówków poza jakimkolwiek lockiem, (2) ustalenie klucza wpisu
     * (podany albo nowy, z unikalnym identyfikatorem, więc dwa identyczne żądania dają dwa osobne wpisy),
     * (3) zapis do magazynu starego (z zabezpieczeniem rozmiaru), (4) zapis do magazynu nowego
     * pod monitorem, z usunięciem najstarszych wpisów po przekroczeniu limitu.
     * <p>
     * Zabezpieczenie rozmiaru magazynu starego: gdy ma już {@code maxEntries} wpisów, jest czyszczony w całości
     * tuż przed dodaniem nowego wpisu (nie ma w nim kolejności, więc nie da się usunąć "najstarszego").
     * Dotyczy to wyłącznie sytuacji, w której nikt nie odczytywał i nie czyścił wpisów przez tysiące żądań -
     * dotychczas oznaczało to nieograniczony wzrost pamięci.
     *
     * @param request    żądanie, na które przyszła odpowiedź
     * @param headers    nagłówki odpowiedzi
     * @param requestKey klucz wpisu (z {@link #createRequestKey(HttpRequest)}) albo {@code null}, by wygenerować nowy;
     *                   podanie tego samego klucza w {@link #notifyObservers(HttpRequest, HttpHeaders, String)}
     *                   pozwala powiązać wpis bufora z callbackiem observera
     */
    public void capture(HttpRequest request, HttpHeaders headers, String requestKey) {
        // brak danych, wyłączone buforowanie albo brak jakichkolwiek subskrypcji - nic do zapisania, wychodzimy bez alokacji
        if (!bufferingEnabled || request == null || headers == null || subscribedHeaders.isEmpty()) {
            return;
        }

        // (1) zbieramy wartości zasubskrybowanych nagłówków; iteracja po zbiorze współbieżnym jest bezpieczna
        Map<String, List<String>> captured = new LinkedHashMap<>();
        for (String h : subscribedHeaders) {
            List<String> headerValues = headers.allValues(h);
            if (!headerValues.isEmpty()) {
                // List.copyOf - niezmienna kopia wartości, niezależna od obiektu odpowiedzi
                captured.put(h, List.copyOf(headerValues));
            }
        }
        // odpowiedź nie zawiera żadnego obserwowanego nagłówka - nie tworzymy pustego wpisu
        if (captured.isEmpty()) {
            return;
        }

        // (2) klucz wpisu: "METODA /sciezka?zapytanie :: UUID"; UUID gwarantuje brak kolizji kluczy
        //     dla identycznych, równoległych żądań. Gdy wołający podał klucz (ten sam trafia do observerów) - używamy go.
        String entryKey = requestKey != null ? requestKey : createRequestKey(request);

        // (3) magazyn STARY: ConcurrentHashMap, bez locka. Wpis dostaje własną, modyfikowalną mapę (HashMap),
        //     niewspółdzieloną z magazynem nowym - modyfikacja wyniku getCaptured() nie zepsuje magazynu nowego.
        if (store.size() >= maxEntries) {
            // zabezpieczenie przed wyciekiem pamięci (patrz Javadoc): reset całego starego magazynu
            store.clear();
        }
        store.put(entryKey, new HashMap<>(captured));

        // (4) magazyn NOWY: pod monitorem wstawiamy wpis (niemodyfikowalna mapa) i w razie potrzeby usuwamy najstarsze
        Map<String, List<String>> immutableEntry = Collections.unmodifiableMap(captured);
        synchronized (drainLock) {
            drainableStore.put(entryKey, immutableEntry);
            if (drainableStore.size() > maxEntries) {
                // LinkedHashMap trzyma kolejność wstawiania, więc pierwszy klucz to najstarszy wpis
                Iterator<String> oldest = drainableStore.keySet().iterator();
                while (drainableStore.size() > maxEntries && oldest.hasNext()) {
                    oldest.next();
                    oldest.remove();
                    evictedCount++;
                }
            }
        }
    }

    /**
     * Rejestruje observera wywoływanego po każdej odpowiedzi zawierającej zasubskrybowany nagłówek,
     * w wątku, który wykonał wywołanie (patrz {@link ResponseHeaderObserver}). Rejestracja jest idempotentna:
     * ten sam obiekt dodany ponownie nie będzie wywoływany podwójnie. Metoda jest bezpieczna wielowątkowo
     * i może być wołana w dowolnym momencie (także gdy klient obsługuje żądania).
     * <p>
     * Observer jest wołany tylko dla nagłówków z {@link #subscribe(String)} - bez subskrypcji nigdy się nie uruchomi.
     *
     * @param observer observer do zarejestrowania
     * @throws NullPointerException gdy {@code observer == null}
     */
    public void addObserver(ResponseHeaderObserver observer) {
        // addIfAbsent - atomowe "dodaj, jeśli jeszcze nie ma" (porównanie przez equals(); dla lambd: tożsamość obiektu)
        observers.addIfAbsent(Objects.requireNonNull(observer, "observer"));
    }

    /**
     * Wyrejestrowuje observera. Wywołania już trwające mogą jeszcze dokończyć callback na starej migawce listy.
     * Brak takiego observera nie jest błędem.
     *
     * @param observer observer do wyrejestrowania
     * @throws NullPointerException gdy {@code observer == null}
     */
    public void removeObserver(ResponseHeaderObserver observer) {
        observers.remove(Objects.requireNonNull(observer, "observer"));
    }

    /**
     * Włącza lub wyłącza zapisywanie wpisów do magazynów ({@link #getAndClear()}, {@link #getCaptured()}).
     * Domyślnie włączone. Wyłącz, jeśli korzystasz wyłącznie z observerów - unikniesz trzymania w pamięci
     * wpisów, których nikt nie odczyta. Wpływa tylko na kolejne wywołania {@code capture}; wpisy już zapisane
     * pozostają, a observery działają niezależnie od tego ustawienia.
     *
     * @param enabled {@code true} - zapisuj wpisy (domyślnie), {@code false} - nie zapisuj
     */
    public void setBufferingEnabled(boolean enabled) {
        this.bufferingEnabled = enabled;
    }

    /**
     * @return czy {@code capture} zapisuje wpisy do magazynów
     */
    public boolean isBufferingEnabled() {
        return bufferingEnabled;
    }

    /**
     * Buduje klucz wywołania: {@code "METODA /sciezka?zapytanie :: UUID"}. Klient woła tę metodę raz na wywołanie
     * i przekazuje ten sam klucz do {@link #capture(HttpRequest, HttpHeaders, String)} (bufor) oraz
     * {@link #notifyObservers(HttpRequest, HttpHeaders, String)} (observery), dzięki czemu oba kanały
     * da się skorelować.
     *
     * @param request żądanie
     * @return nowy, unikalny klucz wywołania
     */
    public String createRequestKey(HttpRequest request) {
        return buildRequestKey(request);
    }

    /**
     * Wywołuje zarejestrowanych observerów, o ile odpowiedź zawiera co najmniej jeden zasubskrybowany nagłówek.
     * Wołane przez klienta <b>w wątku, który wykonał wywołanie</b>, po powrocie z żądania HTTP (a nie z wątku
     * roboczego {@link java.net.http.HttpClient}), więc observer widzi {@link ThreadLocal} tego wywołania.
     * <p>
     * Wyjątek rzucony przez observera jest logowany (poziom WARN) i ignorowany - nie przerywa wywołania
     * ani pozostałych observerów. Observery są wołane w kolejności rejestracji, na migawce listy z chwili wywołania.
     *
     * @param request    żądanie, na które przyszła odpowiedź
     * @param headers    nagłówki odpowiedzi
     * @param requestKey klucz wywołania z {@link #createRequestKey(HttpRequest)} albo {@code null} (zostanie wygenerowany)
     */
    public void notifyObservers(HttpRequest request, HttpHeaders headers, String requestKey) {
        if (request == null || headers == null || observers.isEmpty()) {
            return;
        }
        // observery interesują się wyłącznie odpowiedziami z zasubskrybowanym nagłówkiem (jak bufor)
        if (!containsSubscribedHeader(headers)) {
            return;
        }
        String key = requestKey != null ? requestKey : createRequestKey(request);
        // iteracja po CopyOnWriteArrayList działa na stałej migawce
        for (ResponseHeaderObserver observer : observers) {
            try {
                observer.onHeadersCaptured(request, headers, key);
            } catch (RuntimeException e) {
                // observability nie może psuć wywołania API ani blokować pozostałych observerów
                LOG.warn("ResponseHeaderObserver {} threw an exception and was ignored", observer.getClass().getName(), e);
            }
        }
    }

    /**
     * <b>Atomowo</b> zwraca wszystkie wpisy z nowego magazynu i opróżnia go. Wpis dodany przez dowolny inny wątek
     * trafia do dokładnie jednej migawki - tej zwróconej przez to wywołanie albo przez następne - i nigdy nie jest
     * pomijany ani duplikowany. Zalecany sposób odczytu, także w kodzie jednowątkowym.
     * <p>
     * Wynik jest niezależną, modyfikowalną mapą w kolejności przechwycenia. Metoda nie czyści magazynu starego
     * używanego przez {@link #getCaptured()}.
     *
     * @return wpisy: klucz = "METODA /sciezka?zapytanie :: UUID", wartość = nazwa nagłówka (małymi literami) -> wartości
     */
    public Map<String, Map<String, List<String>>> getAndClear() {
        Map<String, Map<String, List<String>>> drained;
        synchronized (drainLock) {
            // odbieramy dotychczasową mapę i od razu podstawiamy pustą - w tym momencie zapisy zaczynają trafiać do nowej
            drained = drainableStore;
            drainableStore = new LinkedHashMap<>();
        }
        // odebrana mapa nie jest już nigdzie współdzielona; kopiujemy ją do postaci modyfikowalnej poza monitorem
        return toMutableCopy(drained);
    }

    /**
     * Zwraca kopię wpisów ze <b>starego</b> magazynu. Działa jak dotychczas: odczyt to płytka kopia
     * ({@code new HashMap<>(store)}) bez ustalonej kolejności, a mapy wewnętrzne są wspólne z magazynem.
     * <p>
     * <b>Nie jest bezpieczna dla sekwencji odczyt + {@link #clear()}</b>: wpis dodany przez inny wątek
     * pomiędzy tymi wywołaniami zostanie usunięty, choć nikt go nie odczytał.
     *
     * @return kopia wpisów starego magazynu
     * @Deprecated metoda zostanie usunięta w jednej z kolejnych wersji. Użyj {@link #getAndClear()},
     * które odczytuje i czyści atomowo.
     */
    @Deprecated
    public Map<String, Map<String, List<String>>> getCaptured() {
        return new HashMap<>(store);
    }

    /**
     * Usuwa wszystkie wpisy ze <b>starego</b> magazynu (także te, których nikt nie odczytał). Nie dotyka magazynu
     * nowego używanego przez {@link #getAndClear()}.
     *
     * @Deprecated metoda zostanie usunięta w jednej z kolejnych wersji. Użyj {@link #getAndClear()},
     * które odczytuje i czyści atomowo.
     */
    @Deprecated
    public void clear() {
        store.clear();
    }

    /**
     * Zwraca aktualną liczbę wpisów w <b>nowym</b> magazynie (tym, który opróżnia {@link #getAndClear()}).
     * Wartość jest chwilowa - inne wątki mogą ją zmienić tuż po zwróceniu.
     *
     * @return liczba wpisów oczekujących na odczyt przez {@link #getAndClear()}
     */
    public int size() {
        // odczyt pod tym samym monitorem co zapis, żeby zobaczyć spójną wartość
        synchronized (drainLock) {
            return drainableStore.size();
        }
    }

    /**
     * Zwraca łączną liczbę wpisów usuniętych z <b>nowego</b> magazynu z powodu przekroczenia limitu
     * od utworzenia handlera. Wartość większa od zera oznacza, że wpisy nie były odczytywane wystarczająco często.
     *
     * @return liczba usuniętych wpisów
     */
    public long getEvictedCount() {
        // licznik jest chroniony monitorem, tak samo jak zapis w capture()
        synchronized (drainLock) {
            return evictedCount;
        }
    }

    // Czy odpowiedź zawiera którykolwiek z zasubskrybowanych nagłówków (iteracja po zbiorze współbieżnym jest bezpieczna).
    private boolean containsSubscribedHeader(HttpHeaders headers) {
        for (String h : subscribedHeaders) {
            if (headers.firstValue(h).isPresent()) {
                return true;
            }
        }
        return false;
    }

    // Kopiuje mapę wpisów do postaci modyfikowalnej: nowa mapa zewnętrzna i nowe (modyfikowalne) mapy wewnętrzne.
    // Listy wartości są niezmienne i bezpieczne do współdzielenia.
    private static Map<String, Map<String, List<String>>> toMutableCopy(Map<String, Map<String, List<String>>> source) {
        Map<String, Map<String, List<String>>> copy = new LinkedHashMap<>(Math.max(16, (int) (source.size() / 0.75f) + 1));
        for (Map.Entry<String, Map<String, List<String>>> e : source.entrySet()) {
            copy.put(e.getKey(), new LinkedHashMap<>(e.getValue()));
        }
        return copy;
    }

    // Normalizuje nazwę nagłówka do małych liter. Locale.ROOT - nazwy nagłówków HTTP są ASCII;
    // domyślna lokalizacja (np. tr-TR) zamieniałaby 'I' na 'ı' i psuła klucze.
    private static String normalize(String header) {
        return Objects.requireNonNull(header, "header").toLowerCase(Locale.ROOT);
    }

    // Buduje klucz wpisu: METODA + ścieżka + (opcjonalnie) zapytanie + " :: " + losowy UUID.
    private String buildRequestKey(HttpRequest request) {
        URI uri = request.uri();
        return request.method() + " " + uri.getPath()
                + (uri.getQuery() != null ? "?" + uri.getQuery() : "")
                + " :: " + UUID.randomUUID();
    }
}
