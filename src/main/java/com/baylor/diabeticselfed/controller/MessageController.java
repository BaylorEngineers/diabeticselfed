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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

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



    @CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO, Principal connectedUser) {
        try {
            var sender = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            sender = userRepository.findById(sender.getId()).get();
//            User sender = userRepository.findById(messageDTO.getSenderId())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
            User receiver = userRepository.findById(messageDTO.getReceiverId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

            System.out.println("Sender:" + sender.getUsername());
            System.out.println("receiver:" + receiver.getUsername());
            System.out.println(((UsernamePasswordAuthenticationToken) connectedUser).getCredentials());

            // Check if both sender and receiver have the PATIENT role
            if (sender.getRole() == Role.PATIENT && receiver.getRole() == Role.PATIENT) {
                return new ResponseEntity<>("Patient cannot send a message to Patient", HttpStatus.BAD_REQUEST);
            }
            //System.out.println("After if");
            Message sentMessage = messageService.sendMessage(sender, receiver, messageDTO.getContent());
            //System.out.println("After calling sent");
            messageDTO.setReceiverName(sentMessage.getReceiver().getFirstname() + " " + sentMessage.getReceiver().getLastname());
            messageDTO.setSenderName(sentMessage.getSender().getFirstname() + " " + sentMessage.getSender().getLastname());
            messageDTO.setTime(sentMessage.getTimestamp());
            messageDTO.setSenderId(sentMessage.getSender().getId());

            messageDTO.setReceiverId(sentMessage.getReceiver().getId());
            // Notify WebSocket subscribers about the new message
            messagingTemplate.convertAndSend("/topic/messages/" + messageDTO.getReceiverId(), messageDTO);
            messagingTemplate.convertAndSend("/topic/messages/" + messageDTO.getSenderId(), messageDTO);
            //System.out.println("Send finish");
            return new ResponseEntity<>(sentMessage, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
    @GetMapping("/last-messages/{userId}")
    public ResponseEntity<?> getLastMessagesForEachReceiver(@PathVariable Integer userId, Principal connectedUser) {
        try {
            List<LastMessageDTO> dtos = messageService.getLastMessageForEachReceiver(userId,connectedUser);
            return ResponseEntity.ok(dtos);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
    @GetMapping("/conversation")
    public List<MessageDTO> getMessagesBetweenUsers(@RequestParam Integer userId1,
                                                 @RequestParam Integer userId2, Principal connectedUser) {
        var user1 = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        User user2 = userRepository.findById(userId2).orElseThrow();
        List<Message> messageList = messageService.getMessagesBetweenUsers(user1, user2);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message:messageList
             ) {
            if(!message.getIsRead() && message.getReceiver().getId() == user1.getId()){
                message.setIsRead(true);
                messageRepository.save(message);
            }
            MessageDTO newMessaage = new MessageDTO();
            newMessaage.setTime(message.getTimestamp());
            newMessaage.setContent(message.getContent());
            newMessaage.setSenderId(message.getSender().getId());
            newMessaage.setReceiverId(message.getReceiver().getId());
            newMessaage.setIsRead(message.getIsRead());
            messageDTOList.add(newMessaage);
        }
        messageList = messageService.getMessagesBetweenUsers(user2, user1);
        for (Message message:messageList
        ) {
            if(!message.getIsRead()){
                message.setIsRead(true);
                messageRepository.save(message);
            }
            MessageDTO newMessaage = new MessageDTO();
            newMessaage.setTime(message.getTimestamp());
            newMessaage.setContent(message.getContent());
            newMessaage.setSenderId(message.getSender().getId());
            newMessaage.setReceiverId(message.getReceiver().getId());
            newMessaage.setSenderName(message.getSender().getFirstname() + " " + message.getSender().getLastname());
            newMessaage.setReceiverName(message.getReceiver().getFirstname() + " " + message.getReceiver().getLastname());
            newMessaage.setIsRead(message.getIsRead());
            messageDTOList.add(newMessaage);

        }
        Collections.sort(messageDTOList, new Comparator<MessageDTO>() {
            @Override
            public int compare(MessageDTO m1, MessageDTO m2) {
                return m1.getTime().compareTo(m2.getTime());
            }
        });
        messageDTOList.sort(Comparator.comparing(MessageDTO::getTime));

        return messageDTOList;
    }
}
