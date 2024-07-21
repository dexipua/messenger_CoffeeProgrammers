package com.messenger.dto.chat;

import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.dto.message.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Long id;
    private List<MessageResponse> messages;
    private List<AccountResponseSimple> accounts;
}
