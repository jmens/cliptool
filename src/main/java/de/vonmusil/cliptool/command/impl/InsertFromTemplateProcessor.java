package de.vonmusil.cliptool.command.impl;

import static de.vonmusil.cliptool.command.impl.TemplateProcessor.newProcessor;
import static java.text.MessageFormat.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.cliptool.command.CommandProcessor;
import de.vonmusil.cliptool.exception.UnexpectedClipperException;
import de.vonmusil.cliptool.gui.mainframe.CliptoolClipboard;
import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamEditModel;
import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamEditModelBuilder;
import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamInputController;

public class InsertFromTemplateProcessor implements CommandProcessor
{

	private static final Logger LOG = LoggerFactory.getLogger(InsertFromTemplateProcessor.class);

	private final String contentPath;

	public InsertFromTemplateProcessor(String contentPath)
	{
		this.contentPath = contentPath;
	}

	protected String loadContent()
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

	@Override
	public void process()
	{
		final String result = this.getResult();

		final Path path;

		try
		{
			path = Files.createTempFile("Clipper-filecreation-", ".csv");

			LOG.info("Creating file " + path);

			final ByteArrayInputStream in = new ByteArrayInputStream(result.getBytes());
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

			final String filePath = path.toString();

			LOG.info("Setting clipoard: " + filePath);

			CliptoolClipboard.getInstance().setClipboardContent(filePath);
		}
		catch (final IOException e)
		{
			throw new UnexpectedClipperException("Cannot create file", e);
		}

	}

	protected String getResult()
	{
		final String content = this.loadContent();

		final ParamEditModel parameters = this.askUserForParameters(content);

		if (parameters.isCancelled())
		{
			return null;
		}

		return newProcessor()
				.setTemplate(content)
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
