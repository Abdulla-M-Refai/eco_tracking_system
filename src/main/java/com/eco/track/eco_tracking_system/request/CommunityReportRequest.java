package com.eco.track.eco_tracking_system.request;

import lombok.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.eco.track.eco_tracking_system.validator.Annotation.ValidReportFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityReportRequest
{
    @NotBlank(message = "missing topic id")
    @Pattern(regexp = "\\d+", message = "topic id must be a valid number")
    private String topicID;

    @ValidReportFile(message = "invalid or messing report file")
    private MultipartFile report;

    public Long getParsedTopicID()
    {
        return Long.parseLong(topicID);
    }
}
