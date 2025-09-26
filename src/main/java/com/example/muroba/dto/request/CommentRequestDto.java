package com.example.muroba.dto.request;

import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank
    private Long postId;

    @NotBlank
    private Long memberId;

    @NotBlank
    private String comment;
}
