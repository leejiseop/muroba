package com.example.muroba.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.muroba.dto.request.CommentRequestDto;
import com.example.muroba.dto.response.CommentResponseDto;
import com.example.muroba.service.CommentService;
import com.example.muroba.service.LikeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final CommentService answerService;
    private final LikeService likeService;

    // 댓글 등록
    @PostMapping("/post/comment/{postId}")
    public CommentResponseDto createAnswer(@RequestBody CommentRequestDto dto, @RequestParam Long postId) {
        return answerService.createAnswer(dto);
    }

    // 댓글 조회
    @GetMapping("/post/{postId}")
    public List<CommentResponseDto> getAnswersByPost(@PathVariable Long postId) {
        return answerService.getAnswersByPost(postId);
    }

    // 댓글 수정

    // 댓글 삭제

    // 댓글의 대댓글 조회
    @GetMapping("/child/{upperCommentId}")
    public List<CommentResponseDto> getChildAnswers(@PathVariable Long upperCommentId) {
        return answerService.getChildAnswers(upperCommentId);
    }

    // 댓글 좋아요 토글
    @PostMapping("/{answerId}/like")
    public boolean toggleLike(@PathVariable Long answerId, @RequestParam Long memberId) {
        return likeService.toggleLikeAnswer(answerId, memberId);
    }
} 