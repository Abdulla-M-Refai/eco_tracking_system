package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolarIrradianceRequest
{
    @NotBlank(message = "missing date")
    @Pattern(regexp = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "date must be in the YYYY-MM-DD format")
    private String date;

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
