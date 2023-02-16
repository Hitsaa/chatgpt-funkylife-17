package com.chatgpt.funkylife.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatgpt.funkylife.dto.CGTextCompletionRequest;
import com.chatgpt.funkylife.dto.request.BotRequest;
import com.chatgpt.funkylife.dto.response.ChatGptResponse;
import com.chatgpt.funkylife.service.BotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat-gpt")
@RequiredArgsConstructor
public class BotController {
    
    private final BotService botService;

    @PostMapping("/create")
    public ChatGptResponse sendMessage(@RequestBody BotRequest botRequest) {
        return botService.askQuestion(botRequest);
    }
}
