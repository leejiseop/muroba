package com.example.muroba.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long memberId;
    private String fromLang;
    private String toLang;
    private String content;
}