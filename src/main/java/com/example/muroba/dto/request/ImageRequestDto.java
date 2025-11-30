package com.example.muroba.dto.request;

import com.example.muroba.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDto {
    private Long memberId;
    private String comment;
}
