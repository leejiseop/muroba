package com.example.muroba.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long memberId;
    private String nickname;
    private String fromLang;
    private String toLang;
    private String content;
    private LocalDateTime createdAt;
    private int likeCount;
} 