package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationalResourceRequest
{
    @NotBlank(message = "missing title")
    @Size(max = 255, message = "title exceeds maximum length of 255 character")
    private String title;

    @NotBlank(message = "missing url")
    @Pattern(
        regexp = "^(http|https)://[a-zA-Z0-9-.]+\\.[a-zA-Z]{2,}(?:/[^\\s]*)?$",
        message = "Invalid URL format"
    )
    private String url;
}
