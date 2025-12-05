package com.example.muroba.dto.response;

import com.example.muroba.entity.Image;
import com.example.muroba.entity.Member;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {

    private Long id;
    private Long memberId;
    private String nickname;
    private String country;
    private String comment;
    private String path;
    private Long fileSize;

    public ImageResponseDto(Image image) {
        this.id = image.getId();
        this.memberId = image.getMemberId();
        this.nickname = image.getNickname();
        this.country = image.getCountry();
        this.comment = image.getComment();
        this.path = image.getPath();
        this.fileSize = image.getFileSize();
    }

    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(
                image.getId(),
                image.getMemberId(),
                image.getNickname(),
                image.getCountry(),
                image.getComment(),
                image.getPath(),
                image.getFileSize()
        );
    }
}