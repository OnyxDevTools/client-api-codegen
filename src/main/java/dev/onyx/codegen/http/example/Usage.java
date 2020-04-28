package dev.onyx.codegen.http.example;

import dev.onyx.codegen.http.BasicAuthRequestInterceptor;
import dev.onyx.codegen.http.IHttpRestClient;
import dev.onyx.codegen.http.RestClient;

public class Usage {

    public void test()
    {
        IHttpRestClient client = new RestClient();

        client.config()
                .protocol("https")
                .host("localhost")
                .baseFilePath("v2/rest")
                .connectionTimeoutMs(3000)
                .addRequestInterceptor(new BasicAuthRequestInterceptor("username", "password"));

        //client.issuesApi().
    }
}
