package com.mindease.services;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import com.mindease.DTO.ChatLlmDTO;


@Service
public class ChatService {
 
    private final WebClient webClient = WebClient.create();

    public String chat(ChatLlmDTO chatReq){
        String url = "https://mindease-ai-integration.onrender.com/v1/chat";
       
        return webClient.post()
                        .uri(url)
                        .bodyValue(chatReq)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }
}
