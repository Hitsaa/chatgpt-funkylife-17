package com.chatgpt.funkylife.dto.chatgpt;
import lombok.Data;

import java.util.List;

@Data
public class ChatgptApiResponse {
    private String id;
    private String object;
    private String model;
    private long created;
    private Object usage;
    private List<ChatgptChoice> choices;
}