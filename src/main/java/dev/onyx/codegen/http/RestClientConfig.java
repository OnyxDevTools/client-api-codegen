package dev.onyx.codegen.http;

import java.util.LinkedHashSet;

public class RestClientConfig
{
    private LinkedHashSet<IRequestInterceptor> requestInterceptors = new LinkedHashSet<>();

    private String protocol;

    private String host;

    private String baseFilePath;

    private String port;

    private int connectionTimeoutMs = 3000;

    private int requestTimeoutMs = 3000;

    public RestClientConfig addRequestInterceptor(IRequestInterceptor interceptor)
    {
        requestInterceptors.add(interceptor);

        return this;
    }

    public LinkedHashSet<IRequestInterceptor> getRequestInterceptors()
    {
        return requestInterceptors;
    }

    public RestClientConfig requestInterceptors(LinkedHashSet<IRequestInterceptor> requestInterceptors)
    {
        this.requestInterceptors = requestInterceptors;

        return this;
    }

    public String getBaseUrl()
    {
        final StringBuilder builder = new StringBuilder()
                .append(protocol +"://")
                .append(host);

        if(port != null)
        {
            builder.append(":" + port);
        }

        if(baseFilePath != null)
        {
            builder.append("/" + baseFilePath);
        }

        builder.append("/");

        return builder.toString();
    }

    public String getProtocol()
    {
        return protocol;
    }

    public RestClientConfig protocol(String protocol)
    {
        this.protocol = protocol;

        return this;
    }

    public String getHost()
    {
        return host;
    }

    public RestClientConfig host(String host)
    {
        this.host = host;

        return this;
    }

    public String getBaseFilePath()
    {
        return baseFilePath;
    }

    public RestClientConfig baseFilePath(String baseFilePath)
    {
        this.baseFilePath = baseFilePath;

        return this;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public int getConnectionTimeoutMs()
    {
        return connectionTimeoutMs;
    }

    public RestClientConfig connectionTimeoutMs(int connectionTimeoutMs)
    {
        this.connectionTimeoutMs = connectionTimeoutMs;

        return this;
    }

    public int getRequestTimeoutMs()
    {
        return requestTimeoutMs;
    }

    public RestClientConfig requestTimeoutMs(int requestTimeoutMs)
    {
        this.requestTimeoutMs = requestTimeoutMs;

        return this;
    }

}
