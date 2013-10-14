package de.vonmusil.cliptool.command.impl;

import static java.text.MessageFormat.format;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.vonmusil.cliptool.command.Command;
import de.vonmusil.cliptool.command.CommandProcessor;

public class SimpleInsertCommand implements Command
{

    private final String name;
    private final String value;

    public SimpleInsertCommand(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return format("{0} ({1})", name, value);
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String getValue()
    {
        return value;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public CommandProcessor getProcessor()
    {
        return new SimpleInsertProcessor(this);
    }
    
    
    
}
