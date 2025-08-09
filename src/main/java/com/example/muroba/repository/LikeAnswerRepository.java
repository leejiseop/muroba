package com.example.muroba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.Comment;
import com.example.muroba.entity.LikeComment;
import com.example.muroba.entity.Member;

public interface LikeAnswerRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByToAnswerAndFromMember(Comment toAnswer, Member fromMember);
    int countByToAnswer(Comment toAnswer);
} 