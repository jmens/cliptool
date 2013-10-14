package de.vonmusil.cliptool.gui.mainframe;

import org.apache.commons.lang3.StringUtils;

import de.vonmusil.swingapp.model.AbstractModel;

public class MainInputModel extends AbstractModel
{
	public static final String STATUS_MESSAGE = "statusMessage";

	private String statusMessage = "";
	
	public String getStatusMessage()
    {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage)
    {
        final String oldContent = this.statusMessage;
        this.statusMessage = StringUtils.defaultString(statusMessage);

        this.firePropertyChange(STATUS_MESSAGE, oldContent, this.statusMessage);
    }
}
