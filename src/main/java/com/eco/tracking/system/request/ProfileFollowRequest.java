package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileFollowRequest
{
    @NotBlank(message = "missing profile id")
    @Pattern(regexp = "\\d+", message = "profile id must be a valid number")
    private String profileID;

    @NotBlank(message = "missing threshold")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "threshold must be a valid number")
    private String threshold;

    public long getParsedProfileID()
    {
        return Long.parseLong(profileID);
    }

    public double getParsedThreshold()
    {
        return Double.parseDouble(threshold);
    }
}
