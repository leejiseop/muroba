package com.example.muroba.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class JwtToken {
    private String grantType; // JWT 인증 타입, Bearer 인증 방식 사용
    private String accessToken;
    private String refreshToken;
}
