package dev.onyx.codegen.generators;

import dev.onyx.codegen.models.ClientPom;
import dev.onyx.codegen.util.SwaggerUtils;
import dev.onyx.codegen.util.Validate;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.io.IOException;

@Data
@SuperBuilder
public class ApiClientGenerator extends MavenProjectGenerator
{

    private File spec;
    private OpenAPI latestSpec;
    private boolean generateLatestSpecFile = true;

    public void validate()
    {
    }

    public void generate() throws IOException
    {
        super.generate();
        createPom();
        latestSpec = SwaggerUtils.convertToLatestSpec(spec, generateLatestSpecFile);
        mapLatestSpecToClientModel();
    }

    private void mapLatestSpecToClientModel()
    {
        Class clazz = new Class();

    }

    private void createPom() throws IOException
    {
       final ClientPom pom = new ClientPom();

       validatePomAttributes();

       pom.setArtifactId(artifactId);
       pom.setGroupId(groupId);
       pom.setVersion(version);

       pom.generate(outputDirectory.getAbsolutePath() + File.separator + "pom.xml");
    }

    private void validatePomAttributes()
    {
        Validate.notEmpty(artifactId, "clientArtifactId");
        Validate.notEmpty(groupId, "clientGroupId");
        Validate.notEmpty(version, "clientVersion");
        Validate.notEmpty(version, "clientHttpComponentsVersion");
    }
}
