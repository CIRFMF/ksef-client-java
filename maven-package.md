## Paczka Maven
06.11.2025

Paczki maven dostępne są pod adresem: https://github.com/orgs/CIRFMF/packages

### Wymagania wstępne

Aby korzystać z biblioteki ksef-client w swoim projekcie, należy najpierw skonfigurować dostęp do paczek Maven hostowanych w GitHub Packages organizacji CIRFMF.
Wymaga to autoryzacji przy pomocy osobistego tokena dostępu (Personal Access Token – PAT) z uprawnieniem read:packages.

### 1. Wygenerowanie tokena dostępu (PAT)

Aby uzyskać dostęp do paczek z GitHub Packages, należy utworzyć Personal Access Token (PAT) z odpowiednimi uprawnieniami:

Scieżka: GitHub -> Settings -> Developer settings -> Personal access tokens -> Tokens (classic) -> Generate new token -> Generate new token (classic)

W sekcji **Select scopes** należy wybrać **read:packages** a następnie wygenerować i skopiować wartość tokena (będzie widoczna tylko raz)

### 2. Dodanie źródła pakietów

Aby móc pobierać paczki z GitHub Packages, należy dodać źródło z repozytoruim.

```
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/CIRFMF/ksef-client-java")
        credentials {
            username = System.getenv("GITHUB_ACTOR") ?: "youUsername"
            password = System.getenv("GITHUB_TOKEN") ?: "yourToken"
        }
    }
}
```

Jeśli źródło zostanie dodane bez uwierzytelnienia, narzędzia (java/Intelij IDE) zwrócą błąd 401 Unauthorized lub 403 Forbidden.

### 3. Pobranie paczek

Po dodaniu źródła możesz pobrać paczki ksef-client w swoim projekcie.

```
dependencies {
    implementation("pl.akmf.ksef-sdk:ksef-client:$clientVersion")
}
```
