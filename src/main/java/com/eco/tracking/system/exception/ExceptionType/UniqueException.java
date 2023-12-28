package com.eco.tracking.system.exception.ExceptionType;

public class UniqueException extends RuntimeException
{
    public UniqueException(String message)
    {
        super(message);
    }

    public UniqueException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UniqueException(Throwable cause)
    {
        super(cause);
    }
}