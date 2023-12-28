package com.eco.tracking.system.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse
{
    String token;

    String refreshToken;
}
