package dev.onyx.codegen.http;

import dev.onyx.codegen.models.Pair;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

public class BasicAuthRequestInterceptor implements IRequestInterceptor
{

    private String username = null;
    private String password = null;
    private String creds = null;

    public BasicAuthRequestInterceptor(String username, String password)
    {
        this.creds(username, password);
    }

    public BasicAuthRequestInterceptor username(String username)
    {
        this.username = username;

        return this;
    }

    public BasicAuthRequestInterceptor password(String password)
    {
        this.password = password;

        return this;
    }

    public BasicAuthRequestInterceptor creds(String username, String password)
    {
        this.username = username;
        this.password = password;

        creds = username + ":" + password;

        return this;
    }

    protected Pair<String, String> createAuthHeader() throws UnsupportedEncodingException
    {
        this.creds = (creds != null) ? creds : username + ":" + password;

        return new Pair<>("Authentication", "Basic " + Base64.getEncoder().encodeToString(this.creds.getBytes("utf-8")));
    }

    @Override
    public void execute(RestClientConfig config, String endpoint, List<Pair<String, String>> headers) throws ApiClientInterceptorException {
        try
        {
            headers.add(this.createAuthHeader());
        } catch (Exception e)
        {
            throw new ApiClientInterceptorException(endpoint, e);
        }
    }
}
