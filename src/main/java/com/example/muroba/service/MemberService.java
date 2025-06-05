package com.example.muroba.service;

import com.example.muroba.dto.MemberProfileDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.MemberPoint;
import com.example.muroba.repository.LikeMemberRepository;
import com.example.muroba.repository.MemberPointRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.dto.request.MemberCreateRequestDto;
import com.example.muroba.dto.response.MemberCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LikeMemberRepository likeMemberRepository;
    private final MemberPointRepository memberPointRepository;

    @Transactional(readOnly = true)
    public MemberProfileDto getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        int likeCount = likeMemberRepository.countByToMember(member);
        MemberPoint point = memberPointRepository.findByMember(member).orElse(null);
        int level = point != null ? point.getLevel() : 0;
        int pt = point != null ? point.getPoint() : 0;
        return new MemberProfileDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getCountry(),
                likeCount,
                level,
                pt
        );
    }

    @Transactional
    public MemberCreateResponseDto createMember(MemberCreateRequestDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
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

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        memberRepository.delete(member);
    }
} 