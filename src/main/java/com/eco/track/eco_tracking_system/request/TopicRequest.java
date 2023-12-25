package com.eco.track.eco_tracking_system.request;

import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import com.eco.track.eco_tracking_system.validator.Annotation.UniqueTopicType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest
{
    @NotBlank(message = "messing type")
    @Size(max = 50, message = "type exceeds maximum length of 50 character")
    @UniqueTopicType
    private String type;
}
