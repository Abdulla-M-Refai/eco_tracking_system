package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import com.eco.tracking.system.validator.Annotation.UniqueTopicType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest
{
    @NotBlank(message = "missing type")
    @Size(max = 50, message = "type exceeds maximum length of 50 character")
    @UniqueTopicType
    private String type;
}
