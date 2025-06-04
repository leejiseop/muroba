package com.example.muroba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberProfileDto {
    private Long id;
    private String email;
    private String nickname;
    private String country;
    private int likeCount;
    private int level;
    private int point;
} 