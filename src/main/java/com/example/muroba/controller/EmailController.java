package com.example.muroba.controller;

import com.example.muroba.config.RedisUtil;
import com.example.muroba.dto.request.EmailAuthRequestDto;
import com.example.muroba.dto.request.EmailRequestDto;
import com.example.muroba.dto.response.EmailAuthResponseDto;
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

    private final RedisUtil redisUtil;

    @PostMapping("/email/sendauth")
    public ResponseEntity<Map<String, Object>> sendauth(@RequestBody EmailRequestDto emailRequestDto) {
        String authCode = emailService.sendAuthCode(emailRequestDto.getEmail());
        redisUtil.setDataExpire("AUTH-" + emailRequestDto.getEmail(), authCode, 60 * 5L);

        return ResponseEntity.ok(Map.of(
                "message", "인증번호가 이메일로 전송되었습니다.",
                "email", emailRequestDto.getEmail(),
                "authCode", authCode // 테스트용으로 임시 반환
        ));
    }

    @PostMapping("/email/checkauth")
    public ResponseEntity<EmailAuthResponseDto> checkauth(@RequestBody EmailAuthRequestDto emailAuthRequestDto) {
        String email = emailAuthRequestDto.getEmail();
        String authCode = emailAuthRequestDto.getAuth_code();
        String redis_authCode = redisUtil.getData("AUTH-" + email);

        Boolean isValid = redis_authCode != null && redis_authCode.equals(authCode);
        EmailAuthResponseDto emailAuthResponseDto = new EmailAuthResponseDto(email, isValid);

        if (isValid) {
            redisUtil.deleteData("AUTH-" + email);
            redisUtil.setDataExpire("AUTHCOMPLETE-" + email, "done", 60 * 1L);
        }

        return ResponseEntity.ok().body(emailAuthResponseDto);
    }
}
