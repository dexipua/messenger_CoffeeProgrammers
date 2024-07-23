package com.messenger.dto.account;

import com.messenger.models.Role;
import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String email;
    private Role role;
    private String firstName;
    private String lastName;
    private String description;
    private String status;
}
