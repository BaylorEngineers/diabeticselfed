package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.LastMessageDTO;
import com.baylor.diabeticselfed.dto.MessageDTO;
import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.MessageRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.baylor.diabeticselfed.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;
    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/send")
//    public  ResponseEntity<?> sendMessage() {
//
//        return ResponseEntity.ok("Hello World");
//    }
//    @PostMapping("/send")
//    public  ResponseEntity<?> sendMessage(@RequestParam String content) {
//
//        return ResponseEntity.ok("Hello World");
//    }



    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO) {
        try {
            User sender = userRepository.findById(messageDTO.getSenderId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
            User receiver = userRepository.findById(messageDTO.getReceiverId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

            // Check if both sender and receiver have the PATIENT role
            if (sender.getRole() == Role.PATIENT && receiver.getRole() == Role.PATIENT) {
                return new ResponseEntity<>("Patient cannot send a message to Patient", HttpStatus.BAD_REQUEST);
            }

            Message sentMessage = messageService.sendMessage(sender, receiver, messageDTO.getContent());
            System.out.println("Send");
            // Notify WebSocket subscribers about the new message
            messagingTemplate.convertAndSend("/topic/messages/" + messageDTO.getReceiverId(), sentMessage);
            System.out.println("Send finish");
            return new ResponseEntity<>(sentMessage, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/last-messages/{userId}")
    public ResponseEntity<?> getLastMessagesForEachReceiver(@PathVariable Integer userId) {
        try {
            List<LastMessageDTO> dtos = messageService.getLastMessageForEachReceiver(userId);
            return ResponseEntity.ok(dtos);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/conversation")
    public List<Message> getMessagesBetweenUsers(@RequestParam Integer userId1,
                                                 @RequestParam Integer userId2) {
        User user1 = userRepository.findById(userId1).orElseThrow();
        User user2 = userRepository.findById(userId2).orElseThrow();
        return messageService.getMessagesBetweenUsers(user1, user2);
    }
}
