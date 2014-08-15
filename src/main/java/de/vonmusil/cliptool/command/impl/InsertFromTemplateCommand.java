package de.vonmusil.cliptool.command.impl;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.cliptool.command.Command;
import de.vonmusil.cliptool.command.CommandProcessor;
import de.vonmusil.cliptool.gui.mainframe.CliptoolClipboard;

public class InsertFromTemplateCommand extends AbstractFiletemplateProcessor implements CommandProcessor, Command
{
	private static final Logger LOG = LoggerFactory.getLogger(InsertFromTemplateCommand.class);

	protected InsertFromTemplateCommand(String template)
	{
		super(template);
	}

	@Override
	public String toString()
	{
		return "insertfromtemplate";
	}

	@Override
	public CommandProcessor getProcessor()
	{
		return this;
	}

	@Override
	public void process()
	{
		final String template = this.loadTemplate();
		final String result = this.fillTemplate(template);

		CliptoolClipboard.getInstance().setClipboardContent(result);
	}
}
