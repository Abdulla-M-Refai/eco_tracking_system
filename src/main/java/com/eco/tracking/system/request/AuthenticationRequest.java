package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest
{
    @NotBlank(message = "missing username")
    @Size(max = 50, message = "username exceeds maximum length of 50 character")
    private String username;

    @NotBlank(message = "missing password")
    private String password;
}
