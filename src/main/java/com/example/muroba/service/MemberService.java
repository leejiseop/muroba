package com.example.muroba.service;

import com.example.muroba.dto.MemberProfileDto;
import com.example.muroba.dto.request.LoginRequestDto;
import com.example.muroba.dto.response.LoginResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.MemberPoint;
import com.example.muroba.repository.LikeMemberRepository;
import com.example.muroba.repository.MemberPointRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.dto.request.MemberCreateRequestDto;
import com.example.muroba.dto.response.MemberCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LikeMemberRepository likeMemberRepository;
    private final MemberPointRepository memberPointRepository;
//    private final PasswordEncoder passwordEncoder;

    // 멤버 생성
    @Transactional
    public MemberCreateResponseDto createMember(MemberCreateRequestDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
//                .password(passwordEncoder.encode(dto.getPassword()))
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .country(dto.getCountry())
                .isBlocked(false)
                .build();
        Member saved = memberRepository.save(member);
        return new MemberCreateResponseDto(
                saved.getId(),
                saved.getEmail(),
                saved.getNickname(),
                saved.getCountry()
        );
    }

    // 멤버 로그인
    public LoginResponseDto signIn(LoginRequestDto requestDto) {
        // 이메일로 멤버 조회
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        // 비밀번호 확인
        if (!member.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = "dummyAccessToken"; // 실제 토큰 생성 로직 필요
        String refreshToken = "dummyRefreshToken"; // 실제 토큰 생성 로직 필요

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(member.getNickname())
                .country(member.getCountry())
                .email(member.getEmail())
                .memberId(member.getId())
                .build();
    }

    // 전체 멤버 조회

    // 리프레쉬 토큰 갱신
} 