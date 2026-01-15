package pl.akmf.ksef.sdk.api;

import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private HttpUtils() {

    }

    public static String buildUrlWithParams(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return baseUrl;
        }

        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?");

        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {

                if (!first) {
                    url.append("&");
                }

                url.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                url.append("=");
                url.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                first = false;

            }
        }
        return url.toString();
    }

    public static String buildUrlWithParams(String baseUrl, List<KeyValue> keyValues) {
        if (keyValues == null || keyValues.isEmpty()) {
            return baseUrl;
        }

        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?");

        boolean first = true;
        for (KeyValue keyValue : keyValues) {
            if (StringUtils.isNotBlank(keyValue.getKey()) && StringUtils.isNotBlank(keyValue.getValue())) {

                if (!first) {
                    url.append("&");
                }

                url.append(URLEncoder.encode(keyValue.getKey(), StandardCharsets.UTF_8));
                url.append("=");
                url.append(URLEncoder.encode(keyValue.getValue(), StandardCharsets.UTF_8));
                first = false;

            }
        }
        return url.toString();
    }

    public static boolean isValidResponse(HttpResponse<byte[]> response,
                                          HttpStatus expectedStatus) {
        return expectedStatus.getCode() == response.statusCode();
    }

    public static String formatExceptionMessage(String operationId, int statusCode, byte[] body) {
        String exceptionMessage;
        if (body == null || body.length == 0) {
            exceptionMessage = "[no body]";
        } else {
            exceptionMessage = new String(body, StandardCharsets.UTF_8);
        }
        return operationId + " call failed with: " + statusCode + " - " + exceptionMessage;
    }

    public static class KeyValue {
        private final String key;
        private final String value;

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
