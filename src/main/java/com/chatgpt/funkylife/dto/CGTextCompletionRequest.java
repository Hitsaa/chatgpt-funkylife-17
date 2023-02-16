package com.chatgpt.funkylife.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CGTextCompletionRequest {
    private String prompt;
    private float temperature;
    private int maxTokens;
    private String stop;
    private int logprobs;
    private boolean echo;
    private int n;
}
