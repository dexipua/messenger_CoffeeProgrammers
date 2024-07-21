package com.messenger.dto.account;

import lombok.Data;

@Data
public class AccountResponseSimple {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
