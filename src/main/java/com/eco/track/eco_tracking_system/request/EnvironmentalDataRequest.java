package com.eco.track.eco_tracking_system.request;

import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.eco.track.eco_tracking_system.entity.Enum.DataSource;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentalDataRequest
{
    @NotBlank(message = "missing profile id")
    @Pattern(regexp = "\\d+", message = "profile id must be a valid number")
    private String profileID;

    @NotNull(message = "missing data source")
    private DataSource dataSource;

    @NotBlank(message = "missing data type")
    @Size(max = 50, message = "data type exceeds maximum length of 50 character")
    private String dataType;

    @NotBlank(message = "missing value")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "value must be a valid number")
    private String value;

    @NotBlank(message = "missing unit")
    @Size(max = 50, message = "unit exceeds maximum length of 20 character")
    private String unit;

    @NotNull(message = "Time must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @PastOrPresent(message = "Time must be in the past or present")
    private LocalDateTime time;

    @NotBlank(message = "missing latitude")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "latitude must be a valid number")
    private String latitude;

    @NotBlank(message = "missing longitude")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "longitude must be a valid number")
    private String longitude;

    public long getParsedProfileID()
    {
        return Long.parseLong(profileID);
    }

    public float getParsedValue()
    {
        return Float.parseFloat(value);
    }

    public double getParsedLatitude()
    {
        return Double.parseDouble(latitude);
    }

    public double getParsedLongitude()
    {
        return Double.parseDouble(longitude);
    }
}
