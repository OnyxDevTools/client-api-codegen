package dev.onyx.codegen.http;

import dev.onyx.codegen.models.Pair;

import java.io.BufferedReader;
import java.util.List;

public class ApiResponse<T>
{

    private int statusCode;

    private T body;

    private List<Pair<String, String>> headers;
    

    public ApiResponse(BufferedReader response)
    {
        System.out.print(response);
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body)
    {
        this.body = body;
    }

    public List<Pair<String, String>> getHeaders()
    {
        return headers;
    }

    public void setHeaders(List<Pair<String, String>> headers)
    {
        this.headers = headers;
    }
}
