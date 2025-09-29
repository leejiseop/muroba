package com.example.muroba.repository;

import com.example.muroba.entity.Comment;
import com.example.muroba.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByPostIdOrderByIdDesc(Long postId);
}