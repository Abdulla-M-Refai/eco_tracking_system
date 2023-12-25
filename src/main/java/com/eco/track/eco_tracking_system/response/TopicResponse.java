package com.eco.track.eco_tracking_system.response;

import lombok.*;

import com.eco.track.eco_tracking_system.dto.TopicDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponse
{
    private TopicDTO topic;
}
