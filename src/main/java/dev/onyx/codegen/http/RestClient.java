package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestClient implements IHttpRestClient
{
    public ApiResponse<?> execute(String requestMethod, RestClientConfig config, String resourcePath, Class desiredResponseType, int expectedSuccessStatusCode, List<Pair<String, String>> headers) throws ApiClientException
    {
        final String endpoint = config.getBaseUrl() + "/" + resourcePath;

        headers = (headers == null) ? new ArrayList<>() : headers;

        try
        {
            final URL url = new URL(endpoint);

            invokeRequestInterceptors(config, endpoint, headers);

            final HttpURLConnection conn = RestClientConnectionFactory.connect(config, requestMethod, url, headers);

            final BufferedReader response =  new BufferedReader(new InputStreamReader(conn.getInputStream()));

            invokeResponseInterceptors(response, config, endpoint, headers);

            if (conn.getResponseCode() != expectedSuccessStatusCode)
            {
                throw new ApiClientException(requestMethod, endpoint, "Unexpected Response Status Code: " + conn.getResponseCode());
            }

            return new ApiResponse<>(response);

        } catch (IOException ioe)
        {
            throw new ApiClientException(requestMethod, endpoint, ioe);
        }
    }

    private void invokeRequestInterceptors(final RestClientConfig config, final String endpoint, final List<Pair<String, String>> headers) throws ApiClientInterceptorException
    {
        final Iterator<IRequestInterceptor> itr = config.getRequestInterceptors().iterator();

        while (itr.hasNext())
        {
            itr.next().execute(config, endpoint, headers);
        }
    }

    private void invokeResponseInterceptors(BufferedReader response, final RestClientConfig config, final String endpoint, final List<Pair<String, String>> headers) throws ApiClientInterceptorException
    {
        final Iterator<IResponseInterceptor> itr = config.getResponseInterceptors().iterator();

        while (itr.hasNext())
        {
            itr.next().execute(response, config, endpoint, headers);
        }
    }

}