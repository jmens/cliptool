package de.vonmusil.cliptool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.vonmusil.cliptool.Main;

public class MainTest
{

	private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	@Before
	public void setUp()
	{
		final StringSelection selection = new StringSelection("/tmp/test");

		clipboard.setContents(selection, null);
	}

	@Test
	public void clipper() throws Exception
	{
		final Object data = clipboard.getData(DataFlavor.stringFlavor);
		Assert.assertThat(data.toString(), is("/tmp/test"));
	}

	@Test
	public void testLinebreakRemoval() throws Exception
	{
		final Main main = new Main();
		assertThat(main.toSingleLine(""), is(""));
		assertThat(main.toSingleLine("test"), is("test"));
		assertThat(main.toSingleLine("a\nb"), is("a b"));
		assertThat(main.toSingleLine("a  b"), is("a b"));
		assertThat(main.toSingleLine("a   b        c"), is("a b c"));
		assertThat(main.toSingleLine("a   bc"), is("a bc"));
		assertThat(main.toSingleLine("a  \n\n \n  b\n c\n"), is("a b c"));

	}
}
