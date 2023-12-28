package com.eco.tracking.system.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO
{
    private long id;

    private long topicID;

    private String fullName;

    private float rate;
}
