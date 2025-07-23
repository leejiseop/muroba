package com.example.muroba.service;

import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.repository.LikePostRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikePostRepository likePostRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Post post = Post.builder()
                .member(member)
                .fromLang(dto.getFromLang())
                .toLang(dto.getToLang())
                .content(dto.getContent())
                .build();
        Post saved = postRepository.save(post);
        return new PostResponseDto(
                saved.getId(),
                member.getId(),
                member.getNickname(),
                saved.getFromLang(),
                saved.getToLang(),
                saved.getContent(),
                saved.getCreatedAt(),
                0
        );
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(q -> new PostResponseDto(
                        q.getId(),
                        q.getMember().getId(),
                        q.getMember().getNickname(),
                        q.getFromLang(),
                        q.getToLang(),
                        q.getContent(),
                        q.getCreatedAt(),
                        likePostRepository.countByToPost(q)
                ));
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post q = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));
        return new PostResponseDto(
                q.getId(),
                q.getMember().getId(),
                q.getMember().getNickname(),
                q.getFromLang(),
                q.getToLang(),
                q.getContent(),
                q.getCreatedAt(),
                likePostRepository.countByToPost(q)
        );
    }
}