package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.*;

import com.eco.tracking.system.validator.Annotation.UniqueEmail;
import com.eco.tracking.system.validator.Annotation.UniqueUsername;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest
{
    @NotBlank(message = "missing full name")
    @Size(max = 50, message = "full name exceeds maximum length of 50 character")
    private String fullName;

    @NotBlank(message = "missing username")
    @Size(max = 50, message = "username exceeds maximum length of 50 character")
    @UniqueUsername
    private String username;

    @NotBlank(message = "missing password")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
        message = "invalid password"
    )
    private String password;

    @NotBlank(message = "missing email")
    @Email(message = "invalid email")
    @Size(max = 255, message = "email exceeds maximum length of 255 character")
    @UniqueEmail
    private String email;
}
