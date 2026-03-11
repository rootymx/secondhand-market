package com.example.demo.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessage {
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;

    public enum MessageType {
        TEXT, IMAGE, SYSTEM
    }
}