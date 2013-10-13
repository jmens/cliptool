package de.vonmusil.clipper.command.impl;

import static java.text.MessageFormat.format;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.clipper.command.Command;
import de.vonmusil.clipper.command.CommandProcessor;

public class FileCreationCommand implements Command
{
	private final String name;
	private final String templatePath;

	private static final Logger LOG = LoggerFactory.getLogger(FileCreationCommand.class);

	public FileCreationCommand(String name, String templateUrl)
	{
		super();
		this.name = name;
		this.templatePath = templateUrl;
	}

	public String getTemplatePath()
	{
		return templatePath;
	}

	@Override
	public CommandProcessor getProcessor()
	{
		return new FileCreationProcessor(templatePath);
	}

	@Override
	public String toString()
	{
		return format("{0}", name);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
