package com.example.muroba.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LikeMember extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "to_member_id", nullable = false)
    private Member toMember;

    @ManyToOne
    @JoinColumn(name = "from_member_id", nullable = false)
    private Member fromMember;
}
