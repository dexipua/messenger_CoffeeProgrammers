package com.messenger.dto.message;

import com.messenger.dto.account.AccountResponse;
import lombok.Data;

@Data
public class MessageResponse {
    private Long id;
    private String text;
    private AccountResponse accountResponse;
    private String date;
}
