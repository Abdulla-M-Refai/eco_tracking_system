package com.eco.tracking.system.validator.Implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.eco.tracking.system.repository.UserRepository;
import com.eco.tracking.system.validator.Annotation.UniqueEmail;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>
{
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext)
    {
        return userRepository.findByEmail(email).isEmpty();
    }
}