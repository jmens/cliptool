package de.vonmusil.clipper.command;

import static java.util.Collections.unmodifiableList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.clipper.command.impl.FileCreationCommand;
import de.vonmusil.clipper.command.impl.OneLinerCommand;
import de.vonmusil.clipper.command.impl.SimpleInsertCommand;

public class CommandRepository
{
	private static final Logger LOG = LoggerFactory.getLogger(CommandRepository.class);

	private static CommandRepository instance;

	private XMLConfiguration config;

	private List<Command> commands;

	public static CommandRepository instance()
	{
		if (instance == null)
		{
			instance = new CommandRepository();
			instance.initialize();
		}
		return instance;
	}

	private List<Command> parseCommands()
	{
		final List<Command> commands = new ArrayList<>();

		int i = 0;
		while (true)
		{
			final String name = config.getString("task(" + i + ").[@name]");
			final String type = config.getString("task(" + i + ").[@type]");

			LOG.debug("Scanning config: Task " + i + ": " + name + " - " + type);

			if (type == null)
			{
				break;
			}

			final CommandType commandType = CommandType.parse(type);

			switch (commandType)
			{
			case CLIPBOARD_INSERT:
				final String clipboardValue = config.getString("task(" + i + ").value");
				commands.add(new SimpleInsertCommand(name, clipboardValue));
				break;
			case FILE_CREATION:
				final String templateUrl = config.getString("task(" + i + ").value");
				commands.add(new FileCreationCommand(name, templateUrl));
				break;
			case TO_ONE_LINE: 
			    commands.add(new OneLinerCommand());
			    break;

			default:
				break;
			}

			i++;
		}

		return commands;
	}

	public List<Command> getCommands()
	{
		return unmodifiableList(commands);
	}

	public List<Command> getCommands(CommandType type)
	{
		final List<Command> result = new ArrayList<>();
		for (final Command command : commands)
		{
			if (type.getCommandClass().equals(command.getClass()))
			{
				result.add(command);
			}
		}
		return unmodifiableList(result);

	}

	public boolean initialize()
	{
		try
		{
			config = new XMLConfiguration();
			config.load("clipper.conf.xml");

			this.commands = parseCommands();

			return true;
		}
		catch (final ConfigurationException e)
		{
			LOG.error("Cannot load configuration file");

			return false;
		}
	}

	public boolean initialize(InputStream stream)
	{
		if (stream == null)
		{
			return false;
		}

		try
		{
			config = new XMLConfiguration();
			config.load(stream);
			config.setValidating(true);

			this.commands = parseCommands();

			return true;
		}
		catch (final ConfigurationException e)
		{
			LOG.error("Cannot load configuration from stream");

			return false;
		}
	}
}
