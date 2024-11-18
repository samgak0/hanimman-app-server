package org.devkirby.app.hanimman.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.devkirby.app.hanimman.dto.MessageDTO;
import org.devkirby.app.hanimman.entities.Message;
import org.devkirby.app.hanimman.entities.User;
import org.devkirby.app.hanimman.repositories.MessageRepository;
import org.devkirby.app.hanimman.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public MessageDTO createMessage(String content, Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID: " + receiverId));

        Message message = new Message(null, content, sender, receiver, false, LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        return modelMapper.map(savedMessage, MessageDTO.class);
    }

    @Override
    public List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        List<Message> messages = messageRepository.findMessagesBetweenUsers(senderId, receiverId);
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int markMessagesAsRead(List<Long> messageIds) {
        return messageRepository.markMessagesAsRead(messageIds);
    }
}
