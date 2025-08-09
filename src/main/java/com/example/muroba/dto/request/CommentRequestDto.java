package com.example.muroba.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long memberId;
    private Long postId;
    private String comment;
    private Long upperCommentId; // 대댓글일 경우 상위 댓글 ID, 아니면 null
} 