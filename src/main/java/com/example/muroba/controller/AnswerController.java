package com.example.muroba.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.muroba.dto.request.AnswerRequestDto;
import com.example.muroba.dto.response.AnswerResponseDto;
import com.example.muroba.service.AnswerService;
import com.example.muroba.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final LikeService likeService;

    // 댓글 등록
    @PostMapping
    public AnswerResponseDto createAnswer(@RequestBody AnswerRequestDto dto) {
        return answerService.createAnswer(dto);
    }

    // 댓글 조회
    @GetMapping("/question/{questionId}")
    public List<AnswerResponseDto> getAnswersByQuestion(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestion(questionId);
    }

    // 댓글의 대댓글 조회
    @GetMapping("/child/{upperCommentId}")
    public List<AnswerResponseDto> getChildAnswers(@PathVariable Long upperCommentId) {
        return answerService.getChildAnswers(upperCommentId);
    }

    // 댓글 좋아요 토글
    @PostMapping("/{answerId}/like")
    public boolean toggleLike(@PathVariable Long answerId, @RequestParam Long memberId) {
        return likeService.toggleLikeAnswer(answerId, memberId);
    }
} 