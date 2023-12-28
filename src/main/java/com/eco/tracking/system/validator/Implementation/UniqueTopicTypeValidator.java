package com.eco.tracking.system.validator.Implementation;

import com.eco.tracking.system.repository.TopicRepository;
import com.eco.tracking.system.validator.Annotation.UniqueTopicType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
