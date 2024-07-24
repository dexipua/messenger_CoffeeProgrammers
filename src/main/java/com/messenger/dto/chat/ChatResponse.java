package com.messenger.dto.chat;

import com.messenger.dto.account.AccountResponseSimple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class ChatResponse {
    private Long id;
    private String name;
    private List<AccountResponseSimple> accounts;
}
