package com.example.muroba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.LikeMember;
import com.example.muroba.entity.Member;

public interface LikeMemberRepository extends JpaRepository<LikeMember, Long> {
    Optional<LikeMember> findByToMemberAndFromMember(Member toMember, Member fromMember);
    int countByToMember(Member toMember);
} 