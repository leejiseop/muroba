package com.example.muroba.service;

import org.springframework.stereotype.Service;

import com.example.muroba.repository.LikePostRepository;
import com.example.muroba.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikePostRepository likePostRepository;
//    private final LikeAnswerRepository likeAnswerRepository;
//    private final LikeMemberRepository likeMemberRepository;
    private final MemberRepository memberRepository;
//    private final PostRepository postRepository;
//
//    @Transactional
//    public boolean toggleLikePost(Long postId, Long memberId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
//        return likePostRepository.findByToPostAndFromMember(post, member)
//                .map(like -> { likePostRepository.delete(like); return false; })
//                .orElseGet(() -> { likePostRepository.save(LikePost.builder().toPost(post).fromMember(member).build()); return true; });
//    }
////
////    @Transactional
////    public boolean toggleLikeAnswer(Long answerId, Long memberId) {
////        Comment comment = answerRepository.findById(answerId)
////                .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다."));
////        Member member = memberRepository.findById(memberId)
////                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
////        return likeAnswerRepository.findByToAnswerAndFromMember(comment, member)
////                .map(like -> { likeAnswerRepository.delete(like); return false; })
////                .orElseGet(() -> { likeAnswerRepository.save(LikeComment.builder().toAnswer(comment).fromMember(member).build()); return true; });
////    }
//
//    @Transactional
//    public boolean toggleLikeMember(Long toMemberId, Long fromMemberId) {
//        Member toMember = memberRepository.findById(toMemberId)
//                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
//        Member fromMember = memberRepository.findById(fromMemberId)
//                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
//        return likeMemberRepository.findByToMemberAndFromMember(toMember, fromMember)
//                .map(like -> { likeMemberRepository.delete(like); return false; })
//                .orElseGet(() -> { likeMemberRepository.save(LikeMember.builder().toMember(toMember).fromMember(fromMember).build()); return true; });
//    }
} 