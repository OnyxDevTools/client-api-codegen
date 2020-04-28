package dev.onyx.codegen.util;

import org.codehaus.plexus.util.StringUtils;

public class Validate
{

    public static void notEmpty(String val, String configParameter)
    {
        if(StringUtils.isEmpty(val))
        {
            throw new RuntimeException("Missing configuration for parameter: " + configParameter);
        }
    }
}