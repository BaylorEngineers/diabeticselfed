package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.ChatGPTRequest;
import com.baylor.diabeticselfed.dto.ChatGPTResponse;
import com.baylor.diabeticselfed.service.ChatGPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chatgpt")
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app/")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

//    @PostMapping("/generate-response")
//    public Mono<ResponseEntity<ChatGPTResponse>> generateResponse(@RequestBody ChatGPTRequest request) {
//        return chatGPTService.executeCurlAndReturnResponse(request)
//                .map(response -> ResponseEntity.ok(new ChatGPTResponse(response)))
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
}