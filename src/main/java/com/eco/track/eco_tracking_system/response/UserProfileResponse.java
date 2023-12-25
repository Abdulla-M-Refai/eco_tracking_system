package com.eco.track.eco_tracking_system.response;

import lombok.*;

import com.eco.track.eco_tracking_system.dto.UserProfileDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse
{
    private UserProfileDTO userProfile;
}
