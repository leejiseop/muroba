package com.example.muroba.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.muroba.dto.MemberProfileDto;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final LikeService likeService;

    @GetMapping("/{memberId}")
    public MemberProfileDto getProfile(@PathVariable Long memberId) {
        return memberService.getProfile(memberId);
    }

    @PostMapping("/{toMemberId}/like")
    public boolean toggleLike(@PathVariable Long toMemberId, @RequestParam Long fromMemberId) {
        return likeService.toggleLikeMember(toMemberId, fromMemberId);
    }
} 