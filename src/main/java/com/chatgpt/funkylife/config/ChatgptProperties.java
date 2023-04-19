package com.chatgpt.funkylife.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "chatgpt")
public class ChatgptProperties {
    private final ModelDaVinci modelDaVinci = new ModelDaVinci();
    private final ModelGpt3_5Turbo modelGpt3_5Turbo = new ModelGpt3_5Turbo();
    private String apiKey;
    private long maxToken;
    private Double temperature;
    private Double topP;
    private String mediaType;
    private Boolean stream;
    
    @Getter
    @Setter
    public static class ModelDaVinci {
        private String name;
        private String url;
    }

    @Getter
    @Setter
    public static class ModelGpt3_5Turbo {
        private String name;
        private String url;
    }

}
