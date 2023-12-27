package com.eco.track.eco_tracking_system.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationalResourceDTO
{
    private long id;

    private String title;

    private String url;
}
