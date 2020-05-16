package dev.onyx.codegen.http.example;

import dev.onyx.codegen.http.*;
import dev.onyx.codegen.models.HttpHeader;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;


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

    public ApiResponse<Example> getExampleResponseById(final String id, final List<HttpHeader> headers)
    {
        return restClient.request(MessageFormat.format("example/{0}",id))
                .headers(headers)
                .get();
    }

    public Example getExampleById(final String id) throws ApiClientException {
        CompletableFuture<Example> response = asyncGetExampleById(id, null);
        try {
            return response.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApiClientException(e.getMessage());
        }
    }


    public Example getExampleById(final String id, final List<HttpHeader> headers) throws ApiClientException
    {
        CompletableFuture<Example> response = asyncGetExampleById(id,  headers);
        try {
            return response.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApiClientException(e.getMessage());
        }
    }

    public CompletableFuture<Example> asyncGetExampleById(final String id, final List<HttpHeader> headers)
    {
        final CompletableFuture<Example> future = new CompletableFuture<>();

        CompletableFuture<ApiResponse<Example>> response =  getExampleResponseById(id,  headers);
        response.thenAccept(new Consumer<ApiResponse<Example>>() {
            @Override
            public void accept(ApiResponse<Example> exampleApiResponse) {
                future.complete(exampleApiResponse.getBody());
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                future.completeExceptionally(throwable);
                return null;
            }
        });


        return future;
    }
}