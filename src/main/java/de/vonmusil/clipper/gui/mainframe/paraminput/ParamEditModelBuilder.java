package de.vonmusil.clipper.gui.mainframe.paraminput;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.vonmusil.clipper.gui.mainframe.paraminput.ParamEditModel.Parameter;

public class ParamEditModelBuilder
{
    private final ParamEditModel model;
    
    private ParamEditModelBuilder()
    {
        this.model = new ParamEditModel();
    }

    public ParamEditModel create()
    {
        return model;
    }

    public static ParamEditModelBuilder newBuilder()
    {
        return new ParamEditModelBuilder();
    }

    public ParamEditModelBuilder addParameter(String name)
    {
        return this.addParameter(name, "");
    }

    public ParamEditModelBuilder addParameter(String name, String value)
    {
        this.model.getParameters().add(new Parameter(name, value));
        
        return this;
    }

    public ParamEditModelBuilder addParameters(Map<String, String> variables)
    {
        for (final Entry<String, String> entry : variables.entrySet())
        {
            addParameter(entry.getKey().toLowerCase(), entry.getValue());
        }
        
        return this;
    }

    public ParamEditModelBuilder addParameters(List<String> variables)
    {
        for (final String variable : variables)
        {
            addParameter(variable);
        }
        
        return this;
    }

}
