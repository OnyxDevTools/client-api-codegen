package dev.onyx.codegen.http.example;

import dev.onyx.codegen.http.*;

public class ExampleApi
{
    protected IHttpRestClient restClient = new RestClient();

    protected RestClientConfig config;

    private ExampleApi(){}; // must use config constructor

    public ExampleApi(RestClientConfig config)
    {
        this.config = config;
    }

    public Example getExampleById(String id) throws ApiClientException
    {
        return getExampleResponseById(id).getBody();
    }

    public ApiResponse<Example> getExampleResponseById(String id) throws ApiClientException
    {
        return restClient.executeGET(
                config,
                "example/{id}",
                Example.class,
                200
        );
    }
}
