package de.vonmusil.cliptool.command.impl;

import static de.vonmusil.cliptool.command.impl.TemplateProcessor.newProcessor;
import static java.text.MessageFormat.format;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import de.vonmusil.cliptool.command.CommandProcessor;
import de.vonmusil.cliptool.exception.UnexpectedClipperException;
import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamEditModel;
import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamEditModelBuilder;
import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamInputController;

public abstract class AbstractFiletemplateProcessor implements CommandProcessor
{
	final String contentPath;

	protected AbstractFiletemplateProcessor(String contentPath)
	{
		this.contentPath = contentPath;
	}

	protected String loadTemplate()
	{
		ByteArrayOutputStream out;

		try
		{
			final Path source = Paths.get(this.contentPath);
			out = new ByteArrayOutputStream();
			Files.copy(source, out);
			return out.toString();
		}
		catch (final IOException e)
		{
			final String message = format("Cannot load content from {0}", this.contentPath.toString());

			throw new UnexpectedClipperException(message, e);
		}
	}

	protected String fillTemplate(String template)
	{
		final ParamEditModel parameters = this.askUserForParameters(template);

		if (parameters.isCancelled())
		{
			return null;
		}

		return newProcessor()
				.setTemplate(template)
				.bind(parameters.getParameters())
				.process();
	}

	protected ParamEditModel askUserForParameters(final String content)
	{
		final TemplateParser parser = new TemplateParser(content);

		final Map<String, String> values = parser.findDefaultValues();

		final ParamEditModel parameters = ParamEditModelBuilder.newBuilder()
				.addParameters(values)
				.create();

		ParamInputController.showDialog(parameters);
		return parameters;
	}

}