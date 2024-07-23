package com.messenger.dto.message;

import com.messenger.dto.account.AccountResponse;
import com.messenger.dto.chat.ChatResponse;
import lombok.Data;

@Data
public class MessageResponse {
    private Long id;
    private String text;
    private AccountResponse sender;
    private ChatResponse chat;
    private String date;
}
