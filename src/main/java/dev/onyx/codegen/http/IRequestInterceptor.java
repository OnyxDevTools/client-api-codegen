package dev.onyx.codegen.http;

import javafx.util.Pair;
import java.util.List;
import java.net.URL;

public interface IRequestInterceptor
{
    public void execute(URL request, List<Pair<String, String>> headers);
}
