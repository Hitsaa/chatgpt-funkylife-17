package com.chatgpt.funkylife.dto.chatgpt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class SimpleTextRequest {
    String text;
    String apiKey;
}