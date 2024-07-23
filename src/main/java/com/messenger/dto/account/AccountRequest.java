package com.messenger.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRequest {
    @NotBlank(message = "firstName can not be blank")
    @Pattern(regexp = "[A-Z][a-z]*",
            message = "firstName must start with an uppercase letter followed by lowercase letters")
    private String firstName;
    @NotBlank(message = "lastName can not be blank")
    @Pattern(regexp = "[A-Z][a-z]*",
            message = "lastName must start with an uppercase letter followed by lowercase letters")
    private String lastName;
    @NotBlank(message = "description can not be blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ,.]*$",
            message = "description must start with an uppercase letter and can contain letters, digits, spaces, and punctuation marks like commas and periods")
    private String description;
}
