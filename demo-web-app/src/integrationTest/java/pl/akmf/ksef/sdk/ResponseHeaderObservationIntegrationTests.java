package pl.akmf.ksef.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pl.akmf.ksef.sdk.api.DefaultKsefClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderCaptureHandler;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderObservationOptions;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderObservationProperties;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderObserver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Testy obserwacji nagłówków odpowiedzi ({@link ResponseHeaderCaptureHandler}).
 */
class ResponseHeaderObservationIntegrationTests extends BaseIntegrationTest {

    // Nagłówek, którym prosimy środowisko TEST o dodanie ostrzeżenia do odpowiedzi
    private static final String testSystemWarningHeader = "X-Test-System-Warning";
    // Nagłówek odpowiedzi, który obserwujemy
    private static final String systemWarningHeader = "X-System-Warning";
    // Treść ostrzeżenia, którą wymuszamy i potem odnajdujemy w przechwyconych nagłówkach
    private static final String forcedWarningMessage = "Test-forced-system-warning";
    // Klucz, pod którym handler przechowuje nagłówek (nazwy są normalizowane do małych liter, Locale.ROOT)
    private static final String capturedKey = systemWarningHeader.toLowerCase(Locale.ROOT);

    /**
     * Test (środowisko TEST) - sprawdza, że nagłówek X-System-Warning da się odczytać
     * przez subskrypcję. Odczyt przez getAndClear() (getCaptured() + clear() są deprecated).
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>subskrybujemy nagłówek {@code X-System-Warning} w handlerze klienta</li>
     *   <li>ustawiamy domyślny nagłówek {@code X-Test-System-Warning} (środowisko TEST odsyła go w odpowiedzi jako {@code X-System-Warning})</li>
     *   <li>wykonujemy {@code getAuthChallenge()} i odbieramy wpisy przez {@code getAndClear()}</li>
     *   <li>rezygnujemy z subskrypcji ({@code unsubscribe}), wykonujemy kolejne żądanie i znów odbieramy wpisy</li>
     *   <li>usuwamy nagłówek testowy z domyślnych nagłówków</li>
     * </ol>
     * <b>Oczekiwanie:</b> pierwsze żądanie zwraca challenge, a w odebranych wpisach jest nagłówek z wymuszoną treścią; po {@code unsubscribe} kolejne żądanie nie zostawia żadnego wpisu (mapa pusta).
     */
    @Test
    void testSubscriptionResponseHeaders() throws ApiException {
        // subskrybujemy nagłówek, który chcemy obserwować
        ksefClient.getResponseHeaderCaptureHandler().subscribe(systemWarningHeader);
        // wymuszamy X-System-Warning przez X-Test-System-Warning (środowisko TEST odsyła go w odpowiedzi)
        ksefClient.addDefaultHeader(testSystemWarningHeader, forcedWarningMessage);
        // wykonujemy jedno żądanie - jego odpowiedź zostanie przechwycona przez handler
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        // żądanie musiało się udać i zwrócić challenge
        Assertions.assertNotNull(challenge);
        Assertions.assertNotNull(challenge.getChallenge());

        // atomowo odczytujemy i czyścimy przechwycone nagłówki (po każdym żądaniu należy je odebrać)
        Map<String, Map<String, List<String>>> capturedHeaders = ksefClient.getResponseHeaderCaptureHandler().getAndClear();
        // wśród wpisów szukamy takiego, który zawiera wymuszoną treść ostrzeżenia
        Assertions.assertTrue(capturedHeaders.entrySet().stream()
                .anyMatch(entry -> {
                    var values = entry.getValue().get(systemWarningHeader.toLowerCase());
                    return values != null &&
                            values.stream()
                                    .anyMatch(v -> v.contains(forcedWarningMessage));
                })
        );

        // rezygnujemy z obserwacji i sprawdzamy, że kolejne żądanie nie zostawia już żadnego wpisu
        ksefClient.getResponseHeaderCaptureHandler().unsubscribe(systemWarningHeader);
        challenge = ksefClient.getAuthChallenge();

        Assertions.assertNotNull(challenge);
        Assertions.assertNotNull(challenge.getChallenge());

        // po unsubscribe nic nie powinno zostać zapisane
        capturedHeaders = ksefClient.getResponseHeaderCaptureHandler().getAndClear();
        Assertions.assertTrue(capturedHeaders.isEmpty());

        // sprzątamy: usuwamy z domyślnych nagłówków X-Test-System-Warning
        ksefClient.removeDefaultHeader(testSystemWarningHeader);
    }

    /**
     * stare (deprecated) API zachowuje się jak dotychczas, a oba magazyny są niezależne
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>handler bez subskrypcji: {@code capture} nic nie zapisuje</li>
     *   <li>{@code subscribe("X-SyStEm-WaRnInG")} (zmieszana wielkość liter) i odpowiedź z dwiema wartościami obserwowanego nagłówka oraz z obcym nagłówkiem</li>
     *   <li>odpowiedź bez obserwowanego nagłówka; dwa identyczne żądania; {@code clear()}; {@code unsubscribe} i kolejna odpowiedź</li>
     * </ol>
     * <b>Oczekiwanie:</b> stary magazyn ({@code getCaptured()}/{@code clear()}) działa jak dotychczas: zapisuje tylko zasubskrybowane nagłówki pod nazwą małymi literami,
     * zachowuje kolejność wartości, klucz ma postać {@code "GET /path?x=1 :: UUID"}, identyczne żądania dają osobne wpisy, {@code clear()} czyści wszystko,
     * po {@code unsubscribe} nic się nie zapisuje.
     * <p>
     */
    @Test
    @SuppressWarnings("deprecation")
    // celowo testujemy metody oznaczone jako deprecated
    void legacyContractIsPreserved() {
        // nowy handler z domyślnym limitem
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();

        // brak subskrypcji = nic nie jest zapisywane
        handler.capture(request("/a"), headers(systemWarningHeader, "ignored"));
        Assertions.assertTrue(handler.getCaptured().isEmpty());

        // nazwa nagłówka w subscribe() jest nieczuła na wielkość liter
        handler.subscribe("X-SyStEm-WaRnInG");
        // odpowiedź ma dwie wartości obserwowanego nagłówka i jeden obcy nagłówek (X-Other)
        handler.capture(request("/path?x=1"),
                headers(Map.of(systemWarningHeader, List.of("w1", "w2"), "X-Other", List.of("nope"))));

        // stary magazyn zawiera dokładnie jeden wpis
        Map<String, Map<String, List<String>>> captured = handler.getCaptured();
        Assertions.assertEquals(1, captured.size());
        Map.Entry<String, Map<String, List<String>>> entry = captured.entrySet().iterator().next();
        // klucz wpisu: "METODA sciezka?zapytanie :: UUID"
        Assertions.assertTrue(entry.getKey().startsWith("GET /path?x=1 :: "), "klucz: " + entry.getKey());
        // zapisany jest tylko zasubskrybowany nagłówek (bez X-Other), pod nazwą małymi literami
        Assertions.assertEquals(Set.of(capturedKey), entry.getValue().keySet(), "tylko zasubskrybowane nagłówki");
        // obie wartości nagłówka zachowane w kolejności
        Assertions.assertEquals(List.of("w1", "w2"), entry.getValue().get(capturedKey));

        // odpowiedź bez zasubskrybowanego nagłówka nie tworzy wpisu
        handler.capture(request("/b"), headers("X-Other", "v"));
        Assertions.assertEquals(1, handler.getCaptured().size());

        // dwa identyczne żądania = dwa osobne wpisy (klucz zawiera unikalny identyfikator)
        handler.capture(request("/path?x=1"), headers(systemWarningHeader, "w3"));
        Assertions.assertEquals(2, handler.getCaptured().size());

        // clear() usuwa wszystko ze starego magazynu
        handler.clear();
        Assertions.assertTrue(handler.getCaptured().isEmpty());

        // po unsubscribe kolejne odpowiedzi nie są zapisywane
        handler.unsubscribe(systemWarningHeader);
        handler.capture(request("/c"), headers(systemWarningHeader, "w4"));
        Assertions.assertTrue(handler.getCaptured().isEmpty());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>zapisujemy wpis A (trafia do obu magazynów)</li>
     *   <li>{@code getAndClear()} - sprawdzamy, że stary magazyn nadal ma A</li>
     *   <li>zapisujemy wpis B, wołamy {@code clear()} - sprawdzamy, że nowy magazyn nadal ma B</li>
     * </ol>
     * <b>Oczekiwanie:</b> {@code getAndClear()} nie czyści starego magazynu, a {@code clear()} nie czyści nowego; oba widoki są niezależne.
     */
    @Test
    @SuppressWarnings("deprecation")
    // celowo używamy getCaptured()/clear()
    void legacyAndNewStoresAreIndependent() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);

        // wpis A trafia do obu magazynów
        handler.capture(request("/A"), headers(systemWarningHeader, "A"));

        // odczyt przez nowe API opróżnia tylko nowy magazyn...
        Assertions.assertEquals(Set.of("A"), valuesOf(handler.getAndClear()));
        // ...stary magazyn nadal ma wpis A (getAndClear() go nie dotyka)
        Assertions.assertEquals(Set.of("A"), valuesOf(handler.getCaptured()));

        // wpis B trafia znów do obu magazynów
        handler.capture(request("/B"), headers(systemWarningHeader, "B"));

        // clear() czyści tylko stary magazyn...
        handler.clear();
        Assertions.assertTrue(handler.getCaptured().isEmpty());
        // ...a nowy magazyn nadal ma wpis B (clear() go nie dotyka)
        Assertions.assertEquals(Set.of("B"), valuesOf(handler.getAndClear()));
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>zapisujemy trzy wpisy: first, second, third</li>
     *   <li>{@code getAndClear()} i sprawdzenie kolejności wartości</li>
     *   <li>zapisujemy czwarty wpis i sprawdzamy, że zwrócona migawka się nie zmieniła</li>
     *   <li>modyfikujemy migawkę (mapa zewnętrzna i wewnętrzna) i odbieramy kolejną migawkę</li>
     * </ol>
     * <b>Oczekiwanie:</b> migawka zachowuje kolejność przechwycenia, jest modyfikowalna i niezależna: modyfikacje nie wpływają na stan handlera,
     * a następna migawka zawiera tylko czwarty wpis (bez śladu po zmianach).
     */
    @Test
    void getAndClearReturnsMutableOrderedSnapshot() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);

        // trzy wpisy w określonej kolejności
        handler.capture(request("/1"), headers(systemWarningHeader, "first"));
        handler.capture(request("/2"), headers(systemWarningHeader, "second"));
        handler.capture(request("/3"), headers(systemWarningHeader, "third"));

        Map<String, Map<String, List<String>>> snapshot = handler.getAndClear();

        // kolejność wartości w migawce = kolejność przechwycenia
        List<String> inOrder = new ArrayList<>();
        snapshot.values().forEach(v -> inOrder.add(v.get(capturedKey).get(0)));
        Assertions.assertEquals(List.of("first", "second", "third"), inOrder);

        // późniejsze przechwycenie nie zmienia już zwróconej migawki
        handler.capture(request("/4"), headers(systemWarningHeader, "fourth"));
        Assertions.assertEquals(3, snapshot.size());

        // migawka jest modyfikowalna (zewnętrzna i wewnętrzne mapy) i modyfikacja nie psuje stanu handlera
        snapshot.values().iterator().next().put("injected", List.of("x"));
        snapshot.clear();
        Map<String, Map<String, List<String>>> next = handler.getAndClear();
        Assertions.assertEquals(Set.of("fourth"), valuesOf(next));
        Assertions.assertTrue(next.values().stream().noneMatch(v -> v.containsKey("injected")));
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>ustawiamy domyślną lokalizację JVM na {@code tr-TR} (w niej {@code "I".toLowerCase()} daje 'ı')</li>
     *   <li>subskrybujemy {@code X-Request-Id} i zapisujemy odpowiedź z tym nagłówkiem</li>
     *   <li>sprawdzamy klucze w starym i nowym magazynie; w {@code finally} przywracamy lokalizację</li>
     * </ol>
     * <b>Oczekiwanie:</b> nagłówek jest zapisany pod kluczem {@code "x-request-id"} (zwykłe 'i') w obu magazynach, niezależnie od lokalizacji.
     */
    @Test
    @SuppressWarnings("deprecation")
    // sprawdzamy też stary magazyn (getCaptured)
    void headerNamesAreMatchedIndependentlyOfDefaultLocale() {
        // zapamiętujemy lokalizację, żeby ją przywrócić po teście (to globalny stan JVM)
        Locale original = Locale.getDefault();
        try {
            // w lokalizacji tureckiej "I".toLowerCase() daje 'ı' (bez kropki) - klucze handlera nie mogą od tego zależeć
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
            // nagłówek z wielką literą 'I' w nazwie
            handler.subscribe("X-Request-Id");
            handler.capture(request("/tr"), headers("X-Request-Id", "abc"));

            // stary magazyn: klucz "x-request-id" (zwykłe 'i'), a nie "x-request-ıd"
            Map<String, Map<String, List<String>>> legacy = handler.getCaptured();
            Assertions.assertEquals(1, legacy.size());
            Assertions.assertEquals(List.of("abc"), legacy.values().iterator().next().get("x-request-id"));

            // nowy magazyn - to samo
            Map<String, Map<String, List<String>>> drained = handler.getAndClear();
            Assertions.assertEquals(1, drained.size());
            Assertions.assertEquals(List.of("abc"), drained.values().iterator().next().get("x-request-id"));
        } finally {
            // przywracamy lokalizację niezależnie od wyniku testu
            Locale.setDefault(original);
        }
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>tworzymy handler z limitem 0, a potem z limitem -1</li>
     * </ol>
     * <b>Oczekiwanie:</b> oba wywołania rzucają {@code IllegalArgumentException} od razu przy tworzeniu (błędna konfiguracja nie ujawnia się dopiero przy pierwszym {@code capture}).
     */
    @Test
    void constructorRejectsNonPositiveLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ResponseHeaderCaptureHandler(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ResponseHeaderCaptureHandler(-1));
    }

    // Race condition: getCaptured() + clear()  oraz  atomowe getAndClear()

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>zapisujemy wpis A i wołamy {@code getCaptured()}</li>
     *   <li>"inny wątek" zapisuje wpis B dokładnie między {@code getCaptured()} a {@code clear()}</li>
     *   <li>wołamy {@code clear()}</li>
     * </ol>
     * <b>Oczekiwanie:</b> migawka zawiera tylko A, a wpis B znika bez odczytu - test deterministycznie dokumentuje wyścig starego API (dlatego jest zdeprecjonowane).
     */
    @Test
    @SuppressWarnings("deprecation")
    // celowo używamy getCaptured()/clear()
    void legacyGetCapturedThenClearIsNotAtomic_entryCapturedInBetweenIsDropped() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);

        // wpis A jest już w magazynie
        handler.capture(request("/A"), headers(systemWarningHeader, "A"));
        // krok 1 starego wzorca: odczyt
        Map<String, Map<String, List<String>>> snapshot = handler.getCaptured();
        // "inny wątek" dodaje wpis B dokładnie między odczytem a czyszczeniem
        handler.capture(request("/B"), headers(systemWarningHeader, "B"));
        // krok 2 starego wzorca: czyszczenie (usuwa też B, którego nikt nie odczytał)
        handler.clear();

        // migawka zawiera tylko A...
        Assertions.assertEquals(Set.of("A"), valuesOf(snapshot));
        // ...a B przepadł bezpowrotnie - dlatego w kodzie współbieżnym należy używać getAndClear()
        Assertions.assertTrue(handler.getCaptured().isEmpty());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>zapisujemy wpis A i wołamy {@code getAndClear()}</li>
     *   <li>"inny wątek" zapisuje wpis B tuż po odczycie</li>
     *   <li>wołamy {@code getAndClear()} jeszcze dwa razy</li>
     * </ol>
     * <b>Oczekiwanie:</b> pierwsza migawka ma tylko A, druga tylko B (nic nie ginie), trzecia jest pusta, a {@code size()} wynosi 0.
     */
    @Test
    void getAndClearClosesTheRace_entryCapturedAfterGoesToNextSnapshot() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);

        // wpis A jest już w magazynie
        handler.capture(request("/A"), headers(systemWarningHeader, "A"));
        // odczyt + czyszczenie w jednej operacji
        Map<String, Map<String, List<String>>> drained = handler.getAndClear();
        // "inny wątek" dodaje B tuż po odczycie
        handler.capture(request("/B"), headers(systemWarningHeader, "B"));

        // pierwsza migawka ma tylko A
        Assertions.assertEquals(Set.of("A"), valuesOf(drained));
        // B jest w drugiej migawce - nic nie zginęło
        Assertions.assertEquals(Set.of("B"), valuesOf(handler.getAndClear()));
        // trzecia migawka jest pusta, a magazyn pusty
        Assertions.assertTrue(handler.getAndClear().isEmpty());
        Assertions.assertEquals(0, handler.size());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>8 wątków-producentów zapisuje po 2000 unikalnych wpisów (razem 16000), jednocześnie 3 wątki-konsumenci w pętli wołają {@code getAndClear()}</li>
     *   <li>po zakończeniu producentów konsumenci kończą, a test robi końcowe {@code getAndClear()}</li>
     *   <li>zbieramy wszystkie migawki i liczymy klucze oraz wartości</li>
     * </ol>
     * <b>Oczekiwanie:</b> każdy wpis trafił do dokładnie jednej migawki: 16000 różnych kluczy, zestaw wartości identyczny z zapisanym,
     * żadna wartość odczytana więcej niż raz, {@code size()==0} i {@code getEvictedCount()==0}.
     */
    @Test
    @Timeout(120)
    void getAndClearUnderConcurrentCaptureLosesNoEntryAndDuplicatesNone() throws Exception {
        final int producers = 8;       // liczba wątków zapisujących
        final int perProducer = 2_000; // wpisów na wątek
        final int drainers = 3;        // liczba wątków odczytujących równolegle

        // limit równy liczbie wszystkich wpisów - nic nie może zostać usunięte
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler(producers * perProducer);
        handler.subscribe(systemWarningHeader);

        // wszystkie migawki zwrócone przez wszystkich konsumentów
        ConcurrentLinkedQueue<Map<String, Map<String, List<String>>>> snapshots = new ConcurrentLinkedQueue<>();
        // flaga "producenci skończyli" - konsumenci kończą pętlę
        AtomicBoolean producersDone = new AtomicBoolean(false);
        // wszyscy startują jednocześnie, żeby zmaksymalizować nakładanie się operacji
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(producers + drainers);
        try {
            List<Future<?>> producerFutures = new ArrayList<>();
            for (int p = 0; p < producers; p++) {
                final int producer = p;
                producerFutures.add(pool.submit(() -> {
                    start.await(); // czekamy na sygnał startu
                    for (int i = 0; i < perProducer; i++) {
                        // każdy wpis ma unikalną wartość "p<producent>-<numer>", żeby dało się sprawdzić utratę/duplikaty
                        handler.capture(request("/p" + producer + "/" + i), headers(systemWarningHeader, value(producer, i)));
                    }
                    return null;
                }));
            }
            List<Future<?>> drainerFutures = new ArrayList<>();
            for (int d = 0; d < drainers; d++) {
                drainerFutures.add(pool.submit(() -> {
                    start.await(); // czekamy na sygnał startu
                    while (!producersDone.get()) {
                        // odbieramy wszystko, co jest dostępne, i zapamiętujemy migawkę
                        snapshots.add(handler.getAndClear());
                        Thread.yield(); // oddajemy procesor, żeby producenci mieli szansę pisać
                    }
                    return null;
                }));
            }

            start.countDown();            // START
            awaitAll(producerFutures);    // czekamy, aż producenci skończą (propaguje wyjątki)
            producersDone.set(true);      // konsumenci mogą kończyć
            awaitAll(drainerFutures);
            snapshots.add(handler.getAndClear()); // końcowe opróżnienie: zbiera to, co zostało po ostatniej pętli
        } finally {
            pool.shutdownNow();
        }

        // zbiór wszystkich wartości, które powinny zostać odczytane
        Set<String> expected = new HashSet<>();
        for (int p = 0; p < producers; p++) {
            for (int i = 0; i < perProducer; i++) {
                expected.add(value(p, i));
            }
        }

        Set<String> keys = new HashSet<>();                   // klucze wpisów ze wszystkich migawek
        Map<String, Integer> occurrences = new HashMap<>();   // ile razy odczytano daną wartość
        for (Map<String, Map<String, List<String>>> snapshot : snapshots) {
            for (Map.Entry<String, Map<String, List<String>>> e : snapshot.entrySet()) {
                // ten sam klucz w dwóch migawkach = duplikat
                Assertions.assertTrue(keys.add(e.getKey()), "zduplikowany klucz wpisu: " + e.getKey());
                List<String> values = e.getValue().get(capturedKey);
                Assertions.assertNotNull(values, "wpis bez zasubskrybowanego nagłówka");
                Assertions.assertEquals(1, values.size());
                occurrences.merge(values.get(0), 1, Integer::sum);
            }
        }

        // liczba odczytanych wpisów = liczba zapisanych (brak utraty)
        Assertions.assertEquals(expected.size(), keys.size(), "utracone wpisy");
        // dokładnie ten sam zestaw wartości
        Assertions.assertEquals(expected, occurrences.keySet(), "zestaw wartości różni się od oczekiwanego");
        // każda wartość odczytana dokładnie raz (brak duplikatów)
        Assertions.assertTrue(occurrences.values().stream().allMatch(c -> c == 1), "wartość odczytana więcej niż raz");
        // nowy magazyn pusty i nic nie zostało usunięte
        Assertions.assertEquals(0, handler.size());
        Assertions.assertEquals(0L, handler.getEvictedCount());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>6 producentów zapisuje po 2000 wpisów, jeden konsument w pętli robi {@code getCaptured()} i {@code clear()}</li>
     *   <li>po zakończeniu producentów analizujemy wszystkie migawki</li>
     * </ol>
     * <b>Oczekiwanie:</b> stary wzorzec nie rzuca wyjątków (np. {@code ConcurrentModificationException}), migawki są poprawne,
     * żaden wpis nie jest zwrócony dwa razy, a odczytanych jest nie więcej niż zapisanych (część może zostać pominięta - znane ograniczenie).
     */
    @Test
    @Timeout(120)
    @SuppressWarnings("deprecation")
    // celowo używamy getCaptured()/clear()
    void legacyGetCapturedThenClearStaysSafeUnderContention() throws Exception {
        final int producers = 6;
        final int perProducer = 2_000;

        // limit równy liczbie wszystkich wpisów - zabezpieczenie rozmiaru starego magazynu nie może się uruchomić
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler(producers * perProducer);
        handler.subscribe(systemWarningHeader);

        // tylko jeden konsument, więc zwykła lista wystarczy (dostęp z jednego wątku)
        List<Map<String, Map<String, List<String>>>> snapshots = new ArrayList<>();
        AtomicBoolean producersDone = new AtomicBoolean(false);
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(producers + 1);
        try {
            List<Future<?>> producerFutures = new ArrayList<>();
            for (int p = 0; p < producers; p++) {
                final int producer = p;
                producerFutures.add(pool.submit(() -> {
                    start.await();
                    for (int i = 0; i < perProducer; i++) {
                        handler.capture(request("/p" + producer + "/" + i), headers(systemWarningHeader, value(producer, i)));
                    }
                    return null;
                }));
            }
            Future<?> consumer = pool.submit(() -> {
                start.await();
                while (!producersDone.get()) {
                    // stary wzorzec: odczyt, a potem osobne czyszczenie
                    snapshots.add(handler.getCaptured());
                    handler.clear();
                    Thread.yield();
                }
                return null;
            });

            start.countDown();
            awaitAll(producerFutures);
            producersDone.set(true);
            consumer.get(); // propaguje ewentualny wyjątek z konsumenta
        } finally {
            pool.shutdownNow();
        }

        Set<String> keys = new HashSet<>();
        Set<String> seenValues = new HashSet<>();
        for (Map<String, Map<String, List<String>>> snapshot : snapshots) {
            for (Map.Entry<String, Map<String, List<String>>> e : snapshot.entrySet()) {
                // jeden konsument: wpis odczytany przez getCaptured() jest usuwany przez następny clear(), więc nie wróci
                Assertions.assertTrue(keys.add(e.getKey()), "ten sam wpis zwrócony dwukrotnie: " + e.getKey());
                List<String> values = e.getValue().get(capturedKey);
                Assertions.assertNotNull(values);
                Assertions.assertEquals(1, values.size());
                Assertions.assertTrue(seenValues.add(values.get(0)), "zduplikowana wartość: " + values.get(0));
            }
        }
        // odczytano nie więcej niż zapisano (część mogła zostać pominięta w lukach - to znane ograniczenie starego API)
        Assertions.assertTrue(seenValues.size() <= producers * perProducer);
    }

    // Limit pamięci, subskrypcje, leniwa inicjalizacja klienta

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>limit 100, zapisujemy 1000 wpisów v0..v999 bez żadnego odczytu</li>
     *   <li>sprawdzamy {@code size()}, {@code getEvictedCount()} i zawartość {@code getAndClear()}</li>
     * </ol>
     * <b>Oczekiwanie:</b> w nowym magazynie zostaje dokładnie 100 najnowszych wpisów (v900..v999) w kolejności przechwycenia,
     * a licznik usuniętych wynosi 900.
     */
    @Test
    void storeIsBoundedAndEvictsOldestEntries() {
        final int limit = 100;
        final int total = 1_000;
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler(limit);
        handler.subscribe(systemWarningHeader);

        // zapisujemy 1000 wpisów "v0".."v999" bez żadnego odczytu
        for (int i = 0; i < total; i++) {
            handler.capture(request("/" + i), headers(systemWarningHeader, "v" + i));
        }

        // magazyn nie przekracza limitu, a licznik usunięć = 1000 - 100
        Assertions.assertEquals(limit, handler.size());
        Assertions.assertEquals(total - limit, handler.getEvictedCount());

        // zostały najnowsze wpisy v900..v999, w kolejności przechwycenia
        List<String> retained = new ArrayList<>();
        handler.getAndClear().values().forEach(v -> retained.add(v.get(capturedKey).get(0)));
        List<String> expected = new ArrayList<>();
        for (int i = total - limit; i < total; i++) {
            expected.add("v" + i);
        }
        Assertions.assertEquals(expected, retained);
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>limit 100, zapisujemy 1000 wpisów bez odczytu i bez {@code clear()}</li>
     *   <li>sprawdzamy zawartość {@code getCaptured()}</li>
     * </ol>
     * <b>Oczekiwanie:</b> stary magazyn nie przekracza limitu (po osiągnięciu limitu jest czyszczony w całości tuż przed dodaniem nowego wpisu),
     * a najnowszy wpis (v999) zawsze w nim jest.
     */
    @Test
    @SuppressWarnings("deprecation")
    // celowo używamy getCaptured()
    void legacyStoreIsBoundedByResetOnOverflow() {
        final int limit = 100;
        final int total = 1_000;
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler(limit);
        handler.subscribe(systemWarningHeader);

        // 1000 wpisów bez żadnego odczytu ani clear()
        for (int i = 0; i < total; i++) {
            handler.capture(request("/" + i), headers(systemWarningHeader, "v" + i));
        }

        Map<String, Map<String, List<String>>> legacy = handler.getCaptured();
        // rozmiar starego magazynu nie przekracza limitu
        Assertions.assertTrue(legacy.size() <= limit, "rozmiar: " + legacy.size());
        // najnowszy wpis nigdy nie jest tracony przez reset
        Assertions.assertTrue(valuesOf(legacy).contains("v" + (total - 1)));
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>limit 500, 8 wątków zapisuje po 5000 wpisów (razem 40000) bez żadnego odczytu</li>
     *   <li>sprawdzamy rozmiary obu magazynów i licznik usuniętych</li>
     * </ol>
     * <b>Oczekiwanie:</b> nowy magazyn ma dokładnie 500 wpisów, a usunięte 40000 - 500;
     * stary magazyn nie przekracza limitu więcej niż o liczbę wątków (sprawdzenie rozmiaru i zapis nie są tam jedną operacją atomową).
     */
    @Test
    @Timeout(60)
    @SuppressWarnings("deprecation")
    // celowo używamy getCaptured()
    void boundedStoresHoldUnderConcurrentCaptureWithoutAnyReader() throws Exception {
        final int limit = 500;
        final int threads = 8;
        final int perThread = 5_000;
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler(limit);
        handler.subscribe(systemWarningHeader);

        // 8 wątków zapisuje po 5000 wpisów (razem 40000) przy limicie 500
        runConcurrently(threads, t -> {
            for (int i = 0; i < perThread; i++) {
                handler.capture(request("/t" + t + "/" + i), headers(systemWarningHeader, value(t, i)));
            }
            return null;
        });

        // nowy magazyn: dokładnie limit wpisów, a reszta policzona jako usunięta
        Assertions.assertEquals(limit, handler.size());
        Assertions.assertEquals((long) threads * perThread - limit, handler.getEvictedCount());
        // stary magazyn: sprawdzenie rozmiaru i zapis nie są jedną operacją atomową, więc limit może
        // być chwilowo przekroczony najwyżej o liczbę równoległych zapisujących wątków
        Assertions.assertTrue(handler.getCaptured().size() <= limit + threads,
                "rozmiar starego magazynu: " + handler.getCaptured().size());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>4 producentów zapisuje po 5000 odpowiedzi z dwoma nagłówkami</li>
     *   <li>równolegle jeden wątek w pętli robi {@code subscribe}/{@code unsubscribe} drugiego nagłówka</li>
     *   <li>na końcu {@code getAndClear()}</li>
     * </ol>
     * <b>Oczekiwanie:</b> brak wyjątków; wpisów jest dokładnie tyle, ile wywołań {@code capture} (stały nagłówek jest w każdym),
     * a przełączany nagłówek występuje co najwyżej dodatkowo - żadnych obcych kluczy.
     */
    @Test
    @Timeout(60)
    void subscribeAndUnsubscribeConcurrentlyWithCaptureIsSafe() throws Exception {
        final int producers = 4;
        final int perProducer = 5_000;
        final String toggled = "X-Toggled-Header"; // nagłówek, który jest ciągle włączany i wyłączany

        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler(producers * perProducer);
        // ten nagłówek jest zasubskrybowany przez cały test
        handler.subscribe(systemWarningHeader);
        AtomicBoolean producersDone = new AtomicBoolean(false);

        ExecutorService pool = Executors.newFixedThreadPool(producers + 1);
        try {
            // wątek przełączający subskrypcję drugiego nagłówka
            Future<?> toggler = pool.submit(() -> {
                while (!producersDone.get()) {
                    handler.subscribe(toggled);
                    handler.unsubscribe(toggled);
                }
            });
            List<Future<?>> producerFutures = new ArrayList<>();
            for (int p = 0; p < producers; p++) {
                final int producer = p;
                producerFutures.add(pool.submit(() -> {
                    for (int i = 0; i < perProducer; i++) {
                        // odpowiedź zawsze zawiera oba nagłówki
                        handler.capture(request("/p" + producer + "/" + i),
                                headers(Map.of(systemWarningHeader, List.of(value(producer, i)), toggled, List.of("t"))));
                    }
                    return null;
                }));
            }
            awaitAll(producerFutures);
            producersDone.set(true);
            toggler.get(); // propaguje ewentualny wyjątek z wątku przełączającego
        } finally {
            pool.shutdownNow();
        }

        // stale zasubskrybowany nagłówek jest w każdym wpisie, więc wpisów jest dokładnie tyle, ile capture()
        Map<String, Map<String, List<String>>> captured = handler.getAndClear();
        Assertions.assertEquals(producers * perProducer, captured.size());
        for (Map<String, List<String>> entry : captured.values()) {
            Assertions.assertTrue(entry.containsKey(capturedKey));
            // a przełączany nagłówek co najwyżej występuje dodatkowo - żadnych obcych kluczy
            Assertions.assertTrue(Set.of(capturedKey, toggled.toLowerCase(Locale.ROOT)).containsAll(entry.keySet()), "obce klucze: " + entry.keySet());
        }
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>tworzymy świeżego klienta (bez handlera), 16 wątków czeka na barierze i jednocześnie woła {@code getResponseHeaderCaptureHandler()}</li>
     *   <li>powtarzamy 100 razy (wyścig nie zachodzi za każdym razem)</li>
     * </ol>
     * <b>Oczekiwanie:</b> wszystkie wątki dostają tę samą instancję handlera (zbiór instancji ma rozmiar 1), tę samą zwraca klient później.
     */
    @Test
    @Timeout(120)
    void lazyHandlerCreationReturnsSingleInstanceUnderContention() throws Exception {
        final int threads = 16;     // wątki wołające getResponseHeaderCaptureHandler() jednocześnie
        final int iterations = 100; // powtarzamy, bo wyścig nie zachodzi za każdym razem

        for (int it = 0; it < iterations; it++) {
            // świeży klient (bez handlera) - wyścig dotyczy pierwszego utworzenia handlera
            DefaultKsefClient freshClient = new DefaultKsefClient(HttpClient.newHttpClient(), exampleApiProperties, objectMapper);
            // bariera: wszystkie wątki wywołują metodę w tej samej chwili
            CyclicBarrier barrier = new CyclicBarrier(threads);
            // zbiór różnych instancji (porównanie po tożsamości obiektu)
            Map<ResponseHeaderCaptureHandler, Boolean> distinct = Collections.synchronizedMap(new IdentityHashMap<>());

            runConcurrently(threads, t -> {
                barrier.await(); // synchronizacja startu
                ResponseHeaderCaptureHandler handler = freshClient.getResponseHeaderCaptureHandler();
                handler.subscribe(systemWarningHeader);
                distinct.put(handler, Boolean.TRUE); // zapamiętujemy, jaką instancję dostał ten wątek
                return null;
            });

            // wszystkie wątki dostały tę samą instancję
            Assertions.assertEquals(1, distinct.size(), "utworzono więcej niż jeden handler (iteracja " + it + ")");
            // i jest to ta, którą klient zwraca również później
            Assertions.assertSame(freshClient.getResponseHeaderCaptureHandler(), distinct.keySet().iterator().next());
        }
    }

    // Testy (środowisko TEST): realne, równoległe żądania + jednoczesne odczytywanie getAndClear().
    // Każda odpowiedź, która dotarła do serwera, musi zostać zaobserwowana dokładnie raz.
    // Obciążenie jest celowo małe (limity API i circuit breaker w kliencie).

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>subskrybujemy nagłówek, ustawiamy unikalny znacznik w {@code X-Test-System-Warning}</li>
     *   <li>3 wątki wykonują po 4 realne żądania {@code getAuthChallenge()}, a wątek w tle równolegle woła {@code getAndClear()}</li>
     *   <li>po zakończeniu sprzątamy (unsubscribe, usunięcie nagłówka) i liczymy wpisy z naszym znacznikiem</li>
     * </ol>
     * <b>Oczekiwanie:</b> udane żądania &le; zaobserwowane wpisy &le; liczba prób (brak utraty i brak duplikatów);
     * gdy wszystkie 12 żądań się udało - dokładnie 12 wpisów. Obciążenie jest małe (limity API i circuit breaker); 429 są tolerowane.
     */
    @Test
    @Timeout(180)
    void concurrentRealRequestsAreCapturedExactlyOnceWhileDrained() throws Exception {
        final int threads = 3;
        final int requestsPerThread = 4;
        final int attempts = threads * requestsPerThread;
        // unikalny znacznik tego testu - odróżnia nasze wpisy od ewentualnych cudzych
        final String marker = forcedWarningMessage + "-" + java.util.UUID.randomUUID();

        ResponseHeaderCaptureHandler handler = ksefClient.getResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);
        handler.getAndClear(); // odrzucamy ewentualne wpisy pozostawione przez wcześniejsze testy
        // serwer odbije znacznik w nagłówku odpowiedzi X-System-Warning
        ksefClient.addDefaultHeader(testSystemWarningHeader, marker);

        AtomicInteger succeeded = new AtomicInteger();          // ile żądań zakończyło się sukcesem
        AtomicBoolean requestsDone = new AtomicBoolean(false); // sygnał dla wątku odczytującego
        ConcurrentLinkedQueue<Map<String, Map<String, List<String>>>> snapshots = new ConcurrentLinkedQueue<>();
        ExecutorService pool = Executors.newFixedThreadPool(threads + 1);
        try {
            // wątek odczytujący w tle: odbiera wpisy równolegle z trwającymi żądaniami
            Future<?> drainer = pool.submit(() -> {
                while (!requestsDone.get()) {
                    snapshots.add(handler.getAndClear());
                    Thread.sleep(5);
                }
                return null;
            });

            CountDownLatch start = new CountDownLatch(1);
            List<Future<?>> workers = new ArrayList<>();
            for (int t = 0; t < threads; t++) {
                workers.add(pool.submit(() -> {
                    start.await();
                    for (int i = 0; i < requestsPerThread; i++) {
                        try {
                            AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();
                            if (challenge != null && challenge.getChallenge() != null) {
                                succeeded.incrementAndGet();
                            }
                        } catch (Exception ignored) {
                            // 429 / circuit breaker - nie wpływa na kontrakt: liczymy tylko to, co faktycznie obsłużył serwer
                        }
                    }
                    return null;
                }));
            }
            start.countDown();         // START równoległych żądań
            awaitAll(workers);         // czekamy na zakończenie wszystkich żądań
            requestsDone.set(true);    // wątek odczytujący może kończyć
            drainer.get();
            snapshots.add(handler.getAndClear()); // końcowe opróżnienie
        } finally {
            // sprzątanie niezależnie od wyniku: zatrzymanie puli, wyłączenie obserwacji, usunięcie nagłówka testowego
            pool.shutdownNow();
            handler.unsubscribe(systemWarningHeader);
            handler.getAndClear();
            ksefClient.removeDefaultHeader(testSystemWarningHeader);
        }

        Set<String> keys = new HashSet<>();
        int observed = 0; // ile wpisów z naszym znacznikiem odczytano
        for (Map<String, Map<String, List<String>>> snapshot : snapshots) {
            for (Map.Entry<String, Map<String, List<String>>> e : snapshot.entrySet()) {
                List<String> values = e.getValue().get(capturedKey);
                if (values != null && values.stream().anyMatch(v -> v.contains(marker))) {
                    // ten sam klucz dwa razy = duplikat
                    Assertions.assertTrue(keys.add(e.getKey()), "ten sam wpis zaobserwowany dwukrotnie: " + e.getKey());
                    observed++;
                }
            }
        }

        // przynajmniej jedno żądanie musiało się udać
        Assertions.assertTrue(succeeded.get() > 0, "żadne żądanie nie zakończyło się sukcesem - brak podstaw do weryfikacji");
        // brak utraty: każde udane żądanie zostawiło wpis
        Assertions.assertTrue(observed >= succeeded.get(), "utracone wpisy: obserwowane=" + observed + ", udane=" + succeeded.get());
        // brak duplikatów: wpisów nie więcej niż prób
        Assertions.assertTrue(observed <= attempts, "zduplikowane wpisy: obserwowane=" + observed + ", próby=" + attempts);
        // gdy wszystkie żądania się udały, liczby muszą być równe
        if (succeeded.get() == attempts) {
            Assertions.assertEquals(attempts, observed);
        }
    }

    // Observery (ResponseHeaderObserver) - kontrakt handlera (bez sieci)

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>observer (lambda) liczący wywołania; brak subskrypcji - {@code notifyObservers}</li>
     *   <li>po {@code subscribe}: odpowiedź bez obserwowanego nagłówka, potem z nim</li>
     *   <li>wywołania z {@code null} żądaniem i {@code null} nagłówkami</li>
     * </ol>
     * <b>Oczekiwanie:</b> licznik: 0 (brak subskrypcji), 0 (obcy nagłówek), 1 (obserwowany nagłówek); {@code null}-e nie rzucają wyjątku i nie wołają observera.
     */
    @Test
    void observerIsNotifiedOnlyForResponsesWithSubscribedHeader() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        AtomicInteger calls = new AtomicInteger();
        // observer w postaci lambdy (2 argumenty) - liczy wywołania
        handler.addObserver((request, headers) -> calls.incrementAndGet());

        // brak jakiejkolwiek subskrypcji - observer nie może się uruchomić
        handler.notifyObservers(request("/a"), headers(systemWarningHeader, "w"), null);
        Assertions.assertEquals(0, calls.get());

        // po subskrypcji, ale dla odpowiedzi BEZ obserwowanego nagłówka - nadal cisza
        handler.subscribe(systemWarningHeader);
        handler.notifyObservers(request("/b"), headers("X-Other", "v"), null);
        Assertions.assertEquals(0, calls.get());

        // odpowiedź z obserwowanym nagłówkiem - dokładnie jedno wywołanie
        handler.notifyObservers(request("/c"), headers(systemWarningHeader, "w"), null);
        Assertions.assertEquals(1, calls.get());

        // null-e nie powodują wyjątku ani wywołania
        handler.notifyObservers(null, headers(systemWarningHeader, "w"), null);
        handler.notifyObservers(request("/d"), null, null);
        Assertions.assertEquals(1, calls.get());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>observer nadpisujący wariant z kluczem zapamiętuje żądanie, nagłówki i klucz</li>
     *   <li>{@code notifyObservers} z kluczem {@code "MY-KEY"}</li>
     *   <li>{@code notifyObservers} z kluczem {@code null}</li>
     * </ol>
     * <b>Oczekiwanie:</b> observer dostaje to samo żądanie i te same nagłówki (także niezasubskrybowane),
     * klucz podany przez wołającego bez zmian, a bez klucza handler generuje {@code "GET /path?x=1 :: UUID"}; wariant 2-argumentowy nie jest wołany bezpośrednio.
     */
    @Test
    void observerReceivesRequestAllHeadersAndRequestKey() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);

        // zapamiętujemy argumenty callbacku (observer w postaci klasy, żeby nadpisać wariant 3-argumentowy)
        AtomicReference<HttpRequest> seenRequest = new AtomicReference<>();
        AtomicReference<HttpHeaders> seenHeaders = new AtomicReference<>();
        AtomicReference<String> seenKey = new AtomicReference<>();
        handler.addObserver(new ResponseHeaderObserver() {
            @Override
            public void onHeadersCaptured(HttpRequest request, HttpHeaders headers) {
                // wariant 2-argumentowy jest wymagany przez interfejs, ale tu go nie używamy
                Assertions.fail("wołany powinien być wariant z kluczem");
            }

            @Override
            public void onHeadersCaptured(HttpRequest request, HttpHeaders headers, String requestKey) {
                seenRequest.set(request);
                seenHeaders.set(headers);
                seenKey.set(requestKey);
            }
        });

        HttpRequest request = request("/path?x=1");
        HttpHeaders headers = headers(Map.of(systemWarningHeader, List.of("w"), "X-Other", List.of("o")));

        // klucz podany przez wołającego jest przekazywany bez zmian
        handler.notifyObservers(request, headers, "MY-KEY");
        Assertions.assertSame(request, seenRequest.get());
        Assertions.assertSame(headers, seenHeaders.get());
        Assertions.assertEquals("MY-KEY", seenKey.get());
        // observer widzi też nagłówki niezasubskrybowane (pełne nagłówki odpowiedzi)
        Assertions.assertTrue(seenHeaders.get().firstValue("X-Other").isPresent());

        // bez klucza handler generuje go sam: "METODA /sciezka?zapytanie :: UUID"
        handler.notifyObservers(request, headers, null);
        Assertions.assertTrue(seenKey.get().startsWith("GET /path?x=1 :: "), "klucz: " + seenKey.get());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>jak klient: {@code createRequestKey}, potem {@code capture(.., key)} i {@code notifyObservers(.., key)}</li>
     *   <li>odczytujemy bufor przez {@code getAndClear()}</li>
     * </ol>
     * <b>Oczekiwanie:</b> klucz przekazany observerowi jest identyczny z kluczem jedynego wpisu w buforze - callback da się skorelować z wpisem.
     */
    @Test
    void observerKeyEqualsBufferedEntryKeyWhenSharedByCaller() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);
        AtomicReference<String> observerKey = new AtomicReference<>();
        handler.addObserver(new ResponseHeaderObserver() {
            @Override
            public void onHeadersCaptured(HttpRequest request, HttpHeaders headers) {
                // nieużywany - interesuje nas wariant z kluczem
            }

            @Override
            public void onHeadersCaptured(HttpRequest request, HttpHeaders headers, String requestKey) {
                observerKey.set(requestKey);
            }
        });

        HttpRequest request = request("/corr");
        HttpHeaders headers = headers(systemWarningHeader, "w");
        // tak postępuje klient: jeden klucz na wywołanie, przekazany do bufora i do observerów
        String key = handler.createRequestKey(request);
        handler.capture(request, headers, key);
        handler.notifyObservers(request, headers, key);

        // klucz observera == klucz jedynego wpisu w buforze
        Assertions.assertEquals(key, observerKey.get());
        Assertions.assertEquals(Set.of(key), handler.getAndClear().keySet());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>rejestrujemy observera, który zawsze rzuca {@code IllegalStateException}, a po nim prawidłowego observera</li>
     *   <li>{@code notifyObservers} z obserwowanym nagłówkiem</li>
     * </ol>
     * <b>Oczekiwanie:</b> wyjątek nie dociera do wołającego, a drugi observer mimo to zostaje wywołany raz.
     */
    @Test
    void observerExceptionIsIsolatedAndOtherObserversStillRun() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);
        AtomicInteger healthyCalls = new AtomicInteger();

        // pierwszy observer zawsze rzuca wyjątek...
        handler.addObserver((request, headers) -> {
            throw new IllegalStateException("celowy błąd observera");
        });
        // ...a drugi, zarejestrowany po nim, musi mimo to zostać wywołany
        handler.addObserver((request, headers) -> healthyCalls.incrementAndGet());

        // notyfikacja nie może rzucić wyjątku do wołającego (klient nie może się wywrócić przez observera)
        Assertions.assertDoesNotThrow(() -> handler.notifyObservers(request("/x"), headers(systemWarningHeader, "w"), null));
        Assertions.assertEquals(1, healthyCalls.get());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>dodajemy ten sam obiekt dwa razy i wołamy notyfikację</li>
     *   <li>{@code removeObserver} i kolejna notyfikacja; usunięcie nieznanego observera; {@code null} w {@code addObserver}/{@code removeObserver}</li>
     * </ol>
     * <b>Oczekiwanie:</b> po podwójnej rejestracji observer jest wołany raz; po usunięciu wcale; usunięcie nieznanego nie jest błędem; {@code null} daje {@code NullPointerException}.
     */
    @Test
    void addObserverIsIdempotentRemovableAndRejectsNull() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);
        AtomicInteger calls = new AtomicInteger();
        ResponseHeaderObserver observer = (request, headers) -> calls.incrementAndGet();

        // ten sam obiekt dodany dwa razy - wołany tylko raz na odpowiedź
        handler.addObserver(observer);
        handler.addObserver(observer);
        handler.notifyObservers(request("/1"), headers(systemWarningHeader, "w"), null);
        Assertions.assertEquals(1, calls.get());

        // po wyrejestrowaniu observer nie jest już wołany
        handler.removeObserver(observer);
        handler.notifyObservers(request("/2"), headers(systemWarningHeader, "w"), null);
        Assertions.assertEquals(1, calls.get());

        // wyrejestrowanie nieznanego observera nie jest błędem
        Assertions.assertDoesNotThrow(() -> handler.removeObserver(observer));
        // null jest odrzucany od razu (a nie dopiero przy pierwszym wywołaniu)
        Assertions.assertThrows(NullPointerException.class, () -> handler.addObserver(null));
        Assertions.assertThrows(NullPointerException.class, () -> handler.removeObserver(null));
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>bufor domyślnie włączony; {@code setBufferingEnabled(false)}</li>
     *   <li>{@code capture} i {@code notifyObservers}</li>
     *   <li>{@code setBufferingEnabled(true)} i kolejny {@code capture}</li>
     * </ol>
     * <b>Oczekiwanie:</b> przy wyłączonym buforze {@code capture} nic nie zapisuje ({@code size()==0}, {@code getAndClear()} pusty),
     * a observer nadal jest wołany; po ponownym włączeniu bufor znów zapisuje.
     */
    @Test
    void disablingBufferingStopsStoringButObserversStillWork() {
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);
        AtomicInteger calls = new AtomicInteger();
        handler.addObserver((request, headers) -> calls.incrementAndGet());

        // domyślnie bufor jest włączony (zgodność wsteczna)
        Assertions.assertTrue(handler.isBufferingEnabled());
        handler.setBufferingEnabled(false);

        // capture nic nie zapisuje...
        handler.capture(request("/off"), headers(systemWarningHeader, "w"));
        Assertions.assertEquals(0, handler.size());
        Assertions.assertTrue(handler.getAndClear().isEmpty());
        // ...ale observer nadal jest wołany
        handler.notifyObservers(request("/off"), headers(systemWarningHeader, "w"), null);
        Assertions.assertEquals(1, calls.get());

        // po ponownym włączeniu bufor znów działa
        handler.setBufferingEnabled(true);
        handler.capture(request("/on"), headers(systemWarningHeader, "w"));
        Assertions.assertEquals(1, handler.size());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>stały observer liczy notyfikacje; 4 wątki wołają po 20000 razy {@code notifyObservers}</li>
     *   <li>równolegle inny wątek w pętli dodaje i usuwa innego observera</li>
     * </ol>
     * <b>Oczekiwanie:</b> brak wyjątków, a stały observer dostaje każdą notyfikację od każdego wątku (4 &times; 20000).
     */
    @Test
    @Timeout(60)
    void observerRegistrationConcurrentWithNotificationIsSafe() throws Exception {
        final int notifiers = 4;
        final int perNotifier = 20_000;
        ResponseHeaderCaptureHandler handler = new ResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);

        AtomicInteger stableCalls = new AtomicInteger();
        // stały observer - zarejestrowany przez cały test
        handler.addObserver((request, headers) -> stableCalls.incrementAndGet());
        AtomicBoolean notifiersDone = new AtomicBoolean(false);
        ExecutorService pool = Executors.newFixedThreadPool(notifiers + 1);
        try {
            // wątek ciągle dodający i usuwający inny observer
            Future<?> toggler = pool.submit(() -> {
                ResponseHeaderObserver transientObserver = (request, headers) -> {
                };
                while (!notifiersDone.get()) {
                    handler.addObserver(transientObserver);
                    handler.removeObserver(transientObserver);
                }
            });
            List<Future<?>> notifierFutures = new ArrayList<>();
            for (int n = 0; n < notifiers; n++) {
                notifierFutures.add(pool.submit(() -> {
                    HttpRequest request = request("/n");
                    HttpHeaders headers = headers(systemWarningHeader, "w");
                    for (int i = 0; i < perNotifier; i++) {
                        handler.notifyObservers(request, headers, null);
                    }
                    return null;
                }));
            }
            awaitAll(notifierFutures);
            notifiersDone.set(true);
            toggler.get(); // propaguje ewentualny wyjątek z wątku przełączającego
        } finally {
            pool.shutdownNow();
        }
        // stały observer dostał każdą notyfikację od każdego wątku
        Assertions.assertEquals(notifiers * perNotifier, stableCalls.get());
    }

    // Observery na prawdziwym kliencie - korelacja per-call

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>observer rejestrowany na kliencie odczytuje {@code ThreadLocal} z "kontekstem biznesowym" i sprawdza nazwę wątku</li>
     *   <li>3 wątki, każdy z własnym kontekstem ({@code context-0..2}), wykonują po 3 żądania {@code getAuthChallenge()}</li>
     *   <li>po zakończeniu porównujemy liczbę callbacków z liczbą udanych żądań w każdym kontekście</li>
     * </ol>
     * <b>Oczekiwanie:</b> każdy callback wykonał się w wątku wywołującym
     * (kontekst widoczny, wątek nie jest wątkiem {@code HttpClient}); callbacków w kontekście jest co najmniej tyle, ile udanych żądań tego wątku,
     * i nie więcej niż żądań tego wątku (brak przecieku między wątkami).
     */
    @Test
    @Timeout(180)
    void observerRunsInCallingThreadAndSeesItsOwnThreadLocalContext() throws Exception {
        final int threads = 3;
        final int requestsPerThread = 3;
        final String marker = forcedWarningMessage + "-" + java.util.UUID.randomUUID();

        // "kontekst biznesowy" aplikacji, np. identyfikator paczki - ustawiany per wątek
        ThreadLocal<String> context = new ThreadLocal<>();
        // ile callbacków trafiło do którego kontekstu
        Map<String, AtomicInteger> callbacksPerContext = new ConcurrentHashMap<>();
        // ile żądań każdy kontekst wykonał z sukcesem
        Map<String, AtomicInteger> succeededPerContext = new ConcurrentHashMap<>();
        // wszystko, co poszło nie tak wewnątrz callbacku (brak kontekstu = zły wątek)
        ConcurrentLinkedQueue<String> problems = new ConcurrentLinkedQueue<>();

        ResponseHeaderObserver observer = (request, headers) -> {
            String ctx = context.get();
            String threadName = Thread.currentThread().getName();
            if (ctx == null) {
                // callback poza wątkiem biznesowym (np. wątek roboczy HttpClient) - nie widzi naszego ThreadLocal
                problems.add("brak kontekstu w wątku " + threadName);
            } else if (threadName.startsWith("HttpClient-")) {
                problems.add("callback w wątku HttpClient: " + threadName);
            } else {
                callbacksPerContext.computeIfAbsent(ctx, k -> new AtomicInteger()).incrementAndGet();
            }
        };

        ResponseHeaderCaptureHandler handler = ksefClient.getResponseHeaderCaptureHandler();
        handler.subscribe(systemWarningHeader);
        handler.addObserver(observer);
        ksefClient.addDefaultHeader(testSystemWarningHeader, marker);

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            CountDownLatch start = new CountDownLatch(1);
            List<Future<?>> workers = new ArrayList<>();
            for (int t = 0; t < threads; t++) {
                final String ctx = "context-" + t;
                workers.add(pool.submit(() -> {
                    // ustawiamy kontekst biznesowy TEGO wątku
                    context.set(ctx);
                    try {
                        start.await();
                        for (int i = 0; i < requestsPerThread; i++) {
                            try {
                                AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();
                                if (challenge != null && challenge.getChallenge() != null) {
                                    succeededPerContext.computeIfAbsent(ctx, k -> new AtomicInteger()).incrementAndGet();
                                }
                            } catch (Exception ignored) {
                                // 429 / circuit breaker - liczymy tylko udane odpowiedzi
                            }
                        }
                    } finally {
                        // sprzątamy ThreadLocal (wątek wraca do puli)
                        context.remove();
                    }
                    return null;
                }));
            }
            start.countDown();
            awaitAll(workers);
        } finally {
            // sprzątanie niezależnie od wyniku testu
            pool.shutdownNow();
            handler.removeObserver(observer);
            handler.unsubscribe(systemWarningHeader);
            handler.getAndClear();
            ksefClient.removeDefaultHeader(testSystemWarningHeader);
        }

        // żaden callback nie wykonał się w złym wątku
        Assertions.assertTrue(problems.isEmpty(), "callbacki w złym wątku: " + problems);
        int totalSucceeded = succeededPerContext.values().stream().mapToInt(AtomicInteger::get).sum();
        Assertions.assertTrue(totalSucceeded > 0, "żadne żądanie nie zakończyło się sukcesem - brak podstaw do weryfikacji");
        for (Map.Entry<String, AtomicInteger> e : succeededPerContext.entrySet()) {
            int callbacks = callbacksPerContext.getOrDefault(e.getKey(), new AtomicInteger()).get();
            // każda udana odpowiedź tego wątku wywołała callback w TYM wątku (nic nie zginęło)...
            Assertions.assertTrue(callbacks >= e.getValue().get(),
                    e.getKey() + ": callbacki=" + callbacks + ", udane=" + e.getValue().get());
            // ...i nie więcej niż żądań wykonanych przez ten wątek (brak przecieku z innych wątków)
            Assertions.assertTrue(callbacks <= requestsPerThread,
                    e.getKey() + ": za dużo callbacków (" + callbacks + ") - przeciek z innych wątków");
        }
        // callbacki wystąpiły wyłącznie w znanych kontekstach
        Assertions.assertTrue(succeededPerContext.keySet().containsAll(callbacksPerContext.keySet()));
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>rejestrujemy observera rzucającego wyjątek i prawidłowego, liczącego wywołania</li>
     *   <li>wykonujemy {@code getAuthChallenge()}</li>
     *   <li>wyrejestrowujemy zdrowego observera i wykonujemy kolejne żądanie</li>
     * </ol>
     * <b>Oczekiwanie:</b> wywołanie API kończy się sukcesem mimo wyjątku w observerze; zdrowy observer został wywołany dokładnie raz; po wyrejestrowaniu licznik się nie zmienia.
     */
    @Test
    @Timeout(60)
    void observerFailureDoesNotBreakApiCallAndRemovedObserverIsSilent() throws Exception {
        final String marker = forcedWarningMessage + "-" + java.util.UUID.randomUUID();
        ResponseHeaderCaptureHandler handler = ksefClient.getResponseHeaderCaptureHandler();
        AtomicInteger healthyCalls = new AtomicInteger();
        // observer, który zawsze rzuca wyjątek
        ResponseHeaderObserver failing = (request, headers) -> {
            throw new IllegalStateException("celowy błąd observera");
        };
        ResponseHeaderObserver healthy = (request, headers) -> healthyCalls.incrementAndGet();

        handler.subscribe(systemWarningHeader);
        handler.addObserver(failing);
        handler.addObserver(healthy);
        ksefClient.addDefaultHeader(testSystemWarningHeader, marker);
        try {
            // wywołanie API musi się udać mimo wyjątku w observerze
            AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();
            Assertions.assertNotNull(challenge);
            Assertions.assertNotNull(challenge.getChallenge());
            // zdrowy observer został wywołany dokładnie raz
            Assertions.assertEquals(1, healthyCalls.get());

            // po wyrejestrowaniu kolejne wywołanie nie uruchamia już observera
            handler.removeObserver(healthy);
            Assertions.assertNotNull(ksefClient.getAuthChallenge());
            Assertions.assertEquals(1, healthyCalls.get());
        } finally {
            handler.removeObserver(failing);
            handler.removeObserver(healthy);
            handler.unsubscribe(systemWarningHeader);
            handler.getAndClear();
            ksefClient.removeDefaultHeader(testSystemWarningHeader);
        }
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>observer zapamiętuje żądanie i klucz wywołania</li>
     *   <li>wykonujemy jedno {@code getAuthChallenge()} i odbieramy bufor przez {@code getAndClear()}</li>
     * </ol>
     * <b>Oczekiwanie:</b> observer został wywołany; klucz, który dostał, istnieje w buforze jako wpis tego samego wywołania, a zaczyna się od metody i ścieżki żądania.
     */
    @Test
    @Timeout(60)
    void observerRequestKeyCorrelatesWithBufferedEntryOfTheSameCall() throws Exception {
        final String marker = forcedWarningMessage + "-" + java.util.UUID.randomUUID();
        ResponseHeaderCaptureHandler handler = ksefClient.getResponseHeaderCaptureHandler();
        AtomicReference<String> observedKey = new AtomicReference<>();
        AtomicReference<HttpRequest> observedRequest = new AtomicReference<>();
        ResponseHeaderObserver observer = new ResponseHeaderObserver() {
            @Override
            public void onHeadersCaptured(HttpRequest request, HttpHeaders headers) {
                // nieużywany - interesuje nas wariant z kluczem
            }

            @Override
            public void onHeadersCaptured(HttpRequest request, HttpHeaders headers, String requestKey) {
                observedRequest.set(request);
                observedKey.set(requestKey);
            }
        };

        handler.subscribe(systemWarningHeader);
        handler.getAndClear(); // odrzucamy ewentualne wpisy z wcześniejszych testów
        handler.addObserver(observer);
        ksefClient.addDefaultHeader(testSystemWarningHeader, marker);
        Map<String, Map<String, List<String>>> buffered;
        try {
            Assertions.assertNotNull(ksefClient.getAuthChallenge());
            // odbieramy bufor po jednym wywołaniu
            buffered = handler.getAndClear();
        } finally {
            handler.removeObserver(observer);
            handler.unsubscribe(systemWarningHeader);
            ksefClient.removeDefaultHeader(testSystemWarningHeader);
        }

        // observer dostał klucz...
        Assertions.assertNotNull(observedKey.get(), "observer nie został wywołany");
        // ...który istnieje w buforze, jako wpis tego samego wywołania
        Assertions.assertTrue(buffered.containsKey(observedKey.get()), "brak wpisu o kluczu observera: " + observedKey.get());
        // klucz opisuje to żądanie: "METODA /sciezka :: UUID"
        Assertions.assertTrue(observedKey.get().startsWith(observedRequest.get().method() + " " + observedRequest.get().uri().getPath()),
                "klucz: " + observedKey.get());
    }

    /**
     * <b>Scenariusz:</b>
     * <ol>
     *   <li>tworzymy świeżego klienta konstruktorem z observerem i konfiguracją {@code enabled=false}</li>
     *   <li>wykonujemy {@code getAuthChallenge()} (nagłówek testowy ustawiony na tym kliencie)</li>
     *   <li>sprawdzamy licznik observera oraz stan bufora</li>
     * </ol>
     * <b>Oczekiwanie:</b> observer został wywołany raz mimo {@code enabled=false};
     * bufor jest wyłączony ({@code isBufferingEnabled()==false}) i pusty - nic nie jest trzymane w pamięci.
     */
    @Test
    @Timeout(60)
    void observerPassedToConstructorWorksWithBufferDisabled() throws Exception {
        final String marker = forcedWarningMessage + "-" + java.util.UUID.randomUUID();
        AtomicInteger calls = new AtomicInteger();
        // konfiguracja "enabled=false": bufor wyłączony, nagłówek do obserwacji = X-System-Warning
        ResponseHeaderObservationProperties disabledBufferProperties = new ResponseHeaderObservationProperties() {
            @Override
            public ResponseHeaderObservationOptions getResponseHeaderObservationOptions() {
                return new ResponseHeaderObservationOptions(false, new HashSet<>(Set.of(systemWarningHeader)));
            }
        };

        // świeży klient z observerem w konstruktorze (obserwator -> handler tworzony od razu)
        DefaultKsefClient clientWithObserver = new DefaultKsefClient(HttpClient.newHttpClient(), exampleApiProperties,
                null, disabledBufferProperties, (request, headers) -> calls.incrementAndGet(), objectMapper);
        clientWithObserver.addDefaultHeader(testSystemWarningHeader, marker);
        try {
            Assertions.assertNotNull(clientWithObserver.getAuthChallenge());
        } finally {
            clientWithObserver.removeDefaultHeader(testSystemWarningHeader);
        }

        // observer został wywołany...
        Assertions.assertEquals(1, calls.get());
        // ...a bufor jest wyłączony i pusty (nic nie jest trzymane w pamięci)
        Assertions.assertFalse(clientWithObserver.getResponseHeaderCaptureHandler().isBufferingEnabled());
        Assertions.assertTrue(clientWithObserver.getResponseHeaderCaptureHandler().getAndClear().isEmpty());
    }

    // Unikalna wartość nagłówka dla producenta i numeru wpisu, np. "p3-17".
    private static String value(int producer, int i) {
        return "p" + producer + "-" + i;
    }

    // Wszystkie wartości obserwowanego nagłówka ze wszystkich wpisów migawki.
    private static Set<String> valuesOf(Map<String, Map<String, List<String>>> captured) {
        Set<String> values = new HashSet<>();
        captured.values().forEach(v -> values.addAll(v.get(capturedKey)));
        return values;
    }

    // Żądanie GET do adresu lokalnego - tylko po to, żeby handler miał z czego zbudować klucz (nic nie jest wysyłane).
    private static HttpRequest request(String pathAndQuery) {
        return HttpRequest.newBuilder(URI.create("https://localhost" + pathAndQuery)).GET().build();
    }

    // Nagłówki odpowiedzi z jednym nagłówkiem i jedną wartością.
    private static HttpHeaders headers(String name, String value) {
        return headers(Map.of(name, List.of(value)));
    }

    // Nagłówki odpowiedzi zbudowane z mapy (filtr akceptujący wszystko).
    private static HttpHeaders headers(Map<String, List<String>> map) {
        return HttpHeaders.of(map, (k, v) -> true);
    }

    // Czeka na zakończenie wszystkich zadań; get() ponownie rzuca wyjątek, który wystąpił w wątku roboczym.
    private static void awaitAll(List<Future<?>> futures) throws Exception {
        for (Future<?> f : futures) {
            f.get();
        }
    }

    // Uruchamia to samo zadanie równolegle w {@code threads} wątkach (z numerem wątku jako argumentem)
    // i czeka na wszystkie; wyjątek z dowolnego wątku powoduje błąd testu.
    private static void runConcurrently(int threads, ThrowingIntTask task) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (int t = 0; t < threads; t++) {
                final int index = t;
                futures.add(pool.submit((Callable<Void>) () -> {
                    task.run(index);
                    return null;
                }));
            }
            awaitAll(futures);
        } finally {
            pool.shutdownNow();
        }
    }

    // Zadanie przyjmujące numer wątku i mogące rzucać wyjątki sprawdzane.
    @FunctionalInterface
    private interface ThrowingIntTask {
        Object run(int index) throws Exception;
    }
}
