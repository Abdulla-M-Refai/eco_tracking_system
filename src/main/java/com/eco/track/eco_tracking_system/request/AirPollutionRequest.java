package com.eco.track.eco_tracking_system.request;

import lombok.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirPollutionRequest
{
    @NotBlank(message = "missing latitude")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "latitude must be a valid number")
    private String latitude;

    @NotBlank(message = "missing longitude")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "longitude must be a valid number")
    private String longitude;

    public double getParsedLatitude()
    {
        return Double.parseDouble(latitude);
    }

    public double getParsedLongitude()
    {
        return Double.parseDouble(longitude);
    }
}
