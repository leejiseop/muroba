package com.example.muroba.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String country;
    private String email;
    private Long memberId;
}
