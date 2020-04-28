package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestClient implements IHttpRestClient
{
    @Override
    public ApiResponse<?> executeGET(
            RestClientConfig config,
            String resourcePath,
            Class desiredResponseType,
            int expectedSuccessStatusCode
    ) throws ApiClientException
    {
        return this.execute("GET", config, resourcePath, expectedSuccessStatusCode);
    }

    public ApiResponse<?> execute (
                   final String requestMethod,
                   final RestClientConfig config,
                   final String resourcePath,
                   int expectedSuccessStatusCode
    ) throws ApiClientException
    {
        final String endpoint = config.getBaseUrl() + "/" + resourcePath;
        final List<Pair<String, String>> headers = new ArrayList<>();

        try
        {
            final URL url = new URL(endpoint);

            invokeRequestInterceptors(config, url, headers);

            final HttpURLConnection conn = RestClientConnectionFactory.connect(config, requestMethod, url, headers);

            final ObjectInputStream response = new ObjectInputStream(conn.getInputStream());

            if (conn.getResponseCode() != expectedSuccessStatusCode)
            {
                throw new ApiClientException(requestMethod, endpoint, "Unexpected Response Status Code: " + conn.getResponseCode());
            }

            return new ApiResponse<>(response) ;
        }
        catch(IOException ioe)
        {
            throw new ApiClientException(requestMethod, endpoint, ioe);
        }
    }

    private void invokeRequestInterceptors(RestClientConfig config, URL url, List<Pair<String, String>> headers)
    {
        final Iterator<IRequestInterceptor> itr = config.getRequestInterceptors().iterator();

        while(itr.hasNext())
        {
            itr.next().execute(url, headers);
        }
    }
}
