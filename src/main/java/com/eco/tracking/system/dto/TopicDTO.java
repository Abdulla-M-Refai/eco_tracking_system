package com.eco.tracking.system.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO
{
    private long id;

    private String type;
}
