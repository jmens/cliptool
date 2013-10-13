package de.vonmusil.clipper.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.vonmusil.clipper.gui.mainframe.paraminput.ParamEditModel.Parameter;

public class TemplateProcessor
{
    private String template;
    private final List<Parameter> bindings = new ArrayList<>();

    private TemplateProcessor()
    {
        super();
    }
    
    public static TemplateProcessor newProcessor()
    {
        return new TemplateProcessor();
    }

    public String process()
    {
        String result = template;
        for (final Parameter parameter : bindings)
        {
            result = result.replaceAll("\\$\\{" + parameter.getName() + "\\}", parameter.getValue());
        }
        
        return result;
    }

    public TemplateProcessor setTemplate(String template)
    {
        this.template = template;
        return this;
    }

    public TemplateProcessor bind(String variable, String value)
    {
        this.bindings.add(new Parameter(variable, value));
        
        return this;
    }

    public TemplateProcessor bind(Set<Parameter> bindings)
    {
        this.bindings.addAll(bindings);
        
        return this;
    }
    
    
 }
