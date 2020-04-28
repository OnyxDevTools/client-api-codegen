package dev.onyx.codegen.generators;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.IOException;

@Data
@SuperBuilder
public class MavenProjectGenerator
{

    protected File outputDirectory;
    protected String groupId;
    protected String artifactId;
    protected String version;
    protected File groupDir;
    protected File artifactDir;
    protected String groupPath;
    protected String artifactPath;

    public void validate()
    {

    }

    public void generate() throws IOException
    {
        createRootOfProject();
        createGroupFolders();
    }

    private void createGroupFolders()
    {
        groupPath = outputDirectory.getAbsolutePath()
                + File.separator + "src" + File.separator + "main" + File.separator + "java"
                + File.separator + groupId.replace(".", File.separator);

        groupDir = new File(groupPath);
        artifactPath = groupDir + File.separator + artifactId.replace("-", "/");
        artifactDir = new File(artifactPath);

        if ( !groupDir.exists())
        {
            groupDir.mkdirs();
        }

        if ( !artifactDir.exists())
        {
            artifactDir.mkdirs();
        }
    }

    private void createRootOfProject()
    {
        if(StringUtils.isEmpty(artifactId))
        {
            artifactId = outputDirectory.getName();
        }

        if (!outputDirectory.getName().equals(artifactId))
        {
            outputDirectory = new File(outputDirectory.getAbsolutePath() + File.pathSeparator + artifactId);
        }

        if ( !outputDirectory.exists())
        {
            outputDirectory.mkdirs();
        }
    }
}
