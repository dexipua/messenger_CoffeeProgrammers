package com.messenger.dto.account;

import com.messenger.dto.chat.ChatResponse;
import com.messenger.models.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountResponse {
    private Long id;
    private String email;
    private Role role;
    private String firstName;
    private String lastName;
    private String description;
    private String status;
    private List<AccountResponseSimple> accounts = new ArrayList<>();
    private List<ChatResponse> chats = new ArrayList<>();
}
