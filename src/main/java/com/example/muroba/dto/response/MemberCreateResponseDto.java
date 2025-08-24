package com.example.muroba.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String country;
}