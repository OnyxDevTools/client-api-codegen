package dev.onyx.codegen.http;

public interface IHttpRestClient<T>
{
    ApiResponse<T> executeGET(RestClientConfig config,
                              String resourcePath,
                              Class desiredResponseType,
                              int expectedSuccessStatusCode) throws ApiClientException;

}
