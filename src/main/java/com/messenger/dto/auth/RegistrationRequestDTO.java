package com.messenger.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationRequestDTO {

    @NotBlank(message = "Email must be provided")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "Password must be provided")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}$",
            message = "Password must be minimum 6 characters long, containing at least one digit, one uppercase letter, and one lowercase letter")
    private String password;

    @NotBlank(message = "Role must be provided")
    private String role;

    @NotBlank(message = "firstName can not be blank")
    @Pattern(regexp = "[A-Z][a-z]*",
            message = "lastName must start with an uppercase letter followed by lowercase letters")
    private String firstName;

    @NotBlank(message = "lastName can not be blank")
    @Pattern(regexp = "[A-Z][a-z]*",
            message = "lastName must start with an uppercase letter followed by lowercase letters")
    private String lastName;

    @NotBlank(message = "description can not be blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ,.]*$", message = "description must start with an uppercase letter and can contain letters, digits, spaces, and punctuation marks like commas and periods")
    private String description;
}
