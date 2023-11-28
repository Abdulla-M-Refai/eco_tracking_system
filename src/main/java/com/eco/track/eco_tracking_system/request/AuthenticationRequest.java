package com.eco.track.eco_tracking_system.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest
{
    @NotBlank(message = "messing username")
    @Size(max = 50, message = "username exceeds maximum length of 50 character")
    private String username;

    @NotBlank(message = "messing password")
    private String password;
}
