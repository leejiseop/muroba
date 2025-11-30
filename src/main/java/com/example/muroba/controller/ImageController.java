package com.example.muroba.controller;

import com.example.muroba.dto.request.ImageRequestDto;
import com.example.muroba.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/images")
public class ImageController {
    ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestPart MultipartFile file,
                         @RequestPart ImageRequestDto imageRequestDto) {
        return imageService.create(file, imageRequestDto.getMemberId(), imageRequestDto.getComment());
    }
}