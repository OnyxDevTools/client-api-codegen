package dev.onyx.codegen.http;

import dev.onyx.codegen.models.HttpHeader;

import java.util.List;

public interface IRequestInterceptor
{
    void execute(RestClientConfig config, String endpoint, List<HttpHeader> headers) throws ApiClientInterceptorException;
}
