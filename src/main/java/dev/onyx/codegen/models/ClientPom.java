package dev.onyx.codegen.models;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.maven.model.Model;

import java.io.FileWriter;
import java.io.IOException;

public class ClientPom extends Model
{
    public String templateFile = "pom";

    public Handlebars handlebars = new Handlebars();

    public String generate() throws IOException
    {
        Handlebars handlebars = new Handlebars();

        Template template = handlebars.compile(templateFile);

        return template.apply(this);
    }

    public String generate(String fileAbsolutePath) throws IOException
    {
        final String fileContent = generate();
        try
        {
            FileWriter writer = new FileWriter(fileAbsolutePath);
            writer.write(fileContent);
            writer.close();
            System.out.println("Successfully wrote to the file: " + fileAbsolutePath);
        }
        catch (IOException e)
        {
            System.out.println("An error occurred writing to the file: " + fileAbsolutePath);

            throw e;
        }

        return fileContent;
    }
}
