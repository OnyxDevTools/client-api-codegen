package dev.onyx.codegen.http;


import dev.onyx.codegen.models.HttpHeader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IHttpRestClient
{
    ApiRequestBuilder request(String resourcePath);

    <T> CompletableFuture<ApiResponse<T>> execute(String requestMethod,
                                                         String resourcePath,
                                                         int expectedSuccessStatusCode,
                                                         List<HttpHeader> headers);


}
