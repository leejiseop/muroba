package com.example.muroba.service;

import com.example.muroba.dto.request.LoginRequestDto;
import com.example.muroba.dto.request.MemberRequestDto;
import com.example.muroba.dto.response.LoginResponseDto;
import com.example.muroba.dto.response.MemberResponseDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.repository.LikeMemberRepository;
import com.example.muroba.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LikeMemberRepository likeMemberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow( () -> new EntityNotFoundException("사용자가 존재하지 않습니다"));
    }

    public Member createMember(MemberRequestDto memberRequestDto) {
        Member newMember = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .nickname(memberRequestDto.getNickname())
                .country(memberRequestDto.getCountry())
                .build();
        return memberRepository.save(newMember);
    }
//    private final PasswordEncoder passwordEncoder;


    // 회원가입 프론트 연결



    // 전체 멤버 조회

    // 리프레쉬 토큰 갱신
} 