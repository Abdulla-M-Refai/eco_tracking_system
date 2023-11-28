package com.eco.track.eco_tracking_system.validator.Implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.eco.track.eco_tracking_system.repository.UserRepository;
import com.eco.track.eco_tracking_system.validator.Annotation.UniqueUsername;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>
{
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext)
    {
        return userRepository.findByUsername(username).isEmpty();
    }
}