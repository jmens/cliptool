package de.vonmusil.clipper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.Test;

public class ClipboardLearningTest implements FlavorListener, ClipboardOwner
{
	private final Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

	@Test
	public void testName() throws Exception
	{
		final Clipboard sysSelection = Toolkit.getDefaultToolkit().getSystemSelection();
		final Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

		final String selContent = sysSelection.getData(DataFlavor.stringFlavor).toString();
		final String clpContent = sysClip.getData(DataFlavor.stringFlavor).toString();

		sysSelection.addFlavorListener(this);

		System.out.println(selContent);
		System.out.println(clpContent);

	}

	@Override
	public void flavorsChanged(FlavorEvent e)
	{
		final Transferable newContent = clip.getContents(null);
		System.out.println("ClipBoard Changed: " + newContent);

		clip.removeFlavorListener(this);

		clip.setContents(newContent, this);

		clip.addFlavorListener(this);

	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1)
	{
		try
		{
			final String newContent = clip.getContents(null).getTransferData(DataFlavor.stringFlavor).toString();
			System.out.println("ownership lost for: " + newContent);
			clip.setContents(new StringSelection(newContent), this);
		}
		catch (UnsupportedFlavorException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ClipboardLearningTest()
	{
		System.out.println("NewClass constructor");
		clip.setContents(new StringSelection("Hallo"), this);
		clip.addFlavorListener(this);

		try
		{
			Thread.sleep(100000L);
		}
		catch (final InterruptedException e)
		{

		}

	}

	public static void main(String[] args)
	{
		new ClipboardLearningTest();
	}
}
