package com.example.muroba.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LearnLang extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String learnLang;
}
