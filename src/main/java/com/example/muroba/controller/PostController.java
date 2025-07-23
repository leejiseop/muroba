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

import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    // 전체 질문 조회
    @GetMapping
    public Page<PostResponseDto> getPosts(Pageable pageable) {
        return postService.getPosts(pageable);
    }

    // 선택한 질문 조회



    // 질문 생성
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequestDto dto) {
        return postService.createPost(dto);
    }

    // 질문 수정



    // 질문 삭제



    // 질문 좋아요 토글
    @PostMapping("/{postId}/like")
    public boolean toggleLike(@PathVariable Long postId, @RequestParam Long memberId) {
        return likeService.toggleLikePost(postId, memberId);
    }
} 