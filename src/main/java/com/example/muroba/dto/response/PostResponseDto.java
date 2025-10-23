package com.example.muroba.dto.response;

import java.time.LocalDateTime;

import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long memberId;
    private String content;
    private String interested;
    private Long commentsCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.memberId = post.getMember().getId();
        this.content = post.getContent();
        this.interested = post.getInterested();
        this.commentsCount = post.getComments_count();
        this.likeCount = post.getLike_count();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getMember().getId(),
                post.getContent(),
                post.getInterested(),
                post.getComments_count(),
                post.getLike_count(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
