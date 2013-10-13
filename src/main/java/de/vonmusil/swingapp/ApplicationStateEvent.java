package de.vonmusil.swingapp;

public class ApplicationStateEvent
{

    private final ApplicationState state;

    public ApplicationStateEvent(ApplicationState state)
    {
        this.state = state;
    }

    public ApplicationState getState()
    {
        return state;
    }
}
