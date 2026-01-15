package pl.akmf.ksef.sdk.client.model;

import java.util.List;
import java.util.Map;

public class StatusInfo {
    private Integer code;
    private String description;
    private List<String> details;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

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
