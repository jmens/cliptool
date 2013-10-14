package de.vonmusil.cliptool.gui.mainframe;

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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.cliptool.exception.UnexpectedClipperException;
import de.vonmusil.swingapp.model.AbstractModel;

public class CliptoolClipboard extends AbstractModel implements ClipboardOwner, FlavorListener
{
    
    public static final String CLIPBOARD_CONTENT = "clipboardContent";
    private static final Logger LOG = LoggerFactory.getLogger(CliptoolClipboard.class);

    private static final Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final Clipboard selection = Toolkit.getDefaultToolkit().getSystemSelection();

    private static CliptoolClipboard instance;

    private String clipboardContent;

    private CliptoolClipboard()
    {
        super();
    }

    public static CliptoolClipboard getInstance()
    {
        if (instance == null)
        {
            instance = new CliptoolClipboard();

            final Transferable contents = clip.getContents(instance);

            final boolean synced = instance.synchronizeLocalContent(contents);
            
            if (!synced)
            {
                LOG.warn("Overwriting system clipboard content with empty string");
                instance.setClipboardContent("");

            }

            clip.addFlavorListener(instance);

        }
        return instance;
    }

    @Override
    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable contents)
    {
        synchronizeLocalContent(contents);
    }

    private boolean synchronizeLocalContent(Transferable contents)
    {
        try
        {
            if (contents.isDataFlavorSupported(DataFlavor.stringFlavor))
            {
                final String content = clip.getData(DataFlavor.stringFlavor).toString();
                setClipboardContent(content);

                return true;
            }
            else
            {
                LOG.error("DataFlavor 'StringFlavor' not supported. Available Flavors: {}", Arrays.toString(contents.getTransferDataFlavors()));

                return false;
            }
        }
        catch (UnsupportedFlavorException | IOException e)
        {
            throw new UnexpectedClipperException(e);
        }
    }

    @Override
    public void flavorsChanged(FlavorEvent e)
    {

    }

    public String getClipboardContent()
    {
        return clipboardContent;
    }

    public void setClipboardContent(String clipboardContent)
    {
        final String oldContent = this.clipboardContent;

        LOG.debug("Updating clipboard content: " + clipboardContent.substring(0, Math.min(clipboardContent.length(), 32)));
        // clip.removeFlavorListener(this);
        clip.setContents(new StringSelection(clipboardContent), this);
        // clip.addFlavorListener(this);

        selection.setContents(new StringSelection(clipboardContent), null);

        this.clipboardContent = clipboardContent;

        firePropertyChange("clipboardContent", oldContent, clipboardContent);
    }

}
