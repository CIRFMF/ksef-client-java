package pl.akmf.ksef.sdk.client.model;

import pl.akmf.ksef.sdk.client.ExceptionDetails;

import java.time.OffsetDateTime;
import java.util.List;

// Zawiera szczegółowe metadane wyjątku, w tym kod, opis i znacznik czasu.
public class ExceptionObject {
    // Lista szczegółów wyjątków opisujących poszczególne problemy.
    private List<ExceptionDetails> exceptionDetailList;
    // Numer referencyjny służący do korelacji żądania i błędu.
    private String referenceNumber;
    // Unikalny kod reprezentujący instancję usługi, która wygenerowała błąd.
    private String serviceCode;
    // Dodatkowy kontekst usługi
    private String serviceCtx;
    // Nazwa usługi, w której wystąpił błąd.
    private String serviceName;
    // Znacznik czasu wystąpienia wyjątku.
    private OffsetDateTime timestamp;

    public ExceptionObject() {

    }

    public List<ExceptionDetails> getExceptionDetailList() {
        return exceptionDetailList;
    }

    public void setExceptionDetailList(List<ExceptionDetails> exceptionDetailList) {
        this.exceptionDetailList = exceptionDetailList;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceCtx() {
        return serviceCtx;
    }

    public void setServiceCtx(String serviceCtx) {
        this.serviceCtx = serviceCtx;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "exceptionDetailList=" + exceptionDetailList +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", serviceCtx='" + serviceCtx + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", timestamp=" + timestamp;
    }
}
