package com.example.muroba.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {
    private Long id;
    private Long memberId;
    private String nickname;
    private String fromLang;
    private String toLang;
    private String content;
    private LocalDateTime createdAt;
    private int likeCount;
} 