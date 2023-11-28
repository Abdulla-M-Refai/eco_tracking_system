package com.eco.track.eco_tracking_system.response;

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
