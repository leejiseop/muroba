package com.example.muroba.repository;

import com.example.muroba.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageJpaRepository extends JpaRepository<Image, UUID> {
}
