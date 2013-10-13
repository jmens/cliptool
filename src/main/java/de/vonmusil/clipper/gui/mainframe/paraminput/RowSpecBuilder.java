package de.vonmusil.clipper.gui.mainframe.paraminput;

import java.util.ArrayList;
import java.util.List;

import com.jgoodies.forms.layout.RowSpec;

class RowSpecBuilder
{

	private final List<RowSpec> rowSpecs = new ArrayList<>();

	public RowSpecBuilder()
	{
		super();
	}

	public RowSpecBuilder(RowSpec rowSpecification)
	{
		super();

		add(rowSpecification);
	}

	public RowSpecBuilder add(RowSpec rowSpecification)
	{
		this.rowSpecs.add(rowSpecification);

		return this;
	}

	RowSpec[] getSpecifications()
	{
		return rowSpecs.toArray(new RowSpec[rowSpecs.size()]);
	}
}