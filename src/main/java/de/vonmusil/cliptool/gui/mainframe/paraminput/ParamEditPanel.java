package de.vonmusil.cliptool.gui.mainframe.paraminput;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;

import de.vonmusil.cliptool.gui.mainframe.paraminput.ParamEditModel.Parameter;

public class ParamEditPanel extends JXPanel
{
	public ParamEditPanel()
	{
	}

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(ParamEditPanel.class);

	private final List<Parameter> parameters = new ArrayList<>();

	private final Map<String, JTextField> textFields = new HashMap<>();

	protected void initialize(Set<Parameter> list)
	{
		this.parameters.clear();
		this.parameters.addAll(list);

		initializeLayoutManager();

		int row = 2;
		for (final Parameter parameter : this.parameters)
		{
			LOG.debug("Adding component row {} for variable '{}'", row, parameter);

			final JXLabel label = new JXLabel(parameter.getName() + ":");
			final JXTextField text = new JXTextField("");

			textFields.put(parameter.getName(), text);

			final String placementPattern = "{0}, {1}, {2}";
			this.add(label, format(placementPattern, 2, row, "right, default"));
			this.add(text, format(placementPattern, 4, row, "fill, default"));

			row += 2;
		}
	}

	private void initializeLayoutManager()
	{
		final ColumnSpec[] columnSpecs = new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,

				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,

				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, };

		final RowSpecBuilder builder = new RowSpecBuilder(FormFactory.RELATED_GAP_ROWSPEC);

		for (final Parameter parameter : this.parameters)
		{
			LOG.debug("Layouting row for variable '{}'", parameter);
			builder.add(FormFactory.DEFAULT_ROWSPEC);
			builder.add(FormFactory.RELATED_GAP_ROWSPEC);
		}

		builder.add(FormFactory.RELATED_GAP_ROWSPEC);

		LOG.debug(Arrays.toString(builder.getSpecifications()));

		setLayout(new FormLayout(columnSpecs, builder.getSpecifications()));
	}

	public Map<String, JTextField> getTextFields()
	{
		return textFields;
	}
}
