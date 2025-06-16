package com.example.muroba.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.muroba.service.AnswerService;
import com.example.muroba.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("questions", questionService.getQuestions(PageRequest.of(0, 10)).getContent());
        return "main";
    }

    @GetMapping("/questions")
    public String moreQuestions(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("questions", questionService.getQuestions(PageRequest.of(page, 10)).getContent());
        return "fragments/questionList :: questionList";
    }

    @GetMapping("/question/{id}")
    public String questionPage(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        model.addAttribute("comments", answerService.getAnswersByQuestion(id));
        return "question";
    }

    @GetMapping("/write")
    public String writePage() {
        return "write";
    }
} 