package com.eco.tracking.system.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityReportDTO
{
    private long reportID;

    private long topicID;

    private String report;

    private float rate;
}
