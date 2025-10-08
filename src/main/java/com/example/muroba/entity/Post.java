package com.example.muroba.entity;

import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.PostResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String interested;

    @Column(nullable = false)
    private Long comments_count;

    @Column(nullable = false)
    private Long like;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void changeContent(String changed_content) {
        this.content = changed_content;
    }
    
    public Long like() {
        this.like += 1;
        // 이거 엔티티 내에서 this. 꼭 써야하나? 개념 정확하게 정리하고 직접 강의식으로 설명해보기
        return like;
    }

    public Long unlike() {
        if (0 < this.like) this.like -= 1;
        return like;
    }
}