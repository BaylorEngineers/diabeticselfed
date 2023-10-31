package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.LastMessageDTO;
import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.MessageRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    public Message sendMessage(User sender, User receiver, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(new Date());

        // Manage bidirectional relationship
        sender.getSentMessages().add(message);
        receiver.getReceivedMessages().add(message);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(User user1, User user2) {
        return messageRepository.findBySenderAndReceiver(user1, user2);
    }

    public List<LastMessageDTO> getLastMessageForEachReceiver(Integer senderId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user found"));

        List<Integer> receiverIds = messageRepository.findDistinctReceiverIdsBySenderId(senderId);
        List<LastMessageDTO> lastMessagesDTOs = new ArrayList<>();

        for (Integer receiverId : receiverIds) {
            User receiver = userRepository.findById(receiverId).orElse(null); // Fetch the full receiver details
            if (receiver != null) {
                List<Message> conversation = getMessagesBetweenUsers(sender, receiver);
                if (!conversation.isEmpty()) {
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
