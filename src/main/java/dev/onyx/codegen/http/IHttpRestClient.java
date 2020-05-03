package dev.onyx.codegen.http;


import dev.onyx.codegen.models.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IHttpRestClient
{
    <T> CompletableFuture<ApiResponse<T>> execute(String requestMethod,
                                              RestClientConfig config,
                                              String resourcePath,
                                              int expectedSuccessStatusCode,
                                              List<Pair<String, String>> headers);


}
