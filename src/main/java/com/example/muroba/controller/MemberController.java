package com.example.muroba.controller;

import com.example.muroba.config.RedisUtil;
import com.example.muroba.dto.request.EmailRequestDto;
import com.example.muroba.dto.request.MemberRequestDto;
import com.example.muroba.dto.response.MemberResponseDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.service.CommentService;
import com.example.muroba.service.LikeService;
import com.example.muroba.service.MemberService;
import com.example.muroba.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final LikeService likeService;
    private final RedisUtil redisUtil;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok().body(
                memberService.getAllMembers().stream().map(MemberResponseDto::new).collect(Collectors.toList())
        );
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {
        Member findMember = memberService.getMemberById(memberId);
        return ResponseEntity.ok().body(MemberResponseDto.from(findMember));
    }

    @PostMapping("/members/create")
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto) {

        String verified = redisUtil.getData("AUTHCOMPLETE-" + memberRequestDto.getEmail());
        if (verified == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "인증이 만료되었습니다.");

        Member newMember = memberService.createMember(memberRequestDto);
        return ResponseEntity.ok().body(MemberResponseDto.from(newMember));
    }

    @GetMapping("/members/email")
    public ResponseEntity<Map<String, Object>> isEmailDuplicate(@RequestParam String email) {

        System.out.println("/members/email - isEmailDuplicate");
        System.out.println(email);

        boolean available = memberService.isEmailDuplicate(email);
        return ResponseEntity.ok().body(Map.of(
                "email", email,
                "available", available
        ));

    }
}
