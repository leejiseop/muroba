package com.example.muroba.service;

import com.example.muroba.entity.Image;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.repository.ImageJpaRepository;
import com.example.muroba.repository.ImageRepository;
import com.example.muroba.repository.MemberRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageService {
    private final MemberRepository memberRepository;
    private final ImageRepository imageS3Repository;
    private final ImageJpaRepository imageJpaRepository;

    public Image create(MultipartFile file, Long memberId, String comment) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow( () -> new EntityNotFoundException("사용자가 존재하지 않습니다"));

        String path = imageS3Repository.save(file);

        Image image = Image.builder()
                .memberId(memberId)
                .nickname(member.getNickname())
                .country(member.getCountry())
                .comment(comment)
                .path(path)
                .fileSize(file.getSize())
                .build();

        return imageJpaRepository.save(image);
    }

    @Transactional(readOnly = true)
    public Slice<Image> getAllSlicingImages(Pageable pageable) {
        return imageJpaRepository.findAllByOrderByIdDesc(pageable);
    }

}
