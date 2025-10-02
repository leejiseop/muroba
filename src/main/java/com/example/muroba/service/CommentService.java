package com.example.muroba.service;

import com.example.muroba.dto.request.CommentRequestDto;
import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.CommentResponseDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Comment;
import com.example.muroba.entity.Post;
import com.example.muroba.repository.CommentRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Comment createComment(Long postId, CommentRequestDto commentRequestDto) {
        Comment comment = Comment.builder()
                .post(postRepository.findById(postId)
                        .orElseThrow( () -> new EntityNotFoundException("게시글이 존재하지 않습니다.")))
                .member(memberRepository.findById(commentRequestDto.getMemberId())
                        .orElseThrow( () -> new EntityNotFoundException("사용자가 존재하지 않습니다.")))
                .comment(commentRequestDto.getComment())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findAllByPostIdOrderByIdDesc(postId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다")
        );
        commentRepository.delete(comment);
    }

}
