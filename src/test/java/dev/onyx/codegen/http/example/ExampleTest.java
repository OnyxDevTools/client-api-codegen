package dev.onyx.codegen.http.example;

import dev.onyx.codegen.http.ApiClientException;
import dev.onyx.codegen.http.BasicAuthRequestInterceptor;
import dev.onyx.codegen.http.IRequestInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ExampleTest
{
    @Test
    public void exampleUsage() throws ApiClientException
    {
        final ApiClient client = new ApiClient();

        final IRequestInterceptor auth = new BasicAuthRequestInterceptor("username", "password");

        client.config()
                .protocol("https")
                .host("example.free.beeceptor.com")
                .baseFilePath("v2/rest")
                .connectionTimeoutMs(3000)
                .addRequestInterceptor(auth);

        final Example exampleModel = client.exampleApi().getExampleById("id");

        Assertions.assertThat(exampleModel).isNotNull();
    }
}