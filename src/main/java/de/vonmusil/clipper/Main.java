package de.vonmusil.clipper;

import javax.swing.JFrame;

import de.vonmusil.clipper.gui.mainframe.MainInputController;
import de.vonmusil.swingapp.Application;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        final JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final MainInputController inputController = new MainInputController();
        frame.getContentPane().add(inputController.getComponent());
        frame.pack();
        frame.setTitle("Put into clipboard...");

        Application.getInstance().startUp(frame);;

    }


    String toSingleLine(String input)
    {
        final String singleLined = input.replaceAll("\n", " ");
        final String result = singleLined.replaceAll(" {2,}", " ");

        return result.trim();
    }
}
