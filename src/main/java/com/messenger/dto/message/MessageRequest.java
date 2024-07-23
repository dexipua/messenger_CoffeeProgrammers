package com.messenger.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank(message = "text can not be blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ,.]*$",
            message = "text must start with an uppercase letter and can contain letters, digits, spaces, and punctuation marks like commas and periods")
    private String text;
    @NotBlank(message = "chatId can not be blank")
    private long chatId;
    @NotBlank(message = "senderId can not be blank")
    private long senderId;
}
