package dev.onyx.codegen.http;

import java.text.MessageFormat;

public class ApiClientException extends Exception
{
    private static final String MESSAGE_PATTERN = "An Exception occurred when calling {0} '{1}': {2}";

    public ApiClientException(final String httpMethod, final String endpoint, final String message, Throwable cause)
    {
        super(MessageFormat.format(MESSAGE_PATTERN, httpMethod, endpoint, message), cause);
    }

    public ApiClientException(final String httpMethod, final String endpoint, final String message)
    {
        this(httpMethod, endpoint, message, null);
    }

    public ApiClientException(final String httpMethod, final String endpoint, final Exception cause)
    {
        super(MessageFormat.format(MESSAGE_PATTERN, httpMethod, endpoint, cause.getMessage()), cause);
    }
}
