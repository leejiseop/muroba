package com.example.muroba.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String country;
} 