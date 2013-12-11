package de.vonmusil.cliptool.command.impl;

import de.vonmusil.cliptool.command.Command;
import de.vonmusil.cliptool.command.CommandProcessor;
import de.vonmusil.cliptool.gui.mainframe.CliptoolClipboard;

public class OneLinerCommand implements CommandProcessor, Command
{
	@Override
	public String toString()
	{
		return "oneline";
	}

	@Override
	public CommandProcessor getProcessor()
	{
		return this;
	}

	@Override
	public void process()
	{
		final String result = toSingleLine(CliptoolClipboard.getInstance().getClipboardContent());

		CliptoolClipboard.getInstance().setClipboardContent(result);
	}

	String toSingleLine(String input)
	{
		final String singleLined = input.replaceAll("\n", " ");
		final String deTabbed = singleLined.replaceAll("\t", " ");
		final String result = deTabbed.replaceAll(" {2,}", " ");

		return result.trim();
	}

}
