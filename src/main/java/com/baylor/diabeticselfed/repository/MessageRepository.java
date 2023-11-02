package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT DISTINCT m.receiver.id FROM Message m WHERE m.sender.id = ?1")
    List<Integer> findDistinctReceiverIdsBySenderId(Integer senderId);

    @Query("SELECT DISTINCT m.sender.id FROM Message m WHERE m.receiver.id = ?1")
    List<Integer> findDistinctSenderIdsByReceiverId(Integer receiverId);
}
