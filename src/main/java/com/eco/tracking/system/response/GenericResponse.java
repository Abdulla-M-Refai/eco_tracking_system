package com.eco.tracking.system.response;

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
