package com.eco.tracking.system.validator.Annotation;

import com.eco.tracking.system.validator.Implementation.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail
{
    String message() default "email already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
