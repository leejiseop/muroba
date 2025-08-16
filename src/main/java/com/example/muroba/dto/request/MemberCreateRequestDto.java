package com.example.muroba.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
public class MemberCreateRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String country;
}