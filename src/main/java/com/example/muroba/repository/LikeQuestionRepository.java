package com.example.muroba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.LikeQuestion;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Question;

public interface LikeQuestionRepository extends JpaRepository<LikeQuestion, Long> {
    Optional<LikeQuestion> findByToQuestionAndFromMember(Question toQuestion, Member fromMember);
    int countByToQuestion(Question toQuestion);
} 