package com.example.muroba.service;

import com.example.muroba.dto.QuestionRequestDto;
import com.example.muroba.dto.QuestionResponseDto;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Question;
import com.example.muroba.repository.LikeQuestionRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final LikeQuestionRepository likeQuestionRepository;

    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Question question = Question.builder()
                .member(member)
                .fromLang(dto.getFromLang())
                .toLang(dto.getToLang())
                .content(dto.getContent())
                .build();
        Question saved = questionRepository.save(question);
        return new QuestionResponseDto(
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
    public Page<QuestionResponseDto> getQuestions(Pageable pageable) {
        return questionRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(q -> new QuestionResponseDto(
                        q.getId(),
                        q.getMember().getId(),
                        q.getMember().getNickname(),
                        q.getFromLang(),
                        q.getToLang(),
                        q.getContent(),
                        q.getCreatedAt(),
                        likeQuestionRepository.countByToQuestion(q)
                ));
    }
} 