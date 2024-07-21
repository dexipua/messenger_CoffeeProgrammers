package com.messenger.dto.account;

import lombok.Data;

@Data
public class AccountRequest {
    private String firstName;
    private String lastName;
    private String description;
    private String password;
}
