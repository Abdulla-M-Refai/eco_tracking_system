package com.eco.track.eco_tracking_system.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse
{
    private String state;

    private String message;
}
