package dev.onyx.codegen.http;

import dev.onyx.codegen.models.HttpHeader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ApiRequestBuilder<T> {

    private IHttpRestClient restClient;

    private String resourcePath;

    private int expectedSuccessStatusCode;

    private List<HttpHeader> headers;

    private Consumer<ApiResponse> onCompletion;

    public ApiRequestBuilder(IHttpRestClient restClient, String resourcePath) {
        this.restClient = restClient;
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public ApiRequestBuilder resourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    public int getExpectedSuccessStatusCode() {
        return expectedSuccessStatusCode;
    }

    public ApiRequestBuilder expectedSuccessStatusCode(int expectedSuccessStatusCode) {
        this.expectedSuccessStatusCode = expectedSuccessStatusCode;
        return this;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public ApiRequestBuilder headers(List<HttpHeader> headers) {
        this.headers = headers;
        return this;
    }

    public Consumer<ApiResponse> getOnCompletion() {
        return onCompletion;
    }

    public ApiRequestBuilder onCompletion(Consumer<ApiResponse> onCompletion) {
        this.onCompletion = onCompletion;
        return this;
    }

    public ApiResponse<T> get(){

    }

    public CompletableFuture<ApiResponse> get(Consumer<ApiResponse> onCompletion){
        restClient.execute()
    }

}
