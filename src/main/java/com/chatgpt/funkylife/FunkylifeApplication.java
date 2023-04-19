package com.chatgpt.funkylife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.chatgpt.funkylife.config.ChatgptProperties;

@SpringBootApplication
@EnableConfigurationProperties(ChatgptProperties.class)
public class FunkylifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunkylifeApplication.class, args);
	}

}
