package com.eco.track.eco_tracking_system.response;

import lombok.*;

import java.util.List;

import com.eco.track.eco_tracking_system.dto.UserProfileDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilesResponse
{
    private List<UserProfileDTO> userProfiles;
}
