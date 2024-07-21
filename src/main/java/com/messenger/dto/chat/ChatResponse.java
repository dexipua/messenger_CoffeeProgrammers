package com.messenger.dto.chat;

import com.messenger.models.Account;
import com.messenger.models.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Long id;
    private List<Message> messages;
    private List<Account> accounts;
}
