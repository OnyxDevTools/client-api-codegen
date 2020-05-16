package dev.onyx.codegen.http;

import dev.onyx.codegen.models.HttpHeader;

import java.io.BufferedReader;
import java.util.List;

public interface IResponseInterceptor
{
    void execute(BufferedReader response, RestClientConfig config, String endpoint, List<HttpHeader> headers) throws ApiClientInterceptorException;
}
