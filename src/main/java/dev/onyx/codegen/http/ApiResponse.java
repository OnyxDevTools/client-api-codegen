package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.io.ObjectInputStream;
import java.util.List;

public class ApiResponse<T>
{

    private int statusCode;

    private T body;

    private List<Pair<String, String>> headers;
    

    public ApiResponse(ObjectInputStream response)
    {

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
