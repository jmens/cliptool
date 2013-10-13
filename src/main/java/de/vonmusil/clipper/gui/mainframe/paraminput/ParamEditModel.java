package de.vonmusil.clipper.gui.mainframe.paraminput;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

import de.vonmusil.swingapp.model.AbstractModel;

public class ParamEditModel extends AbstractModel
{
    private final Set<Parameter> parameters;
    private boolean cancelled = false;

    public ParamEditModel()
    {
        this.parameters = new HashSet<>();
    }

    public Set<Parameter> getParameters()
    {
        return parameters;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public static class Parameter extends AbstractModel
    {
        public static final String VALUE = "value";
        public static final String NAME = "name";

        private String name;
        private String value;

        public Parameter()
        {
            super();
        }

        public Parameter(String name, String value)
        {
            super();
            this.name = name;
            this.value = value;
        }

        @Override
        public int hashCode()
        {
            return HashCodeBuilder.reflectionHashCode(this, new String[] { "value" });
        }

        @Override
        public boolean equals(Object obj)
        {
         return EqualsBuilder.reflectionEquals(this, obj, new String[]{"value"});
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            final String oldName = name;
            this.name = name;
            firePropertyChange(NAME, oldName, name);
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            final String oldValue = this.value;
            this.value = value;
            firePropertyChange(VALUE, oldValue, value);
        }
    }
}
