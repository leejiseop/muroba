package com.example.muroba.service;

import com.example.muroba.config.SecurityConfig;
import com.example.muroba.dto.request.MemberRequestDto;
import com.example.muroba.entity.Member;
import com.example.muroba.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
//    private final LikeMemberRepository likeMemberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow( () -> new EntityNotFoundException("사용자가 존재하지 않습니다"));
    }

    public Member createMember(MemberRequestDto memberRequestDto) {

        // 회원 중복 검증 -> 동일 이메일?

        Member newMember = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()))
                .nickname(memberRequestDto.getNickname())
                .country(memberRequestDto.getCountry())
                .role("ROLE_USER") // 임시
                .build();
        return memberRepository.save(newMember);
    }

    public Boolean isEmailDuplicate(String email) {

        if (email == null || email.isBlank()) return false;
        return !memberRepository.existsByEmailIgnoreCase(email.trim());

    }
//    private final PasswordEncoder passwordEncoder;

}