package dev.onyx.codegen.models;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;

public abstract class AbstractModel
{
    public Handlebars handlebars = new Handlebars();

    public String content;

    public String generate() throws IOException
    {
        Template template = handlebars.compileInline(content);

        return template.apply(this);
    }
}
