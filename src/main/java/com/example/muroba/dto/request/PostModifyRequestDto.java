package com.example.muroba.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostModifyRequestDto {

    @NotBlank
    private Long memberId;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String content;

}