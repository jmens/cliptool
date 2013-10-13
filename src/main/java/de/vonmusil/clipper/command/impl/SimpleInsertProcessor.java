package de.vonmusil.clipper.command.impl;

import de.vonmusil.clipper.command.CommandProcessor;
import de.vonmusil.clipper.gui.mainframe.CliptoolClipboard;

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
