package com.example.muroba.service;

import com.example.muroba.dto.request.LoginRequestDto;
import com.example.muroba.dto.response.LoginResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.repository.LikeMemberRepository;
import com.example.muroba.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LikeMemberRepository likeMemberRepository;
//    private final PasswordEncoder passwordEncoder;


    // 전체 멤버 조회

    // 리프레쉬 토큰 갱신
} 