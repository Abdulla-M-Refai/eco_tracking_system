package com.eco.tracking.system.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    private long id;

    private String fullName;

    private String username;

    private String email;

    private float communityReportRate;

    private boolean isEnabled;
}
