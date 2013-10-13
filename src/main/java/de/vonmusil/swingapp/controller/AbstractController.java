package de.vonmusil.swingapp.controller;

import java.awt.Component;

import javax.swing.Action;

import de.vonmusil.swingapp.Application;
import de.vonmusil.swingapp.model.AbstractModel;

public abstract class AbstractController<D extends AbstractModel, C extends Component>
{
	private final D data;
	private final C component;

	public AbstractController(D data, C component)
	{
		this.data = data;
		this.component = component;

		Application.getInstance().register(this);

		this.initComponent();
		this.initBinding();
	}

	protected void initBinding()
	{

	}

	protected void initComponent()
	{

	}

	public D getData()
	{
		return this.data;
	}

	public C getComponent()
	{
		return component;
	}

	public Action getAction(String name)
	{
		return Application.getInstance().getAction(this, name);
	}
}
