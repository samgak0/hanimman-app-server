package org.devkirby.app.hanimman.services;

import java.util.List;

import org.devkirby.app.hanimman.dto.MessageDTO;

public interface MessageService {
    MessageDTO createMessage(String content, Integer senderId, Integer receiverId);

    int markMessagesAsRead(List<Integer> messageIds);

    List<MessageDTO> getMessagesBetweenUsers(Integer senderId, Integer receiverId);
}
