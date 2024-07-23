package com.messenger.dto.chat;

import com.messenger.dto.account.AccountResponseSimple;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Long id;
    private List<AccountResponseSimple> accounts;
}
