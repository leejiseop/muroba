package com.example.muroba.dto.response;

import com.example.muroba.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailAuthResponseDto {
    private String email;
    private Boolean isValid;
}
