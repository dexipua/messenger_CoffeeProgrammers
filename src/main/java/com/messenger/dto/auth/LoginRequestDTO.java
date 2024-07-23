package com.messenger.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email must be provided")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "Password must be provided")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}$",
            message =
                    "Password must be minimum 6 characters long, " +
                            "containing at least one digit, " +
                            "one uppercase letter, " +
                            "and one lowercase letter")
    private String password;

    @NotBlank(message = "Code must be provided")
    private String code;
}
