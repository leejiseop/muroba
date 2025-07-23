package com.example.muroba.controller;

import com.example.muroba.dto.request.LoginRequestDto;
import com.example.muroba.dto.response.LoginResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final LikeService likeService;

    // 회원 등록
    @PostMapping("/sign-up")
    public ResponseEntity<MemberCreateResponseDto> createMember(@RequestBody MemberCreateRequestDto requestDto) {
        MemberCreateResponseDto responseDto = memberService.createMember(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 회원 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> signIn(@RequestBody LoginRequestDto requestDto) {

        LoginResponseDto responseDto = memberService.signIn(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    // 회원 조회
    @GetMapping("/{memberId}")
    public MemberProfileDto getProfile(@PathVariable Long memberId) {
        return memberService.getProfile(memberId);
    }

    // 회원 좋아요 토글
    @PostMapping("/{toMemberId}/like")
    public boolean toggleLike(@PathVariable Long toMemberId, @RequestParam Long fromMemberId) {
        return likeService.toggleLikeMember(toMemberId, fromMemberId);
    }

    // 회원 탈퇴
    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
} 