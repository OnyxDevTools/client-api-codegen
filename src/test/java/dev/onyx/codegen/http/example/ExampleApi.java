package dev.onyx.codegen.http.example;

import dev.onyx.codegen.http.*;
import javafx.util.Pair;

import java.util.List;

/**
 * this file is an example of the generated output
 */
public class ExampleApi
{
    protected IHttpRestClient restClient = new RestClient();

    protected RestClientConfig config;

    private ExampleApi(){}; // must use config constructor

    public ExampleApi(RestClientConfig config)
    {
        this.config = config;
    }

    public ApiResponse<Example> getExampleResponseById(final String id, final List<Pair<String, String>> headers) throws ApiClientException
    {
        return (ApiResponse<Example>) restClient.execute(
                "GET",
                config,
                "example/{id}",
                Example.class,
                200,
                headers
        );
    }

    public Example getExampleById(final String id) throws ApiClientException
    {
        return getExampleById(id, null);
    }

    public Example getExampleById(final String id, final List<Pair<String, String>> headers) throws ApiClientException
    {
        ApiResponse<Example> response =  getExampleResponseById(id,  headers);

        return (response != null) ? response.getBody() : null;
    }
}
