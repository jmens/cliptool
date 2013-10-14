package de.vonmusil.cliptool.gui.mainframe;

import static org.slf4j.LoggerFactory.getLogger;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.slf4j.Logger;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.extras.DelayedWriteValueModel;
import com.jgoodies.binding.list.SelectionInList;

import de.vonmusil.cliptool.command.Command;
import de.vonmusil.cliptool.command.CommandRepository;
import de.vonmusil.swingapp.Application;
import de.vonmusil.swingapp.action.Actionmethod;
import de.vonmusil.swingapp.controller.AbstractController;

public class MainInputController extends AbstractController<MainInputModel, MainInputPanel>
{
	private static final Logger LOG = getLogger(MainInputController.class);
	private static final String EXECUTE_COMMAND = "execute.command";
	private static final String CANCEL = "cancel";
	private SelectionInList<Command> selectedCommand;

	public MainInputController()
	{
		super(new MainInputModel(), new MainInputPanel());
	}

	@Override
	protected void initBinding()
	{
		final BeanAdapter<CliptoolClipboard> clipboardAdapter = new BeanAdapter<CliptoolClipboard>(CliptoolClipboard.getInstance(), true);
		final BeanAdapter<MainInputModel> modelAdapter = new BeanAdapter<MainInputModel>(getData(), true);
		
		Bindings.bind(getComponent().getCommandInputBox(), selectedCommand);

		final DelayedWriteValueModel delayedWriteModel = new DelayedWriteValueModel(clipboardAdapter.getValueModel(CliptoolClipboard.CLIPBOARD_CONTENT), 1000);
		
		Bindings.bind(getComponent().getTxtClipboardContent(), delayedWriteModel);
		
		Bindings.bind(getComponent().getLblStatusLabel(), modelAdapter.getValueModel(MainInputModel.STATUS_MESSAGE));

		final JXButton btnExecute = getComponent().getBtnExecute();

		final KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, Event.ALT_MASK);
        btnExecute.setAction(getAction(EXECUTE_COMMAND));
		btnExecute.getActionMap().put(EXECUTE_COMMAND, getAction(EXECUTE_COMMAND));
        
		btnExecute.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, EXECUTE_COMMAND);
		
		getComponent().getBtnCancel().setAction(getAction(CANCEL));

		getComponent().getTxtClipboardContent().setAction(getAction(EXECUTE_COMMAND));


	}

	@Override
	protected void initComponent()
	{
		final JComboBox<Command> inputBox = getComponent().getCommandInputBox();
		AutoCompleteDecorator.decorate(inputBox);

		final List<Command> list = CommandRepository.instance().getCommands();
		selectedCommand = new SelectionInList<>(list);

		if (selectedCommand.getSize() > 0)
		{
			selectedCommand.setSelectionIndex(0);
		}
		
		printStatusMessage("started...");
	}
	
	@Actionmethod(caption = "Execute", name = EXECUTE_COMMAND)
	public void executeCommand()
	{
		final Command selection = selectedCommand.getSelection();

		LOG.debug("Execute selected- running '{}'", selection);

		if (selection == null)
		{
			LOG.debug("No command selected, nothing to do");
			return;
		}

		selection.getProcessor().process();
		
		printStatusMessage("Executed: " + selection.toString());
	}

	@Actionmethod(caption = "Cancel", name = CANCEL)
	public void cancel()
	{
		LOG.debug("Cancel selected- shutdown application");
		Application.getInstance().shutdown();
	}

    private void printStatusMessage(String message)
    {
        getData().setStatusMessage(message);
        
        final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
    
            @Override
            protected Void doInBackground() throws Exception
            {
                Thread.sleep(2500);
                
                return null;
            }
    
            @Override
            protected void done()
            {
                getData().setStatusMessage(" ");
            }
        };
        
        worker.execute();
    }
}
