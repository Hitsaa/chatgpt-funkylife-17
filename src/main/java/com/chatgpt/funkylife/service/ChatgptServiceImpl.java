package com.chatgpt.funkylife.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chatgpt.funkylife.config.ChatgptProperties;
import com.chatgpt.funkylife.dto.chatgpt.*;
import com.chatgpt.funkylife.enums.ChatgptRole;
import com.chatgpt.funkylife.exception.AppException;
import com.chatgpt.funkylife.utils.ErrorMessage;
import com.chatgpt.funkylife.utils.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class ChatgptServiceImpl implements ChatgptService {
    private final ChatgptProperties chatgptProperties;
    private final RestTemplate restTemplate;


    
    ChatgptServiceImpl(ChatgptProperties chatgptProperties, RestTemplate restTemplate) {
        this.chatgptProperties = chatgptProperties;
        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofMinutes(5))
                .build();
    }

    @Override
    public Object generateText(TextGenerationRequest textGenerationRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers = this.generateHeaders(headers, textGenerationRequest.getApiKey());

        List<ChatgptMessageDto> messageList = new ArrayList<ChatgptMessageDto>();
        String content = "10 " + textGenerationRequest.getIdea() + " keywords in the " + textGenerationRequest.getTopic() + " topic with difficulty level " + textGenerationRequest.getDifficultyLevel();
        messageList.add(new ChatgptMessageDto(ChatgptRole.USER.getAction(), content));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", chatgptProperties.getModelGpt3_5Turbo().getName());
        requestBody.put("messages", messageList);
        requestBody.put("temperature", chatgptProperties.getTemperature());
        requestBody.put("stream", chatgptProperties.getStream());

        HttpEntity<String> request;
        ChatgptApiResponse response = new ChatgptApiResponse();
        try {
            request = new HttpEntity<>(new ObjectMapper().writeValueAsString(requestBody), headers);
            response = restTemplate
                        .exchange(chatgptProperties.getModelGpt3_5Turbo().getUrl(), HttpMethod.POST, request, ChatgptApiResponse.class)
                        .getBody();
        } catch (JsonProcessingException e) {
            
            e.printStackTrace();
        }

        return response;
        // if (response.getStatusCode() == HttpStatus.OK) {
        //     ChatgptApiResponse responseBody = response.getBody();
        //     if (responseBody != null && !responseBody.getChoices().isEmpty()) {
        //         return responseBody.getChoices().get(0).getText();
        //     }
        // }

        // return "";
    }

    @Override
    public Object generateTextIdeas(SimpleTextRequest simpleTextRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers = this.generateHeaders(headers, simpleTextRequest.getApiKey());
        List<ChatgptMessageDto> messageList = new ArrayList<ChatgptMessageDto>();
        messageList.add(new ChatgptMessageDto(ChatgptRole.USER.getAction(), simpleTextRequest.getText()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", chatgptProperties.getModelGpt3_5Turbo().getName());
        requestBody.put("messages", messageList);
        requestBody.put("temperature", chatgptProperties.getTemperature());
        requestBody.put("stream", chatgptProperties.getStream());

        HttpEntity<String> request;
        ChatgptApiResponse response = new ChatgptApiResponse();
        try {
            request = new HttpEntity<>(new ObjectMapper().writeValueAsString(requestBody), headers);
            ResponseEntity<ChatgptApiResponse> responseEntity = restTemplate
                    .exchange(chatgptProperties.getModelGpt3_5Turbo().getUrl(), HttpMethod.POST, request, ChatgptApiResponse.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK) {
                response = responseEntity.getBody();
            }
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        if(response != null) {
            TextGenerationResponse textGenerationResponse = new TextGenerationResponse();
            textGenerationResponse.setStatus(HttpStatus.OK)
                                  .setCode(200)
                                  .setText(response.getChoices().get(0).getMessage().getContent());
            return textGenerationResponse;
        }
        else {
            throw new AppException(ErrorMessage.QUERY_NOT_APPROPRIATE, HttpStatus.BAD_REQUEST);
        }
    }

    private HttpHeaders generateHeaders(HttpHeaders headers, String apiKey) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String openaiApiKey = StringUtils.isStringNotNullEmpty(apiKey) ? apiKey : chatgptProperties.getApiKey();
        headers.setBearerAuth(openaiApiKey);
        return headers;
    }

    @Override
    public StreamingResponseBody generateTextIdeasWithStream(SimpleTextRequest simpleTextRequest) {
        HttpHeaders headers = new HttpHeaders();

        headers = this.generateHeaders(headers, simpleTextRequest.getApiKey());
        List<ChatgptMessageDto> messageList = new ArrayList<ChatgptMessageDto>();
        messageList.add(new ChatgptMessageDto(ChatgptRole.USER.getAction(), simpleTextRequest.getText()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", chatgptProperties.getModelGpt3_5Turbo().getName());
        requestBody.put("messages", messageList);
        requestBody.put("temperature", chatgptProperties.getTemperature());
        requestBody.put("stream", true);
        String url = chatgptProperties.getModelGpt3_5Turbo().getUrl();

        HttpEntity<String> request;
        try {
            request = new HttpEntity<>(new ObjectMapper().writeValueAsString(requestBody), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return outputStream -> {
            try {
                ResponseEntity<String> responseEntity = restTemplate
                        .exchange(url, HttpMethod.POST, request, String.class);

                String responseBody = responseEntity.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                try (BufferedReader reader = new BufferedReader(new StringReader(responseBody))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.startsWith("data:")) {
                            continue;
                        }
                        String json = line.substring(6);
                        ChatgptApiResponse response = objectMapper.readValue(json, ChatgptApiResponse.class);
                        if(StringUtils.isStringNotNullEmpty(response.getChoices().get(0).getDelta().getContent())) {
                            String content = response.getChoices().get(0).getDelta().getContent();
                            outputStream.write(content.getBytes());
                        }

                        outputStream.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public SseEmitter streamText(SimpleTextRequest simpleTextRequest) {
        SseEmitter emitter = new SseEmitter();
        // start a background thread to send SSE messages to the client
        CompletableFuture.runAsync(() -> {
            try {
                // send SSE messages to the client
                this.generateTextIdeasWithLiveStream(simpleTextRequest, emitter);
                // complete the emitter when finished sending messages
                emitter.complete();
            } catch (Exception e) {
                // handle errors and exceptions
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    private void generateTextIdeasWithLiveStream(SimpleTextRequest simpleTextRequest, SseEmitter sseEmitter) {
        HttpHeaders headers = new HttpHeaders();

        headers = this.generateHeaders(headers, simpleTextRequest.getApiKey());
        List<ChatgptMessageDto> messageList = new ArrayList<ChatgptMessageDto>();
        messageList.add(new ChatgptMessageDto(ChatgptRole.USER.getAction(), simpleTextRequest.getText()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", chatgptProperties.getModelGpt3_5Turbo().getName());
        requestBody.put("messages", messageList);
        requestBody.put("temperature", chatgptProperties.getTemperature());
        requestBody.put("stream", true);
        String url = chatgptProperties.getModelGpt3_5Turbo().getUrl();

        HttpEntity<String> request;
        try {
            request = new HttpEntity<>(new ObjectMapper().writeValueAsString(requestBody), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

            try {
                ResponseEntity<String> responseEntity = restTemplate
                        .exchange(url, HttpMethod.POST, request, String.class);

                String responseBody = responseEntity.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                try (BufferedReader reader = new BufferedReader(new StringReader(responseBody))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.startsWith("data:")) {
                            continue;
                        }
                        String json = line.substring(6);
                        ChatgptApiResponse response = objectMapper.readValue(json, ChatgptApiResponse.class);
                        if(StringUtils.isStringNotNullEmpty(response.getChoices().get(0).getDelta().getContent())) {
                            String content = response.getChoices().get(0).getDelta().getContent();
                            sseEmitter.send(SseEmitter.event().data(content));
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
