package com.messenger.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email must be provided")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "Password must be provided")
    private String password;

    @NotBlank(message = "Code must be provided")
    private String code;
}
