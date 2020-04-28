package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.util.List;

public interface IHttpRestClient
{
    ApiResponse<?> execute(
                            String requestMethod,
                            RestClientConfig config,
                            String resourcePath,
                            Class desiredResponseType,
                            int expectedSuccessStatusCode,
                            List<Pair<String, String>> headers
    ) throws ApiClientException;

}
