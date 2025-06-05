package com.example.muroba.dto.request;

import lombok.Getter;

@Getter
public class QuestionRequestDto {
    private Long memberId;
    private String fromLang;
    private String toLang;
    private String content;
} 