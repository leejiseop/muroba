package com.example.muroba.dto;

import lombok.Getter;

@Getter
public class AnswerRequestDto {
    private Long memberId;
    private Long questionId;
    private String comment;
    private Long upperCommentId; // 대댓글일 경우 상위 댓글 ID, 아니면 null
} 