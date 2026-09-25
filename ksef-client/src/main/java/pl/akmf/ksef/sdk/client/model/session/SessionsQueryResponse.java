package pl.akmf.ksef.sdk.client.model.session;

import java.util.ArrayList;
import java.util.List;

/**
 * SessionsQueryResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SessionsQueryResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista sesji.
     */
    private List<SessionsQueryResponseItem> sessions = new ArrayList<>();

    public SessionsQueryResponse() {
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public String getContinuationToken() {
        return continuationToken;
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    /**
     * Lista sesji.
     */
    public List<SessionsQueryResponseItem> getSessions() {
        return sessions;
    }

    /**
     * Lista sesji.
     */
    public void setSessions(List<SessionsQueryResponseItem> sessions) {
        this.sessions = sessions;
    }
}
