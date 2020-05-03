package dev.onyx.codegen.http;

import dev.onyx.codegen.models.Pair;

import java.util.List;

public interface IRequestInterceptor
{
    void execute(RestClientConfig config, String endpoint, List<Pair<String, String>> headers) throws ApiClientInterceptorException;
}
