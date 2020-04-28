package dev.onyx.codegen.util;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.converter.SwaggerConverter;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SwaggerUtils
{

    public static String getSpecContent(File spec)
    {
        final String specContent;

        try {
            specContent = new String (Files.readAllBytes( Paths.get(spec.getAbsolutePath()) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("cannot read content of: " + spec.getAbsolutePath());
        }

        return specContent;
    }

    public static int getSpecVersion(File spec)
    {
        final String specContent = getSpecContent(spec);

        return getSpecVersion(specContent);
    }

    public static int getSpecVersion(String specContent)
    {
        if(specContent.contains("\"swagger\": \"2."))
        {
            return 2;
        }
        else if(specContent.contains("openapi:") || specContent.contains("\"openapi\":"))
        {
            return 3;
        }

        throw new RuntimeException("Invalid spec version. bust be 1.0 or 2.0");
    }

    public static OpenAPI convertToLatestSpec(File spec, boolean writeNextToOrig) throws IOException
    {
        final String specContent = getSpecContent(spec);
        final int specVersion = getSpecVersion(specContent);

        final SwaggerConverter converter = new SwaggerConverter();
        final SwaggerParseResult parseResult = converter.readContents(specContent, null, null);
        final OpenAPI latestSpec = parseResult.getOpenAPI();

        if(latestSpec == null)
        {
            throw new RuntimeException("Could not parse proved swagger to open API. " + parseResult.getMessages().toString());
        }

        if(specVersion == 2 && writeNextToOrig)
        {
            final String v3FileContent = Yaml.mapper().writeValueAsString(latestSpec);
            String baseName = FilenameUtils.getBaseName(spec.getAbsolutePath());

            if(baseName.contains(".v2")){
                baseName = baseName.replace(".v2", "");
            }

            final String v3FileAbsolutePath = spec.getParent() + File.separator + baseName + ".v3.yml";
            Files.write(Paths.get(v3FileAbsolutePath), v3FileContent.getBytes());
        }

        return latestSpec;
    }

}
