package com.example.muroba.controller;

import com.example.muroba.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {
    private final MemberService memberService;
    private final EmailService emailService;



}
