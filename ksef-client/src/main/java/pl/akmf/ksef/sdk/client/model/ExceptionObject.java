package pl.akmf.ksef.sdk.client.model;

import pl.akmf.ksef.sdk.client.ExceptionDetails;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * ExceptionObject.
 * Zawiera szczegółowe metadane wyjątku, w tym kod, opis i znacznik czasu.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code ExceptionInfo}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ExceptionObject {

    /**
     * Lista szczegółów wyjątków opisujących poszczególne problemy.
     */
    private List<ExceptionDetails> exceptionDetailList;

    /**
     * Numer referencyjny służący do korelacji żądania i błędu.
     */
    private String referenceNumber;

    /**
     * Unikalny kod reprezentujący instancję usługi, która wygenerowała błąd.
     */
    private String serviceCode;

    /**
     * Dodatkowy kontekst usługi
     */
    private String serviceCtx;

    /**
     * Nazwa usługi, w której wystąpił błąd.
     */
    private String serviceName;

    /**
     * Znacznik czasu wystąpienia wyjątku.
     */
    private OffsetDateTime timestamp;

    public ExceptionObject() {
    }

    /**
     * Lista szczegółów wyjątków opisujących poszczególne problemy.
     */
    public List<ExceptionDetails> getExceptionDetailList() {
        return exceptionDetailList;
    }

    /**
     * Lista szczegółów wyjątków opisujących poszczególne problemy.
     */
    public void setExceptionDetailList(List<ExceptionDetails> exceptionDetailList) {
        this.exceptionDetailList = exceptionDetailList;
    }

    /**
     * Numer referencyjny służący do korelacji żądania i błędu.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny służący do korelacji żądania i błędu.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Unikalny kod reprezentujący instancję usługi, która wygenerowała błąd.
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Unikalny kod reprezentujący instancję usługi, która wygenerowała błąd.
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * Dodatkowy kontekst usługi
     */
    public String getServiceCtx() {
        return serviceCtx;
    }

    /**
     * Dodatkowy kontekst usługi
     */
    public void setServiceCtx(String serviceCtx) {
        this.serviceCtx = serviceCtx;
    }

    /**
     * Nazwa usługi, w której wystąpił błąd.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Nazwa usługi, w której wystąpił błąd.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Znacznik czasu wystąpienia wyjątku.
     */
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Znacznik czasu wystąpienia wyjątku.
     */
    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "exceptionDetailList=" + exceptionDetailList + ", referenceNumber='" + referenceNumber + '\'' + ", serviceCode='" + serviceCode + '\'' + ", serviceCtx='" + serviceCtx + '\'' + ", serviceName='" + serviceName + '\'' + ", timestamp=" + timestamp;
    }
}
