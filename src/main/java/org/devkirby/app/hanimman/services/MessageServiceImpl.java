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
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @AllArgsConstructor
    @Data
    public static class MessageResponse {
        private MessageDTO data;
        private String messageId;
        private boolean pushSuccess;
    }

    @Override
    public MessageResponse createMessage(String content, Integer senderId, Integer receiverId)
            throws FirebaseMessagingException {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID: " + receiverId));

        Message message = new Message(null, content, sender, receiver, false, null, LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        String registrationToken = receiver.getToken();

        String messageId = null;
        boolean pushSuccess = false;

        if (registrationToken != null && registrationToken.length() > 10) {
            com.google.firebase.messaging.Message firebaseMessage = com.google.firebase.messaging.Message.builder()
                    .putData("id", savedMessage.getId().toString())
                    .putData("content", content)
                    .putData("senderId", senderId.toString())
                    .putData("senderName", sender.getUsername())
                    .putData("receiverId", receiverId.toString())
                    .putData("receiverName", receiver.getUsername())
                    .putData("createdAt", savedMessage.getCreatedAt().toString())
                    .setToken(registrationToken)
                    .build();

            messageId = FirebaseMessaging.getInstance().send(firebaseMessage);
            pushSuccess = true;
            message.setPushToken(messageId);
            savedMessage = messageRepository.save(message);
        }

        MessageDTO savedMessageDTO = modelMapper.map(savedMessage, MessageDTO.class);
        return new MessageResponse(savedMessageDTO, messageId, pushSuccess);
    }

    @Override
    public List<MessageDTO> getMessagesBetweenUsers(Integer senderId, Integer receiverId) {
        List<Message> messages = messageRepository.findMessagesBetweenUsers(senderId, receiverId);
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int markMessagesAsRead(List<Integer> messageIds) {
        return messageRepository.markMessagesAsRead(messageIds);
    }
}
