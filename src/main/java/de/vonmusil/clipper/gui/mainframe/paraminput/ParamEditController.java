package de.vonmusil.clipper.gui.mainframe.paraminput;

import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.PropertyAdapter;

import de.vonmusil.clipper.gui.mainframe.paraminput.ParamEditModel.Parameter;
import de.vonmusil.swingapp.controller.AbstractController;
import de.vonmusil.swingapp.model.AbstractModel;

public class ParamEditController extends AbstractController<ParamEditModel, ParamEditPanel>
{
    private static final Logger LOG = LoggerFactory.getLogger(ParamEditController.class);
    
	public ParamEditController(ParamEditModel data)
	{
		super(data, new ParamEditPanel());
	}

	@Override
	protected void initBinding()
	{
		final ParamEditPanel component = getComponent();

		final Map<String, JTextField> textFields = component.getTextFields();

		final Set<Parameter> parameters = getData().getParameters();
		
		for (final Parameter parameter : parameters)
        {
		    LOG.debug("Binding parameter {}", parameter.getName());
            final JTextField textField = textFields.get(parameter.getName());
            Bindings.bind(textField, new PropertyAdapter<Parameter>(parameter, Parameter.VALUE, true));
        }
	}

	@Override
	protected void initComponent()
	{
		final ParamEditModel data = getData();
		final ParamEditPanel component = getComponent();

		component.initialize(data.getParameters());
	}

	public static class SimpleProperty extends AbstractModel
	{
		public static final String VALUE = "value";

		private String name;
		private String value;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}
	}
}
