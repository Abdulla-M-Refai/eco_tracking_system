package com.eco.track.eco_tracking_system.validator.Implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.eco.track.eco_tracking_system.repository.TopicRepository;
import com.eco.track.eco_tracking_system.validator.Annotation.UniqueTopicType;

@Component
@RequiredArgsConstructor
public class UniqueTopicTypeValidator implements ConstraintValidator<UniqueTopicType, String>
{
    private final TopicRepository topicRepository;

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext)
    {
        return topicRepository.findByType(type).isEmpty();
    }
}
