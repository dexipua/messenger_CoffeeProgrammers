package com.messenger.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private final Long userId;
    private final String lastName;
    private final String firstName;
    private final String email;
    private final String token;
    private final String Role;
}
