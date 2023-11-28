package com.eco.track.eco_tracking_system.validator.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.eco.track.eco_tracking_system.validator.Implementation.UniqueUsernameValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername
{
    String message() default "username already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
