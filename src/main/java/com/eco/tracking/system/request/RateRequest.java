package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateRequest
{
    @NotBlank(message = "missing value")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "value must be a valid number")
    private String rate;

    public float getParsedRate()
    {
        return Float.parseFloat(rate);
    }
}
