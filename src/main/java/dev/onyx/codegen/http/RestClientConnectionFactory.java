package dev.onyx.codegen.http;

import dev.onyx.codegen.models.HttpHeader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public  class RestClientConnectionFactory
{

    public static HttpURLConnection connect(RestClientConfig config, String requestMethod, URL url, List<HttpHeader> headers) throws ApiClientException
    {
        HttpURLConnection conn = null;

        try
        {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setReadTimeout(config.getConnectionTimeoutMs());

            for(HttpHeader<String, String> pair : headers)
            {
                conn.addRequestProperty(pair.getKey(), pair.getValue());
            }

            return conn;
        }
        catch(IOException exception)
        {
            throw new ApiClientException(requestMethod, url.getPath(), exception);
        }
        finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
        }
    }
    
}