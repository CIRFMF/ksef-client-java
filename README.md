# **KSeF**


## Wstęp – Struktura projektu i technologie

Projekt składa się z dwóch modułów:
- **demo-web-app** – przykładowa aplikacja webowa (spring boot), która używa SDK i rozszerza o przykładowe użycia czy scenariusze testowe.
- **ksef-client** – biblioteka (SDK), zawierająca wspólną logikę, modele oraz interfejsy.
+ katalogu .http z wywołaniami testowych usług z `demo-web-app`, które w określonych scenariuszach bądź bezpośrednio wywołują usługi z klienta  

Całość napisana jest w języku **Java**. Do komunikacji HTTP wykorzystywany jest java.net.http.HttpClient.


## 🔧 Wersje technologii
| Komponent      | Java | Spring Boot |
|----------------|------|-------------|
| `ksef-client`  | 11   | –           |
| `demo-web-app` | 21   | 3.5.7       |

---

### Struktura katalogów ksef-client


- **api**  
  Zawiera buildery do tworzenia requestów do API oraz serwisy wspomagające generowanie certyfikatów, kryptograficzny czy sam klient http który realizuje żądania HTTP do API.

- **client**  
  Zawiera modele klas odpowiadających zwrotkom z API, definicje wyjątków, interfejsy oraz modele.



### Struktura katalogów demo-web-app

- **integrationTest**  
  Zawiera przykładowe scenariusze testowe.

- **api**  
  Zawiera przykładowe rest kontrolery, które wywołują bezpośrednio ksef clienta.




## Zawartość repozytorium

- Przykładowa aplikacja
- Implementacja klienta KSeF 2.0
- Przykłady użycia i integracji z KSeF 2.0


## Pakiety maven

Projekt ksef-client jest dostępny jako pakiet maven w GitHub Packages organizacji CIRFMF.

Należy najpierw skonfigurować dostęp do paczek Maven opublikowanych w GitHub Packages organizacji CIRFMF.
Wymaga to autoryzacji przy pomocy osobistego tokena dostępu (Personal Access Token – PAT) z uprawnieniem read:packages.
Dokładny poradnik jest dostępny w pliku [*maven-packege.md*](https://github.com/CIRFMF/ksef-client-java/blob/main/maven-package.md).
