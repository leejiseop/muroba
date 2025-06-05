package com.example.muroba.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.muroba.dto.request.QuestionRequestDto;
import com.example.muroba.dto.response.QuestionResponseDto;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final LikeService likeService;

    @PostMapping
    public QuestionResponseDto createQuestion(@RequestBody QuestionRequestDto dto) {
        return questionService.createQuestion(dto);
    }

    @GetMapping
    public Page<QuestionResponseDto> getQuestions(Pageable pageable) {
        return questionService.getQuestions(pageable);
    }

    @PostMapping("/{questionId}/like")
    public boolean toggleLike(@PathVariable Long questionId, @RequestParam Long memberId) {
        return likeService.toggleLikeQuestion(questionId, memberId);
    }
} 