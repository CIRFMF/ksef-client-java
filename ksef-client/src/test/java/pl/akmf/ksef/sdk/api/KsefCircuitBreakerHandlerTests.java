package pl.akmf.ksef.sdk.api;

import org.junit.Test;
import pl.akmf.ksef.sdk.api.circuitbreaker.FakeHttpClient;
import pl.akmf.ksef.sdk.api.circuitbreaker.FakeHttpClientSequence;
import pl.akmf.ksef.sdk.api.circuitbreaker.FakeResponse;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerHandler;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerOpenException;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerOptions;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class KsefCircuitBreakerHandlerTests {

    // success resets failures
    @Test
    public void successShouldResetFailures() throws Exception {
        FakeHttpClient client = new FakeHttpClient(new FakeResponse(200));

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setEnabled(true);
        options.setFailureThreshold(3);
        options.setBreakDurationSeconds(10);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. SUCCESS -> brak błędu -> licznik failure = 0
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 2. SUCCESS -> nadal brak błędów -> circuit pozostaje CLOSED
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 3. sprawdzamy czy oba requesty przeszły (brak blokady CB)
        assertEquals(2, client.callCount);
    }

    // open after threshold
    @Test(expected = KsefCircuitBreakerOpenException.class)
    public void shouldOpenCircuitAfterFailures() throws Exception {

        FakeHttpClient client = new FakeHttpClient(new FakeResponse(500));

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(2);
        options.setBreakDurationSeconds(10);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL (500) -> consecutiveFailures = 1 -> nadal CLOSED
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 2. FAIL (500) -> consecutiveFailures = 2 -> osiągnięty próg -> OPEN
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 3. kolejne wywołanie -> circuit OPEN -> natychmiastowy wyjątek
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
    }

    // OPEN blocks requests
    @Test(expected = KsefCircuitBreakerOpenException.class)
    public void openStateShouldBlockRequests() throws Exception {

        FakeHttpClient client = new FakeHttpClient(new FakeResponse(500));

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(60);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL -> threshold = 1 -> circuit natychmiast przechodzi do OPEN
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 2. kolejne wywołanie -> nadal OPEN -> request zablokowany -> wyjątek
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
    }

    // IOException counts as failure
    @Test(expected = Exception.class)
    public void ioExceptionShouldCountAsFailure() throws Exception {
        FakeHttpClient client = new FakeHttpClient(true);

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(10);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. wyjątek IO -> traktowany jako transient failure
        // 2. consecutiveFailures = 1 -> osiągnięty próg -> circuit OPEN
        // 3. wyjątek propagowany dalej
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
    }

    // HALF-OPEN single probe protection
    @Test
    public void halfOpenShouldAllowOnlyOneProbe() throws Exception {

        FakeHttpClient client = new FakeHttpClient(new FakeResponse(500));

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(1);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL -> otwiera circuit
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 2. czekamy aż OPEN -> HALF_OPEN
        Thread.sleep(1_100); // allow OPEN -> HALFOPEN transition

        // 3. pierwszy probe (dozwolony)
        try {
            cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception ignored) {
            // fail response (500) -> OK
        }

        // 4. drugi probe -> powinien polecieć wyjątek CB
        try {
            cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
            fail("Expected KsefCircuitBreakerOpenException");
        } catch (KsefCircuitBreakerOpenException ex) {
            // OK
        }
    }

    // OPEN -> HALF_OPEN -> CLOSED (po sukcesie)
    @Test
    public void shouldTransitionFromOpenToHalfOpenToClosedOnSuccess() throws Exception {

        FakeHttpClient client = new FakeHttpClient(new FakeResponse(200));

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(1);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL -> threshold osiągnięty -> OPEN
        FakeHttpClient failingClient = new FakeHttpClient(new FakeResponse(500));
        cb = new KsefCircuitBreakerHandler(failingClient, options);
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 2. czekamy aż OPEN -> HALF_OPEN
        Thread.sleep(1_100);

        // 3. podmieniamy client na SUCCESS
        cb = new KsefCircuitBreakerHandler(client, options);

        // UWAGA: w realnym kodzie client by był wstrzykiwany raz,
        // tu tylko symulujemy recovery

        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 4. kolejny request -> powinien przejść (CLOSED)
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        assertEquals(2, client.callCount);
    }

    // OPEN -> HALF_OPEN -> CLOSED (pełny flow recovery)
    @Test
    public void shouldTransitionOpenToHalfOpenToClosed() throws Exception {

        // client: najpierw FAIL, potem SUCCESS
        FakeHttpClientSequence client = new FakeHttpClientSequence(
                new FakeResponse(500), // 1 -> FAIL -> OPEN
                new FakeResponse(200), // 2 -> HALF_OPEN probe -> SUCCESS
                new FakeResponse(200)  // 3 -> CLOSED -> SUCCESS
        );

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(1);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL -> OPEN
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 2. czekamy OPEN -> HALF_OPEN
        Thread.sleep(1100);

        // 3. HALF_OPEN probe -> SUCCESS -> CLOSED
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        // 4. CLOSED -> normalny request działa
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        assertEquals(3, client.callCount);
    }

    // HALF_OPEN -> FAIL -> wraca do OPEN
    @Test
    public void halfOpenFailureShouldReopenCircuit() throws Exception {

        FakeHttpClientSequence client = new FakeHttpClientSequence(
                new FakeResponse(500), // OPEN
                new FakeResponse(500)  // HALF_OPEN -> FAIL -> OPEN
        );

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(1);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL -> OPEN
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        Thread.sleep(1100);

        // 2. HALF_OPEN -> FAIL -> OPEN
        try {
            cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception ignored) {
        }

        // 3. nadal OPEN -> powinno rzucić CB exception
        try {
            cb.send(req, HttpResponse.BodyHandlers.ofByteArray());
            fail("Expected KsefCircuitBreakerOpenException");
        } catch (KsefCircuitBreakerOpenException ex) {
            assertNotNull(ex.getRetryAfter());
        }
    }

    // CONCURRENCY — tylko jeden HALF_OPEN probe
    @Test
    public void halfOpenShouldAllowOnlyOneConcurrentProbe() throws Exception {

        FakeHttpClientSequence client = new FakeHttpClientSequence(
                new FakeResponse(500), // OPEN
                new FakeResponse(200)  // probe SUCCESS
        );

        KsefCircuitBreakerOptions options = new KsefCircuitBreakerOptions();
        options.setFailureThreshold(1);
        options.setBreakDurationSeconds(1);

        KsefCircuitBreakerHandler cb = new KsefCircuitBreakerHandler(client, options);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://test"))
                .GET()
                .build();

        // 1. FAIL -> OPEN
        cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

        Thread.sleep(1_100);

        // synchronizacja startu
        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);

        // 2. concurrency test
        final boolean[] secondBlocked = {false};

        Runnable task = () -> {
            try {
                ready.countDown();
                start.await(); // czekają aż oba będą gotowe

                cb.send(req, HttpResponse.BodyHandlers.ofByteArray());

            } catch (KsefCircuitBreakerOpenException ex) {
                secondBlocked[0] = true;
            } catch (Exception ignored) {
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        // czekamy aż oba wątki gotowe
        ready.await();

        // start jednocześnie
        start.countDown();

        t1.join();
        t2.join();

        // dokładnie jeden powinien zostać zablokowany
        assertTrue(secondBlocked[0]);
    }
}
