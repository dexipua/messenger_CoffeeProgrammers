package com.messenger.dto.message;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class MessageRequest {
    @Max(1024)
    private String text;
}
