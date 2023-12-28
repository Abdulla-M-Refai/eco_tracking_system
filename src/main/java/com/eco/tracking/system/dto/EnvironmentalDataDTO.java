package com.eco.tracking.system.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.eco.tracking.system.entity.Enum.DataSource;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentalDataDTO
{
    private long profileID;

    private DataSource dataSource;

    private String dataType;

    private float value;

    private String unit;

    private LocalDateTime time;

    private double latitude;

    private double longitude;

    private float rate;
}
