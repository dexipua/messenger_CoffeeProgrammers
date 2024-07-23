package com.messenger.dto.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChatRequest {
    @NotBlank(message = "chat name cannot be blank")
    @NotNull(message = "chat name cannot be null")
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ,.]*$",
            message = "chat name must start with an uppercase letter and can contain letters, digits, spaces, and punctuation marks like commas and periods")
    private String name;
}
