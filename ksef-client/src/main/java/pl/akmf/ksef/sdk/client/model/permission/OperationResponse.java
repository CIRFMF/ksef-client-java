package pl.akmf.ksef.sdk.client.model.permission;

import java.io.Serializable;

/**
 * OperationResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PermissionsOperationResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class OperationResponse implements Serializable {

    /**
     * Numer referencyjny operacji nadania lub odbierania uprawnień.
     */
    private String referenceNumber;

    public OperationResponse() {
    }

    /**
     * Numer referencyjny operacji nadania lub odbierania uprawnień.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny operacji nadania lub odbierania uprawnień.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
