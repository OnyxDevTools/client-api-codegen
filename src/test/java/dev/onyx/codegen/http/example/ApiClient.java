package dev.onyx.codegen.http.example;

import dev.onyx.codegen.http.RestClientConfig;

public class ApiClient
{
    private RestClientConfig config = new RestClientConfig();

    public RestClientConfig config()
    {
        return config;
    }

    public ExampleApi exampleApi()
    {
        return new ExampleApi(config);
    }
}
