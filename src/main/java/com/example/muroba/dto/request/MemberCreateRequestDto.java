package com.example.muroba.dto.request;

import lombok.Getter;

@Getter
public class MemberCreateRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String country;
}