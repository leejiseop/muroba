package com.example.muroba.controller;

import com.example.muroba.dto.request.PostModifyRequestDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.PostService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final LikeService likeService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/paging-posts")
    public ResponseEntity<Page<PostResponseDto>> getAllPagingPosts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok().body(postService.getAllPagingPosts(pageable)
                .map(PostResponseDto::new));
    }

    @GetMapping("/slicing-posts")
    public ResponseEntity<Slice<PostResponseDto>> getAllSlicingPosts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok().body(postService.getAllSlicingPosts(pageable)
                .map(PostResponseDto::new));
    }

    @PostMapping("/posts/create")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        Post post = postService.createPost(postRequestDto);
        return ResponseEntity.ok().body(PostResponseDto.from(post));
    }

    @DeleteMapping("/posts/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }

    @PutMapping("/posts/modify/{postId}")
    public ResponseEntity<PostResponseDto> modifyPost(@PathVariable Long postId, @RequestBody PostModifyRequestDto postModifyRequestDto) {
        Post post = postService.modifyPost(postId, postModifyRequestDto);
        return ResponseEntity.ok().body(PostResponseDto.from(post));
    }

//
//    // 게시글 좋아요 토글
//    @PostMapping("/{postId}/like")
//    public boolean toggleLike(@PathVariable Long postId, @RequestParam Long memberId) {
//        return likeService.toggleLikePost(postId, memberId);
//    }
}