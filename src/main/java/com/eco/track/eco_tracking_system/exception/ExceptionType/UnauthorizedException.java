package com.eco.track.eco_tracking_system.exception.ExceptionType;

public class UnauthorizedException extends RuntimeException
{
    public UnauthorizedException(String message)
    {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause)
    {
        super(cause);
    }
}
