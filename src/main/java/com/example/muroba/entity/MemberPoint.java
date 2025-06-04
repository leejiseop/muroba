package com.example.muroba.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberPoint extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int point;
}
