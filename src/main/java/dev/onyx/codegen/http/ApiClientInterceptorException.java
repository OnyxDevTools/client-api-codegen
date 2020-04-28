package dev.onyx.codegen.http;

import java.text.MessageFormat;

public class ApiClientInterceptorException extends ApiClientException
{

    private static final String MESSAGE_PATTERN = "An Exception occurred when invoking interceptor {0} '{1}': {2}";

    public ApiClientInterceptorException(String endpoint, Exception e)
    {
        super(MessageFormat.format(MESSAGE_PATTERN, endpoint, e.getMessage()));
    }
}
