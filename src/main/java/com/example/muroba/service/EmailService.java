package com.example.muroba.service;

import com.example.muroba.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    // https://notavoid.tistory.com/102

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public String createAuthCode() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

    public String sendAuthCode(@Email @NotBlank String email) {

        String authCode = createAuthCode();
        sendEmail(email, authCode);

        return authCode;
    }

    private void sendEmail(String email, String authCode) {
        String subject = "[서비스명] 이메일 인증번호 안내";
        String content = """
            <div style='font-family: Arial; font-size: 15px'>
              <p>안녕하세요 👋</p>
              <p>요청하신 이메일 인증번호는 아래와 같습니다:</p>
              <h2 style='color: #3366cc;'>%s</h2>
              <p>본 인증번호는 5분간 유효합니다.</p>
            </div>
        """.formatted(authCode);

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new IllegalStateException("이메일 전송 실패", e);
        }

    }

}
