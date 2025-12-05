package com.example.muroba.repository;

import com.example.muroba.entity.Image;
import com.example.muroba.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageJpaRepository extends JpaRepository<Image, UUID> {
    Slice<Image> findAllByOrderByIdDesc(Pageable pageable);
}
