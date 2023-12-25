package com.eco.track.eco_tracking_system.response;

import lombok.*;

import java.util.List;

import com.eco.track.eco_tracking_system.dto.TopicDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicsResponse
{
    private List<TopicDTO> topics;
}
