package com.example.muroba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.muroba.dto.MemberProfileDto;
import com.example.muroba.dto.request.MemberCreateRequestDto;
import com.example.muroba.dto.response.MemberCreateResponseDto;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final LikeService likeService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberCreateResponseDto> createMember(@RequestBody MemberCreateRequestDto requestDto) {
        MemberCreateResponseDto responseDto = memberService.createMember(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping("/{memberId}")
    public MemberProfileDto getProfile(@PathVariable Long memberId) {
        return memberService.getProfile(memberId);
    }

    @PostMapping("/{toMemberId}/like")
    public boolean toggleLike(@PathVariable Long toMemberId, @RequestParam Long fromMemberId) {
        return likeService.toggleLikeMember(toMemberId, fromMemberId);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
} 