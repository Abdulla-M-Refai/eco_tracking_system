package com.eco.track.eco_tracking_system.validator.Annotation;

import java.lang.annotation.*;
import jakarta.validation.Constraint;

import jakarta.validation.Payload;

import com.eco.track.eco_tracking_system.validator.Implementation.ValidReportFileValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidReportFileValidator.class)
public @interface ValidReportFile
{
    String message() default "invalid report file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}