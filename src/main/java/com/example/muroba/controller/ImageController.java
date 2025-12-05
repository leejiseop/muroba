package com.example.muroba.controller;

import com.example.muroba.dto.request.ImageRequestDto;
import com.example.muroba.dto.response.ImageResponseDto;
import com.example.muroba.dto.response.MemberResponseDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Image;
import com.example.muroba.entity.Member;
import com.example.muroba.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api")
public class ImageController {
    ImageService imageService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ImageResponseDto> upload(@RequestPart MultipartFile file,
                                                   @RequestPart ImageRequestDto imageRequestDto) {

        Image image = imageService.create(file, imageRequestDto.getMemberId(), imageRequestDto.getComment());

        return ResponseEntity.ok().body(ImageResponseDto.from(image));
    }

    @GetMapping("/slicing-images")
    public ResponseEntity<Slice<ImageResponseDto>> getAllSlicingImagess(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok().body(imageService.getAllSlicingImages(pageable)
                .map(ImageResponseDto::new));
    }
}