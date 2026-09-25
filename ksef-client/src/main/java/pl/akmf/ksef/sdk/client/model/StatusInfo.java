package pl.akmf.ksef.sdk.client.model;

import java.util.List;
import java.util.Map;

/**
 * StatusInfo, InvoiceStatusInfo.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class StatusInfo {

    /**
     * Kod statusu
     */
    private Integer code;

    /**
     * Opis statusu
     */
    private String description;

    /**
     * Dodatkowe szczegóły statusu
     */
    private List<String> details;

    // tylko w InvoiceStatusInfo
    private Map<String, String> extensions;

    public StatusInfo() {
    }

    public StatusInfo(Integer code, String description, List<String> details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }

    public StatusInfo(Integer code, String description, List<String> details, Map<String, String> extensions) {
        this.code = code;
        this.description = description;
        this.details = details;
        this.extensions = extensions;
    }

    /**
     * Kod statusu
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Kod statusu
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Opis statusu
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis statusu
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Dodatkowe szczegóły statusu
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Dodatkowe szczegóły statusu
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }

    public Map<String, String> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, String> extensions) {
        this.extensions = extensions;
    }
}
