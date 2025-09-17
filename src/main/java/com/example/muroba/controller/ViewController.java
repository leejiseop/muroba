package com.example.muroba.controller;

import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    // 테스트용
    @GetMapping("/threads")
    public String threads(Model model) {

        return "1_threads";
    }

    @GetMapping("/messages")
    public String messages(Model model) {

        return "2_messages";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {

        return "3_mypage";
    }

    @GetMapping("/mypage_modify")
    public String mypage_modify(Model model) {

        return "3_mypage_modify";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {

        return "4_gallery";
    }

    @GetMapping("/post-test")
    public String postTest(Model model) {
        return "test";
    }

    // 선택한 게시글 조회
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.getPostById(id);
        model.addAttribute("test", post);
        return "test";
    }
}
