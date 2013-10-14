package de.vonmusil.cliptool.command;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.vonmusil.cliptool.command.Command;
import de.vonmusil.cliptool.command.CommandRepository;
import de.vonmusil.cliptool.command.CommandType;
import de.vonmusil.cliptool.command.impl.FileCreationCommand;
import de.vonmusil.cliptool.command.impl.FileCreationProcessor;
import de.vonmusil.cliptool.command.impl.SimpleInsertCommand;

public class CommandRepositoryTest
{
	private static final String URL_TEMPLATE_FILE = "file:///tmp/demo.template";

	private static final Logger LOG = LoggerFactory.getLogger(CommandRepositoryTest.class);

	private final CommandRepository repo = CommandRepository.instance();

	@Test
	public void testInitializeWithNull() throws Exception
	{
		assertThat(repo.initialize(null), Matchers.equalTo(false));
	}

	@Test
	public void testRepoDeliversCommands() throws Exception
	{
		repo.initialize(getTestDocument());

		final List<Command> commands = repo.getCommands();

		assertThat(commands, hasSize(3));
		assertThat((SimpleInsertCommand) commands.get(0), isA(SimpleInsertCommand.class));
		assertThat((SimpleInsertCommand) commands.get(1), isA(SimpleInsertCommand.class));
	}

	@Test
	public void testRepoSimpleInsertIsCorrect() throws Exception
	{
		repo.initialize(getTestDocument());

		final List<Command> commands = repo.getCommands();

		final SimpleInsertCommand command = (SimpleInsertCommand) commands.get(0);
		assertThat(command.getName(), equalTo("iban"));
		assertThat(command.getValue(), equalTo("12345967890"));
	}

	@Test
	public void testRepoFindsFileCreationCommand() throws Exception
	{
		repo.initialize(getTestDocument());

		final List<Command> commands = repo.getCommands();

		final Command command = new FileCreationCommand("rlsDebit", URL_TEMPLATE_FILE);

		assertThat(commands, hasItem(command));
	}

	@Test
	public void testFileCreationCommandContainsTemplate() throws Exception
	{
		repo.initialize(getTestDocument());

		final List<Command> commands = repo.getCommands(CommandType.FILE_CREATION);

		assertThat(((FileCreationCommand) commands.get(0)).getTemplatePath(), equalTo(URL_TEMPLATE_FILE));
	}

	@Test
	public void testFileCreationCommandDeliversProcessor() throws Exception
	{
		repo.initialize(getTestDocument());

		final List<Command> commands = repo.getCommands(CommandType.FILE_CREATION);

		final FileCreationCommand command = (FileCreationCommand) commands.get(0);
		final FileCreationProcessor processor = (FileCreationProcessor) command.getProcessor();
		assertThat(processor, isA(FileCreationProcessor.class));
	}

	private InputStream getTestDocument() throws Exception
	{
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		final Document document = docBuilder.newDocument();

		// Task 1
		final Element value1 = document.createElement("value");
		value1.setTextContent("12345967890");

		final Element task1 = document.createElement("task");
		task1.setAttribute("type", "InSeRt");
		task1.setAttribute("name", "iban");
		task1.appendChild(value1);

		// Task 2
		final Element value2 = document.createElement("value");
		value2.setTextContent("abcdefg");

		final Element task2 = document.createElement("task");
		task2.setAttribute("type", "insert");
		task2.setAttribute("name", "bic");
		task2.appendChild(value2);

		//File creation task
		final Element fileCreationValue = document.createElement("value");
		fileCreationValue.setTextContent(URL_TEMPLATE_FILE);

		final Element fileCreationTask = document.createElement("task");
		fileCreationTask.setAttribute("type", "filecreate");
		fileCreationTask.setAttribute("name", "rlsDebit");
		fileCreationTask.appendChild(fileCreationValue);

		// Root
		final Element root = document.createElement("clipper");
		document.appendChild(root);
		root.appendChild(task1);
		root.appendChild(task2);
		root.appendChild(fileCreationTask);

		// Make XML
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		final DOMSource source = new DOMSource(document);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final StreamResult result = new StreamResult(outputStream);

		transformer.transform(source, result);

		LOG.debug("Config reads: " + new String(outputStream.toByteArray()));
		final InputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
		return stream;
	}
}
