package com.eco.track.eco_tracking_system.exception.ExceptionType;

public class ValidationException extends RuntimeException
{
    public ValidationException(String message)
    {
        super(message);
    }

    public ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ValidationException(Throwable cause)
    {
        super(cause);
    }
}