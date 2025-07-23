package com.example.muroba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.muroba.entity.LikePost;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByToPostAndFromMember(Post toPost, Member fromMember);
    int countByToPost(Post toPost);
} 