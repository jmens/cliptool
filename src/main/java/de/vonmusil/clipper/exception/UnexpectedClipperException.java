package de.vonmusil.clipper.exception;

public class UnexpectedClipperException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public UnexpectedClipperException()
	{
		super();
	}

	public UnexpectedClipperException(String message)
	{
		super(message);
	}

	public UnexpectedClipperException(Throwable cause)
	{
		super(cause);
	}

	public UnexpectedClipperException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnexpectedClipperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
