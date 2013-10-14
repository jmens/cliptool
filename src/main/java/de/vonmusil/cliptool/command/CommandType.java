package de.vonmusil.cliptool.command;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import de.vonmusil.cliptool.command.impl.FileCreationCommand;
import de.vonmusil.cliptool.command.impl.OneLinerCommand;
import de.vonmusil.cliptool.command.impl.SimpleInsertCommand;

public enum CommandType
{
	UNKNOWN("", Void.class),
	CLIPBOARD_INSERT("insert", SimpleInsertCommand.class),
	TO_ONE_LINE("oneline", OneLinerCommand.class),
	FILE_CREATION("filecreate", FileCreationCommand.class);

	private String key;
	private final Class<?> commandClass;

	private CommandType(String key, Class<?> commandClass)
	{
		this.commandClass = commandClass;
		this.key = key.toLowerCase();
	}

	public String getKey()
	{
		return key;
	}

	public Class<?> getCommandClass()
	{
		return commandClass;
	}

	public static CommandType parse(Object key)
	{
		final String toParse = defaultIfNull(key, "").toString().toLowerCase();

		for (final CommandType task : CommandType.values())
		{
			if (task.getKey().equals(toParse))
			{
				return task;
			}
		}

		return UNKNOWN;
	}
}
