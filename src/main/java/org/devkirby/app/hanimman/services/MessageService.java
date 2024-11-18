package org.devkirby.app.hanimman.services;

import java.util.List;

import org.devkirby.app.hanimman.dto.MessageDTO;

public interface MessageService {
    MessageDTO createMessage(String content, Long senderId, Long receiverId);

    int markMessagesAsRead(List<Long> messageIds);

    List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long receiverId);
}
