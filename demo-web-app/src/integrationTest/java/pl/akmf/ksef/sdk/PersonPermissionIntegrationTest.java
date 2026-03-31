package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.akmf.ksef.sdk.api.builders.permission.entity.GrantEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.PersonPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.proxy.GrantAuthorizationPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.subunit.SubunitPermissionsGrantRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.entity.EntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.entity.GrantEntityPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionPersonByFingerprintWithId;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionPersonById;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.proxy.GrantAuthorizationPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.proxy.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;
import pl.akmf.ksef.sdk.client.model.permission.search.PermissionState;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsAuthorizedIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsTargetIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantTargetIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalPermissionTypes;
import pl.akmf.ksef.sdk.client.model.permission.subunit.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.PermissionsSubunitPersonByIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.subunit.PermissionsSubunitSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.subunit.SubunitSubjectDetails;
import pl.akmf.ksef.sdk.client.model.testdata.SubjectTypeTestData;
import pl.akmf.ksef.sdk.client.model.testdata.Subunit;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataPersonCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectCreateRequest;
import pl.akmf.ksef.sdk.client.model.testdata.TestDataSubjectRemoveRequest;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class PersonPermissionIntegrationTest extends BaseIntegrationTest {

    @Test
    void personPermissionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        String personValue = IdentifierGeneratorUtils.getRandomPesel();

        String grantReferenceNumber = grantPersonPermission(personValue, accessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantReferenceNumber, accessToken));

        List<String> permission = searchPersonPermission(personValue, 2, accessToken);

        permission.forEach(e -> {
            String revokeReferenceNumber = revokePermission(e, accessToken);

            await().pollDelay(Duration.ZERO)
                    .atMost(30, SECONDS)
                    .pollInterval(2, SECONDS)
                    .until(() -> isOperationFinish(revokeReferenceNumber, accessToken));
        });
        searchPersonPermission(personValue, 0, accessToken);
    }

    static Stream<Arguments> inputTestParameters() {
        return Stream.of(
                Arguments.of(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, IdentifierGeneratorUtils.generateRandomNIP(), List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD, PersonPermissionType.INTROSPECTION, PersonPermissionType.CREDENTIALSREAD, PersonPermissionType.CREDENTIALSMANAGE)),
                Arguments.of(PersonPermissionsSubjectIdentifier.IdentifierType.PESEL, IdentifierGeneratorUtils.getRandomPesel(), List.of(PersonPermissionType.INVOICEREAD, PersonPermissionType.INVOICEWRITE, PersonPermissionType.CREDENTIALSMANAGE, PersonPermissionType.CREDENTIALSREAD, PersonPermissionType.INTROSPECTION, PersonPermissionType.SUBUNITMANAGE))
        );
    }

    // Nadanie uprawnień przez osobę z uprawnieniem do zarządzania uprawnieniami (dla NIPu i Peselu)
    @ParameterizedTest
    @MethodSource("inputTestParameters")
    void grantPermissionsByDelegatedUnit(PersonPermissionsSubjectIdentifier.IdentifierType delegate2Type, String delegate2Value, List<PersonPermissionType> permissions) throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        String delegateNip = IdentifierGeneratorUtils.generateRandomNIP();

        String grantOperationReferenceNumber = grantPersonPermission(delegateNip, PersonPermissionsSubjectIdentifier.IdentifierType.NIP,
                List.of(PersonPermissionType.CREDENTIALSMANAGE),
                "Grant CREDENTIALSMANAGE to NIP:" + delegateNip,
                ownerAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantOperationReferenceNumber, ownerAccessToken));

        String delegateAccessToken = authWithCustomNip(ownerNip, delegateNip).accessToken();
        //Nadanie uprawnień jako menadżer uprawnień
        String grantDelegate2OperationReferenceNumber = grantPersonPermission(delegate2Value, delegate2Type,
                permissions,
                new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                        new PersonPermissionPersonById("Jan", "Testowy"),
                        null,
                        null
                ),
                "Grant permissions to: " + delegate2Value,
                delegateAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantDelegate2OperationReferenceNumber, delegateAccessToken));

        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.fromValue(delegate2Type.getValue()), delegate2Value))
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .withPermissionState(PermissionState.ACTIVE)
                .build();
        List<String> grantedPermissionsIds = searchPersonPermission(request, permissions.size(), delegateAccessToken);

        //uwierzytelnienie w kontekście w którym otrzymano uprawnienia
        String delegate2AccessToken = PersonPermissionsSubjectIdentifier.IdentifierType.NIP.equals(delegate2Type)
                ? authWithCustomNip(ownerNip, delegate2Value).accessToken()
                : authWithCustomPesel(ownerNip, delegate2Value).accessToken();

        grantedPermissionsIds.forEach(e -> {
            String revokeReferenceNumber = revokePermission(e, ownerAccessToken);

            await().pollDelay(Duration.ZERO)
                    .atMost(30, SECONDS)
                    .pollInterval(1, SECONDS)
                    .until(() -> isOperationFinish(revokeReferenceNumber, ownerAccessToken));
        });
        searchPersonPermission(request, 0, delegateAccessToken);
    }

    // Uwierzytelnienie na uprawnienia nadane w sposób pośredni (selektywnie) z kompletnym łańcuchem (wspólny zakres).
    // 1) Owner NIP → GRANT dla NIP biura.
    // 2) Biuro (własny kontekst NIP) → GRANT dla PESEL pracownika.
    // 3) Osoba (PESEL) w kontekście NIP właściciela → QUERY personal/grants (Active).
    @Test
    void authIndirectSelectiveCompleteChainShouldExposeMatchingEffectivePermission() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String intermediaryNip = IdentifierGeneratorUtils.generateRandomNIP();
        String personPesel = IdentifierGeneratorUtils.getRandomPesel();

        createEnforcementSubject(ownerNip, "E2E-Subject-Owner-" + ownerNip);
        createEnforcementSubject(intermediaryNip, "E2E-Subject-Interm-" + intermediaryNip);

        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        String grantAuthorizationOperationReferenceNumber = grantAuthorizationPermission(intermediaryNip, "E2E-Indirect-OwnerToInterm-Read-" + intermediaryNip, ownerAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantAuthorizationOperationReferenceNumber, ownerAccessToken));

        String intermediaryAccessToken = authWithCustomNip(intermediaryNip, intermediaryNip).accessToken();

        String grantPersonOperationReferenceNumber = grantPersonPermission(personPesel, intermediaryAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantPersonOperationReferenceNumber, intermediaryAccessToken));

        // AUTH: osoba (PESEL) w kontekście NIP właściciela
        String personAccessToken = authWithCustomPesel(intermediaryNip, personPesel).accessToken();

        QueryPersonalGrantRequest request = new QueryPersonalGrantRequest();
        request.setContextIdentifier(new QueryPersonalGrantContextIdentifier(QueryPersonalGrantContextIdentifier.IdentifierType.NIP, intermediaryNip));
        request.setTargetIdentifier(new QueryPersonalGrantTargetIdentifier(QueryPersonalGrantTargetIdentifier.IdentifierType.NIP, intermediaryNip));
        request.setPermissionState(PermissionState.ACTIVE);
        QueryPersonalGrantResponse response = ksefClient.searchPersonalGrantPermission(request, 0, 50, personAccessToken);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getPermissions().size());
    }

    // Test E2E weryfikujący pobranie listy „moich uprawnień” do pracy w KSeF
    // dla osoby zidentyfikowanej PESEL w kontekście wskazanego NIP.
    // Scenariusz: właściciel nadaje uprawnienia osobie w swoim kontekście NIP,
    // następnie ta osoba uwierzytelnia się w tym samym kontekście i wywołuje
    // searchGrantedPersonPermissions filtrowane po NIP. Test sprawdza, że zwrócono dokładnie dwa nadane uprawnienia
    // w oczekiwanym kontekście.
    @Test
    void personalPermissionsByPeselInNipContextShouldReturnPermissionsInContext() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String pesel = IdentifierGeneratorUtils.getRandomPesel();
        // Właściciel uwierzytelnia się we własnym kontekście
        String ownerAccessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // Nadaj uprawnienia osobie (PESEL) w kontekście NIPu właściciela
        String grantPersonOperationReferenceNumber = grantPersonPermission(pesel, PersonPermissionsSubjectIdentifier.IdentifierType.PESEL,
                List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD, PersonPermissionType.CREDENTIALSREAD),
                new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                        new PersonPermissionPersonById("Anna", "Testowa"),
                        null,
                        null
                ),
                String.format("Nadanie uprawnień przeglądania i wystawiania faktur dla PESEL %s w kontekście NIP %s", pesel, contextNip),
                ownerAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantPersonOperationReferenceNumber, ownerAccessToken));

        // Uwierzytelnij się jako osoba (PESEL) w kontekście NIP właściciela
        String personAccessToken = authWithCustomPesel(contextNip, pesel).accessToken();

        // pobierz moje uprawnienia dla osoby w bieżącym kontekście NIP, filtrując po kontekście na poziomie zapytania
        PersonPermissionsContextIdentifier contextIdentifier = new PersonPermissionsContextIdentifier();
        contextIdentifier.setType(PersonPermissionsContextIdentifier.IdentifierType.NIP);
        contextIdentifier.setValue(contextNip);
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withContextIdentifier(contextIdentifier)
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();
        searchPersonPermission(request, 3, personAccessToken);
    }

    // E2E: nadane uprawnienia (właściciel) w kontekście NIP z filtrowaniem po odcisku palca certyfikatu (fingerprint SHA-256).
    // Właściciel podmiotu – pełen dostęp w kontekście własnego NIP; powiązanie NIP–PESEL.
    // Generujemy cert testowy → fingerprint (SHA-256, HEX, UPPER)
    // GRANT dla fingerprint → QUERY z filtrem fingerprint.
    // Asercja dopasowania fingerprint.
    @Test
    void searchGrantedAsOwnerNipFilterByAuthorizedFingerprintShouldReturnMatch() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String fingerprintNipIdentifier = IdentifierGeneratorUtils.generateRandomNIP();

        // cert testowy → fingerprint (SHA256 HEX, uppercase)
        SelfSignedCertificate personalCertificate = certificateService.getPersonalCertificate("PL", "Person", "TINPL", ownerNip, "e2e authorized person");
        String authorizedFingerprint = certificateService.getSha256Fingerprint(personalCertificate.certificate());

        // owner (nadawca == owner)
        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        // GRANT → nadaj np. InvoiceRead fingerprintowi
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.FINGERPRINT, authorizedFingerprint))
                .withPermissions(List.of(PersonPermissionType.INVOICEREAD))
                .withDescription("E2E-Grant-Read-FP-authorizedFingerprint" + authorizedFingerprint.substring(authorizedFingerprint.length() - 8))
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_FINGERPRINT_WITH_IDENTIFIER,
                                null,
                                new PersonPermissionPersonByFingerprintWithId("Anna", "Testowa", new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, fingerprintNipIdentifier)),
                                null
                        )
                )
                .build();
        String grantPersonOperationReferenceNumber = grantPersonPermission(request, ownerAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantPersonOperationReferenceNumber, ownerAccessToken));

        // Zapytanie: nadane uprawnienia (owner) z filtrem po Fingerprint
        PersonPermissionsContextIdentifier contextIdentifier = new PersonPermissionsContextIdentifier();
        contextIdentifier.setType(PersonPermissionsContextIdentifier.IdentifierType.NIP);
        contextIdentifier.setValue(ownerNip);
        PersonPermissionsQueryRequest personPermissionsQueryRequest = new PersonPermissionsQueryRequestBuilder()
                .withContextIdentifier(contextIdentifier)
                .withTargetIdentifier(new PersonPermissionsTargetIdentifier(PersonPermissionsTargetIdentifier.IdentifierType.NIP, ownerNip))
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.FINGERPRINT, authorizedFingerprint))
                .withPermissionState(PermissionState.ACTIVE)
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        // czekamy aż pojawi się wpis Z TYM fingerprintem
        await().pollDelay(Duration.ZERO)
                .atMost(25, SECONDS)
                .pollInterval(3, SECONDS)
                .until(() -> {
                            QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(personPermissionsQueryRequest, 0, 10, ownerAccessToken);
                            return response.getPermissions().stream()
                                    .anyMatch(permission ->
                                            PersonPermissionsAuthorizedIdentifier.IdentifierType.FINGERPRINT.equals(permission.getAuthorizedIdentifier().getType())
                                                    && authorizedFingerprint.equals(permission.getAuthorizedIdentifier().getValue())
                                    );
                        }
                );
    }

    // E2E: „Nadane uprawnienia” (właściciel, kontekst NIP) z filtrowaniem po NIP uprawnionego.
    // Przebieg: utwórz podmiot i osobę → nadaj uprawnienie → uwierzytelnij właściciela → zapytaj o nadane (z filtrem NIP) → asercje.
    // Seed: Subject (NIP właściciela) + Person (NIP+PESEL) przez testdata.
    // GRANT (real API persons) NIPowi uprawnionemu.
    // QUERY: nadane w bieżącym kontekście + filtr NIP uprawnionego.
    // ASSERT: dopasowanie po AuthorizedIdentifier=NIP oraz AuthorIdentifier=NIP właściciela.
    @Test
    void searchGrantedAsOwnerNipFilterByAuthorizedNipShouldReturnWithMatch() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedPesel = IdentifierGeneratorUtils.getRandomPesel();

        // Subject (kontekst właściciela) – testdata setup
        createEnforcementSubject(ownerNip, "E2E-Subject-" + ownerNip);

        // Osoba uprawniona – testdata setup (NIP + PESEL, żeby grant był możliwy deterministycznie)
        createAuthorizedPerson(authorizedNip, authorizedPesel, "E2E-Person-" + authorizedNip);

        // Auth po stronie właściciela (kontekst NIP)
        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        // Grant (REAL API): persons/grants – uprawnienie InvoiceRead osobie identyfikowanej NIPem
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, authorizedNip))
                .withPermissions(List.of(PersonPermissionType.INVOICEREAD))
                .withDescription("E2E-Grant-Read-" + authorizedNip)
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();
        String grantPersonOperationReferenceNumber = grantPersonPermission(request, ownerAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantPersonOperationReferenceNumber, ownerAccessToken));

        // Query: granted w bieżącym kontekście + filtr po NIP uprawnionego
        PersonPermissionsContextIdentifier contextIdentifier = new PersonPermissionsContextIdentifier();
        contextIdentifier.setType(PersonPermissionsContextIdentifier.IdentifierType.NIP);
        contextIdentifier.setValue(ownerNip);
        PersonPermissionsQueryRequest personPermissionsQueryRequest = new PersonPermissionsQueryRequestBuilder()
                .withContextIdentifier(contextIdentifier)
                .withTargetIdentifier(new PersonPermissionsTargetIdentifier(PersonPermissionsTargetIdentifier.IdentifierType.NIP, ownerNip))
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.NIP, authorizedNip))
                .withPermissionState(PermissionState.ACTIVE)
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        // czekamy na pojawienie się grantu w wynikach
        await().pollDelay(Duration.ZERO)
                .atMost(25, SECONDS)
                .pollInterval(3, SECONDS)
                .until(() -> {
                            QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(personPermissionsQueryRequest, 0, 10, ownerAccessToken);
                            return response.getPermissions().stream()
                                    .anyMatch(permission ->
                                            permission != null
                                                    && permission.getAuthorizedIdentifier() != null
                                                    && permission.getAuthorIdentifier() != null
                                                    && PersonPermissionsAuthorizedIdentifier.IdentifierType.NIP.equals(permission.getAuthorizedIdentifier().getType())
                                                    && authorizedNip.equals(permission.getAuthorizedIdentifier().getValue())
                                                    && ownerNip.equals(permission.getAuthorIdentifier().getValue())
                                                    && PersonPermissionType.INVOICEREAD.equals(permission.getPermissionScope())
                                    );
                        }
                );
    }

    // E2E: nadane uprawnienia (właściciel) w kontekście NIP z filtrowaniem po PESEL.
    // Nadanie uprawnienia osobie z PESEL.
    // Zapytanie: nadane w bieżącym kontekście + filtr PESEL.
    // Asercja: istnieje wpis z dokładnie tym PESEL.
    @Test
    void searchGrantedAsOwnerNipFilterByAuthorizedPeselShouldReturnMatch() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedPesel = IdentifierGeneratorUtils.getRandomPesel();

        // owner (nadawca == owner)
        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        // GRANT — nadajemy np. InvoiceRead osobie po PESELu
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.PESEL, authorizedPesel))
                .withPermissions(List.of(PersonPermissionType.INVOICEREAD))
                .withDescription("E2E-Grant-Read-PESEL-" + authorizedPesel)
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();
        String grantPersonOperationReferenceNumber = grantPersonPermission(request, ownerAccessToken);
        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantPersonOperationReferenceNumber, ownerAccessToken));

        // Query: nadane (owner) + filtr po PESEL
        PersonPermissionsContextIdentifier contextIdentifier = new PersonPermissionsContextIdentifier();
        contextIdentifier.setType(PersonPermissionsContextIdentifier.IdentifierType.NIP);
        contextIdentifier.setValue(ownerNip);
        PersonPermissionsQueryRequest personPermissionsQueryRequest = new PersonPermissionsQueryRequestBuilder()
                .withContextIdentifier(contextIdentifier)
                .withTargetIdentifier(new PersonPermissionsTargetIdentifier(PersonPermissionsTargetIdentifier.IdentifierType.NIP, ownerNip))
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.PESEL, authorizedPesel))
                .withPermissionState(PermissionState.ACTIVE)
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .build();

        // czekamy na pojawienie się grantu w wynikach
        await().pollDelay(Duration.ZERO)
                .atMost(25, SECONDS)
                .pollInterval(3, SECONDS)
                .until(() -> {
                            QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(personPermissionsQueryRequest, 0, 10, ownerAccessToken);
                            return response.getPermissions().stream()
                                    .anyMatch(permission ->
                                            permission != null
                                                    && permission.getAuthorizedIdentifier() != null
                                                    && PersonPermissionsAuthorizedIdentifier.IdentifierType.PESEL.equals(permission.getAuthorizedIdentifier().getType())
                                                    && authorizedPesel.equals(permission.getAuthorizedIdentifier().getValue())
                                    );
                        }
                );
    }


    // Scenariusz E2E weryfikujący:
    // - poprawkę w API zwracającą w liście uprawnień (POST /v2/permissions/query/personal/grants)
    //   również uprawnienia podmiotowe InvoiceRead/InvoiceWrite nadane z canDelegate=false,
    // - filtrowanie wyników po InternalId,
    // - dopuszczalną długość identyfikatora InternalId (16 znaków).
    //
    // Kroki:
    // 1. Utworzenie podmiotu głównego (VatGroup) z jednostką podrzędną
    // 2. Nadanie dyrektorowi (PESEL) uprawnień administracyjnych do jednostki podrzędnej w kontekście InternalId
    // 3. Uwierzytelnienie dyrektora w kontekście InternalId
    // 4. Nadanie dyrektorowi uprawnień osobistych InvoiceRead/InvoiceWrite w kontekście InternalId
    // 5. Nadanie w tym samym kontekście uprawnień podmiotowych InvoiceRead/InvoiceWrite z canDelegate=false
    // 6. Weryfikacja listy uprawnień bez filtra (powinny zawierać InvoiceRead/InvoiceWrite z CanDelegate=false)
    // 7. Weryfikacja listy uprawnień z filtrem InternalId (powinny zawierać InvoiceRead/InvoiceWrite z CanDelegate=false)
    // 8. Sprzątanie danych testowych
    @Test
    void personalPermissionsWithInternalIdFilterShouldReturnPermissions() throws ApiException, JAXBException, IOException {
        // Przygotowanie danych
        String municipalOfficeNip = IdentifierGeneratorUtils.generateRandomNIP();
        String subunitNip = IdentifierGeneratorUtils.generateRandomNIP();
        String kindergartenId = IdentifierGeneratorUtils.generateInternalIdentifier(municipalOfficeNip);
        String directorPesel = IdentifierGeneratorUtils.getRandomPesel();

        // Weryfikacja kontraktu: InternalId ma długość 16
        Assertions.assertEquals(16, kindergartenId.length());

        // Utworzenie podmiotu głównego z jednostką podrzędną
        createVatGroupWithSubunit(municipalOfficeNip, subunitNip, "Przedszkole testowe", "Gmina testowa");

        // 1) Uwierzytelnienie jako podmiot główny (właściciel kontekstu)
        String municipalOfficeAuthToken = authWithCustomNip(municipalOfficeNip, municipalOfficeNip).accessToken();

        // 2) Nadanie dyrektorowi uprawnień administracyjnych do jednostki podrzędnej w kontekście InternalId
        String operationGrantNumber = grantPermissionSubunit(directorPesel, kindergartenId, "Przedszkole Testowe", "Sub-unit permission grant", municipalOfficeAuthToken);

        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(operationGrantNumber, municipalOfficeAuthToken));

        // 3) Uwierzytelnienie dyrektora w kontekście InternalId
        String kindergartenAuthToken = authAsInternalId(kindergartenId, directorPesel).accessToken();

        // 4) Nadanie uprawnień osobistych InvoiceRead/InvoiceWrite w kontekście InternalId
        String grantPersonReferenceNumber = grantPersonPermission(directorPesel, "Nadanie InvoiceRead i InvoiceWrite dyrektorowi w kontekście InternalId", kindergartenAuthToken);

        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantPersonReferenceNumber, kindergartenAuthToken));

        // 5) Nadanie uprawnień podmiotowych (entity) InvoiceRead/InvoiceWrite z canDelegate=false
        String grantEntityReferenceNumber = grantPermissionEntity(municipalOfficeNip,
                "Uprawnienia podmiotowe InvoiceRead/InvoiceWrite bez delegowania",
                "Podmiot " + municipalOfficeNip,
                kindergartenAuthToken);

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isOperationFinish(grantEntityReferenceNumber, kindergartenAuthToken));

        // 6) Weryfikacja listy uprawnień bez filtra
        QueryPersonalGrantRequest queryWithoutFilter = new QueryPersonalGrantRequest();
        QueryPersonalGrantResponse personalPermissions = ksefClient.searchPersonalGrantPermission(queryWithoutFilter, 0, 50, kindergartenAuthToken);

        // Uprawnienia InvoiceRead/InvoiceWrite powinny być zwrócone również wtedy, gdy canDelegate=false
        Assertions.assertTrue(personalPermissions.getPermissions().stream()
                .anyMatch(p -> QueryPersonalPermissionTypes.INVOICE_READ.equals(p.getPermissionScope()) && p.getCanDelegate() == false)
        );
        Assertions.assertTrue(personalPermissions.getPermissions().stream()
                .anyMatch(p -> QueryPersonalPermissionTypes.INVOICE_WRITE.equals(p.getPermissionScope()) && p.getCanDelegate() == false)
        );

        // 7) Weryfikacja listy uprawnień z filtrem ContextIdentifier=InternalId
        QueryPersonalGrantRequest request = new QueryPersonalGrantRequest();
        request.setContextIdentifier(new QueryPersonalGrantContextIdentifier(QueryPersonalGrantContextIdentifier.IdentifierType.INTERNAL_ID, kindergartenId));
        QueryPersonalGrantResponse permissionsWithInternalIdFilter = ksefClient.searchPersonalGrantPermission(request, 0, 50, kindergartenAuthToken);

        Assertions.assertTrue(permissionsWithInternalIdFilter.getPermissions().stream()
                .anyMatch(p -> QueryPersonalPermissionTypes.INVOICE_READ.equals(p.getPermissionScope()) && p.getCanDelegate() == false)
        );
        Assertions.assertTrue(permissionsWithInternalIdFilter.getPermissions().stream()
                .anyMatch(p -> QueryPersonalPermissionTypes.INVOICE_WRITE.equals(p.getPermissionScope()) && p.getCanDelegate() == false)
        );

        // 8) Sprzątanie danych testowych
        ksefClient.removeTestSubject(new TestDataSubjectRemoveRequest(municipalOfficeNip));
    }

    private void createVatGroupWithSubunit(String vatGroupNip, String subunitNip, String subunitDescription, String description) throws ApiException {
        TestDataSubjectCreateRequest request = new TestDataSubjectCreateRequest();
        request.setSubjectNip(vatGroupNip);
        request.setSubjectType(SubjectTypeTestData.VAT_GROUP);
        request.setSubunits(List.of(new Subunit(subunitNip, subunitDescription)));
        request.setDescription(description);

        ksefClient.createTestSubject(request);
    }

    private void createEnforcementSubject(String nip, String description) throws ApiException {
        TestDataSubjectCreateRequest request = new TestDataSubjectCreateRequest();
        request.setSubjectNip(nip);
        request.setSubjectType(SubjectTypeTestData.ENFORCEMENT_AUTHORITY);
        request.setDescription(description);

        ksefClient.createTestSubject(request);
    }

    private void createAuthorizedPerson(String nip, String pesel, String description) throws ApiException {
        TestDataPersonCreateRequest request = new TestDataPersonCreateRequest();
        request.setCreatedDate(OffsetDateTime.now());
        request.setDescription(description);
        request.setNip(nip);
        request.setPesel(pesel);
        request.setIsDeceased(false);
        request.setIsBailiff(false);

        ksefClient.createTestPerson(request);
    }

    private List<String> searchPersonPermission(String personValue, int expectedPermissionsSize, String accessToken) throws ApiException {
        PersonPermissionsQueryRequest request = new PersonPermissionsQueryRequestBuilder()
                .withAuthorizedIdentifier(new PersonPermissionsAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier.IdentifierType.PESEL, personValue))
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .withPermissionTypes(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD))
                .build();

        return searchPersonPermission(request, expectedPermissionsSize, accessToken);
    }

    private List<String> searchPersonPermission(PersonPermissionsQueryRequest request, int expectedPermissionsSize, String accessToken) throws ApiException {
        QueryPersonPermissionsResponse response = ksefClient.searchGrantedPersonPermissions(request, 0, 10, accessToken);
        if (expectedPermissionsSize > 0) {
            Assertions.assertEquals(expectedPermissionsSize, response.getPermissions().size());
        }

        return response.getPermissions()
                .stream()
                .map(PersonPermission::getId)
                .toList();
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private String grantAuthorizationPermission(String nip, String description, String accessToken) throws ApiException {
        GrantAuthorizationPermissionsRequest.PermissionsAuthorizationSubjectDetails subjectDetails =
                new GrantAuthorizationPermissionsRequest.PermissionsAuthorizationSubjectDetails();
        subjectDetails.setFullName("Podmiot Testowy 1");

        GrantAuthorizationPermissionsRequest request = new GrantAuthorizationPermissionsRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.NIP, nip))
                .withPermission(InvoicePermissionType.RR_INVOICING)
                .withDescription(description)
                .withSubjectDetails(subjectDetails)
                .build();

        OperationResponse response = ksefClient.grantsPermissionsProxyEntity(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private String grantPersonPermission(String personValue, String accessToken) throws ApiException {
        return grantPersonPermission(personValue, "e2e test grant", accessToken);
    }

    private String grantPersonPermission(String personValue, String description, String accessToken) throws ApiException {
        return grantPersonPermission(personValue, PersonPermissionsSubjectIdentifier.IdentifierType.PESEL,
                List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD),
                new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                        new PersonPermissionPersonById("Anna", "Testowa"),
                        null,
                        null
                ),
                description,
                accessToken);
    }

    private String grantPersonPermission(String subjectValue, PersonPermissionsSubjectIdentifier.IdentifierType subjectType, List<PersonPermissionType> permissions, String description, String accessToken) throws ApiException {
        return grantPersonPermission(subjectValue, subjectType, permissions,
                new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                        new PersonPermissionPersonById("Anna", "Testowa"),
                        null,
                        null
                ),
                description, accessToken
        );
    }

    private String grantPersonPermission(String subjectValue, PersonPermissionsSubjectIdentifier.IdentifierType subjectType, List<PersonPermissionType> permissions, PersonPermissionSubjectDetails subjectDetails, String description, String accessToken) throws ApiException {
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(subjectType, subjectValue))
                .withPermissions(permissions)
                .withDescription(description)
                .withSubjectDetails(subjectDetails)
                .build();

        return grantPersonPermission(request, accessToken);
    }

    private String grantPersonPermission(GrantPersonPermissionsRequest request, String accessToken) throws ApiException {
        OperationResponse response = ksefClient.grantsPermissionPerson(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private String grantPermissionSubunit(String pesel, String contextIdentifierValue, String subunitName, String subunitDescription, String accessToken) throws ApiException {
        SubunitPermissionsGrantRequest request = new SubunitPermissionsGrantRequestBuilder()
                .withSubjectIdentifier(new pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier(pl.akmf.ksef.sdk.client.model.permission.subunit.SubjectIdentifier.IdentifierType.PESEL, pesel))
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.INTERNALID, contextIdentifierValue))
                .withDescription(subunitDescription)
                .withSubunitName(subunitName)
                .withSubjectDetails(
                        new SubunitSubjectDetails(PermissionsSubunitSubjectDetailsType.PersonByIdentifier,
                                new PermissionsSubunitPersonByIdentifier("Jan", "Kowalski"),
                                null,
                                null
                        )
                )
                .build();

        return grantPermissionSubunit(request, accessToken);
    }

    private String grantPermissionSubunit(SubunitPermissionsGrantRequest request, String accessToken) throws ApiException {
        OperationResponse response = ksefClient.grantsPermissionSubUnit(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private String grantPermissionEntity(String targetNip, String description, String subjectDetailsFullName, String accessToken) throws ApiException {
        GrantEntityPermissionsRequest request = new GrantEntityPermissionsRequestBuilder()
                .withPermissions(List.of(
                        new EntityPermission(EntityPermissionType.INVOICE_READ, false),
                        new EntityPermission(EntityPermissionType.INVOICE_WRITE, false)))
                .withDescription(description)
                .withSubjectIdentifier(new pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier(pl.akmf.ksef.sdk.client.model.permission.entity.SubjectIdentifier.IdentifierType.NIP, targetNip))
                .withSubjectDetails(
                        new GrantEntityPermissionsRequest.PermissionsEntitySubjectDetails(subjectDetailsFullName)
                )
                .build();

        OperationResponse response = ksefClient.grantsPermissionEntity(request, accessToken);
        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }
}
