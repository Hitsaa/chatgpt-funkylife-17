package com.chatgpt.funkylife.dto.chatgpt;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class TextGenerationResponse {
    private String text;
    private HttpStatus status;
    private Integer code;
}