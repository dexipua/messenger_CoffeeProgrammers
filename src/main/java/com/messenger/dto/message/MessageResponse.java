package com.messenger.dto.message;

import com.messenger.dto.account.AccountResponseSimple;
import lombok.Data;

@Data
public class MessageResponse {
    private Long id;
    private String text;
    private AccountResponseSimple sender;
    private String date;
}
