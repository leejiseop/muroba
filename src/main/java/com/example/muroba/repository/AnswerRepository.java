package com.example.muroba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByPostId(Long postId);
    List<Answer> findByUpperCommentId(Long upperCommentId);
} 