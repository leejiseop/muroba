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
    @GeneratedValue(strategy = GenerationType.UUID)
    // GenerationType.UUID으로 id를 생성하면 insert 전에 id값을 미리 알 수 있다 -> 외부 리소스 매핑시 편리 (S3)
    private UUID id;

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