package de.vonmusil.clipper.command.impl;

import de.vonmusil.clipper.command.Command;
import de.vonmusil.clipper.command.CommandProcessor;
import de.vonmusil.clipper.gui.mainframe.CliptoolClipboard;

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
        final String result = singleLined.replaceAll(" {2,}", " ");

        return result.trim();
    }

}
