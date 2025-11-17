package com.example.muroba.controller;

import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    // 테스트용
    @GetMapping("/threads")
    public String threads(Model model) {

        // Session Login
        // JWT로 로그인해도 동일하게 사용 가능한가?
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//
//        String name = authentication.getName();
//        String role = auth.getAuthority(); // ADMIN 접근 가능 페이지 확인 등 -> admin이 아니면 return 등 ...
//
//        System.out.println(name); // lkj0245@naver.com
//        System.out.println(role); // ROLE_USER

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

//    @GetMapping("/post-test")
//    public String postTest(Model model) {
//        return "test";
//    }

}
