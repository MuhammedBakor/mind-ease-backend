package com.mindease.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record ChatLlmDTO(
    @NotBlank(message = "user id is required")
    @JsonProperty("user_id")  
    String userId,
    @NotBlank(message = "content is required")  
    String content,
    @JsonProperty("conversation_id") 
    String conversationId
  ) {

}
