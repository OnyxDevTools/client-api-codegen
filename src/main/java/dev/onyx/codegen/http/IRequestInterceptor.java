package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.util.List;

public interface IRequestInterceptor
{
    public void execute(RestClientConfig config, String endpoint, List<Pair<String, String>> headers) throws ApiClientInterceptorException;
}
