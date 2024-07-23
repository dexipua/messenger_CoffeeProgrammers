package com.messenger.dto.message;

import lombok.Data;

@Data
public class MessageRequest {
    private String text;
    private long chatId;
    private long senderId;
}
