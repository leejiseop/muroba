package com.example.muroba.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.muroba.entity.Answer;
import com.example.muroba.entity.LikeAnswer;
import com.example.muroba.entity.LikeMember;
import com.example.muroba.entity.LikeQuestion;
import com.example.muroba.entity.Member;
import com.example.muroba.entity.Question;
import com.example.muroba.repository.AnswerRepository;
import com.example.muroba.repository.LikeAnswerRepository;
import com.example.muroba.repository.LikeMemberRepository;
import com.example.muroba.repository.LikeQuestionRepository;
import com.example.muroba.repository.MemberRepository;
import com.example.muroba.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeQuestionRepository likeQuestionRepository;
    private final LikeAnswerRepository likeAnswerRepository;
    private final LikeMemberRepository likeMemberRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public boolean toggleLikeQuestion(Long questionId, Long memberId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return likeQuestionRepository.findByToQuestionAndFromMember(question, member)
                .map(like -> { likeQuestionRepository.delete(like); return false; })
                .orElseGet(() -> { likeQuestionRepository.save(LikeQuestion.builder().toQuestion(question).fromMember(member).build()); return true; });
    }

    @Transactional
    public boolean toggleLikeAnswer(Long answerId, Long memberId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return likeAnswerRepository.findByToAnswerAndFromMember(answer, member)
                .map(like -> { likeAnswerRepository.delete(like); return false; })
                .orElseGet(() -> { likeAnswerRepository.save(LikeAnswer.builder().toAnswer(answer).fromMember(member).build()); return true; });
    }

    @Transactional
    public boolean toggleLikeMember(Long toMemberId, Long fromMemberId) {
        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return likeMemberRepository.findByToMemberAndFromMember(toMember, fromMember)
                .map(like -> { likeMemberRepository.delete(like); return false; })
                .orElseGet(() -> { likeMemberRepository.save(LikeMember.builder().toMember(toMember).fromMember(fromMember).build()); return true; });
    }
} 