package de.vonmusil.cliptool.gui.mainframe.paraminput;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.swingapp.action.Actionmethod;
import de.vonmusil.swingapp.controller.AbstractController;

public class ParamInputController extends AbstractController<ParamEditModel, ParamInputDialog>
{

    private static final String OK_PRESSED = "choice.ok";
    private static final String CANCEL_PRESSED = "choice.cancel";

    private static final Logger LOG = LoggerFactory.getLogger(ParamInputController.class);
    
    public ParamInputController(ParamEditModel data)
    {
        super(data, new ParamInputDialog());
    }

    @Override
    protected void initComponent()
    {
        final ParamEditController editorController = new ParamEditController(getData());

        getComponent().getDialogContentPanel().add(editorController.getComponent(), BorderLayout.CENTER);
        getComponent().getOkButton().setAction(getAction(OK_PRESSED));
        getComponent().getCancelButton().setAction(getAction(CANCEL_PRESSED));
    }

    @Override
    protected void initBinding()
    {
        super.initBinding();
    }

    public static ParamEditModel showDialog(ParamEditModel parameters)
    {
        final ParamInputController controller = new ParamInputController(parameters);

        final ParamInputDialog dialog = controller.getComponent();
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);

        return parameters;
    }
    
    @Actionmethod(name=OK_PRESSED, caption="OK")
    public void selectOk()
    {
        getData().setCancelled(false);
        getComponent().dispose();
    }
    
    @Actionmethod(name=CANCEL_PRESSED, caption="Cancel")
    public void selectCancel()
    {
        getData().setCancelled(true);
        getComponent().dispose();
    }

    public static void main(String[] args)
    {
        final ParamEditModel model = ParamEditModelBuilder.newBuilder()
                .addParameter("Betrag")
                .addParameter("Rechnungsnummer", "T123456")
                .create();
        
        final ParamEditModel parameters = showDialog(model);
        
        LOG.debug("Cancel pressed: " + parameters.isCancelled());
        LOG.debug("Parameters: " + parameters.getParameters());
    }
}
