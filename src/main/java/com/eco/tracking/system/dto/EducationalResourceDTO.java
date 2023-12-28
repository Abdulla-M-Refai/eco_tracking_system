package com.eco.tracking.system.dto;

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
