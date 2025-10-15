package pl.akmf.ksef.sdk.util;

import java.net.http.HttpClient;

public class HttpClientBuilder {

    private HttpClientBuilder() {
    }

    public static HttpClient.Builder createHttpBuilder(HttpClientConfig config) {
        HttpClient.Builder builder = HttpClient.newBuilder()
                .connectTimeout(config.getConnectTimeout())
                .followRedirects(config.getFollowRedirects())
                .executor(config.getExecutor())
                .version(config.getVersion());

        if (config.getProxySelector() != null) {
            builder.proxy(config.getProxySelector());
        }

        return builder;
    }
}
