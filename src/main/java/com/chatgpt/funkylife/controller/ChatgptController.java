package com.chatgpt.funkylife.controller;

import com.chatgpt.funkylife.dto.chatgpt.SimpleTextRequest;
import com.chatgpt.funkylife.dto.chatgpt.TextGenerationRequest;
import com.chatgpt.funkylife.service.ChatgptService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


@RestController
@RequestMapping("/api/v1/suggestions")
public class ChatgptController {
    private final ChatgptService chatgptService;

    ChatgptController(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    @PostMapping("/page")
    public Object generateIdeas(@RequestBody TextGenerationRequest botRequest) {
        return chatgptService.generateText(botRequest);
    }

    @PostMapping("/text")
    public Object generateText(@RequestBody SimpleTextRequest simpleTextRequest) {
        return chatgptService.generateTextIdeas(simpleTextRequest);
    }

    @PostMapping(value = "/text-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public StreamingResponseBody generateTextWithStreams(@RequestBody SimpleTextRequest simpleTextRequest, HttpServletResponse response) {
        response.setHeader("Content-Type", MediaType.APPLICATION_STREAM_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        return chatgptService.generateTextIdeasWithStream(simpleTextRequest);
    }

    @PostMapping(value = "/stream-text")
    public SseEmitter generateTextWithLiveStreams(@RequestBody SimpleTextRequest simpleTextRequest) {
        return chatgptService.streamText(simpleTextRequest);
    }

}
