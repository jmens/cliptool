package de.vonmusil.cliptool.command.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.cliptool.command.CommandProcessor;
import de.vonmusil.cliptool.exception.UnexpectedClipperException;
import de.vonmusil.cliptool.gui.mainframe.CliptoolClipboard;

public class FileCreationProcessor extends AbstractFiletemplateProcessor implements CommandProcessor
{

	private static final Logger LOG = LoggerFactory.getLogger(FileCreationProcessor.class);

	public FileCreationProcessor(String contentPath)
	{
		super(contentPath);
	}

	@Override
	public void process()
	{
		final String template = this.loadTemplate();
		final String result = this.fillTemplate(template);

		final Path path = this.writeFile(result);

		LOG.info("Setting clipoard: {}", path.toString());

		CliptoolClipboard.getInstance().setClipboardContent(path.toString());
	}

	private Path writeFile(String content)
	{
		final Path path;

		try
		{
			path = Files.createTempFile("Clipper-filecreation-", ".csv");

			LOG.info("Creating file " + path);

			final ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

			return path;
		}
		catch (final IOException e)
		{
			throw new UnexpectedClipperException("Cannot create file", e);
		}
	}
}
