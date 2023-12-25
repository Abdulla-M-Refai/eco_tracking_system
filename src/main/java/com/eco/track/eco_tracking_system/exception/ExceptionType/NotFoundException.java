package com.eco.track.eco_tracking_system.exception.ExceptionType;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message)
    {
        super(message);
    }

    public NotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotFoundException(Throwable cause)
    {
        super(cause);
    }
}