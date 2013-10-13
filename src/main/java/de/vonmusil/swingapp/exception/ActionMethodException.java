package de.vonmusil.swingapp.exception;

public class ActionMethodException extends UnexpectedSwingappException
{
    private static final long serialVersionUID = 1L;

    public ActionMethodException()
    {
        super();
    }

    public ActionMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ActionMethodException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ActionMethodException(String message)
    {
        super(message);
    }

    public ActionMethodException(Throwable cause)
    {
        super(cause);
    }

}
