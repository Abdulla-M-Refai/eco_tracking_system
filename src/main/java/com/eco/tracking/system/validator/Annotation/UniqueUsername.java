package com.eco.tracking.system.validator.Annotation;

import com.eco.tracking.system.validator.Implementation.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername
{
    String message() default "username already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
