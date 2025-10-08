package com.example.muroba.controller;

import com.example.muroba.dto.request.EmailRequestDto;
import com.example.muroba.service.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {
    private final MemberService memberService;
    private final EmailService emailService;
    private final JavaMailSender javaMailSender; // 테스트 끝나고 지우기

    @PostMapping("/email/sendauth")
    public ResponseEntity<Map<String, Object>> sendauth(@RequestBody EmailRequestDto emailRequestDto) {
        String AuthCode = emailService.sendAuthCode(emailRequestDto.getEmail());

        // redis 저장 -> 5분 후 만료 구현

        return ResponseEntity.ok(Map.of(
                "message", "인증번호가 이메일로 전송되었습니다.",
                "email", emailRequestDto.getEmail(),
                "authCode", AuthCode // 테스트용으로만 반환
        ));
    }
}
