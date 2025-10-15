package pl.akmf.ksef.sdk.util;

import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class HttpClientConfig {
    private Duration connectTimeout = Duration.ofSeconds(5);
    private HttpClient.Version version = HttpClient.Version.HTTP_2;
    private HttpClient.Redirect followRedirects = HttpClient.Redirect.NORMAL;
    private ExecutorService executor = ForkJoinPool.commonPool();
    private ProxySelector proxySelector;

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public HttpClientConfig setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpClient.Version getVersion() {
        return version;
    }

    public HttpClientConfig setVersion(HttpClient.Version version) {
        this.version = version;
        return this;
    }

    public HttpClient.Redirect getFollowRedirects() {
        return followRedirects;
    }

    public HttpClientConfig setFollowRedirects(HttpClient.Redirect followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public HttpClientConfig setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public ProxySelector getProxySelector() {
        return proxySelector;
    }

    public HttpClientConfig setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }
}
