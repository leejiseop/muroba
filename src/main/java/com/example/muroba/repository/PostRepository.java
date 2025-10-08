package com.example.muroba.repository;

import com.example.muroba.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Slice<Post> findAllByOrderByIdDesc(Pageable pageable);
    Slice<Post> findAllByOrderByInterestedDesc(Pageable pageable); // 필터 조회
}