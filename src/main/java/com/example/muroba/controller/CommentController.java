package com.example.muroba.controller;

import com.example.muroba.dto.request.CommentRequestDto;
import com.example.muroba.dto.request.PostRequestDto;
import com.example.muroba.dto.response.CommentResponseDto;
import com.example.muroba.dto.response.PostResponseDto;
import com.example.muroba.entity.Comment;
import com.example.muroba.entity.Post;
import com.example.muroba.service.CommentService;
import com.example.muroba.service.MemberService;
import com.example.muroba.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    @PostMapping("/comments/{postId}/create")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentService.createComment(postId, commentRequestDto);
        return ResponseEntity.ok().body(CommentResponseDto.from(comment));
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok().body(commentService.getAllComments(postId));
    }

    @DeleteMapping("/comments/delete/{commentId}")
    public ResponseEntity<String> deletePost(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }

}
