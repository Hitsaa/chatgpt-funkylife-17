package com.chatgpt.funkylife.dto.chatgpt;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class ChatgptChoice {
    private Integer index;
    private String text;

    @JsonProperty("finish_reason")
    private String finishReason;
    private ChatgptMessageDto message;
    private ChatgptMessageDto delta;
} 