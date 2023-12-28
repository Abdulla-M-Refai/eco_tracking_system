package com.eco.tracking.system.request;

import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest
{
    @NotBlank(message = "missing full name")
    @Size(max = 50, message = "full name exceeds maximum length of 50 character")
    private String fullName;

    @NotBlank(message = "missing username")
    @Size(max = 50, message = "username exceeds maximum length of 50 character")
    private String username;

    @NotBlank(message = "missing email")
    @Email(message = "invalid email")
    @Size(max = 255, message = "email exceeds maximum length of 255 character")
    private String email;
}
