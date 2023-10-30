package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.MessageRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.baylor.diabeticselfed.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private UserRepository userRepository;

    @PostMapping("/send")
    public Message sendMessage(@RequestParam Integer senderId,
                               @RequestParam Integer receiverId,
                               @RequestParam String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

        return messageService.sendMessage(sender, receiver, content);
    }

    @GetMapping("/conversation")
    public List<Message> getMessagesBetweenUsers(@RequestParam Integer userId1,
                                                 @RequestParam Integer userId2) {
        User user1 = userRepository.findById(userId1).orElseThrow();
        User user2 = userRepository.findById(userId2).orElseThrow();
        return messageService.getMessagesBetweenUsers(user1, user2);
    }
}
