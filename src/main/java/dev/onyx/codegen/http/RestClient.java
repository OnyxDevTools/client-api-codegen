package dev.onyx.codegen.http;

import dev.onyx.codegen.models.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RestClient implements IHttpRestClient
{

    private HttpURLConnectionPool connectionPool = null;

    private RestClientConfig config;

    public RestClient(RestClientConfig config) {
        this.config = config;
    }

    public ApiRequestBuilder request(String resourcePath)
    {
        return new ApiRequestBuilder(this, resourcePath);
    }

    public <T> CompletableFuture<ApiResponse<T>> execute(String requestMethod,
                                  String resourcePath,
                                  int expectedSuccessStatusCode,
                                  List<HttpHeader> headers

    ) {

        final CompletableFuture<ApiResponse<T>> future = new CompletableFuture<>();
        final String endpoint = config.getBaseUrl() + "/" + resourcePath;
        final List<HttpHeader> requestHeaders = (headers == null) ? new ArrayList<>() : headers;

        try {
            synchronized (this) {
                if (connectionPool == null) {
                    final URL url = new URL(endpoint);
                    connectionPool = new HttpURLConnectionPool(10, () -> {
                        try {
                            return RestClientConnectionFactory.connect(config, requestMethod, url, requestHeaders);
                        } catch (ApiClientException e) {
                            future.completeExceptionally(e);
                            return null;
                        }
                    });
                }
            }

            connectionPool.acquireConnectionThen(httpConnection -> {
                try {
                    invokeRequestInterceptors(config, endpoint, requestHeaders);
                    final BufferedReader response = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    invokeResponseInterceptors(response, config, endpoint, requestHeaders);

                    if (httpConnection.getResponseCode() != expectedSuccessStatusCode) {
                        future.completeExceptionally(new ApiClientException(requestMethod, endpoint, "Unexpected Response Status Code: " + httpConnection.getResponseCode()));
                        return;
                    }

                    future.complete(new ApiResponse<>(response));
                } catch (ApiClientException | IOException apiClientException) {
                    future.completeExceptionally(new ApiClientException(requestMethod, endpoint, apiClientException));
                }
            });
        } catch (IOException | InterruptedException e) {
            future.completeExceptionally(new ApiClientException(requestMethod, endpoint, e));
        }

        return future;
    }

    private void invokeRequestInterceptors(final RestClientConfig config, final String endpoint, final List<HttpHeader> headers) throws ApiClientInterceptorException
    {

        for (IRequestInterceptor iRequestInterceptor : config.getRequestInterceptors()) {
            iRequestInterceptor.execute(config, endpoint, headers);
        }
    }

    private void invokeResponseInterceptors(BufferedReader response, final RestClientConfig config, final String endpoint, final List<HttpHeader> headers) throws ApiClientInterceptorException
    {

        for (IResponseInterceptor iResponseInterceptor : config.getResponseInterceptors()) {
            iResponseInterceptor.execute(response, config, endpoint, headers);
        }
    }

}