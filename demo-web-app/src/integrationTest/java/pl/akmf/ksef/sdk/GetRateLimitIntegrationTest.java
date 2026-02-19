package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.ExceptionDetails;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.limit.BatchSessionLimit;
import pl.akmf.ksef.sdk.client.model.limit.BatchSessionRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.CertificateLimit;
import pl.akmf.ksef.sdk.client.model.limit.ChangeContextLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.ChangeSubjectCertificateLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.EffectiveApiRateLimits;
import pl.akmf.ksef.sdk.client.model.limit.EnrollmentLimit;
import pl.akmf.ksef.sdk.client.model.limit.GetContextLimitResponse;
import pl.akmf.ksef.sdk.client.model.limit.GetRateLimitResponse;
import pl.akmf.ksef.sdk.client.model.limit.GetSubjectLimitResponse;
import pl.akmf.ksef.sdk.client.model.limit.InvoiceDownloadRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.InvoiceExportRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.InvoiceMetadataRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.InvoiceSendRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.InvoiceStatusRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.OnlineSessionLimit;
import pl.akmf.ksef.sdk.client.model.limit.OnlineSessionRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.OtherRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.SessionInvoiceListRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.SessionListRateLimit;
import pl.akmf.ksef.sdk.client.model.limit.SessionMiscRateLimits;
import pl.akmf.ksef.sdk.client.model.limit.SetRateLimitsRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;

class GetRateLimitIntegrationTest extends BaseIntegrationTest {

    @Test
    void getRateLimitIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        GetRateLimitResponse getRateLimitResponse = ksefClient.getRateLimit(accessToken);

        Assertions.assertNotNull(getRateLimitResponse);
    }

    // Sprawdzenie dostępnych limitów w kontekście bieżącej sesji.
    @Test
    void currentSessionLimitsPositive() throws JAXBException, IOException, ApiException {
        int limitsChangeValue = 4;

        // 1. Uwierzytelnianie i uzyskanie tokenu dostępu
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // 2. Pobranie limitów bieżącego kontekstu sesji
        GetContextLimitResponse limitsForContext = ksefClient.getContextSessionLimit(accessToken);

        // 3. Sprawdzenie czy limity są większe od zera
        Assertions.assertNotNull(limitsForContext);
        Assertions.assertTrue(limitsForContext.getOnlineSession().getMaxInvoices() > 0);
        Assertions.assertTrue(limitsForContext.getOnlineSession().getMaxInvoiceSizeInMB() > 0);
        Assertions.assertTrue(limitsForContext.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB() > 0);

        Assertions.assertTrue(limitsForContext.getBatchSession().getMaxInvoices() > 0);
        Assertions.assertTrue(limitsForContext.getBatchSession().getMaxInvoiceSizeInMB() > 0);
        Assertions.assertTrue(limitsForContext.getBatchSession().getMaxInvoiceWithAttachmentSizeInMB() > 0);

        // 4. Zmiana limitów bieżącego kontekstu sesji
        ChangeContextLimitRequest newLimits = new ChangeContextLimitRequest();
        OnlineSessionLimit onlineSession = new OnlineSessionLimit();
        onlineSession.setMaxInvoices(limitsForContext.getOnlineSession().getMaxInvoices() + limitsChangeValue);
        onlineSession.setMaxInvoiceSizeInMB(limitsForContext.getOnlineSession().getMaxInvoiceSizeInMB() + limitsChangeValue);
        onlineSession.setMaxInvoiceWithAttachmentSizeInMB(limitsForContext.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB() + limitsChangeValue);
        newLimits.setOnlineSession(onlineSession);
        BatchSessionLimit batchSession = new BatchSessionLimit();
        batchSession.setMaxInvoices(limitsForContext.getBatchSession().getMaxInvoices() + limitsChangeValue);
        batchSession.setMaxInvoiceSizeInMB(limitsForContext.getBatchSession().getMaxInvoiceSizeInMB() + limitsChangeValue);
        batchSession.setMaxInvoiceWithAttachmentSizeInMB(limitsForContext.getBatchSession().getMaxInvoiceWithAttachmentSizeInMB() + limitsChangeValue);
        newLimits.setBatchSession(batchSession);

        ksefClient.changeContextLimitTest(newLimits, accessToken);

        // 5. Sprawdzenie czy limity zostały zmienione
        limitsForContext = ksefClient.getContextSessionLimit(accessToken);

        Assertions.assertEquals(limitsForContext.getOnlineSession().getMaxInvoices(), newLimits.getOnlineSession().getMaxInvoices());
        Assertions.assertEquals(limitsForContext.getOnlineSession().getMaxInvoiceSizeInMB(), newLimits.getOnlineSession().getMaxInvoiceSizeInMB());
        Assertions.assertEquals(limitsForContext.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB(), newLimits.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB());
        Assertions.assertNotNull(limitsForContext.getBatchSession());

        // 6. Przywrócenie oryginalnych limitów bieżącego kontekstu sesji
        ksefClient.resetContextLimitTest(accessToken);

        // 7. Sprawdzenie czy oryginalne limity zostały przywrócone
        limitsForContext = ksefClient.getContextSessionLimit(accessToken);

        Assertions.assertEquals(limitsForContext.getOnlineSession().getMaxInvoices(), newLimits.getOnlineSession().getMaxInvoices() - limitsChangeValue);
        Assertions.assertEquals(limitsForContext.getOnlineSession().getMaxInvoiceSizeInMB(), newLimits.getOnlineSession().getMaxInvoiceSizeInMB() - limitsChangeValue);
        Assertions.assertEquals(limitsForContext.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB(), newLimits.getOnlineSession().getMaxInvoiceWithAttachmentSizeInMB() - limitsChangeValue);
        Assertions.assertNotNull(limitsForContext.getBatchSession());
    }

    // Sprawdzenie dostępnych limitów certyfikatów bieżącego podmiotu.
    @Test
    void certificatesLimitsPositive() throws JAXBException, IOException, ApiException {
        int limitsChangeValue = 4;

        // 1. Uwierzytelnianie i uzyskanie tokenu dostępu
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // 2. Pobranie limitów bieżącego podmiotu
        GetSubjectLimitResponse limitsForSubject = ksefClient.getSubjectCertificateLimit(accessToken);

        Assertions.assertNotNull(limitsForSubject);
        Assertions.assertTrue(limitsForSubject.getCertificate().getMaxCertificates() > 0);
        Assertions.assertTrue(limitsForSubject.getEnrollment().getMaxEnrollments() > 0);

        // 3. Zmiana limitów
        ChangeSubjectCertificateLimitRequest newCertificateLimitsForSubject = new ChangeSubjectCertificateLimitRequest();
        newCertificateLimitsForSubject.setSubjectIdentifierType(ChangeSubjectCertificateLimitRequest.SubjectType.NIP);
        newCertificateLimitsForSubject.setCertificate(new CertificateLimit(limitsForSubject.getCertificate().getMaxCertificates() + limitsChangeValue));
        newCertificateLimitsForSubject.setEnrollment(new EnrollmentLimit(limitsForSubject.getEnrollment().getMaxEnrollments() + limitsChangeValue));

        ksefClient.changeSubjectLimitTest(newCertificateLimitsForSubject, accessToken);

        // 4. Pobranie aktualnych limitów bieżącego podmiotu
        limitsForSubject = ksefClient.getSubjectCertificateLimit(accessToken);

        // 5. Sprawdzenie czy limity bieżącego podmiotu zostały zmienione
        Assertions.assertEquals(limitsForSubject.getCertificate().getMaxCertificates(), newCertificateLimitsForSubject.getCertificate().getMaxCertificates());
        Assertions.assertEquals(limitsForSubject.getEnrollment().getMaxEnrollments(), newCertificateLimitsForSubject.getEnrollment().getMaxEnrollments());

        // 6. Przywrócenie oryginalnych limitów bieżącego podmiotu
        ksefClient.resetSubjectCertificateLimit(accessToken);

        // 7. Pobranie aktualnych limitów bieżącego podmiotu
        limitsForSubject = ksefClient.getSubjectCertificateLimit(accessToken);

        // 8. Sprawdzenie czy oryginalne limity bieżącego podmiotu zostały przywrócone
        Assertions.assertEquals(limitsForSubject.getCertificate().getMaxCertificates(), newCertificateLimitsForSubject.getCertificate().getMaxCertificates() - limitsChangeValue);
        Assertions.assertEquals(limitsForSubject.getEnrollment().getMaxEnrollments(), newCertificateLimitsForSubject.getEnrollment().getMaxEnrollments() - limitsChangeValue);
    }

    // Maksymalne wartości (min zawsze 1) zgodne z walidacją API
    RateMax onlineSessionMax = new RateMax(10, 30, 120);
    RateMax batchSessionMax = new RateMax(10, 20, 120);
    RateMax invoiceSendMax = new RateMax(10, 30, 180);
    RateMax invoiceStatusMax = new RateMax(30, 120, 720);
    RateMax sessionListMax = new RateMax(5, 10, 60);
    RateMax sessionInvoiceListMax = new RateMax(10, 20, 200);
    RateMax sessionMiscMax = new RateMax(10, 120, 720);
    RateMax invoiceMetadataMax = new RateMax(8, 16, 20);
    RateMax invoiceExportMax = new RateMax(4, 8, 20);
    RateMax invoiceDownloadMax = new RateMax(8, 16, 64);
    RateMax otherMax = new RateMax(10, 30, 120);
    int minApiRateLimit = 1;

    // pobiera bieżące limity, wylicza i ustawia nowe ograniczenia w dopuszczalnych granicach,
    // sprawdza czy zmiany zostały zastosowane, przywraca wartości oryginalne i weryfikuje, że są zgodne
    // z wartościami sprzed modyfikacji.
    @Test
    void rateLimitsPositive() throws JAXBException, IOException, ApiException {
        int limitsChangeValue = 1;

        // 1. Uwierzytelnianie i uzyskanie tokenu dostępu
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // Pobranie aktualnych limitów
        EffectiveApiRateLimits originalLimits = convert(ksefClient.getRateLimit(accessToken));

        // Assert: Wstępna walidacja danych wejściowych testu
        Assertions.assertNotNull(originalLimits);

        // Act: Wyliczenie nowych limitów w bezpiecznych widełkach (min=1, max wg kategorii)
        EffectiveApiRateLimits modifiedLimits = cloneAndModifyWithinBounds(originalLimits, limitsChangeValue);

        SetRateLimitsRequest setRequest = new SetRateLimitsRequest(modifiedLimits);

        // Act: Ustawienie nowych limitów
        ksefClient.setRateLimits(setRequest, accessToken);

        // Act: Ponowne pobranie limitów po zmianie
        EffectiveApiRateLimits currentLimits = convert(ksefClient.getRateLimit(accessToken));

        // Assert: Weryfikacja, że limity zostały zmienione zgodnie z oczekiwaniami
        assertRateLimitsEqual(modifiedLimits, currentLimits);

        // Act: Przywrócenie wartości domyślnych
        ksefClient.restoreRateLimits(accessToken);

        // Act: Ponowne pobranie po przywróceniu
        EffectiveApiRateLimits restoredLimits = convert(ksefClient.getRateLimit(accessToken));

        // Assert: Weryfikacja, że wartości po przywróceniu są identyczne jak oryginalne
        assertRateLimitsEqual(originalLimits, restoredLimits);
    }

    // próba ustawienia wartości limitów przekraczających dopuszczalne maksimum
    // powinna zakończyć się rzuceniem wyjątku KsefApiException.
    @Test
    void rateLimitsNegativeInvalidValues() throws JAXBException, IOException, ApiException {
        // Arrange: Uwierzytelnianie i uzyskanie tokenu dostępu
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // Arrange: Pobranie aktualnych limitów do bazowania
        EffectiveApiRateLimits baseLimits = convert(ksefClient.getRateLimit(accessToken));
        Assertions.assertNotNull(baseLimits);

        // Arrange: Przygotowanie jawnie nieprawidłowych wartości (OnlineSession poza maksimum)
        EffectiveApiRateLimits invalidLimits = new EffectiveApiRateLimits(
                new OnlineSessionRateLimit(onlineSessionMax.perSecond + 1,
                        onlineSessionMax.perMinute + 1,
                        onlineSessionMax.perHour + 11111
                ), // RateMax onlineSessionMax = new RateMax(10, 30, 120);
                // ustawienie pozostałych kategorii na aktualne poprawne wartości, by zminimalizować wpływ
                baseLimits.getBatchSession(),
                baseLimits.getInvoiceSend(),
                baseLimits.getInvoiceStatus(),
                baseLimits.getSessionList(),
                baseLimits.getSessionInvoiceList(),
                baseLimits.getSessionMisc(),
                baseLimits.getInvoiceMetadata(),
                baseLimits.getInvoiceExport(),
                baseLimits.getInvoiceStatusExport(),
                baseLimits.getInvoiceDownload(),
                baseLimits.getOther()
        );

        SetRateLimitsRequest request = new SetRateLimitsRequest(invalidLimits);

        ApiException apiException = Assertions.assertThrows(ApiException.class, () ->
                ksefClient.setRateLimits(request, accessToken));
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21405, details.getExceptionCode());
        Assertions.assertEquals("Błąd walidacji danych wejściowych.", details.getExceptionDescription());
    }

    // Tworzy kopię przekazanych limitów i modyfikuje je w oparciu o delta, nie przekraczając granic (min=1, max wg kategorii).
    // source - Oryginalne limity.
    // delta - Wartość inkrementacji/dekrementacji.
    // zwraca nowy obiekt z bezpiecznie zmodyfikowanymi limitami.
    private EffectiveApiRateLimits cloneAndModifyWithinBounds(EffectiveApiRateLimits source, int delta) {
        return new EffectiveApiRateLimits(
                modifyWithinBounds(source.getOnlineSession(), delta, onlineSessionMax),
                modifyWithinBounds(source.getBatchSession(), delta, batchSessionMax),
                modifyWithinBounds(source.getInvoiceSend(), delta, invoiceSendMax),
                modifyWithinBounds(source.getInvoiceStatus(), delta, invoiceStatusMax),
                modifyWithinBounds(source.getSessionList(), delta, sessionListMax),
                modifyWithinBounds(source.getSessionInvoiceList(), delta, sessionInvoiceListMax),
                modifyWithinBounds(source.getSessionMisc(), delta, sessionMiscMax),
                modifyWithinBounds(source.getInvoiceMetadata(), delta, invoiceMetadataMax),
                modifyWithinBounds(source.getInvoiceExport(), delta, invoiceExportMax),
                null,
                modifyWithinBounds(source.getInvoiceDownload(), delta, invoiceDownloadMax),
                modifyWithinBounds(source.getOther(), delta, otherMax)
        );
    }

    // Modyfikuje wartości limitów jednej kategorii, pozostając w dopuszczalnych granicach.
    // Jeśli dodanie delta przekracza maksimum, próbuje odjąć delta; jeśli to również jest poza minimum, zwraca bieżącą wartość.
    // values - Bieżące wartości limitów dla kategorii.
    // delta - Wartość inkrementacji/dekrementacji.
    // max - Maksymalne dopuszczalne wartości dla kategorii.
    // zwraca nowe wartości limitów w granicach.
    private OnlineSessionRateLimit modifyWithinBounds(OnlineSessionRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new OnlineSessionRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private BatchSessionRateLimit modifyWithinBounds(BatchSessionRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new BatchSessionRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private InvoiceSendRateLimit modifyWithinBounds(InvoiceSendRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new InvoiceSendRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private InvoiceStatusRateLimit modifyWithinBounds(InvoiceStatusRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new InvoiceStatusRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private SessionListRateLimit modifyWithinBounds(SessionListRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new SessionListRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private SessionInvoiceListRateLimit modifyWithinBounds(SessionInvoiceListRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new SessionInvoiceListRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private SessionMiscRateLimits modifyWithinBounds(SessionMiscRateLimits values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new SessionMiscRateLimits(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private InvoiceMetadataRateLimit modifyWithinBounds(InvoiceMetadataRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new InvoiceMetadataRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private InvoiceExportRateLimit modifyWithinBounds(InvoiceExportRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new InvoiceExportRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private InvoiceDownloadRateLimit modifyWithinBounds(InvoiceDownloadRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new InvoiceDownloadRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    private OtherRateLimit modifyWithinBounds(OtherRateLimit values, int delta, RateMax max) {
        if (values == null) {
            return null;
        }

        return new OtherRateLimit(
                adjust(values.getPerSecond(), delta, minApiRateLimit, max.perSecond),
                adjust(values.getPerMinute(), delta, minApiRateLimit, max.perMinute),
                adjust(values.getPerHour(), delta, minApiRateLimit, max.perHour)
        );
    }

    // Zwraca nową wartość po dodaniu lub odjęciu delta tak, aby nie przekroczyć min/max.
    // current - Wartość bieżąca.
    // delta - Wartość inkrementacji/dekrementacji.
    // min - Minimalna dopuszczalna wartość.
    // max - Maksymalna dopuszczalna wartość.
    // zwraca Skorygowana wartość w przedziale [min, max].
    private int adjust(int current, int delta, int min, int max) {
        if (current + delta <= max) {
            return current + delta;
        }

        if (current - delta >= min) {
            return current - delta;
        }

        return current; // w praktyce nie powinno się zdarzyć dla delta=1 i max>1
    }

    // Porównuje wszystkie wartości limitów pomiędzy oczekiwanymi i aktualnymi.
    // expected - Oczekiwane limity.
    // actual - Aktualne limity.
    private void assertRateLimitsEqual(EffectiveApiRateLimits expected, EffectiveApiRateLimits actual) {
        Assertions.assertNotNull(expected);
        Assertions.assertNotNull(actual);

        // OnlineSession
        Assertions.assertEquals(expected.getOnlineSession().getPerSecond(), actual.getOnlineSession().getPerSecond());
        Assertions.assertEquals(expected.getOnlineSession().getPerMinute(), actual.getOnlineSession().getPerMinute());
        Assertions.assertEquals(expected.getOnlineSession().getPerHour(), actual.getOnlineSession().getPerHour());
        // BatchSession
        Assertions.assertEquals(expected.getBatchSession().getPerSecond(), actual.getBatchSession().getPerSecond());
        Assertions.assertEquals(expected.getBatchSession().getPerMinute(), actual.getBatchSession().getPerMinute());
        Assertions.assertEquals(expected.getBatchSession().getPerHour(), actual.getBatchSession().getPerHour());
        // InvoiceSend
        Assertions.assertEquals(expected.getInvoiceSend().getPerSecond(), actual.getInvoiceSend().getPerSecond());
        Assertions.assertEquals(expected.getInvoiceSend().getPerMinute(), actual.getInvoiceSend().getPerMinute());
        Assertions.assertEquals(expected.getInvoiceSend().getPerHour(), actual.getInvoiceSend().getPerHour());
        // InvoiceStatus
        Assertions.assertEquals(expected.getInvoiceStatus().getPerSecond(), actual.getInvoiceStatus().getPerSecond());
        Assertions.assertEquals(expected.getInvoiceStatus().getPerMinute(), actual.getInvoiceStatus().getPerMinute());
        Assertions.assertEquals(expected.getInvoiceStatus().getPerHour(), actual.getInvoiceStatus().getPerHour());
        // SessionList
        Assertions.assertEquals(expected.getSessionList().getPerSecond(), actual.getSessionList().getPerSecond());
        Assertions.assertEquals(expected.getSessionList().getPerMinute(), actual.getSessionList().getPerMinute());
        Assertions.assertEquals(expected.getSessionList().getPerHour(), actual.getSessionList().getPerHour());
        // SessionInvoiceList
        Assertions.assertEquals(expected.getSessionInvoiceList().getPerSecond(), actual.getSessionInvoiceList().getPerSecond());
        Assertions.assertEquals(expected.getSessionInvoiceList().getPerMinute(), actual.getSessionInvoiceList().getPerMinute());
        Assertions.assertEquals(expected.getSessionInvoiceList().getPerHour(), actual.getSessionInvoiceList().getPerHour());
        // SessionMisc
        Assertions.assertEquals(expected.getSessionMisc().getPerSecond(), actual.getSessionMisc().getPerSecond());
        Assertions.assertEquals(expected.getSessionMisc().getPerMinute(), actual.getSessionMisc().getPerMinute());
        Assertions.assertEquals(expected.getSessionMisc().getPerHour(), actual.getSessionMisc().getPerHour());
        // InvoiceMetadata
        Assertions.assertEquals(expected.getInvoiceMetadata().getPerSecond(), actual.getInvoiceMetadata().getPerSecond());
        Assertions.assertEquals(expected.getInvoiceMetadata().getPerMinute(), actual.getInvoiceMetadata().getPerMinute());
        Assertions.assertEquals(expected.getInvoiceMetadata().getPerHour(), actual.getInvoiceMetadata().getPerHour());
        // InvoiceExport
        Assertions.assertEquals(expected.getInvoiceExport().getPerSecond(), actual.getInvoiceExport().getPerSecond());
        Assertions.assertEquals(expected.getInvoiceExport().getPerMinute(), actual.getInvoiceExport().getPerMinute());
        Assertions.assertEquals(expected.getInvoiceExport().getPerHour(), actual.getInvoiceExport().getPerHour());
        // InvoiceDownload
        Assertions.assertEquals(expected.getInvoiceDownload().getPerSecond(), actual.getInvoiceDownload().getPerSecond());
        Assertions.assertEquals(expected.getInvoiceDownload().getPerMinute(), actual.getInvoiceDownload().getPerMinute());
        Assertions.assertEquals(expected.getInvoiceDownload().getPerHour(), actual.getInvoiceDownload().getPerHour());
        // Other
        Assertions.assertEquals(expected.getOther().getPerSecond(), actual.getOther().getPerSecond());
        Assertions.assertEquals(expected.getOther().getPerMinute(), actual.getOther().getPerMinute());
        Assertions.assertEquals(expected.getOther().getPerHour(), actual.getOther().getPerHour());
    }

    private EffectiveApiRateLimits convert(GetRateLimitResponse source) {
        return new EffectiveApiRateLimits(
                source.getOnlineSession(),
                source.getBatchSession(),
                source.getInvoiceSend(),
                source.getInvoiceStatus(),
                source.getSessionList(),
                source.getSessionInvoiceList(),
                source.getSessionMisc(),
                source.getInvoiceMetadata(),
                source.getInvoiceExport(),
                source.getInvoiceStatusExport(),
                source.getInvoiceDownload(),
                source.getOther()
        );
    }

    private class RateMax {
        int perSecond;
        int perMinute;
        int perHour;

        public RateMax(int perSecond, int perMinute, int perHour) {
            this.perSecond = perSecond;
            this.perMinute = perMinute;
            this.perHour = perHour;
        }
    }
}
