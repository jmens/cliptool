package de.vonmusil.cliptool.gui.mainframe.paraminput;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ParamInputDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private final JPanel dialogContentPanel = new JPanel();
	private JButton cancelButton;
	private JButton okButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			final ParamInputDialog dialog = new ParamInputDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ParamInputDialog()
	{
		setTitle("Specify Parameters");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		dialogContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(dialogContentPanel, BorderLayout.CENTER);
		dialogContentPanel.setLayout(new BorderLayout(0, 0));
		{
			final JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public JButton getCancelButton()
	{
		return cancelButton;
	}

	public JButton getOkButton()
	{
		return okButton;
	}

	public JPanel getDialogContentPanel()
	{
		return dialogContentPanel;
	}
}
