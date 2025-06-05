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

    @PostMapping
    public AnswerResponseDto createAnswer(@RequestBody AnswerRequestDto dto) {
        return answerService.createAnswer(dto);
    }

    @GetMapping("/question/{questionId}")
    public List<AnswerResponseDto> getAnswersByQuestion(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestion(questionId);
    }

    @GetMapping("/child/{upperCommentId}")
    public List<AnswerResponseDto> getChildAnswers(@PathVariable Long upperCommentId) {
        return answerService.getChildAnswers(upperCommentId);
    }

    @PostMapping("/{answerId}/like")
    public boolean toggleLike(@PathVariable Long answerId, @RequestParam Long memberId) {
        return likeService.toggleLikeAnswer(answerId, memberId);
    }
} 