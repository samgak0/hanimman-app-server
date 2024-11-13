package org.devkirby.app.hanimman.controllers;

import java.util.List;
import java.util.Objects;

import org.devkirby.app.hanimman.dto.MessageDTO;
import org.devkirby.app.hanimman.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageCreateRequest request) {
        if (Objects.equals(request.sender, request.receiver)) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same user.");
        }
        MessageDTO savedMessage = messageService.createMessage(
                request.getContent(),
                request.getSender(),
                request.getReceiver());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        List<MessageDTO> messages = messageService.getMessagesBetweenUsers(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @Data
    public static class MessageCreateRequest {
        private String content;
        private Long sender;
        private Long receiver;
    }

}