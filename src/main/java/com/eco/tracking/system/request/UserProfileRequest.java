package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest
{
    @NotBlank(message = "missing topic id")
    @Pattern(regexp = "\\d+", message = "topic id must be a valid number")
    private String topicID;

    @NotBlank(message = "missing full name")
    @Size(max = 255, message = "full name exceeds maximum length of 255 character")
    private String fullName;

    public Long getParsedTopicID()
    {
        return Long.parseLong(topicID);
    }
}
