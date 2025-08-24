package com.example.muroba.controller;

import com.example.muroba.dto.request.LoginRequestDto;
import com.example.muroba.dto.response.LoginResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.muroba.dto.MemberProfileDto;
import com.example.muroba.dto.request.MemberCreateRequestDto;
import com.example.muroba.dto.response.MemberCreateResponseDto;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.MemberService;

import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final LikeService likeService;

    //
    @GetMapping("/test")
    @ResponseBody // 뷰 이름이 아니라 응답 텍스트로 보내기
    public String testtest() {
        return "ok";
    }


        // 회원 등록 - PRG 패턴 적용해보자
    @PostMapping("/sign-up")
    public void createMember(@RequestBody MemberCreateRequestDto requestDto) {

        // service 단
        // 이메일 유저명 등 중복체크
        // dto를 이용해서 user 객체를 만든다
        // 비밀번호는 passwordEncoder.encode(requestDto.getPassword())
        // user 객체를 save하고 종료
        MemberCreateResponseDto responseDto = memberService.createMember(requestDto);

//        return "redirect:/board/write";
    }

    // 회원 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> signIn(@RequestBody LoginRequestDto requestDto) {

        // 로그인 성공 시 토큰 발급
        LoginResponseDto responseDto = memberService.signIn(requestDto);

//        HttpHeaders headers = new HttpHeaders();
//        headers.set(JwtUtil.AUTHORIZATION_HEADER, tokenResponseDto.getAccessToken());
//        headers.set(JwtUtil.REFRESH_TOKEN_HEADER, tokenResponseDto.getRefreshToken());
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));


        return ResponseEntity.ok(responseDto);
    }

    // 회원 조회
    @GetMapping("/member/{memberId}")
    public MemberProfileDto getProfile(@PathVariable Long memberId) {
        return memberService.getProfile(memberId);
    }

    // 회원 좋아요 토글
    @PostMapping("/{toMemberId}/like")
    public boolean toggleLike(@PathVariable Long toMemberId, @RequestParam Long fromMemberId) {
        return likeService.toggleLikeMember(toMemberId, fromMemberId);
    }

    // 회원 탈퇴
    @DeleteMapping("/member/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
} 