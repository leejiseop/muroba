package com.example.muroba.service;

import com.example.muroba.dto.request.AnswerRequestDto;
import com.example.muroba.dto.response.AnswerResponseDto;
import com.example.muroba.entity.Answer;
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
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeAnswerRepository likeAnswerRepository;

    // 댓글 등록
    @Transactional
    public AnswerResponseDto createAnswer(AnswerRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));
        Answer answer = Answer.builder()
                .member(member)
                .post(post)
                .comment(dto.getComment())
                .upperCommentId(dto.getUpperCommentId())
                .build();
        Answer saved = answerRepository.save(answer);
        return new AnswerResponseDto(
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

    // 질문의 댓글 조회
    @Transactional(readOnly = true)
    public List<AnswerResponseDto> getAnswersByPost(Long postId) {
        List<Answer> answers = answerRepository.findByPostId(postId);
        return answers.stream().map(a -> new AnswerResponseDto(
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
    public List<AnswerResponseDto> getChildAnswers(Long upperCommentId) {
        List<Answer> answers = answerRepository.findByUpperCommentId(upperCommentId);
        return answers.stream().map(a -> new AnswerResponseDto(
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