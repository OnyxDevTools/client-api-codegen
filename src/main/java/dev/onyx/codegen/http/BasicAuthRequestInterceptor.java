package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.net.URL;
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

    protected Pair<String, String> createAuthHeader()
    {
        this.creds = (creds != null) ? creds : username + ":" + password;

        return new Pair<>("Authentication", "Basic " + new Base64.Encoder().encode(this.creds.getBytes()));
    }

    @Override
    public void execute(URL request, List<Pair<String, String>> headers)
    {
        headers.add(this.createAuthHeader());
    }
}
