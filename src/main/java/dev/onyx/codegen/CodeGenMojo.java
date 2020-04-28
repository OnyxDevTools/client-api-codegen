package dev.onyx.codegen;


import dev.onyx.codegen.generators.ApiClientGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

@Mojo( name = "generate-sources", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class CodeGenMojo
    extends AbstractMojo
{

    @Parameter( property = "generateClient")
    private boolean generateClient = false;

    @Parameter( property = "clientOutputDirectory")
    private File clientOutputDirectory;

    @Parameter( property = "clientArtifactId")
    private String clientArtifactId;

    @Parameter( property = "clientVersion")
    private String clientVersion;

    @Parameter( property = "clientGroupId")
    private String clientGroupId;

    @Parameter( property = "spec", required = true)
    private File spec;

    @Parameter( property = "generateLatestSpecFile")
    private Boolean generateLatestSpecFile = true;

    public void execute()
        throws MojoExecutionException
    {
        try
        {
            generateClient();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new MojoExecutionException( "Error when generating sources " + e.getMessage() , e );
        }
    }

    private void generateClient() throws IOException
    {
        System.out.println("--------------------------------[ onyx client codegen ]---------------------------------");

        final ApiClientGenerator clientGen = ApiClientGenerator
                .builder()
                .version(clientVersion)
                .groupId(clientGroupId)
                .outputDirectory(clientOutputDirectory)
                .artifactId(clientArtifactId)
                .spec(spec)
                .generateLatestSpecFile(generateLatestSpecFile)
                .build();

        clientGen.generate();
    }
}
