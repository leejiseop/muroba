package com.example.muroba.service;

import com.example.muroba.dto.request.CommentRequestDto;
import com.example.muroba.dto.response.CommentResponseDto;
import com.example.muroba.entity.Comment;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Post;
import com.example.muroba.repository.AnswerRepository;
import com.example.muroba.repository.LikeAnswerRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeAnswerRepository likeAnswerRepository;

    // 댓글 등록
    @Transactional
    public CommentResponseDto createAnswer(CommentRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .comment(dto.getComment())
                .upperCommentId(dto.getUpperCommentId())
                .build();
        Comment saved = answerRepository.save(comment);
        return new CommentResponseDto(
                saved.getId(),
                member.getId(),
                member.getNickname(),
                post.getId(),
                saved.getComment(),
                saved.getUpperCommentId(),
                saved.getCreatedAt(),
                0
        );
    }

    // 게시글의 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAnswersByPost(Long postId) {
        List<Comment> answers = answerRepository.findByPostId(postId);
        return answers.stream().map(a -> new CommentResponseDto(
                a.getId(),
                a.getMember().getId(),
                a.getMember().getNickname(),
                a.getPost().getId(),
                a.getComment(),
                a.getUpperCommentId(),
                a.getCreatedAt(),
                likeAnswerRepository.countByToAnswer(a)
        )).collect(Collectors.toList());
    }

    // 댓글의 대댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getChildAnswers(Long upperCommentId) {
        List<Comment> answers = answerRepository.findByUpperCommentId(upperCommentId);
        return answers.stream().map(a -> new CommentResponseDto(
                a.getId(),
                a.getMember().getId(),
                a.getMember().getNickname(),
                a.getPost().getId(),
                a.getComment(),
                a.getUpperCommentId(),
                a.getCreatedAt(),
                likeAnswerRepository.countByToAnswer(a)
        )).collect(Collectors.toList());
    }
} 