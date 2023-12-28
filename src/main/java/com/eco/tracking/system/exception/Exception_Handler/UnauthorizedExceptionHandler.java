package com.eco.tracking.system.exception.Exception_Handler;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.core.Ordered;

import com.eco.tracking.system.exception.ExceptionType.UnauthorizedException;
import com.eco.tracking.system.exception.ExceptionResponse.ExceptionResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UnauthorizedExceptionHandler
{
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleException(UnauthorizedException unauthorizedException)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
            HttpStatus.UNAUTHORIZED.value(),
            unauthorizedException.getMessage(),
            System.currentTimeMillis()
        );

        return new ResponseEntity<>(exceptionResponse,HttpStatus.UNAUTHORIZED);
    }
}
