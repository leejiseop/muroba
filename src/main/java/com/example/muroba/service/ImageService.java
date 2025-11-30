package com.example.muroba.service;

import com.example.muroba.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageService {
    ImageRepository imageRepository;

    public String create(MultipartFile file, Long memberId, String comment) {
        return imageRepository.save(file);
    }
}