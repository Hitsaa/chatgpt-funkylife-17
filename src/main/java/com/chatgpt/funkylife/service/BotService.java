package com.chatgpt.funkylife.service;

import com.chatgpt.funkylife.dto.request.BotRequest;
import com.chatgpt.funkylife.dto.response.ChatGptResponse;

public interface BotService {

    ChatGptResponse askQuestion(BotRequest botRequest);
}
