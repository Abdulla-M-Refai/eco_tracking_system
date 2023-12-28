package com.eco.tracking.system.validator.Annotation;

import java.lang.annotation.*;

import com.eco.tracking.system.validator.Implementation.ValidReportFileValidator;
import jakarta.validation.Constraint;

import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidReportFileValidator.class)
public @interface ValidReportFile
{
    String message() default "invalid report file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}