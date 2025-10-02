package com.example.muroba.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final LikeService likeService;

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
        Member newMember = memberService.createMember(memberRequestDto);
        return ResponseEntity.ok().body(MemberResponseDto.from(newMember));
    }

}
