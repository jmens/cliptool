package de.vonmusil.swingapp;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.slf4j.LoggerFactory.getLogger;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import javax.swing.AbstractAction;

import org.slf4j.Logger;

import de.vonmusil.swingapp.exception.ActionMethodException;

class ReflectionAction extends AbstractAction
{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = getLogger(ReflectionAction.class);

	private final Object object;
	private final Method method;
	private final String name;

	private static final String PTRN_ACTIONMETHOD_FAILED = "Exception while executing action method '{0}': {1}";

	public ReflectionAction(String name, Object object, Method method, String caption)
	{
		super(caption);
		this.name = name;
		this.object = object;
		this.method = method;
	}

	@Override
	public String toString()
	{
		return reflectionToString(this);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		try
		{
			this.method.invoke(this.object, (Object[]) null);
		}
		catch (final IllegalAccessException e)
		{
			throw new ActionMethodException(MessageFormat.format(PTRN_ACTIONMETHOD_FAILED, this.name, e.getMessage(), e));
		}
		catch (final IllegalArgumentException e)
		{
			throw new ActionMethodException(MessageFormat.format(PTRN_ACTIONMETHOD_FAILED, this.name, e.getMessage(), e));
		}
		catch (final InvocationTargetException e)
		{
			LOG.info("Exception in method " + this.name);
			final String message = MessageFormat.format(PTRN_ACTIONMETHOD_FAILED, this.name, e.getTargetException().getMessage());
			throw new ActionMethodException(message, e.getTargetException());
		}
	}
}