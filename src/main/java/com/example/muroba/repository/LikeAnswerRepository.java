package com.example.muroba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.Answer;
import com.example.muroba.entity.LikeAnswer;
import com.example.muroba.entity.Member;

public interface LikeAnswerRepository extends JpaRepository<LikeAnswer, Long> {
    Optional<LikeAnswer> findByToAnswerAndFromMember(Answer toAnswer, Member fromMember);
    int countByToAnswer(Answer toAnswer);
} 