package com.eco.track.eco_tracking_system.validator.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.eco.track.eco_tracking_system.validator.Implementation.UniqueTopicTypeValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTopicTypeValidator.class)
public @interface UniqueTopicType
{
    String message() default "topic type already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
