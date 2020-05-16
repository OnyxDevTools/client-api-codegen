package dev.onyx.codegen.http;

import dev.onyx.codegen.models.HttpHeader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

public class ApiRequestBuilder {

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

    public <T> ApiResponse<T> get() throws ExecutionException, InterruptedException {
        //noinspection unchecked
        return (ApiResponse<T>) restClient.execute("GET", this.resourcePath, this.expectedSuccessStatusCode, this.headers).get();
    }

    public <T> CompletableFuture<ApiResponse<T>> get(Consumer<ApiResponse<T>> onCompletion){
        CompletableFuture<ApiResponse<T>> future = restClient.execute("GET", this.resourcePath, this.expectedSuccessStatusCode, this.headers);
        future.thenAccept(onCompletion);
        future.exceptionally(throwable -> {
            // Figure out your exception handling
            onCompletion.accept(null);
            return null;
        });

        return future;
    }

}
