package com.example.muroba.service;

import com.example.muroba.dto.request.PostModifyRequestDto;
import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.repository.LikePostRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikePostRepository likePostRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPagingPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Slice<PostResponseDto> getAllSlicingPosts(Pageable pageable) {
//        Slice<Post> postList = postRepository.findAll(pageable);
        return postRepository.findAllByOrderByIdDesc(pageable)
                .map(PostResponseDto::new);
    }

    @Transactional
    public Post createPost(PostRequestDto postRequestDto) {
        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .interested(postRequestDto.getInterested())
                .member(memberRepository.findById(postRequestDto.getMemberId())
                        .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다.")))
                .comments_count(0L)
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        postRepository.delete(post);
    }

    @Transactional
    public Post modifyPost(Long postId, PostModifyRequestDto postModifyRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow( () -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        post.changeContent(postModifyRequestDto.getContent());

        return post;
    }
}