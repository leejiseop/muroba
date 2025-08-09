package com.example.muroba.controller;

import com.example.muroba.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    // 전체 게시글 조회
    @GetMapping("/posts")
    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        return postService.getPosts(pageable);
    }

    // 선택한 게시글 조회
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.getPostById(id);
        model.addAttribute("test", post);
        return "post";
    }


    // 게시글 생성
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequestDto dto) {


        return postService.createPost(dto);
    }

    // 게시글 수정 페이지
//    @PostMapping("/modify/{id}")
//    public String modifyPost() {
//        return "postModify";
//    }

    // 게시글 수정 - 보안?
//    @PostMapping("/modify/{id}")
//    public String updatePost() {
//        return "redirect:/posts";
//    }

    // 게시글 삭제
//    @GetMapping("/delete")
//    public String deletePost() {
////        postService.
//        return "temp";
//    }

    // 게시글 좋아요 토글
    @PostMapping("/{postId}/like")
    public boolean toggleLike(@PathVariable Long postId, @RequestParam Long memberId) {
        return likeService.toggleLikePost(postId, memberId);
    }
} 