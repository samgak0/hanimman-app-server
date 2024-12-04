package org.devkirby.app.hanimman.services;

import java.util.List;

import org.devkirby.app.hanimman.dto.MessageDTO;
import org.devkirby.app.hanimman.services.MessageServiceImpl.MessageResponse;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface MessageService {
    MessageResponse createMessage(String content, Integer senderId, Integer receiverId)
            throws FirebaseMessagingException;

    public int markMessagesAsRead(List<Integer> messageIds) throws FirebaseMessagingException;

    List<MessageDTO> getMessagesBetweenUsers(Integer senderId, Integer receiverId);
}
