package com.example.muroba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByUpperCommentId(Long upperCommentId);
} 