package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.LastMessageDTO;
import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.MessageRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    public Message sendMessage(User sender, User receiver, String content) {
        System.out.println("Start Service");
        Message message = new Message();
        message.setSender(sender);
        System.out.println("finish setSender");
        message.setReceiver(receiver);
        System.out.println("finish setReceiver");
        message.setContent(content);
        message.setTimestamp(new Date());

        // Manage bidirectional relationship
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
        sender.getSentMessages().add(message);
        System.out.println("Reach");
        receiver.getReceivedMessages().add(message);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(User user1, User user2) {
        return messageRepository.findBySenderAndReceiver(user1, user2);
    }

    public List<LastMessageDTO> getLastMessageForEachReceiver(Integer senderId, Principal connectedUser) {
        User sender = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        sender = userRepository.findById(sender.getId()).get();
        List<Integer> receiverIds = messageRepository.findDistinctReceiverIdsBySenderId(senderId);
        System.out.println("receiverIds:" + receiverIds);
        List<Integer> senderIds = messageRepository.findDistinctSenderIdsByReceiverId(senderId);
        System.out.println("senderIds:" + senderIds);
        List<LastMessageDTO> lastMessagesDTOs = new ArrayList<>();
        List<Integer> uniqueIdsList = Stream.concat(receiverIds.stream(), senderIds.stream())
                .distinct()
                .collect(Collectors.toList());
        for (Integer receiverId : uniqueIdsList) {
            User receiver = userRepository.findById(receiverId).orElse(null); // Fetch the full receiver details
            if (receiver != null) {
                System.out.println("receiver found");
                List<Message> conversation = getMessagesBetweenUsers(sender, receiver);
                List<Message> conversation1 = getMessagesBetweenUsers(receiver,sender );
                for (Message message: conversation1
                     ) {
//                    System.out.println(message.toString());
                    conversation.add(message);
                }

                if (!conversation.isEmpty()) {
                    System.out.println("conversation found");
                    Message lastMessage = conversation.get(conversation.size() - 1);

                    LastMessageDTO dto = new LastMessageDTO();
                    dto.setReceiverId(receiverId);
                    dto.setReceiverFirstName(receiver.getFirstname());
                    dto.setReceiverLastName(receiver.getLastname());
                    dto.setContent(lastMessage.getContent());
                    dto.setTimestamp(lastMessage.getTimestamp());

                    lastMessagesDTOs.add(dto);
                }
            }
        }

        return lastMessagesDTOs;
    }

}
