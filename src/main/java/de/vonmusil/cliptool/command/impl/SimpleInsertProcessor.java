package de.vonmusil.cliptool.command.impl;

import de.vonmusil.cliptool.command.CommandProcessor;
import de.vonmusil.cliptool.gui.mainframe.CliptoolClipboard;

public class SimpleInsertProcessor implements CommandProcessor
{

	private final SimpleInsertCommand command;

	public SimpleInsertProcessor(SimpleInsertCommand command)
	{
		this.command = command;
	}

	@Override
	public void process()
	{
		CliptoolClipboard.getInstance().setClipboardContent(command.getValue());
	}
}
