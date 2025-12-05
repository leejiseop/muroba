package com.example.muroba.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private Long fileSize;

}