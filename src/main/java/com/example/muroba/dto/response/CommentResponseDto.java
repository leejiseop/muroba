package com.example.muroba.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long memberId;
    private String nickname;
    private Long postId;
    private String comment;
    private Long upperCommentId;
    private LocalDateTime createdAt;
    private int likeCount;
} 