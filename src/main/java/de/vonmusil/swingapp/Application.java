package de.vonmusil.swingapp;

import java.awt.EventQueue;
import java.awt.Window;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vonmusil.swingapp.action.Actionmethod;
import de.vonmusil.swingapp.controller.AbstractController;

public class Application
{
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private static Application instance = new Application();

	private final Map<AbstractController<?, ?>, Map<String, AbstractAction>> actionsPerController = new HashMap<>();

	private final List<ApplicationListener> changeListeners = new ArrayList<>();
	
    private Window mainWindow;

	private Application()
	{
		super();
	}

	public static Application getInstance()
	{
		return instance;
	}

	public void addApplicationListener(ApplicationListener listener)
	{
	    this.changeListeners.add(listener); 
	}
	
	public void removeApplicationListener(ApplicationListener listener)
	{
	    this.changeListeners.add(listener); 
	}
	
	public ApplicationListener[] getApplicationListeners()
	{
	    return  this.changeListeners.toArray(new ApplicationListener[ this.changeListeners.size()]);
	}

	public void register(AbstractController<?, ?> controller)
    {
    	if (controller == null)
    	{
    		return;
    	}
    
    	Map<String, AbstractAction> actions = this.actionsPerController.get(controller);
    
    	if (actions == null)
    	{
    		actions = new HashMap<String, AbstractAction>();
    		this.actionsPerController.put(controller, actions);
    	}
    
    	final Method[] methods = controller.getClass().getDeclaredMethods();
    	for (final Method method : methods)
    	{
    		LOG.info("Method {}", method.getName());
    		if (method.isAnnotationPresent(Actionmethod.class))
    		{
    			final Actionmethod annotation = method.getAnnotation(Actionmethod.class);
    			final String name = annotation.name();
    			final String caption = annotation.caption();
    
    			actions.put(name, new ReflectionAction(name, controller, method, caption));
    
    			LOG.info("Registered action {} on viewer {}", name, controller);
    		}
    	}
    }

    public Action getAction(AbstractController<?, ?> controller, String actionName)
	{
		return this.actionsPerController.get(controller).get(actionName);
	}
	
	public void startUp(Window mainWindow)
	{
        this.mainWindow = mainWindow;

        LOG.info("Startup application");

        Application.this.fireApplicationStateEvent(ApplicationState.STARTING);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Application.this.mainWindow.setVisible(true);
                    Application.this.fireApplicationStateEvent(ApplicationState.RUNNING);
                }
                catch (final Exception e)
                {
                    LOG.error("Cannot startup application", e);
                }
            }
        });

	}

    public void shutdown()
    {
        LOG.info("Shutdown application");
    
        Application.this.fireApplicationStateEvent(ApplicationState.STOPPING);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Application.this.mainWindow.setVisible(false);
                    Application.this.mainWindow.dispose();
                    Application.this.fireApplicationStateEvent(ApplicationState.STOPPED);
                }
                catch (final Exception e)
                {
                    LOG.error("Cannot shutdown application properly", e);
                }
            }
        });
    }

    protected void fireApplicationStateEvent(ApplicationState state)
    {
        final ApplicationStateEvent event = new ApplicationStateEvent(state);
        for (final ApplicationListener listener : changeListeners)
        {
            listener.stateChanged(event);
        }
    }

}
