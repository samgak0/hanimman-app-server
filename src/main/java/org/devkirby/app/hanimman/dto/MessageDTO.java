package org.devkirby.app.hanimman.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO sender;
    private UserDTO receiver;
    private Boolean isRead;
}