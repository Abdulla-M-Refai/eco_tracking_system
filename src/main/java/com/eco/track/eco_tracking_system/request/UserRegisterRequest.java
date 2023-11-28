package com.eco.track.eco_tracking_system.request;

import lombok.*;
import jakarta.validation.constraints.*;

import com.eco.track.eco_tracking_system.validator.Annotation.UniqueEmail;
import com.eco.track.eco_tracking_system.validator.Annotation.UniqueUsername;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest
{
    @NotBlank(message = "messing username")
    @Size(max = 50, message = "username exceeds maximum length of 50 character")
    @UniqueUsername
    private String username;

    @NotBlank(message = "messing password")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
        message = "invalid password"
    )
    private String password;

    @NotBlank(message = "messing email")
    @Email(message = "invalid email")
    @Size(max = 255, message = "email exceeds maximum length of 255 character")
    @UniqueEmail
    private String email;
}
