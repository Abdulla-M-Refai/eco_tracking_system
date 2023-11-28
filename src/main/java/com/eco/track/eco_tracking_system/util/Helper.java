package com.eco.track.eco_tracking_system.util;

import org.springframework.validation.BindingResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import jakarta.validation.ValidationException;

import java.util.List;

public class Helper
{
    private Helper()
    {
    }

    public static void fieldsValidate(BindingResult result) throws ValidationException
    {
        if (result.hasErrors())
        {
            List<String> errorMessages = result
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

            throw new ValidationException(errorMessages.toString());
        }
    }
}