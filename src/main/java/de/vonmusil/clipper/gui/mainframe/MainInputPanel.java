package de.vonmusil.clipper.gui.mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.vonmusil.clipper.command.Command;

public class MainInputPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final JComboBox<Command> commandInputBox;
	private final JXButton btnExecute;
	private final JXButton btnCancel;
	private final JLabel lblClipboard;
	private final JTextField txtClipboardContent;
	private final JXLabel lblStatusLabel;
	private final JXPanel statusPanel;

	public MainInputPanel()
	{
		final FormLayout formLayout = new FormLayout(new ColumnSpec[] {
		        FormFactory.RELATED_GAP_COLSPEC,
		        FormFactory.DEFAULT_COLSPEC,
		        FormFactory.RELATED_GAP_COLSPEC,
		        ColumnSpec.decode("default:grow"),
		        FormFactory.RELATED_GAP_COLSPEC,},
		    new RowSpec[] {
		        FormFactory.RELATED_GAP_ROWSPEC,
		        FormFactory.DEFAULT_ROWSPEC,
		        FormFactory.RELATED_GAP_ROWSPEC,
		        FormFactory.DEFAULT_ROWSPEC,
		        FormFactory.RELATED_GAP_ROWSPEC,
		        FormFactory.DEFAULT_ROWSPEC,
		        FormFactory.RELATED_GAP_ROWSPEC,
		        FormFactory.DEFAULT_ROWSPEC,
		        FormFactory.RELATED_GAP_ROWSPEC,});
		setLayout(formLayout);

		lblClipboard = new JLabel("Clipboard");
		add(lblClipboard, "2, 2, right, default");

		txtClipboardContent = new JTextField();
		add(txtClipboardContent, "4, 2, fill, default");
		txtClipboardContent.setColumns(10);

		final JLabel lblCommand = new JLabel("Command");
		add(lblCommand, "2, 4, right, default");

		commandInputBox = new JComboBox();
		add(commandInputBox, "4, 4, fill, default");

		final JXPanel panel = new JXPanel();
		add(panel, "2, 6, 3, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default"), },
				new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC, }));

		btnCancel = new JXButton();
		btnCancel.setText("Cancel");
		panel.add(btnCancel, "4, 1");

		btnExecute = new JXButton();
		btnExecute.setText("Execute");
		panel.add(btnExecute, "6, 1, right, default");
		
		statusPanel = new JXPanel();
		statusPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(statusPanel, "2, 8, 3, 1, fill, fill");
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		lblStatusLabel = new JXLabel();
		statusPanel.add(lblStatusLabel, BorderLayout.CENTER);
		lblStatusLabel.painted = true;
		lblStatusLabel.setBackground(Color.LIGHT_GRAY);
		lblStatusLabel.setFont(new Font("Dialog", Font.BOLD, 9));
		lblStatusLabel.setText("status label");

	}

	public JComboBox<Command> getCommandInputBox()
	{
		return commandInputBox;
	}

	public JXButton getBtnExecute()
	{
		return btnExecute;
	}

	public JXButton getBtnCancel()
	{
		return btnCancel;
	}

	public JTextField getTxtClipboardContent()
	{
		return txtClipboardContent;
	}
    public JXLabel getLblStatusLabel() {
        return lblStatusLabel;
    }
}
