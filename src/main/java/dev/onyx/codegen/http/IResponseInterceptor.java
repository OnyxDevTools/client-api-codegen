package dev.onyx.codegen.http;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.util.List;

public interface IResponseInterceptor
{
    public void execute(BufferedReader response, RestClientConfig config, String endpoint, List<Pair<String, String>> headers) throws ApiClientInterceptorException;
}
