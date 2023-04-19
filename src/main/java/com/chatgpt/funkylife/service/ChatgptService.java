package com.chatgpt.funkylife.service;

import com.chatgpt.funkylife.dto.chatgpt.SimpleTextRequest;
import com.chatgpt.funkylife.dto.chatgpt.TextGenerationRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface ChatgptService {
    Object generateText(TextGenerationRequest textGenerationRequest);

    Object generateTextIdeas(SimpleTextRequest simpleTextRequest);

    StreamingResponseBody generateTextIdeasWithStream(SimpleTextRequest simpleTextRequest);

    SseEmitter streamText(SimpleTextRequest simpleTextRequest);
}
